import { Button, Col, Row, Table } from "react-bootstrap";
import { useEffect, useState } from "react";
import useStore from "../../../store/useStore";
import CreditLimitAddModel from "./CreditLimitAddModel";

const CreditLimit = () => {
    const [isShow, setIsShow] = useState(false);

    const { creditLimits, defaultCreditLimit, findAllCreditLimit, getDefaultCreditLimit } = useStore((state) => ({
        creditLimits: state.creditLimits,
        defaultCreditLimit: state.defaultCreditLimit,
        findAllCreditLimit: state.findAllCreditLimit,
        getDefaultCreditLimit: state.getDefaultCreditLimit,
    }));

    useEffect(() => {
        getDefaultCreditLimit();
        findAllCreditLimit();
    }, []);

    return (
        <>
            <Row className="d-flex align-items-center">
                <Col md="6">
                    <strong>Default Credit Limit:</strong> ${defaultCreditLimit?.creditLimit}
                </Col>
                <Col md="6" className="text-end">
                    <Button onClick={() => setIsShow(true)}>Add Credit Limit</Button>
                </Col>
            </Row>
            <Row>
                <Col>
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Credit Limit</th>
                                <th>Credit Limit Date</th>
                            </tr>
                        </thead>
                        <tbody className={creditLimits.length > 0 ? "" : "border"}>
                            {creditLimits.length > 0 ? (
                                creditLimits.map((data, i) => (
                                    <tr key={i}>
                                        <td>{i + 1}</td>
                                        <td>{data.creditLimit}</td>
                                        <td>{data.creditLimitDate.toString()}</td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td />
                                    <td />
                                    <td />
                                </tr>
                            )}
                        </tbody>
                    </Table>
                </Col>
            </Row>

            <CreditLimitAddModel show={isShow} handleClose={() => setIsShow(false)} />
        </>
    );
};

export default CreditLimit;
