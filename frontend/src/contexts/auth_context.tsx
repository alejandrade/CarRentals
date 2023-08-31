import React, {createContext, useContext, useState, ReactNode, useEffect} from "react";
import userService from "../services/user/UserService";

interface AuthContextType {
    token: string | null;
    login: (token: string, authorities: string[]) => void;
    logout: () => void;
    isAuthenticated: boolean;
    authorities: string[];
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
    const [token, setToken] = useState<string | null>(localStorage.getItem("token"));
    const [authorities, setAuthorities] = useState<string[]>([]);

    useEffect(()=>{
        const auths = localStorage.getItem("authorities");
        if (auths) {
            setAuthorities( auths.split(","));
        }

        if (token) {
            init();
        }

    }, []);

    async function init() {
        const authTest = await userService.authTest();
        if (!authTest) {
            logout();
        }
    }

    const login = (newToken: string, authorities: string[]) => {
        setToken(newToken);
        setAuthorities(authorities);
        // Optionally, you can store the token in localStorage/sessionStorage
        localStorage.setItem('token', newToken);
        localStorage.setItem('authorities', authorities.join(","));
    };

    const logout = () => {
        setToken(null);
        setAuthorities([])
        // Optionally, remove the token from localStorage/sessionStorage
        localStorage.removeItem('token');
        localStorage.removeItem('authorities')
    };

    return (
        <AuthContext.Provider value={{ token, login, logout, isAuthenticated: token !== null, authorities }}>
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
