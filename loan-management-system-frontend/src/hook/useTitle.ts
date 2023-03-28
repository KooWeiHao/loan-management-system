import { useEffect } from "react";

const useTitle = (title: string) => {
    useEffect(() => {
        document.title = `LMS - ${title}`;
    });
};

export default useTitle;
