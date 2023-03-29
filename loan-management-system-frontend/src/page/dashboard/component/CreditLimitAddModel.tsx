import { Button, Form, Modal, ModalFooter } from "react-bootstrap";
import { useFormik, validateYupSchema, yupToFormErrors } from "formik";
import { toast } from "react-toastify";
import * as Yup from "yup";
import { ValidationError } from "yup";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import useStore from "../../../store/useStore";

interface CreditLimitAddForm {
    creditLimit: string;
    creditLimitDate: string;
}

interface CreditLimitCreateModelProps {
    show: boolean;
    handleClose: () => void;
}

const CreditLimitAddModel = ({ show, handleClose }: CreditLimitCreateModelProps) => {
    const createCreditLimit = useStore((state) => state.createCreditLimit);

    const creditLimitAddForm = useFormik<CreditLimitAddForm>({
        initialValues: {
            creditLimit: "",
            creditLimitDate: "",
        },
        onSubmit: (values, formikHelpers) => {
            const creditLimit = +values.creditLimit;
            const creditLimitDate = new Date(values.creditLimitDate);

            createCreditLimit(creditLimit, creditLimitDate)
                .then(() => {
                    toast.success("Success");
                })
                .finally(() => {
                    formikHelpers.setSubmitting(false);
                });
        },

        validate: async (values) => {
            try {
                const validationSchema = Yup.object({
                    creditLimit: Yup.number()
                        .positive()
                        .test("", "Invalid currency amount format", (value) => {
                            if (value) {
                                const decimal = value.toString().split(".")[1];
                                return decimal !== undefined && decimal.length <= 2;
                            }
                            return true;
                        })
                        .required("Required credit limit is not present"),
                    creditLimitDate: Yup.date().required("Required credit limit date is not present"),
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

    return (
        <Modal show={show} onHide={handleClose}>
            <Form onSubmit={creditLimitAddForm.handleSubmit} noValidate>
                <Modal.Header closeButton>
                    <Modal.Title>Add Credit Limit</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <fieldset disabled={creditLimitAddForm.isSubmitting}>
                        <Form.Group controlId="creditLimit" className="mb-3">
                            <Form.Label>
                                <FontAwesomeIcon icon={faUser} /> Credit Limit
                            </Form.Label>
                            <Form.Control
                                type="number"
                                placeholder="1000.00"
                                onChange={creditLimitAddForm.handleChange}
                                value={creditLimitAddForm.values.creditLimit}
                            />
                        </Form.Group>
                        <Form.Group controlId="creditLimitDate" className="mb-3">
                            <Form.Label>
                                <FontAwesomeIcon icon={faUser} /> Credit Limit Date
                            </Form.Label>
                            <Form.Control
                                type="date"
                                placeholder="creditLimitDate"
                                onChange={creditLimitAddForm.handleChange}
                                value={creditLimitAddForm.values.creditLimitDate}
                            />
                        </Form.Group>
                    </fieldset>
                </Modal.Body>

                <ModalFooter>
                    <Button type="submit" disabled={creditLimitAddForm.isSubmitting}>
                        Add
                    </Button>
                </ModalFooter>
            </Form>
        </Modal>
    );
};

export default CreditLimitAddModel;
