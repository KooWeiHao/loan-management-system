enum LoanType {
    HOME = "HOME",
    CAR = "CAR",
}

enum PaymentType {
    FULL_PAYMENT = "FULL_PAYMENT",
    PARTIAL_PAYMENT_ONE_YEAR = "PARTIAL_PAYMENT_ONE_YEAR",
    PARTIAL_PAYMENT_TWO_YEARS = "PARTIAL_PAYMENT_TWO_YEARS",
}

enum Status {
    PROCESSING = "PROCESSING",
    ACTIVE = "ACTIVE",
    PAID = "PAID",
}

interface LoanState {
    applicantUsername: string;
    loanId: number;
    type: LoanType;
    principalAmount: number;
    paidAmount: number;
    remainingAmount: number;
    interestRate: number;
    status: Status;
    firstPaymentDate: Date;
    lastPaymentDate: Date;
    createdDate: Date;
    approvedBy: string;
    approvedDate: Date;
}

interface LoanSlice {
    currentApplicantLoans: LoanState[];
    loans: LoanState[];

    getLoanByLoanId: (loanId: number) => Promise<LoanState>;
    findLoan: () => Promise<LoanState[]>;
    findAllLoan: () => Promise<LoanState[]>;
    createLoan: (amount: number, type: LoanType, paymentType: PaymentType) => Promise<LoanState>;
    approveLoan: (loanId: number) => Promise<LoanState>;
}

export type { LoanState };
export { LoanType, PaymentType, Status };
export default LoanSlice;
