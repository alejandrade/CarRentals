import React from "react";
import {createBrowserRouter} from "react-router-dom";
import AuthorizationWrapper from "./components/AuthorizationWrapper";
import LoginPage from "./pages/login/LoginPage";
import DemographicsPage from "./pages/demographics/DemographicsPage";
import AuthoritySelectPage from "./pages/RoleSelect/AuthoritySelectPage";
import StaffDash from "./pages/staff/StaffDash";
import StandardLayout from "./components/StandardLayout";
import UserDash from "./pages/user/UserDash";
import ClerkDash from "./pages/clerk/ClerkDash";
import AdminDash from "./pages/admin/AdminDash";
import * as path from "path";
import CarRentalForm from "./pages/clerk/CarRentalForm";


function clerkChildren() {
    return [{
        path: "rent/:shortId",
        element: <CarRentalForm/>
    }];
}

function dashTabs() {
    return [
        {
            path: "user",
            element: (
                <UserDash/>
            )
        },
        {
            path: "clerk",
            children: clerkChildren(),
            element: (
                <ClerkDash/>
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
    ];
}

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
        children: dashTabs()
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
