import * as React from "react";
import {accessTokenExpiryKey, accessTokenKey, idTokenKey} from "../../constants/storage";
import {logout, setTokens} from "../../authcode/authcode";

const AuthContext = React.createContext({
    auth: {
        accessToken: null,
        accessTokenExpiry: null,
        idToken: null
    },
    updateAuthInfo: () => {
        // This is intentional
    },
    isLoggedIn: () => {
        // This is intentional
    }
});

type AuthContextProviderProps = {
    children: React.ReactNode;
};

export const AuthTokenProvider = ({ children }: AuthContextProviderProps) => {
    const [auth, setAuth] = React.useState({
        accessToken: undefined,
        accessTokenExpiry: undefined,
        idToken: undefined
    });

    React.useEffect(() => {
        setAuth({
            accessToken: localStorage.getItem(accessTokenKey) || null,
            accessTokenExpiry: localStorage.getItem(accessTokenExpiryKey) || null,
            idToken: localStorage.getItem(idTokenKey) || null
        })
    }, []);

    const updateAuthInfo = (data) => {
        if (!data) {
            logout();
            setAuth({
                accessToken: null,
                accessTokenExpiry: null,
                idToken: null
            });
        } else {
            const result = setTokens(data);
            setAuth(result);
        }
    }

    const isLoggedIn = () => {
        return !!auth.accessToken && !hasTokenExpired();
    }

    const hasTokenExpired = () => {
        let tokenExpired: boolean = false
        const accessTokenExpiry = auth.accessTokenExpiry
        if (accessTokenExpiry) {
            const now = new Date();
            const accessTokenExpiryDate = new Date(accessTokenExpiry)
            if (now >= accessTokenExpiryDate) {
                tokenExpired = true
            }
        }
        return tokenExpired
    }

    return auth.accessToken === undefined ? null : (
        <AuthContext.Provider value={{ auth, updateAuthInfo, isLoggedIn }}>
            {children}
        </AuthContext.Provider>
    );
}

export  const useAuthContext = () => {
    const { auth, updateAuthInfo,  isLoggedIn} = React.useContext(AuthContext);
    return [auth, updateAuthInfo, isLoggedIn];
}