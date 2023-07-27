import React from "react";
import {createBrowserRouter} from "react-router-dom";
import AuthorizationWrapper from "./components/AuthorizationWrapper";
import LoginPage from "./pages/login/LoginPage";
import DashPage from "./pages/dash/DashPage";
import DemographicsPage from "./pages/demographics/DemographicsPage";

export default createBrowserRouter([
    {
        path: "/",
        element: (
            <AuthorizationWrapper>
                <LoginPage/>
            </AuthorizationWrapper>
        )
        ,
    },
    {
        path: "/dash",
        element: (
            <AuthorizationWrapper>
                <DashPage/>
            </AuthorizationWrapper>
        ),
    },
    {
        path: "/demographics",
        element: (
            <AuthorizationWrapper>
                <DemographicsPage/>
            </AuthorizationWrapper>
        ),
    },
]);