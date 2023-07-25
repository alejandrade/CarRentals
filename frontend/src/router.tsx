import React from "react";
import {createBrowserRouter} from "react-router-dom";
import RedirectToRootIfNotAuthenticated from "./components/RedirectToRootIfNotAuthenticated";

export default createBrowserRouter([
    {
        path: "/",
        element: <div>Hello world!</div>,
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