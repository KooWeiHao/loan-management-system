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

    // Redirect to login page if the user is unauthenticated
    if (auth.status === Status.UNAUTHENTICATED) {
        return <Navigate to="/login" />;
    }

    if (auth.role !== role) {
        return auth.role === Role.BANK_STAFF ? <Navigate to="/dashboard" /> : <Navigate to="/" />;
    }

    // By default, redirect to the requested page if the user is BANK_STAFF
    return <Outlet />;
};

export default ProtectedRoute;
