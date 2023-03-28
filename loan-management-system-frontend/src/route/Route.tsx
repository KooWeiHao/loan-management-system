import { Route, Routes } from "react-router-dom";
import NotFoundPage from "../page/not-found";

export default () => {
    return (
        <Routes>
            {/* Route not found */}
            <Route path="*" element={<NotFoundPage />} />
        </Routes>
    );
};
