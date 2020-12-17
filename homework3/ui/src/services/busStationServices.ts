import axios, { CancelTokenSource } from "axios";
import { BusStationDto } from "../api.types";

const { REACT_APP_API_BASE_URL: baseUrl } = process.env;
const apiUri = `${baseUrl}/buses`;

export const getAllBusStations = (source: CancelTokenSource): Promise<Array<BusStationDto>> => {
    return axios
        .get(`${apiUri}`, { cancelToken: source.token })
        .then(response => response.data);
};