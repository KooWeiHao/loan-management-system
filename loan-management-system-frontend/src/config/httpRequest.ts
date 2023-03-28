import axios from "axios";
import { Buffer } from "buffer";

const httpRequest = axios.create({
    baseURL: process.env.REACT_APP_LOAN_MANAGEMENT_SYSTEM_BACKEND_API_PATH,
});

httpRequest.interceptors.request.use((config) => {
    const tempBase64Credentials = Buffer.from(
        "erickoo:password",
        "utf8"
    ).toString("base64");
    config.headers.Authorization = `Basic ${tempBase64Credentials}`;

    return config;
});

httpRequest.interceptors.response.use(
    (response) => {
        // return data from a response if the API call is success
        return response.data;
    },
    (error) => {
        // return message from a response of API call, or error message from axios, or the error itself in string, if the API call is failed.
        const message =
            error.response?.data?.message || error.message || error.toString();

        return Promise.reject(message);
    }
);

export default httpRequest;
