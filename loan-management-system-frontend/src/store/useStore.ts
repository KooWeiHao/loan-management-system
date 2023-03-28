import { create } from "zustand";
import { devtools } from "zustand/middleware";
import AuthSlice from "./interface/IAuthSlice";
import createAuthSlice from "./slice/AuthSlice";

const useStore = create<AuthSlice>()(
    devtools((...data) => ({
        ...createAuthSlice(...data),
    }))
);

export default useStore;
