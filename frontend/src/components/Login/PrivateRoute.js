import {Navigate, Outlet} from "react-router";
import {useAuthContext} from "./AuthTokenProvider";

export const PrivateRoute = () => {
    const [,,isLoggedIn] = useAuthContext();
    return (
        <div>
            {isLoggedIn() ? <Outlet/> : <Navigate to="/login" />}
        </div>
    )
}