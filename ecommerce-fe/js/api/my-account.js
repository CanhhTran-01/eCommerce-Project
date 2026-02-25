import { httpClient } from "../common/httpClient.js";

export async function fetchAccountInfo() {
    return httpClient('/accounts/info');
}