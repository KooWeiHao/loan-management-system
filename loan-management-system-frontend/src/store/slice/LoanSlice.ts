import { StateCreator } from "zustand";
import produce from "immer";
import LoanSlice, { LoanState } from "../interface/ILoanSlice";
import httpRequest from "../../config/httpRequest";

const createLoanSLice: StateCreator<LoanSlice, [["zustand/devtools", never]]> = (setState, getState) => ({
    currentApplicantLoans: [],
    loans: [],

    findLoan: () => {
        return httpRequest.get<void, LoanState[]>("/loan").then((response) => {
            setState(
                {
                    currentApplicantLoans: response,
                },
                false,
                "findLoan"
            );

            return response;
        });
    },

    getLoanByLoanId: (loanId) => {
        return httpRequest.get<void, LoanState>(`loan/${loanId}`).then((response) => {
            setState(
                {
                    currentApplicantLoans: produce(getState().currentApplicantLoans, (draft) => {
                        const index = draft.findIndex((loan) => loan.loanId === response.loanId);
                        if (index !== -1) {
                            draft[index] = response;
                        }
                    }),
                },
                false,
                "getLoanByLoanId"
            );

            return response;
        });
    },

    findAllLoan: () => {
        return httpRequest.get<void, LoanState[]>("/loan/all").then((response) => {
            setState(
                {
                    loans: response,
                },
                false,
                "findAllLoan"
            );

            return response;
        });
    },

    createLoan: (amount, type, paymentType) => {
        const data = {
            amount,
            type,
            paymentType,
        };

        return httpRequest.post<void, LoanState>("/loan", data).then((response) => {
            setState(
                {
                    currentApplicantLoans: produce(getState().currentApplicantLoans, (draft) => {
                        draft.unshift(response);
                    }),
                },
                false,
                "createLoan"
            );

            return response;
        });
    },

    approveLoan: (loanId) => {
        const headers = {
            "Content-Type": "application/x-www-form-urlencoded",
        };

        const data = {
            loanId,
        };

        return httpRequest.post<void, LoanState>("/loan/approve", data, { headers }).then((response) => {
            setState(
                {
                    loans: produce(getState().loans, (draft) => {
                        const index = draft.findIndex((loan) => loan.loanId === response.loanId);
                        if (index !== -1) {
                            draft[index] = response;
                        }
                    }),
                },
                false,
                "approveLoan"
            );

            return response;
        });
    },
});

export default createLoanSLice;
