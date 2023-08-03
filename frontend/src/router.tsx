import React from "react";
import {createBrowserRouter} from "react-router-dom";
import AuthorizationWrapper from "./components/AuthorizationWrapper";
import LoginPage from "./pages/login/LoginPage";
import AuthoritySelectPage from "./pages/RoleSelect/AuthoritySelectPage";
import StaffDash from "./pages/staff/StaffDash";
import StandardLayout from "./components/StandardLayout";
import UserDash from "./pages/user/UserDash";
import ClerkDash from "./pages/clerk/ClerkDash";
import AdminDash from "./pages/admin/AdminDash";
import CarRentalForm from "./pages/clerk/CarRentalForm";
import Test_StripeCheckoutSetup from "./pages/admin/Test_StripeCheckoutSetup";
import Test_StripeCheckoutPayment from "./pages/admin/Test_StripeCheckoutPayment";

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
        path: "rent/:shortId",
        element: (
            <AuthorizationWrapper>
                <StandardLayout>
                    <CarRentalForm/>
                </StandardLayout>
            </AuthorizationWrapper>
        )
    },
    //TODO(justin): below this is all test routes that should be deleted after properly integrated.
    {
        path:"test/stripeCheckout_Setup",
        element: (
            <AuthorizationWrapper>
                <StandardLayout>
                    <Test_StripeCheckoutSetup></Test_StripeCheckoutSetup>
                </StandardLayout>
            </AuthorizationWrapper>
        )
    },
    {
        path:"test/stripeCheckout_Payment",
        element: (
            <AuthorizationWrapper>
                <StandardLayout>
                    <Test_StripeCheckoutPayment></Test_StripeCheckoutPayment>
                </StandardLayout>
            </AuthorizationWrapper>
        )
    }
]);
