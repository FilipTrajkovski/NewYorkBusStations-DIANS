import axios from "axios";
import React, { Component } from "react";
import { Button, Col, Container, InputGroup, Row } from "react-bootstrap";
import { Dropdown } from "semantic-ui-react";
import { BusDto, BusStationDto, DropdownOption } from "../api.types";
import { getAllBuses } from "../services/busServices";
import { getAllBusStations } from "../services/busStationServices";

const cancelTokenSource = axios.CancelToken.source();

interface State {
    searchOptions: Array<DropdownOption>,
    isFetching: boolean
}

class SearchPage extends Component<{}, State> {

    constructor(props: {}) {
        super(props);

        this.state = {
            searchOptions: [],
            isFetching: true
        }

        this.getAllOptions();
    }

    getAllOptions = () => {
        const methods = [ getAllBuses(cancelTokenSource), getAllBusStations(cancelTokenSource) ]

        Promise.all(methods).then((response: Array<any>) => {
            const buses: Array<BusDto> = response[0];
            const busStations: Array<BusStationDto> = response[1];

            const searchOptions: Array<DropdownOption> = [];

            const mappedBuses: Array<DropdownOption> = buses.map((bus: BusDto) => ({
                key: `${bus.id}-bus`,
                text: bus.name,
                value: bus.id
            }));

            const mappedBusStations: Array<DropdownOption> = busStations.map((busStation: BusStationDto) => ({
                key: `${busStation.id}-bus-station`,
                text: busStation.name,
                value: busStation.id
            }));

            searchOptions.push(...mappedBuses);
            searchOptions.push(...mappedBusStations);

            this.setState({
                searchOptions: searchOptions,
                isFetching: false
            })
        })
    }

    onSearchClicked = () => {

    }

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
                            />
                        </div>
                        <div className="m-3" style={{ padding: "0 25rem 0 25rem" }}>
                            <Button className="w-100">Submit</Button>
                        </div>
                    </Col>
                </Row>
            </Container>
        );
    }
}

export default SearchPage;
