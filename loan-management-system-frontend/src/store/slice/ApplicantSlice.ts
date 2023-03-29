import { StateCreator } from "zustand";
import produce from "immer";
import ApplicantSlice, { ApplicantState } from "../interface/IApplicantSlice";
import httpRequest from "../../config/httpRequest";

const createApplicantSlice: StateCreator<ApplicantSlice, [["zustand/devtools", never]]> = (setState, getState) => ({
    applicants: [],

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

    findAllApplicant: () => {
        return httpRequest.get<void, ApplicantState[]>("/applicant/all").then((response) => {
            setState(
                {
                    applicants: response,
                },
                false,
                "findAllApplicant"
            );

            return response;
        });
    },

    approveApplicant: (applicantId) => {
        const headers = {
            "Content-Type": "application/x-www-form-urlencoded",
        };

        const data = {
            applicantId,
        };

        return httpRequest.post<void, ApplicantState>("/applicant/approve", data, { headers }).then((response) => {
            setState(
                {
                    applicants: produce(getState().applicants, (draft) => {
                        const index = draft.findIndex((applicant) => applicant.applicantId === response.applicantId);
                        if (index !== -1) {
                            draft[index] = response;
                        }
                    }),
                },
                false,
                "approveApplicant"
            );

            return response;
        });
    },
});

export default createApplicantSlice;
