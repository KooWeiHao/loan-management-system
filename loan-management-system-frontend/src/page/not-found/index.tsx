import useTitle from "../../hook/useTitle";

const NotFoundPage = () => {
    useTitle("Not Found");

    return <h1 className="text-danger">Page Not Found</h1>;
};

export default NotFoundPage;
