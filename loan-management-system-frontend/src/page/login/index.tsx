import { Button } from "react-bootstrap";
import { toast } from "react-toastify";
import useTitle from "../../hook/useTitle";
import useStore from "../../store/useStore";
import { Role } from "../../store/interface/IAuthSlice";

const LoginPage = () => {
    useTitle("Login");
    const login = useStore((state) => state.login);

    return (
        <Button onClick={() => login("admin", "admin", Role.APPLICANT).catch((error) => toast.error(error))}>
            Login
        </Button>
    );
};

export default LoginPage;
