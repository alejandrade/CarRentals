import React from "react";
import {createBrowserRouter} from "react-router-dom";
import AuthorizationWrapper from "./components/AuthorizationWrapper";
import LoginPage from "./pages/login/LoginPage";
import DemographicsPage from "./pages/demographics/DemographicsPage";
import AuthoritySelectPage from "./pages/RoleSelect/AuthoritySelectPage";
import StaffDash from "./pages/staff/StaffDash";
import StandardLayout from "./components/StandardLayout";
import UserDash from "./pages/user/UserDash";
import PatronDash from "./pages/patron/PatronDash";
import AdminDash from "./pages/admin/AdminDash";

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
                <StandardLayout>
                    <AuthoritySelectPage/>
                </StandardLayout>
            </AuthorizationWrapper>
        ),
        children: [
            {
                path: "user",
                element: (
                    <UserDash/>
                )
            },
            {
                path: "patron",
                element: (
                    <PatronDash/>
                )
            },
            {
                path: "admin",
                element: (
                    <AdminDash/>
                )
            },
            {
                path: "staff",
                element: (
                    <StaffDash/>
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
