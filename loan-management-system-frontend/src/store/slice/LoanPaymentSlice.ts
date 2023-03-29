import { StateCreator } from "zustand";
import LoanPaymentSlice, { LoanPaymentState } from "../interface/ILoanPaymentSlice";
import httpRequest from "../../config/httpRequest";
import LoanSlice from "../interface/ILoanSlice";

const createLoanPaymentSlice: StateCreator<
    LoanPaymentSlice & LoanSlice,
    [["zustand/devtools", never]],
    [],
    LoanPaymentSlice
> = (setState, getState) => ({
    loanPayments: [],

    addLoanPayment: (loanId, amount) => {
        const data = {
            loanId,
            amount,
        };

        return httpRequest.post<void, LoanPaymentState>("/loan-payment", data).then((response) => {
            getState().getLoanByLoanId(response.loanId);
            return response;
        });
    },

    findLoanPaymentByLoanId: (loanId) => {
        return httpRequest.get<void, LoanPaymentState[]>("/loan-payment", { params: { loanId } }).then((response) => {
            setState(
                {
                    loanPayments: response,
                },
                false,
                "findLoanPaymentByLoanId"
            );

            return response;
        });
    },

    resetLoanPayments: () => {
        setState(
            {
                loanPayments: [],
            },
            false,
            "resetLoanPayments"
        );
    },
});

export default createLoanPaymentSlice;
