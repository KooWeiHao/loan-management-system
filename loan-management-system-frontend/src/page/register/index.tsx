import useTitle from "../../hook/useTitle";
import AuthLayout from "../../layout/AuthLayout";
import RegisterForm from "./component/RegisterForm";

const RegisterPage = () => {
    useTitle("Register");

    return (
        <AuthLayout>
            <RegisterForm />
        </AuthLayout>
    );
};

export default RegisterPage;
