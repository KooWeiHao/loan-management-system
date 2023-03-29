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
    createdDate: Date;
    approvedBy?: string;
    approvedDate?: Date;
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
