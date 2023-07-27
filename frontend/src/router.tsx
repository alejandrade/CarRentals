import React from "react";
import {createBrowserRouter} from "react-router-dom";
import AuthorizationWrapper from "./components/AuthorizationWrapper";
import LoginPage from "./pages/login/LoginPage";
import DemographicsPage from "./pages/demographics/DemographicsPage";
import AuthoritySelectPage from "./pages/RoleSelect/AuthoritySelectPage";
import StaffDash from "./pages/staff/StaffDash";

export default createBrowserRouter([
    {
        path: "/",
        element: (
            <AuthorizationWrapper>
                <LoginPage/>
            </AuthorizationWrapper>
        ),
    },
    {
        path: "/dash",
        element: (
            <AuthorizationWrapper>
                <AuthoritySelectPage/>
            </AuthorizationWrapper>
        ),
        children: [
            {
                path: "user",
                element: (
                    <AuthorizationWrapper>
                        <StaffDash/>
                    </AuthorizationWrapper>
                )
            },
            {
                path: "patron",
                element: (
                    <AuthorizationWrapper>
                        <p>patron</p>
                    </AuthorizationWrapper>
                )
            },
            {
                path: "admin",
                element: (
                    <AuthorizationWrapper>
                        <p>admin</p>
                    </AuthorizationWrapper>
                )
            },
            {
                path: "staff",
                element: (
                    <AuthorizationWrapper>
                        <p>staff</p>
                    </AuthorizationWrapper>
                )
            }
        ]
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
