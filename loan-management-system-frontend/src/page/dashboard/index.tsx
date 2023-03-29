import useTitle from "../../hook/useTitle";
import AppLayout from "../../layout/AppLayout";

const DashboardPage = () => {
    useTitle("Dashboard");

    return (
        <AppLayout>
            <h1>Bank Staff</h1>
        </AppLayout>
    );
};

export default DashboardPage;
