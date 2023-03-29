enum Status {
    PROCESSING = "PROCESSING",
    APPROVED = "APPROVED",
}

interface CreditFacility {
    creditFacilityId: number;
    creditLimit: number;
}

interface ApplicantState {
    applicantId: number;
    username: string;
    status: Status;
    approvedBy?: string;
    approvedDate?: string;
    creditFacility?: CreditFacility;
}

interface ApplicantSlice {
    currentApplicant?: ApplicantState;
    applicants: ApplicantState[];

    getApplicant: () => Promise<ApplicantState>;
    findAllApplicant: () => Promise<ApplicantState[]>;
    approveApplicant: (applicantId: number) => Promise<ApplicantState>;
}

export type { ApplicantState };
export { Status };
export default ApplicantSlice;
