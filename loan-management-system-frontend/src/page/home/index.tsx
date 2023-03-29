import useTitle from "../../hook/useTitle";
import AppLayout from "../../layout/AppLayout";

const HomePage = () => {
    useTitle("Home");

    return (
        <AppLayout>
            <h1>Applicant</h1>
        </AppLayout>
    );
};

export default HomePage;
