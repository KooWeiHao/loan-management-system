import { useEffect, useState } from "react";
import { Button, Col, Row, Table } from "react-bootstrap";
import moment from "moment/moment";
import { LoanState, Status } from "../../../store/interface/ILoanSlice";
import useStore from "../../../store/useStore";
import LoanPaymentAddModel from "./LoanPaymentAddModel";
import LoanPaymentViewModel from "../../../component/LoanPaymentViewModel";

const LoanTable = () => {
    const { currentApplicantLoans, findLoan } = useStore((state) => ({
        currentApplicantLoans: state.currentApplicantLoans,
        findLoan: state.findLoan,
    }));

    const [loan, setLoan] = useState<LoanState>();
    const [loanId, setLoanId] = useState<number>();

    const [activeLoans, setActiveLoans] = useState<LoanState[]>([]);
    const [processingLoans, setProcessingLoans] = useState<LoanState[]>([]);
    const [paidLoans, setPaidLoans] = useState<LoanState[]>([]);

    useEffect(() => {
        findLoan();
    }, []);

    useEffect(() => {
        setActiveLoans(currentApplicantLoans.filter((l) => l.status === Status.ACTIVE));
        setProcessingLoans(currentApplicantLoans.filter((l) => l.status === Status.PROCESSING));
        setPaidLoans(currentApplicantLoans.filter((l) => l.status === Status.PAID));
    }, [currentApplicantLoans]);

    return (
        <>
            <Row>
                <Col>
                    <h4>Processing Loans</h4>
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Loan Id</th>
                                <th>Principal Amount</th>
                                <th>Interest Rate(%)</th>
                                <th>Period</th>
                                <th>Created Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            {processingLoans.map((data, i) => (
                                <tr key={data.loanId}>
                                    <td>{i + 1}</td>
                                    <td>{data.loanId}</td>
                                    <td>${data.principalAmount}</td>
                                    <td>{(data.interestRate * 100).toFixed(2)}</td>
                                    <td>
                                        {moment(data.firstPaymentDate).format("yyyy-MM-DD")} -{" "}
                                        {moment(data.lastPaymentDate).format("yyyy-MM-DD")}
                                    </td>
                                    <td>{moment(data.createdDate).format("yyyy-MM-DD HH:mm:ss")}</td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>
                </Col>
            </Row>

            <Row>
                <Col>
                    <h4>Active Loans</h4>
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Loan Id</th>
                                <th>Principal Amount</th>
                                <th>Interest Rate(%)</th>
                                <th>Period</th>
                                <th>Created Date</th>
                                <th aria-label="view payments button" />
                                <th aria-label="pay loan button" />
                            </tr>
                        </thead>
                        <tbody>
                            {activeLoans.map((data, i) => (
                                <tr key={data.loanId}>
                                    <td>{i + 1}</td>
                                    <td>{data.loanId}</td>
                                    <td>${data.principalAmount}</td>
                                    <td>{(data.interestRate * 100).toFixed(2)}</td>
                                    <td>
                                        {moment(data.firstPaymentDate).format("yyyy-MM-DD")} -{" "}
                                        {moment(data.lastPaymentDate).format("yyyy-MM-DD")}
                                    </td>
                                    <td>{moment(data.createdDate).format("yyyy-MM-DD HH:mm:ss")}</td>
                                    <td className="text-center">
                                        <Button size="sm" onClick={() => setLoanId(data.loanId)}>
                                            View Payments
                                        </Button>
                                    </td>
                                    <td className="text-center">
                                        <Button size="sm" onClick={() => setLoan(data)}>
                                            Pay
                                        </Button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>
                </Col>
            </Row>

            <Row>
                <Col>
                    <h4>Paid Loans</h4>
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Loan Id</th>
                                <th>Principal Amount</th>
                                <th>Interest Rate(%)</th>
                                <th>Period</th>
                                <th>Created Date</th>
                                <th aria-label="view payments button" />
                            </tr>
                        </thead>
                        <tbody>
                            {paidLoans.map((data, i) => (
                                <tr key={data.loanId}>
                                    <td>{i + 1}</td>
                                    <td>{data.loanId}</td>
                                    <td>${data.principalAmount}</td>
                                    <td>{(data.interestRate * 100).toFixed(2)}</td>
                                    <td>
                                        {moment(data.firstPaymentDate).format("yyyy-MM-DD")} -{" "}
                                        {moment(data.lastPaymentDate).format("yyyy-MM-DD")}
                                    </td>
                                    <td>{moment(data.createdDate).format("yyyy-MM-DD HH:mm:ss")}</td>
                                    <td className="text-center">
                                        <Button size="sm" onClick={() => setLoanId(data.loanId)}>
                                            View Payments
                                        </Button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>
                </Col>
            </Row>

            <LoanPaymentAddModel loan={loan} handleClose={() => setLoan(undefined)} />
            <LoanPaymentViewModel loanId={loanId} handleClose={() => setLoanId(undefined)} />
        </>
    );
};

export default LoanTable;
