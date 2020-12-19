import axios, {CancelTokenSource} from "axios";
import {BusDto} from "../api.types";

const { REACT_APP_API_BASE_URL: baseUrl } = process.env;
const apiUri = `${baseUrl}/buses`;

export const getAllBuses = (source: CancelTokenSource): Promise<Array<BusDto>> => {
    return axios
        .get(`${apiUri}`, { cancelToken: source.token })
        .then(response => response.data);
};