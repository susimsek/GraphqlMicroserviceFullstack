import React, {useEffect, useState} from 'react';
import {Alert, Button, Container} from "react-bootstrap";
import {doAuthorizationCodeFlow, isLoggedIn} from "../../authcode/authcode";
import {useLocation, useNavigate} from "react-router";

const Login = () => {
    const navigate = useNavigate();
    const [errorMessage, setErrorMessage] = useState("");

    const { state } = useLocation();
    const { error } = state || "";


    useEffect(() => {
        if (error) {
            setErrorMessage(error)
        }
    }, [error]);

    const onClickLogin = () => {
        setErrorMessage("")
        if(isLoggedIn()) {
            navigate('/')
        } else {
            doAuthorizationCodeFlow();
        }
    }

    return (
        <Container>
            <h1 className="text-left">Please sign in</h1>
            {errorMessage && <Alert variant="danger" className="d-inline-block">
                {errorMessage}
            </Alert>}
            <p className="text-left">Chose the account to login with.</p>
            <p>
                <Button variant="primary" size="lg" onClick={onClickLogin}>Login</Button>
            </p>
        </Container>
    );
};

export default Login;