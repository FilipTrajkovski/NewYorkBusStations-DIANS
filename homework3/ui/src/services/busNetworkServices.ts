import axios, { CancelTokenSource } from "axios";
import { BusConnectionDto, BusStationConnectionDto } from "../api.types";

const {REACT_APP_API_BASE_URL: baseUrl} = process.env;
const apiUri = `${baseUrl}/bus-networks/connections`;

export const getConnectionsForBus = (busId: number, source: CancelTokenSource): Promise<BusConnectionDto> => {
    return axios
        .get(`${apiUri}/bus/${busId}`, {cancelToken: source.token})
        .then(response => response.data);
};

export const getConnectionsForBusStation = (busStationId: number, source: CancelTokenSource): Promise<BusStationConnectionDto> => {
    return axios
        .get(`${apiUri}/bus-station/${busStationId}`, {cancelToken: source.token})
        .then(response => response.data);
};