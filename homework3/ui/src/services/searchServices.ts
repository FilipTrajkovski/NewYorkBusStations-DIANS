import axios, { CancelTokenSource } from "axios";
import { SearchResponse } from "../api.types";

const { REACT_APP_API_BASE_URL: baseUrl } = process.env;
const apiUri = `${baseUrl}/search`;

export const searchBusData = (query: string, source: CancelTokenSource): Promise<SearchResponse> => {
    return axios
        .get(`${apiUri}/${query}`, { cancelToken: source.token })
        .then(response => response.data);
}
