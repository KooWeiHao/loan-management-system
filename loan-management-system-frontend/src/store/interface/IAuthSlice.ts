enum Role {
    APPLICANT = "ROLE_APPLICANT",
    BANK_STAFF = "ROLE_BANK_STAFF",
}

enum Status {
    PENDING_AUTHENTICATION,
    AUTHENTICATED,
    UNAUTHENTICATED,
}

interface AuthResponseModel {
    username: string;
    role: Role;
}

interface AuthStoredData {
    username: string;
    role: Role;
    token: string;
}

interface AuthState {
    username?: string;
    role?: Role;
    status: Status;
}

interface AuthSlice {
    auth: AuthState;

    register: (username: string, password: string) => Promise<AuthResponseModel>;
    login: (username: string, password: string, role: Role) => Promise<AuthResponseModel>;
    logout: () => void;
    validateAuthenticationStatus: () => void;
}

export type { AuthResponseModel, AuthStoredData };
export { Status, Role };
export default AuthSlice;
