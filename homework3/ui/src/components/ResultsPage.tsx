import React, { Component } from "react";
import { RouterProps } from "react-router";
import { Container, Dimmer, Header, Loader } from "semantic-ui-react";
import { Button, Col, ListGroup, Row } from "react-bootstrap";
import axios from "axios";
import { getConnectionsForBus, getConnectionsForBusStation } from "../services/busNetworkServices";
import { BusConnectionDto, BusDto, BusStationConnectionDto, BusStationDto } from "../api.types";
import GoogleMapReact from 'google-map-react';

const cancelTokenSource = axios.CancelToken.source();
const { REACT_APP_MAPS_KEY: mapsKey } = process.env;
const newYorkCoordinates = {
    center: {
        lat: 40.948788179193485,
        lng: -73.61595153808594
    },
    zoom: 8
};

interface Props extends RouterProps {

}

interface State {
    selected?: {
        id: number,
        longitude?: string,
        latitude?: string
    },
    data: {
        value: number,
        name: string,
        type: "bus" | "busStation",
        longitude?: string,
        latitude?: string
    },
    foundData: Array<BusDto|BusStationDto>,
    isFetching: boolean,
    mapsApi?: any,
    mapInstance?: any,
    marker?: any
}

class ResultsPage extends Component<Props, State> {

    constructor(props: Props) {
        super(props);

        const { history } = props;
        const { state } = history.location;

        if (state !== undefined && state !== null) {

            const { selected }: any = history.location.state;

            if (selected === undefined || selected === null) {
                history.push("/");
            } else {
                this.state = {
                    data: selected,
                    foundData: [],
                    isFetching: true
                }

                this.getData(selected);
            }
        } else {
            history.push("/");
        }
    }

    getData = (data: any) => {
        if (data.type === "bus") {
            getConnectionsForBus(data.value, cancelTokenSource)
                .then((response: BusConnectionDto) => {
                    this.setState({
                        foundData: response.busStations,
                        isFetching: false
                    });
                })
        } else {
            getConnectionsForBusStation(data.value, cancelTokenSource)
                .then((response: BusStationConnectionDto) => {
                    this.setState({
                        foundData: response.buses,
                        isFetching: false
                    });
                })
        }
    }

    onListSelectOption = (selectedOption: BusDto|BusStationDto) => {
        if ((selectedOption as BusStationDto).longitude) {

            const { mapsApi, mapInstance, marker: prevMarker } = this.state;

            const myLatlng = new mapsApi.LatLng(
                (selectedOption as BusStationDto).latitude,
                (selectedOption as BusStationDto).longitude
            );

            if (prevMarker !== undefined && prevMarker !== null) {
                prevMarker.setMap(null);
            }

            const marker = new mapsApi.Marker({
                position: myLatlng
            });

            marker.setMap(mapInstance);

            this.setState({
                selected: {
                    id: selectedOption.id,
                    longitude: (selectedOption as BusStationDto).longitude,
                    latitude: (selectedOption as BusStationDto).latitude
                },
                marker: marker
            })
        } else {
            this.setState({
                selected: {
                    id: selectedOption.id
                }
            })
        }
    }

    apiIsLoaded = (mapInstance: any, mapsApi: any) => {

        const { data } = this.state;
        this.setState({
            mapsApi: mapsApi,
            mapInstance: mapInstance
        });

        if (data.longitude && data.latitude) {
            const myLatlng = new mapsApi.LatLng(
                data.latitude,
                data.longitude
            );

            const marker = new mapsApi.Marker({
                position: myLatlng
            });

            marker.setMap(mapInstance);

            this.setState({
                marker: marker
            })
        }
    }

    goToSearchPage = () => {
        const { history } = this.props;

        history.push("/")
    }

    render() {
        return (
            <Container className="h-100">
                <Row className="h-100 align-items-center">
                    {this.renderListWithMap()}
                </Row>
            </Container>
        );
    }

    renderListWithMap = (): React.ReactNode => {

        const { foundData, isFetching } = this.state;

        return (
            <React.Fragment>
                <Col md={6}>
                    { foundData.length > 0 || isFetching
                        ? this.renderList()
                        : this.renderNoResults() }
                    <Button className="w-100" onClick={this.goToSearchPage}>Go back to search</Button>
                </Col>
                <Col md={6}>
                    <div style={{ height: '500px', width: '100%' }}>
                        <GoogleMapReact
                            bootstrapURLKeys={{ key: mapsKey as string }}
                            defaultCenter={newYorkCoordinates.center}
                            defaultZoom={newYorkCoordinates.zoom}
                            yesIWantToUseGoogleMapApiInternals
                            onGoogleApiLoaded={({ map, maps }) => this.apiIsLoaded(map, maps)}
                        >
                        </GoogleMapReact>
                    </div>
                </Col>
            </React.Fragment>
        )
    }

    renderList = (): React.ReactNode => {

        const { foundData, isFetching, selected, data } = this.state;

        const mappedData = foundData.map(data => {
            return (
                <ListGroup.Item
                    key={data.id}
                    active={selected && selected.id === data.id}
                    onClick={() => this.onListSelectOption(data)}
                >
                    {data.name}
                </ListGroup.Item>
            )
        });

        return (
            <div style={{ backgroundColor: "white", paddingTop: "10px", marginBottom: "10px" }}>
                {
                    !isFetching
                        ? <Header as='h1'>Showing results for: {data.name}</Header>
                        : ""
                }
                <ListGroup
                    style={{
                        height: "400px",
                        overflowY: "auto",
                        backgroundColor: "white",
                        borderTop: "1px solid lightgray"
                    }}
                >
                    {
                        !isFetching
                            ? mappedData
                            : (
                                <Dimmer active inverted>
                                    <Loader inverted>Loading</Loader>
                                </Dimmer>
                            )
                    }
                </ListGroup>
            </div>
        )
    }

    renderNoResults = (): React.ReactNode => {
        const { data: { name } } = this.state;
        return (
            <div style={{ height: "470px", backgroundColor: "white", paddingTop: "2rem", marginBottom: "10px" }}>
                <Header as='h1'>No results for {name}.</Header>
            </div>
        )
    }
}

export default ResultsPage;