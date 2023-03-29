interface LoanPaymentState {
    loanId: number;
    amount: number;
    createdDate: Date;
}

interface LoanPaymentSlice {
    loanPayments: LoanPaymentState[];

    addLoanPayment: (loanId: number, amount: number) => Promise<LoanPaymentState>;
}

export type { LoanPaymentState };
export default LoanPaymentSlice;
