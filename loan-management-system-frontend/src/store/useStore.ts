import { create } from "zustand";
import { devtools } from "zustand/middleware";
import AuthSlice from "./interface/IAuthSlice";
import createAuthSlice from "./slice/AuthSlice";
import createApplicantSlice from "./slice/ApplicantSlice";
import ApplicantSlice from "./interface/IApplicantSlice";

const useStore = create<AuthSlice & ApplicantSlice>()(
    devtools((...data) => ({
        ...createAuthSlice(...data),
        ...createApplicantSlice(...data),
    }))
);

export default useStore;
