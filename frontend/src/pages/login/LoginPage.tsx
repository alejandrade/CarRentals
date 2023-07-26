// LoginPage.tsx

import * as React from 'react';
import StandardLayout from "../../components/StandardLayout";
import LoginForm from "./LoginForm";
import { Card, CardContent, CardHeader } from "@mui/material";

const LoginPage: React.FC = () => {
    return (
        <StandardLayout>
            <Card style={{ maxWidth: '400px', margin: '0 auto' }}>
                <CardHeader title="Car Rental Sign In" />
                <CardContent>
                    <LoginForm />
                </CardContent>
            </Card>
        </StandardLayout>
    );
}

export default LoginPage;
