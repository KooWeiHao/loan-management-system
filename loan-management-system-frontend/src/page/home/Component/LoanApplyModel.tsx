import { Button, Form, Modal } from "react-bootstrap";
import { useFormik, validateYupSchema, yupToFormErrors } from "formik";
import * as Yup from "yup";
import { toast } from "react-toastify";
import { ValidationError } from "yup";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarAlt, faLandmark, faSackDollar } from "@fortawesome/free-solid-svg-icons";
import { useEffect } from "react";
import { PaymentType, LoanType } from "../../../store/interface/ILoanSlice";
import useStore from "../../../store/useStore";

interface LoanApplyForm {
    amount: string;
    type: string;
    paymentType: string;
}

interface LoanApplyModelModelProps {
    show: boolean;
    handleClose: () => void;
}

const LoanApplyModel = ({ show, handleClose }: LoanApplyModelModelProps) => {
    const {
        createLoan,
        getLatestInterestRate,
        fullPaymentInterestRate,
        partialPaymentTwoYearInterestRate,
        partialPaymentOneYearInterestRate,
    } = useStore((state) => ({
        createLoan: state.createLoan,
        getLatestInterestRate: state.getLatestInterestRate,
        fullPaymentInterestRate: state.fullPaymentInterestRate,
        partialPaymentOneYearInterestRate: state.partialPaymentOneYearInterestRate,
        partialPaymentTwoYearInterestRate: state.partialPaymentTwoYearInterestRate,
    }));

    useEffect(() => {
        getLatestInterestRate();
    }, []);

    const loanApplyForm = useFormik<LoanApplyForm>({
        initialValues: {
            amount: "",
            type: "",
            paymentType: "",
        },

        onSubmit: (values, formikHelpers) => {
            const amount = +values.amount;
            const type = values.type as LoanType;
            const paymentType = values.paymentType as PaymentType;

            createLoan(amount, type, paymentType)
                .then(() => {
                    toast.success("Success");
                    formikHelpers.resetForm();
                    handleClose();
                })
                .finally(() => {
                    formikHelpers.setSubmitting(false);
                });
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
                    type: Yup.string().required("Required type is not present"),
                    paymentType: Yup.string().required("Required payment type is not present"),
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
        loanApplyForm.resetForm();
        handleClose();
    };

    const toPercentage = (value?: number): string => {
        return value ? (value * 100).toFixed(2) : "";
    };

    return (
        <Modal show={show} onHide={onCLose}>
            <Form onSubmit={loanApplyForm.handleSubmit} noValidate>
                <Modal.Header closeButton>
                    <Modal.Title>Apply Loan</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <fieldset disabled={loanApplyForm.isSubmitting}>
                        <Form.Group controlId="amount" className="mb-3">
                            <Form.Label>
                                <FontAwesomeIcon icon={faSackDollar} /> Credit Limit
                            </Form.Label>
                            <Form.Control
                                type="number"
                                placeholder="1000.00"
                                onChange={loanApplyForm.handleChange}
                                value={loanApplyForm.values.amount}
                            />
                        </Form.Group>
                        <Form.Group controlId="type" className="mb-3">
                            <Form.Label>
                                <FontAwesomeIcon icon={faLandmark} /> Loan Type
                            </Form.Label>
                            <Form.Select value={loanApplyForm.values.type} onChange={loanApplyForm.handleChange}>
                                <option>Open this select menu</option>
                                <option value={LoanType.HOME}>Home</option>
                                <option value={LoanType.CAR}>Car</option>
                            </Form.Select>
                        </Form.Group>
                        <Form.Group controlId="paymentType" className="mb-3">
                            <Form.Label>
                                <FontAwesomeIcon icon={faCalendarAlt} /> Repayment Period - Interest rate %
                            </Form.Label>
                            <Form.Select value={loanApplyForm.values.paymentType} onChange={loanApplyForm.handleChange}>
                                <option>Open this select menu</option>
                                <option value={PaymentType.FULL_PAYMENT}>
                                    Full payment - {toPercentage(fullPaymentInterestRate?.interestRate)}%
                                </option>
                                <option value={PaymentType.PARTIAL_PAYMENT_ONE_YEAR}>
                                    1 year - {toPercentage(partialPaymentOneYearInterestRate?.interestRate)}%
                                </option>
                                <option value={PaymentType.PARTIAL_PAYMENT_TWO_YEARS}>
                                    2 years - {toPercentage(partialPaymentTwoYearInterestRate?.interestRate)}%
                                </option>
                            </Form.Select>
                        </Form.Group>
                    </fieldset>
                </Modal.Body>

                <Modal.Footer>
                    <Button type="submit" disabled={loanApplyForm.isSubmitting}>
                        Submit
                    </Button>
                </Modal.Footer>
            </Form>
        </Modal>
    );
};

export default LoanApplyModel;
