import { useEffect, useState } from "react";
import { Button, Col, Row } from "react-bootstrap";
import useTitle from "../../hook/useTitle";
import AppLayout from "../../layout/AppLayout";
import useStore from "../../store/useStore";
import { Status } from "../../store/interface/IApplicantSlice";
import LoanApplyModel from "./Component/LoanApplyModel";
import LoanTable from "./Component/LoanTable";

const HomePage = () => {
    useTitle("Home");

    const [isApplyLoan, setIsApplyLoan] = useState(false);

    const { applicant, getApplicant } = useStore((state) => ({
        applicant: state.currentApplicant,
        getApplicant: state.getApplicant,
    }));

    useEffect(() => {
        getApplicant();
    }, []);

    switch (applicant?.status) {
        case Status.PROCESSING:
            return (
                <AppLayout>
                    <h3>Welcome to Loan Management System.</h3>
                    <p>Please be patience. Our team is reviewing your account</p>
                </AppLayout>
            );

        case Status.APPROVED:
            return (
                <AppLayout>
                    <Row className="d-flex align-items-center">
                        <Col md="6">
                            <strong>Credit Limit:</strong> ${applicant?.creditFacility?.creditLimit}
                        </Col>
                        <Col md="6" className="text-end">
                            <Button onClick={() => setIsApplyLoan(true)}>Apply Loan</Button>
                        </Col>
                    </Row>

                    <LoanTable />

                    <LoanApplyModel show={isApplyLoan} handleClose={() => setIsApplyLoan(false)} />
                </AppLayout>
            );

        default:
            return null;
    }
};

export default HomePage;
