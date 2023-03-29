import { PaymentType } from "./ILoanSlice";

interface InterestRateState {
    interestRate: number;
    interestRateDate: number;
    type: PaymentType;
}

interface InterestRateSlice {
    fullPaymentInterestRate?: InterestRateState;
    partialPaymentOneYearInterestRate?: InterestRateState;
    partialPaymentTwoYearInterestRate?: InterestRateState;

    getLatestInterestRate: () => void;
}

export type { InterestRateState };
export default InterestRateSlice;
