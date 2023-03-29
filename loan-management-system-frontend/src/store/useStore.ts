import { create } from "zustand";
import { devtools } from "zustand/middleware";
import AuthSlice from "./interface/IAuthSlice";
import createAuthSlice from "./slice/AuthSlice";
import createApplicantSlice from "./slice/ApplicantSlice";
import ApplicantSlice from "./interface/IApplicantSlice";
import CreditLimitSlice from "./interface/ICreditLimitSlice";
import createCreditLimitSlice from "./slice/CreditLimitSlice";

const useStore = create<AuthSlice & ApplicantSlice & CreditLimitSlice>()(
    devtools((...data) => ({
        ...createAuthSlice(...data),
        ...createApplicantSlice(...data),
        ...createCreditLimitSlice(...data),
    }))
);

export default useStore;
