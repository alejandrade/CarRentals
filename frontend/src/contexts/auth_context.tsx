import React, { createContext, useContext, useState, ReactNode } from "react";

interface AuthContextType {
    token: string | null;
    login: (token: string) => void;
    logout: () => void;
    isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
    const [token, setToken] = useState<string | null>(
        localStorage.getItem("token"));

    const login = (newToken: string) => {
        setToken(newToken);
        // Optionally, you can store the token in localStorage/sessionStorage
        localStorage.setItem('token', newToken);
    };

    const logout = () => {
        setToken(null);
        // Optionally, remove the token from localStorage/sessionStorage
        localStorage.removeItem('token');
    };

    return (
        <AuthContext.Provider value={{ token, login, logout, isAuthenticated: token !== null }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = (): AuthContextType => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
};
