import { create } from "zustand";
import { devtools } from "zustand/middleware";
import AuthSlice from "./interface/IAuthSlice";
import createAuthSlice from "./slice/AuthSlice";
import createApplicantSlice from "./slice/ApplicantSlice";
import ApplicantSlice from "./interface/IApplicantSlice";
import CreditLimitSlice from "./interface/ICreditLimitSlice";
import createCreditLimitSlice from "./slice/CreditLimitSlice";
import LoanSlice from "./interface/ILoanSlice";
import createLoanSLice from "./slice/LoanSlice";
import InterestRateSlice from "./interface/IInterestRateSlice";
import createInterestRateSlice from "./slice/InterestRateSlice";
import LoanPaymentSlice from "./interface/ILoanPaymentSlice";
import createLoanPaymentSlice from "./slice/LoanPaymentSlice";

const useStore = create<
    AuthSlice & ApplicantSlice & CreditLimitSlice & LoanSlice & InterestRateSlice & LoanPaymentSlice
>()(
    devtools((...data) => ({
        ...createAuthSlice(...data),
        ...createApplicantSlice(...data),
        ...createCreditLimitSlice(...data),
        ...createLoanSLice(...data),
        ...createInterestRateSlice(...data),
        ...createLoanPaymentSlice(...data),
    }))
);

export default useStore;
