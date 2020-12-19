import axios from "axios";
import React, { Component } from "react";
import { Button, Col, Container, Row } from "react-bootstrap";
import { Dropdown, DropdownOnSearchChangeData, DropdownProps } from "semantic-ui-react";
import { BusDto, BusStationDto, DropdownOption, SearchResponse } from "../api.types";
import { debounce } from 'lodash';
import { searchBusData } from "../services/searchServices";
import { RouterProps } from "react-router";

const cancelTokenSource = axios.CancelToken.source();

interface State {
    searchOptions: Array<DropdownOption>,
    selectedValue?: {
        value: number,
        type: "bus"|"busStation"
    },
    isFetching: boolean,
    busStations: Array<BusStationDto>,
    buses: Array<BusDto>
}

interface Props extends RouterProps {

}

class SearchPage extends Component<Props, State> {

    constructor(props: Props) {
        super(props);

        this.state = {
            searchOptions: [],
            busStations: [],
            buses: [],
            isFetching: false
        }
    }

    onSearchChange = debounce((e: React.SyntheticEvent<HTMLElement>, { searchQuery }: DropdownOnSearchChangeData) => {

        if (searchQuery !== "") {
            this.setState({
                isFetching: true
            });

            searchBusData(searchQuery, cancelTokenSource)
                .then((response: SearchResponse) => {
                    const buses: Array<BusDto> = response.buses;
                    const busStations: Array<BusStationDto> = response.busStations;

                    const searchOptions: Array<DropdownOption> = [];

                    const mappedBuses: Array<DropdownOption> = buses.map((bus: BusDto) => ({
                        key: `${bus.id}-bus`,
                        text: bus.name,
                        value: `${bus.id}-bus`
                    }));

                    const mappedBusStations: Array<DropdownOption> = busStations.map((busStation: BusStationDto) => ({
                        key: `${busStation.id}-busStation`,
                        text: busStation.name,
                        value: `${busStation.id}-busStation`
                    }));

                    searchOptions.push(...mappedBuses);
                    searchOptions.push(...mappedBusStations);

                    this.setState({
                        searchOptions: searchOptions,
                        busStations: busStations,
                        buses: buses,
                        isFetching: false
                    });
                })
        } else {
            this.setState({
                searchOptions: []
            });
        }
    }, 200);

    onSubmitClicked = () => {
        const { selectedValue, busStations, buses } = this.state;
        const { history } = this.props;

        let dataToSend: any = {
            ...selectedValue
        };

        if (selectedValue?.type === "busStation") {
            const foundBusStation = busStations.find((busStation: BusStationDto) => {
               return busStation.id === selectedValue.value
            });

            if (foundBusStation !== undefined && foundBusStation !== null) {
                dataToSend = {
                    ...dataToSend,
                    name: foundBusStation.name,
                    longitude: foundBusStation.longitude,
                    latitude: foundBusStation.latitude,
                }
            }
        } else if (selectedValue?.type === "bus") {
            const foundBus = buses.find((bus: BusDto) => {
                return bus.id === selectedValue.value
            });

            if (foundBus !== undefined && foundBus !== null) {
                dataToSend = {
                    ...dataToSend,
                    name: foundBus.name,
                }
            }
        }


        history.push("/results", { selected: dataToSend })
    }

    onValueChange = ({ value }: DropdownProps) => {

        const values = (value as string).split("-");

        const id = parseInt(values[0]);
        const type = values[1];

        this.setState({
            selectedValue: {
                value: id,
                type: type === "bus" ? "bus" : "busStation"
            }
        })
    };

    render() {

        const { searchOptions, isFetching } = this.state;

        return (
            <Container className="h-100">
                <Row className="h-100 align-items-center">
                    <Col>
                        <div className="m-3" style={{ padding: "0 25rem 0 25rem" }}>
                            <Dropdown
                                placeholder='Search bus or bus station'
                                fluid
                                selection
                                search
                                options={searchOptions}
                                loading={isFetching}
                                onChange={(event, data) => this.onValueChange(data)}
                                onSearchChange={this.onSearchChange}
                            />
                        </div>
                        <div className="m-3" style={{ padding: "0 25rem 0 25rem" }}>
                            <Button className="w-100" onClick={this.onSubmitClicked}>Submit</Button>
                        </div>
                    </Col>
                </Row>
            </Container>
        );
    }
}

export default SearchPage;
