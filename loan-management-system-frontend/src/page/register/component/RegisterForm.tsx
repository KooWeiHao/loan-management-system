import { useFormik, validateYupSchema, yupToFormErrors } from "formik";
import { toast } from "react-toastify";
import * as Yup from "yup";
import { ValidationError } from "yup";
import { Link, useNavigate } from "react-router-dom";
import { Button, Card, Col, Form, Row } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLock, faUser } from "@fortawesome/free-solid-svg-icons";
import { useEffect } from "react";
import useStore from "../../../store/useStore";

interface RegisterForm {
    username: string;
    password: string;
}

const RegisterForm = () => {
    const { register, latestCreditLimit, getLatestCreditLimit } = useStore((state) => ({
        register: state.register,
        latestCreditLimit: state.latestCreditLimit,
        getLatestCreditLimit: state.getLatestCreditLimit,
    }));
    const navigate = useNavigate();

    useEffect(() => {
        getLatestCreditLimit();
    }, []);

    const registerForm = useFormik<RegisterForm>({
        initialValues: {
            username: "",
            password: "",
        },

        onSubmit: (values, formikHelpers) => {
            register(values.username, values.password)
                .then(() => {
                    navigate("/login");
                })
                .catch(() => {
                    formikHelpers.setSubmitting(false);
                });
        },

        validate: async (values) => {
            try {
                const validationSchema = Yup.object({
                    username: Yup.string().required("Required username is not present"),
                    password: Yup.string().required("Required password is not present"),
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
        <Card>
            <Card.Header as="h2" className="text-center">
                Register
            </Card.Header>

            <Card.Body>
                <Card.Title>Open account today</Card.Title>
                <Card.Title as="h6" className="text-info">
                    Credit Limit - ${latestCreditLimit?.creditLimit}
                </Card.Title>
                <Form onSubmit={registerForm.handleSubmit} noValidate>
                    <fieldset disabled={registerForm.isSubmitting}>
                        <Form.Group controlId="username" className="mb-3">
                            <Form.Label>
                                <FontAwesomeIcon icon={faUser} /> Username
                            </Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="username"
                                onChange={registerForm.handleChange}
                                value={registerForm.values.username}
                            />
                        </Form.Group>
                        <Form.Group controlId="password" className="mb-3">
                            <Form.Label>
                                <FontAwesomeIcon icon={faLock} /> Password
                            </Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="password"
                                onChange={registerForm.handleChange}
                                value={registerForm.values.password}
                            />
                        </Form.Group>

                        <Row className="text-center">
                            <Col>
                                <Button type="submit" disabled={registerForm.isSubmitting}>
                                    Submit
                                </Button>
                            </Col>
                        </Row>
                    </fieldset>
                </Form>
            </Card.Body>

            <Card.Footer className="text-end">
                <Link to="/login" className="me-3">
                    Login
                </Link>
                <Link to="/login-as-bank-staff">Login As Bank Staff</Link>
            </Card.Footer>
        </Card>
    );
};

export default RegisterForm;
