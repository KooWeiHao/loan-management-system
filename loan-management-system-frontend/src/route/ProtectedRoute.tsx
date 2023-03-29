import { Navigate, Outlet } from "react-router-dom";
import { useEffect } from "react";
import useStore from "../store/useStore";
import { Role, Status } from "../store/interface/IAuthSlice";

interface ProtectedRouteProps {
    role: Role;
}

const ProtectedRoute = ({ role }: ProtectedRouteProps) => {
    const { auth, validateAuthenticationStatus } = useStore((state) => ({
        auth: state.auth,
        validateAuthenticationStatus: state.validateAuthenticationStatus,
    }));

    useEffect(() => {
        validateAuthenticationStatus();
    }, []);

    switch (auth.status) {
        case Status.PENDING_AUTHENTICATION:
            return <p>Loading...</p>;

        case Status.UNAUTHENTICATED:
            return <Navigate to="/login" />;

        case Status.AUTHENTICATED:
        default:
            if (auth.role !== role) {
                return auth.role === Role.BANK_STAFF ? <Navigate to="/dashboard" /> : <Navigate to="/" />;
            }

            // By default, redirect to the requested page if the user is BANK_STAFF
            return <Outlet />;
    }
};

export default ProtectedRoute;
