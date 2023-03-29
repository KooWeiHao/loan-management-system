interface CreditLimitState {
    creditLimit: number;
    creditLimitDate: Date;
}

interface CreditLimitSlice {
    creditLimits: CreditLimitState[];
    defaultCreditLimit?: CreditLimitState;
    latestCreditLimit?: CreditLimitState;

    findAllCreditLimit: () => Promise<CreditLimitState[]>;
    getDefaultCreditLimit: () => Promise<CreditLimitState>;
    getLatestCreditLimit: () => Promise<CreditLimitState>;
    createCreditLimit: (creditLimit: number, creditLimitDate: Date) => Promise<CreditLimitState>;
}

export type { CreditLimitState };
export default CreditLimitSlice;
