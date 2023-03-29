import { useEffect, useState } from "react";
import { Button, Col, Row, Table } from "react-bootstrap";
import moment from "moment";
import { ApplicantState, Status } from "../../../store/interface/IApplicantSlice";
import useStore from "../../../store/useStore";

const Applicant = () => {
    const [approvedApplicants, setApprovedApplicants] = useState<ApplicantState[]>([]);
    const [newApplicants, setNewApplicants] = useState<ApplicantState[]>([]);

    const { applicants, findAllApplicant, approveApplicant } = useStore((state) => ({
        applicants: state.applicants,
        findAllApplicant: state.findAllApplicant,
        approveApplicant: state.approveApplicant,
    }));

    useEffect(() => {
        findAllApplicant();
    }, []);

    useEffect(() => {
        setApprovedApplicants(applicants.filter((applicant) => applicant.status === Status.APPROVED));
        setNewApplicants(applicants.filter((applicant) => applicant.status === Status.PROCESSING));
    }, [applicants]);

    return (
        <>
            <Row>
                <Col>
                    <h4>New Applicants</h4>
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Applicant Id</th>
                                <th>Username</th>
                                <th>Created Date</th>
                                <th aria-label="approve button" />
                            </tr>
                        </thead>
                        <tbody>
                            {newApplicants.length > 0 &&
                                newApplicants.map((data, i) => (
                                    <tr key={data.applicantId}>
                                        <td>{i + 1}</td>
                                        <td>{data.applicantId}</td>
                                        <td>{data.username}</td>
                                        <td>{moment(data.createdDate).format("yyyy-MM-DD HH:mm:ss")}</td>
                                        <td className="text-center">
                                            <Button size="sm" onClick={() => approveApplicant(data.applicantId)}>
                                                Approve
                                            </Button>
                                        </td>
                                    </tr>
                                ))}
                        </tbody>
                    </Table>
                </Col>
            </Row>

            <Row>
                <Col>
                    <h4>Approved Applicants</h4>
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Applicant Id</th>
                                <th>Username</th>
                                <th>Created Date</th>
                                <th>Credit Facility Id</th>
                                <th>Credit Limit</th>
                                <th>Approved By</th>
                                <th>Approved Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            {approvedApplicants.length > 0 &&
                                approvedApplicants.map((data, i) => (
                                    <tr key={data.applicantId}>
                                        <td>{i + 1}</td>
                                        <td>{data.applicantId}</td>
                                        <td>{data.username}</td>
                                        <td>{moment(data.createdDate).format("yyyy-MM-DD HH:mm:ss")}</td>
                                        <td>{data.creditFacility?.creditFacilityId}</td>
                                        <td>{data.creditFacility?.creditLimit}</td>
                                        <td>{data.approvedBy}</td>
                                        <td>{moment(data.approvedDate).format("yyyy-MM-DD HH:mm:ss")}</td>
                                    </tr>
                                ))}
                        </tbody>
                    </Table>
                </Col>
            </Row>
        </>
    );
};

export default Applicant;
