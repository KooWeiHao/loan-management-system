import { Card, Col, Nav, Row, Tab } from "react-bootstrap";
import useTitle from "../../hook/useTitle";
import AppLayout from "../../layout/AppLayout";
import Applicant from "./component/Applicant";
import CreditLimit from "./component/CreditLimit";
import InterestRate from "./component/InterestRate";

const DashboardPage = () => {
    useTitle("Dashboard");

    const tabComponents = [
        {
            key: "applicant",
            title: "Applicants",
            component: <Applicant />,
        },
        {
            key: "credit-limit",
            title: "Credit Limit",
            component: <CreditLimit />,
        },
        {
            key: "interest-rate",
            title: "Interest Rate",
            component: <InterestRate />,
        },
    ];

    return (
        <AppLayout>
            <Tab.Container defaultActiveKey="applicant">
                <Row>
                    <Col md={2}>
                        <Nav variant="pills" className="flex-column border-start border-end">
                            {tabComponents.map((tab, i) => (
                                <Nav.Item
                                    key={tab.key}
                                    className={`border-top ${i === tabComponents.length - 1 ? "border-bottom" : ""}`}
                                >
                                    <Nav.Link eventKey={tab.key}>{tab.title}</Nav.Link>
                                </Nav.Item>
                            ))}
                        </Nav>
                    </Col>
                    <Col md={10}>
                        <Tab.Content>
                            {tabComponents.map((tab) => (
                                <Tab.Pane eventKey={tab.key} key={tab.key}>
                                    <Card>
                                        <Card.Header as="h3">{tab.title}</Card.Header>

                                        <Card.Body>{tab.component}</Card.Body>
                                    </Card>
                                </Tab.Pane>
                            ))}
                        </Tab.Content>
                    </Col>
                </Row>
            </Tab.Container>
        </AppLayout>
    );
};

export default DashboardPage;
