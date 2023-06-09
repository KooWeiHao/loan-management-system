import { Navigate, Outlet } from "react-router-dom";
import { useEffect } from "react";
import useStore from "../store/useStore";
import { Role, Status } from "../store/interface/IAuthSlice";

const PublicRoute = () => {
    const { auth, validateAuthenticationStatus } = useStore((state) => ({
        auth: state.auth,
        validateAuthenticationStatus: state.validateAuthenticationStatus,
    }));

    useEffect(() => {
        validateAuthenticationStatus();
    }, []);

    if (auth.status === Status.PENDING_AUTHENTICATION) {
        return <p>Loading...</p>;
    }

    switch (auth.role) {
        case Role.APPLICANT:
            return <Navigate to="/" />;

        case Role.BANK_STAFF:
            return <Navigate to="/dashboard" />;

        default:
            return <Outlet />;
    }
};

export default PublicRoute;
