import React, { ReactNode } from 'react';
import { useLocation, Navigate } from 'react-router-dom';
import {useAuth} from "../contexts/auth_context";
interface Props {
    children: ReactNode;
}
const AuthorizationWrapper: React.FC<Props> = ({ children }) => {
    const { pathname } = useLocation();
    const { isAuthenticated } = useAuth();
    if (pathname !== '/' && !isAuthenticated) {
        return <Navigate to="/" />;
    } else if (pathname === '/' && isAuthenticated) {
        return <Navigate to="/dash" />;
    }

    return <>{children}</>;
};

export default AuthorizationWrapper;
