import { Button } from "react-bootstrap";
import useTitle from "../../hook/useTitle";
import useStore from "../../store/useStore";
import { Role } from "../../store/interface/IAuthSlice";

const LoginPage = () => {
    useTitle("Login");
    const login = useStore((state) => state.login);

    return <Button onClick={() => login("admin", "admin", Role.APPLICANT)}>Login</Button>;
};

export default LoginPage;
