import { Route, Routes } from "react-router-dom";
import NotFoundPage from "../page/not-found";
import LoginPage from "../page/login";
import HomePage from "../page/home";
import ProtectedRoute from "./ProtectedRoute";
import Dashboard from "../page/dashboard";
import { Role } from "../store/interface/IAuthSlice";
import PublicRoute from "./PublicRoute";

export default () => {
    return (
        <Routes>
            {/* Route not found */}
            <Route path="*" element={<NotFoundPage />} />

            <Route element={<PublicRoute />}>
                <Route path="/login" element={<LoginPage />} />
            </Route>

            <Route element={<ProtectedRoute role={Role.APPLICANT} />}>
                <Route path="/" element={<HomePage />} />
            </Route>

            <Route element={<ProtectedRoute role={Role.BANK_STAFF} />}>
                <Route path="/dashboard" element={<Dashboard />} />
            </Route>
        </Routes>
    );
};
