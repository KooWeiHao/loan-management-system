import { toast, ToastContainer } from "react-toastify";
import Route from "./route/Route";
import "react-toastify/dist/ReactToastify.css";

const App = () => {
    return (
        <>
            <Route />

            <ToastContainer position={toast.POSITION.BOTTOM_LEFT} hideProgressBar />
        </>
    );
};

export default App;
