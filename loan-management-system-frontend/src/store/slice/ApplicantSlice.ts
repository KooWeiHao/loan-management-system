import { StateCreator } from "zustand";
import ApplicantSlice, { ApplicantState } from "../interface/IApplicantSlice";
import httpRequest from "../../config/httpRequest";

const createApplicantSlice: StateCreator<ApplicantSlice, [["zustand/devtools", never]]> = (setState) => ({
    getApplicant: () => {
        return httpRequest.get<void, ApplicantState>("/applicant").then((response) => {
            setState(
                {
                    currentApplicant: response,
                },
                false,
                "getApplicant"
            );

            return response;
        });
    },
});

export default createApplicantSlice;
