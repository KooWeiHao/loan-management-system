interface LoanPaymentState {
    loanId: number;
    amount: number;
    createdDate: Date;
}

interface LoanPaymentSlice {
    loanPayments: LoanPaymentState[];

    addLoanPayment: (loanId: number, amount: number) => Promise<LoanPaymentState>;
    findLoanPaymentByLoanId: (loanId: number) => Promise<LoanPaymentState[]>;
    resetLoanPayments: () => void;
}

export type { LoanPaymentState };
export default LoanPaymentSlice;
