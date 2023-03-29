import { useEffect } from "react";
import useTitle from "../../hook/useTitle";
import AppLayout from "../../layout/AppLayout";
import useStore from "../../store/useStore";
import { Status } from "../../store/interface/IApplicantSlice";

const HomePage = () => {
    useTitle("Home");

    const { applicant, getApplicant } = useStore((state) => ({
        applicant: state.currentApplicant,
        getApplicant: state.getApplicant,
    }));

    useEffect(() => {
        getApplicant();
    }, []);

    return (
        <AppLayout>
            {applicant?.status === Status.PROCESSING ? (
                <>
                    <h3>Welcome to Loan Management System.</h3>
                    <p>Please be patience. Our team is reviewing your account</p>
                </>
            ) : (
                <>Credit Limit & Loan</>
            )}
        </AppLayout>
    );
};

export default HomePage;
