import axios from "axios";
import { toast } from "react-toastify";
import { AuthStoredData } from "../store/interface/IAuthSlice";

const httpRequest = axios.create({
    baseURL: process.env.REACT_APP_LOAN_MANAGEMENT_SYSTEM_BACKEND_API_PATH,
});

httpRequest.interceptors.request.use((config) => {
    const user = sessionStorage.getItem("USER");

    if (user) {
        const { token } = <AuthStoredData>JSON.parse(user);
        config.headers.Authorization = `Basic ${token}`;
    }

    return config;
});

httpRequest.interceptors.response.use(
    (response) => {
        // return data from a response if the API call is success
        return response.data;
    },
    (error) => {
        // return message from a response of API call, or error message from axios, or the error itself in string, if the API call is failed.
        const message = error.response?.data?.message || error.message || error.toString();

        toast.error(message);

        return Promise.reject(message);
    }
);

export default httpRequest;
