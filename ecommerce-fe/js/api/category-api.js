import { httpClient } from "../common/httpClient.js";

export async function fetchCategories() {
    return httpClient('/categories/list');
}