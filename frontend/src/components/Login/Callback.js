import React, {useEffect, useState} from 'react';
import {
    doAuthorizationCodeFlow,
    getURIParameterByName,
    isLoggedIn,
    tradeCodeForToken
} from "../../authcode/authcode";
import {Container, Spinner} from "react-bootstrap";
import {useNavigate} from "react-router";
import {useAuthContext} from "./AuthTokenProvider";

const signInErrorText = 'Sorry, we were unable to sign you in. Please try again later.'

const Callback = () => {

    const [, updateAuthInfo] = useAuthContext();
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        processAuth()
    }, []);

    const processAuth = async () => {
        setLoading(true)
        if (isLoggedIn()) {
            if (loading) {
                setLoading(false)
                navigate('/')
            }
        } else {
            const code = getURIParameterByName('code', window.location.href)
            if (code) {
                try {
                    const response = await tradeCodeForToken(code)
                    updateAuthInfo(response)
                    setLoading(false)
                    navigate('/')
                } catch (err) {
                    setLoading(false)
                    navigate('/login', {
                        state: {
                            error: signInErrorText
                        }
                    })
                }
            } else {
                getAuthCode()
            }
        }
    }

    const getAuthCode = () => {
        setLoading(true)
        doAuthorizationCodeFlow()
    }

    if (loading) return <div className="d-flex justify-content-center">
        <Spinner animation="border" role="status">
            <span className="visually-hidden">Loading...</span>
        </Spinner>
    </div>;

    return (
    <Container>
        <p>Redirecting ...</p>
    </Container>
    )
};

export default Callback;