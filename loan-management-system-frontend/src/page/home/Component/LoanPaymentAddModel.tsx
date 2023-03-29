import { useFormik, validateYupSchema, yupToFormErrors } from "formik";
import { toast } from "react-toastify";
import { ValidationError } from "yup";
import * as Yup from "yup";
import { Button, Form, Modal } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSackDollar } from "@fortawesome/free-solid-svg-icons";
import useStore from "../../../store/useStore";
import { LoanState } from "../../../store/interface/ILoanSlice";

interface LoanPaymentAddForm {
    amount: string;
}

interface LoanPaymentAddModelProps {
    loan?: LoanState;
    handleClose: () => void;
}

const LoanPaymentAddModel = ({ loan, handleClose }: LoanPaymentAddModelProps) => {
    const addLoanPayment = useStore((state) => state.addLoanPayment);

    const loanPaymentAddForm = useFormik<LoanPaymentAddForm>({
        initialValues: {
            amount: "",
        },

        onSubmit: (values, formikHelpers) => {
            const amount = +values.amount;

            if (loan) {
                addLoanPayment(loan.loanId, amount)
                    .then(() => {
                        toast.success("Success");
                        formikHelpers.resetForm();
                        handleClose();
                    })
                    .finally(() => {
                        formikHelpers.setSubmitting(false);
                    });
            }
        },

        validate: async (values) => {
            try {
                const validationSchema = Yup.object({
                    amount: Yup.number()
                        .positive()
                        .test("", "Invalid currency amount format", (value) => {
                            if (value) {
                                const decimal = value.toString().split(".")[1];
                                if (decimal) {
                                    return decimal.length <= 2;
                                }
                            }
                            return true;
                        })
                        .required("Required amount is not present"),
                });

                await validateYupSchema(values, validationSchema);

                return {};
            } catch (error) {
                const validationError = error as ValidationError;
                const errorMessages = validationError.errors;

                toast.error(errorMessages[0]);

                return yupToFormErrors(validationError);
            }
        },

        validateOnChange: false,
        validateOnBlur: false,
    });

    const onCLose = () => {
        loanPaymentAddForm.resetForm();
        handleClose();
    };

    return (
        <Modal show={loan !== undefined} onHide={onCLose}>
            <Form onSubmit={loanPaymentAddForm.handleSubmit} noValidate>
                <Modal.Header closeButton>
                    <Modal.Title>Pay Loan</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <p>Principal Amount: ${loan?.principalAmount}</p>
                    <p>Interest Rate: {loan?.interestRate ? (100 * loan.interestRate).toFixed(2) : 0}%</p>
                    <p>Paid Amount: ${loan?.paidAmount}</p>
                    <p>Remaining Amount: ${loan?.remainingAmount}</p>

                    <fieldset disabled={loanPaymentAddForm.isSubmitting}>
                        <Form.Group controlId="amount" className="mb-3">
                            <Form.Label>
                                <FontAwesomeIcon icon={faSackDollar} /> Credit Limit
                            </Form.Label>
                            <Form.Control
                                type="number"
                                placeholder="1000.00"
                                onChange={loanPaymentAddForm.handleChange}
                                value={loanPaymentAddForm.values.amount}
                            />
                        </Form.Group>
                    </fieldset>
                </Modal.Body>

                <Modal.Footer>
                    <Button type="submit" disabled={loanPaymentAddForm.isSubmitting}>
                        Submit
                    </Button>
                </Modal.Footer>
            </Form>
        </Modal>
    );
};

export default LoanPaymentAddModel;
