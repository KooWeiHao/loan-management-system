import { StateCreator } from "zustand";
import { Buffer } from "buffer";
import AuthSlice, { AuthResponseModel, AuthStoredData, Role, Status } from "../interface/IAuthSlice";
import httpRequest from "../../config/httpRequest";

const createAuthSlice: StateCreator<AuthSlice, [["zustand/devtools", never]]> = (setState, getState) => ({
    auth: {
        status: Status.PENDING_AUTHENTICATION,
    },

    register: (username, password) => {
        const data = { username, password };

        return httpRequest.post<void, AuthResponseModel>("/auth/register", data).then((response) => {
            setState(
                {
                    auth: {
                        status: Status.UNAUTHENTICATED,
                    },
                },
                false,
                "register"
            );

            return response;
        });
    },

    login: (username, password, role) => {
        const data = { username, password };
        const loginApi = role === Role.BANK_STAFF ? "login-as-bank-staff" : "login";

        return httpRequest.post<void, AuthResponseModel>(`/auth/${loginApi}`, data).then((response) => {
            setState(
                {
                    auth: {
                        username: response.username,
                        role: response.role,
                        status: Status.AUTHENTICATED,
                    },
                },
                false,
                "login"
            );

            sessionStorage.setItem(
                "USER",
                JSON.stringify({
                    username: response.username,
                    role: response.role,
                    token: Buffer.from(`${response.username}:${password}`, "utf8").toString("base64"),
                })
            );

            return response;
        });
    },

    logout: () => {
        setState(
            {
                auth: {
                    status: Status.UNAUTHENTICATED,
                },
            },
            false,
            "logout"
        );

        sessionStorage.removeItem("USER");
    },

    validateAuthenticationStatus: () => {
        if (getState().auth.status === Status.PENDING_AUTHENTICATION) {
            const user = sessionStorage.getItem("USER");

            if (user) {
                const data = <AuthStoredData>JSON.parse(user);

                setState(
                    {
                        auth: {
                            username: data.username,
                            role: data.role,
                            status: Status.AUTHENTICATED,
                        },
                    },
                    false,
                    "setAuthState"
                );
            } else {
                setState(
                    {
                        auth: {
                            status: Status.UNAUTHENTICATED,
                        },
                    },
                    false,
                    "setAuthState"
                );
            }
        }
    },
});

export default createAuthSlice;
