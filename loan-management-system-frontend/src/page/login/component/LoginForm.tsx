import { Button, Card, Col, Form, Row } from "react-bootstrap";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useFormik, validateYupSchema, yupToFormErrors } from "formik";
import { toast } from "react-toastify";
import * as Yup from "yup";
import { ValidationError } from "yup";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLock, faUser } from "@fortawesome/free-solid-svg-icons";
import { Role } from "../../../store/interface/IAuthSlice";
import useStore from "../../../store/useStore";

interface LoginForm {
    username: string;
    password: string;
}

const LoginForm = () => {
    const login = useStore((state) => state.login);
    const location = useLocation();
    const navigate = useNavigate();

    const role = location.pathname === "/login-as-bank-staff" ? Role.BANK_STAFF : Role.APPLICANT;
    const targetPath = role === Role.BANK_STAFF ? "/dashboard" : "/";

    const loginForm = useFormik<LoginForm>({
        initialValues: {
            username: "",
            password: "",
        },

        onSubmit: (values, formikHelpers) => {
            login(values.username, values.password, role)
                .then(() => {
                    navigate(targetPath);
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
                Login {role === Role.BANK_STAFF && <>(Bank Staff)</>}
            </Card.Header>

            <Card.Body>
                <Form onSubmit={loginForm.handleSubmit} noValidate>
                    <fieldset disabled={loginForm.isSubmitting}>
                        <Form.Group controlId="username" className="mb-3">
                            <Form.Label>
                                <FontAwesomeIcon icon={faUser} /> Username
                            </Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="username"
                                onChange={loginForm.handleChange}
                                value={loginForm.values.username}
                            />
                        </Form.Group>
                        <Form.Group controlId="password" className="mb-3">
                            <Form.Label>
                                <FontAwesomeIcon icon={faLock} /> Password
                            </Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="password"
                                onChange={loginForm.handleChange}
                                value={loginForm.values.password}
                            />
                        </Form.Group>

                        <Row className="text-center">
                            <Col>
                                <Button type="submit">Submit</Button>
                            </Col>
                        </Row>
                    </fieldset>
                </Form>
            </Card.Body>

            <Card.Footer className="text-end">
                {role === Role.BANK_STAFF ? (
                    <Link to="/login" className="me-3">
                        Login
                    </Link>
                ) : (
                    <Link to="/login-as-bank-staff" className="me-3">
                        Login As Bank Staff
                    </Link>
                )}
                <Link to="/register">Register</Link>
            </Card.Footer>
        </Card>
    );
};

export default LoginForm;
