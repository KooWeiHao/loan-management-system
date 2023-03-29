import { Container } from "react-bootstrap";
import Footer from "../component/Footer";
import Header from "../component/Header";

interface AppLayoutProps {
    children: JSX.Element | JSX.Element[];
}

const AppLayout = ({ children }: AppLayoutProps) => {
    return (
        <Container fluid>
            <section id="header">
                <Header />
            </section>

            <section id="body">{children}</section>

            <section id="footer">
                <Footer />
            </section>
        </Container>
    );
};

export default AppLayout;
