import { useEffect } from "react";
import { Modal, Table } from "react-bootstrap";
import moment from "moment";
import useStore from "../store/useStore";

interface LoanPaymentViewModelProps {
    loanId?: number;
    handleClose: () => void;
}

const LoanPaymentViewModel = ({ loanId, handleClose }: LoanPaymentViewModelProps) => {
    const { loanPayments, findLoanPaymentByLoanId, resetLoanPayments } = useStore((state) => ({
        loanPayments: state.loanPayments,
        findLoanPaymentByLoanId: state.findLoanPaymentByLoanId,
        resetLoanPayments: state.resetLoanPayments,
    }));

    useEffect(() => {
        if (loanId) {
            findLoanPaymentByLoanId(loanId);
        }

        return () => resetLoanPayments();
    }, [loanId]);

    return (
        <Modal show={loanId !== undefined} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Loan Payments - {loanId}</Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Amount</th>
                            <th>Created Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        {loanPayments.map((data, i) => (
                            <tr key={i}>
                                <td>{i + 1}</td>
                                <td>${data.amount}</td>
                                <td>{moment(data.createdDate).format("yyyy-MM-DD HH:mm:ss")}</td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            </Modal.Body>
        </Modal>
    );
};

export default LoanPaymentViewModel;
