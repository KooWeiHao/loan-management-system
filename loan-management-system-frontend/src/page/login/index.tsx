import useTitle from "../../hook/useTitle";
import AuthLayout from "../../layout/AuthLayout";
import LoginForm from "./component/LoginForm";

const LoginPage = () => {
    useTitle("Login");

    return (
        <AuthLayout>
            <LoginForm />
        </AuthLayout>
    );
};

export default LoginPage;
