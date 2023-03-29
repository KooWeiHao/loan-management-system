import { Col, Container, Row } from "react-bootstrap";

interface AuthLayoutProps {
    children: JSX.Element | JSX.Element[];
}

const AuthLayout = ({ children }: AuthLayoutProps) => {
    return (
        <Container>
            <Row className="form-center">
                <Col>{children}</Col>
            </Row>
        </Container>
    );
};

export default AuthLayout;
