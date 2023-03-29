import { useEffect } from "react";
import { Button, Container, Navbar } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircleUser } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";
import { ReactComponent as LogoImage } from "../asset/image/logo.svg";
import { Role } from "../store/interface/IAuthSlice";
import useStore from "../store/useStore";

const Header = () => {
    const { auth, logout } = useStore((state) => ({
        auth: state.auth,
        logout: state.logout,
    }));
    const navigate = useNavigate();

    const defaultPath = auth.role === Role.BANK_STAFF ? "/dashboard" : "/";

    useEffect(() => {
        document.body.style.paddingTop = "70px";
        document.body.style.paddingBottom = "70px";
        return () => {
            document.body.style.paddingTop = "unset";
            document.body.style.paddingBottom = "unset";
        };
    });

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    return (
        <Navbar expand="lg" bg="light" variant="light" fixed="top" className="shadow-sm mb-1">
            <Container fluid>
                <Navbar.Brand href={defaultPath}>
                    <LogoImage role="img" width="30px" height="30px" className="align-top me-2" />
                    Loan Management System
                </Navbar.Brand>

                <Navbar.Toggle aria-controls="navbar" />

                <Navbar.Collapse id="navbar" className="justify-content-end text-end">
                    <div className="py-1 float-end d-flex align-items-center">
                        <FontAwesomeIcon icon={faCircleUser} className="me-1" /> {auth.username?.toUpperCase()}{" "}
                        <Button size="sm" className="ms-2" onClick={handleLogout}>
                            Logout
                        </Button>
                    </div>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Header;
