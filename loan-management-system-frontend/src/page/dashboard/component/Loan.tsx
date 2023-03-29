import { useEffect, useState } from "react";
import { Button, Col, Row, Table } from "react-bootstrap";
import moment from "moment";
import { LoanState, Status } from "../../../store/interface/ILoanSlice";
import useStore from "../../../store/useStore";

const Loan = () => {
    const [activeLoans, setActiveLoans] = useState<LoanState[]>([]);
    const [newLoans, setNewLoans] = useState<LoanState[]>([]);
    const [paidLoans, setPaidLoans] = useState<LoanState[]>([]);

    const { loans, findAllLoan, approveLoan } = useStore((state) => ({
        loans: state.loans,
        findAllLoan: state.findAllLoan,
        approveLoan: state.approveLoan,
    }));

    useEffect(() => {
        findAllLoan();
    }, []);

    useEffect(() => {
        setActiveLoans(loans.filter((loan) => loan.status === Status.ACTIVE));
        setNewLoans(loans.filter((loan) => loan.status === Status.PROCESSING));
        setPaidLoans(loans.filter((loan) => loan.status === Status.PAID));
    }, [loans]);

    return (
        <>
            {/* TODO - Change the implementation below by not creating three tables manually */}
            <Row>
                <Col>
                    <h4>New Loans</h4>
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Loan Id</th>
                                <th>Amount</th>
                                <th>Interest Rate(%)</th>
                                <th>Applicant</th>
                                <th>Period</th>
                                <th>Created Date</th>
                                <th aria-label="approve button" />
                            </tr>
                        </thead>
                        <tbody>
                            {newLoans.map((data, i) => (
                                <tr key={data.loanId}>
                                    <td>{i + 1}</td>
                                    <td>{data.loanId}</td>
                                    <td>${data.principalAmount}</td>
                                    <td>{(data.interestRate * 100).toFixed(2)}</td>
                                    <td>{data.applicantUsername}</td>
                                    <td>
                                        {moment(data.firstPaymentDate).format("yyyy-MM-DD")} -{" "}
                                        {moment(data.lastPaymentDate).format("yyyy-MM-DD")}
                                    </td>
                                    <td>{moment(data.createdDate).format("yyyy-MM-DD HH:mm:ss")}</td>
                                    <td className="text-center">
                                        <Button size="sm" onClick={() => approveLoan(data.loanId)}>
                                            Approve
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
                    <h4>Active Loans</h4>
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Loan Id</th>
                                <th>Amount</th>
                                <th>Interest Rate(%)</th>
                                <th>Applicant</th>
                                <th>Period</th>
                                <th>Created Date</th>
                                <th aria-label="view payments button" />
                            </tr>
                        </thead>
                        <tbody>
                            {activeLoans.map((data, i) => (
                                <tr key={data.loanId}>
                                    <td>{i + 1}</td>
                                    <td>{data.loanId}</td>
                                    <td>${data.principalAmount}</td>
                                    <td>{(data.interestRate * 100).toFixed(2)}</td>
                                    <td>{data.applicantUsername}</td>
                                    <td>
                                        {moment(data.firstPaymentDate).format("yyyy-MM-DD")} -{" "}
                                        {moment(data.lastPaymentDate).format("yyyy-MM-DD")}
                                    </td>
                                    <td>{moment(data.createdDate).format("yyyy-MM-DD HH:mm:ss")}</td>
                                    <td className="text-center">
                                        <Button size="sm">View Payments</Button>
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
                                <th>Amount</th>
                                <th>Interest Rate(%)</th>
                                <th>Applicant</th>
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
                                    <td>{data.applicantUsername}</td>
                                    <td>
                                        {moment(data.firstPaymentDate).format("yyyy-MM-DD")} -{" "}
                                        {moment(data.lastPaymentDate).format("yyyy-MM-DD")}
                                    </td>
                                    <td>{moment(data.createdDate).format("yyyy-MM-DD HH:mm:ss")}</td>
                                    <td className="text-center">
                                        <Button size="sm">View Payments</Button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>
                </Col>
            </Row>
        </>
    );
};

export default Loan;
