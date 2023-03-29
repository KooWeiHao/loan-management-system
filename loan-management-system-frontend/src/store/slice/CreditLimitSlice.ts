import { StateCreator } from "zustand";
import produce from "immer";
import CreditLimitSlice, { CreditLimitState } from "../interface/ICreditLimitSlice";
import httpRequest from "../../config/httpRequest";

const createCreditLimitSlice: StateCreator<CreditLimitSlice, [["zustand/devtools", never]]> = (setState, getState) => ({
    creditLimits: [],

    findAllCreditLimit: () => {
        return httpRequest.get<void, CreditLimitState[]>("/credit-limit/all").then((response) => {
            setState(
                {
                    creditLimits: response,
                },
                false,
                "findAllCreditLimit"
            );

            return response;
        });
    },

    getDefaultCreditLimit: () => {
        return httpRequest.get<void, CreditLimitState>("/credit-limit/default").then((response) => {
            setState(
                {
                    defaultCreditLimit: response,
                },
                false,
                "getDefaultCreditLimit"
            );

            return response;
        });
    },

    getLatestCreditLimit: () => {
        return httpRequest.get<void, CreditLimitState>("/credit-limit").then((response) => {
            setState(
                {
                    latestCreditLimit: response,
                },
                false,
                "getLatestCreditLimit"
            );

            return response;
        });
    },

    createCreditLimit: (creditLimit, creditLimitDate) => {
        const data = {
            creditLimit,
            creditLimitDate,
        };

        return httpRequest.post<void, CreditLimitState>("/credit-limit", data).then((response) => {
            setState(
                {
                    creditLimits: produce(getState().creditLimits, (draft) => {
                        draft.unshift(response);
                    }),
                },
                false,
                "createCreditLimit"
            );

            return response;
        });
    },
});

export default createCreditLimitSlice;
