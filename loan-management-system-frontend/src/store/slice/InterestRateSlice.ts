import { StateCreator } from "zustand";
import IInterestRateSlice, { InterestRateState } from "../interface/IInterestRateSlice";
import httpRequest from "../../config/httpRequest";
import { PaymentType } from "../interface/ILoanSlice";

const createInterestRateSlice: StateCreator<IInterestRateSlice, [["zustand/devtools", never]]> = (setState) => ({
    getLatestInterestRate: async () => {
        const fullPaymentInterestRate = await httpRequest.get<void, InterestRateState>("/interest-rate", {
            params: { type: PaymentType.FULL_PAYMENT },
        });
        const partialPaymentOneYearInterestRate = await httpRequest.get<void, InterestRateState>("/interest-rate", {
            params: { type: PaymentType.PARTIAL_PAYMENT_ONE_YEAR },
        });
        const partialPaymentTwoYearInterestRate = await httpRequest.get<void, InterestRateState>("/interest-rate", {
            params: { type: PaymentType.PARTIAL_PAYMENT_TWO_YEARS },
        });

        setState(
            {
                fullPaymentInterestRate,
                partialPaymentOneYearInterestRate,
                partialPaymentTwoYearInterestRate,
            },
            false,
            "getLatestInterestRate"
        );
    },
});

export default createInterestRateSlice;
