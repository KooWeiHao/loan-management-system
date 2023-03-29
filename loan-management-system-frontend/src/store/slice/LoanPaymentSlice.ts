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
});

export default createLoanPaymentSlice;
