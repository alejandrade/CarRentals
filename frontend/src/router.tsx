import React from "react";
import {createBrowserRouter} from "react-router-dom";
import RedirectToRootIfNotAuthenticated from "./components/RedirectToRootIfNotAuthenticated";
import LoginPage from "./pages/login/LoginPage";

export default createBrowserRouter([
    {
        path: "/",
        element: <LoginPage/>,
    },
    {
        path: "/secured",
        element: (
            <RedirectToRootIfNotAuthenticated>
                <div>Hello world test!</div>
            </RedirectToRootIfNotAuthenticated>
        ),
    },
]);