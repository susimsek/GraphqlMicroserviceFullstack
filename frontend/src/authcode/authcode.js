import * as crypto from 'crypto-js';
import sha256 from 'crypto-js/sha256';
import Base64 from 'crypto-js/enc-base64';
import {authorizeApiUri, tokenApiUri} from "../constants/url";
import {getAccessToken} from "../api/apiCalls";
import {
    accessTokenExpiryKey, accessTokenKey,
    authCodeNonceKey,
    authCodeStateKey,
    authCodeVerifierKey,
    idTokenKey, refreshTokenKey
} from "../constants/storage";
import {clientId} from "../constants/oauth2";

const scope = 'openid';
const grantType = 'authorization_code';
const useState = true;
const usePkce = true;
const useNonce = true;

export const isLoggedIn = () => {
    return hasAccessToken() && !hasTokenExpired();
}

export const tradeCodeForToken = async (code) => {
    return await getToken(
        tokenApiUri,
        '/callback',
        scope,
        usePkce,
        useState,
        useNonce,
        code
    )
}

const getToken = async (tokenUrl,
                         callBackPath,
                         scope,
                         usePkce,
                         useState,
                         useNonce,
                         code) => {
    try {
        if (useState) {
            const codeState = getURIParameterByName(
                'state',
                window.location.href
            )
            const origState = localStorage.getItem(authCodeStateKey)
            if (codeState !== origState) {
                const stateErr = 'State does not match'
                throw new Error(stateErr)
            }
            localStorage.removeItem(authCodeStateKey)
        }

        const params = {
            grant_type: grantType,
            redirect_uri: buildRedirectUri(callBackPath),
            scope: scope,
            code: code
        }
        if (scope) {
            params.scope = scope
        }

        if (usePkce) {
            const verifier = localStorage.getItem(authCodeVerifierKey)
            if (verifier) {
                params.code_verifier = verifier
            }
        }
        const response = await getAccessToken(tokenUrl, params);
        if (!response.data || !response.data.access_token) {
            throw new Error('Access token not present in token response')
        }
        if (response.data.error) {
            throw new Error(response.data.error_description)
        }
        return response.data
    }catch (error) {
        console.log(error)
        throw error
    }
}

export const setTokens = (response) => {
    // save id token in storage and check nonce
    const idToken = response.id_token
    if (idToken) {
        const origNonce = localStorage.getItem(authCodeNonceKey)
        if (useNonce && origNonce) {
            const idTokenObj = JSON.parse(parseJwt(idToken))
            if (idTokenObj) {
                if (idTokenObj.nonce !== origNonce) {
                    throw new Error('Nonce does not match')
                }
            }
        }
        if (origNonce) {
            localStorage.removeItem(authCodeNonceKey)
        }
        localStorage.setItem(idTokenKey, idToken)
    }

    const now = new Date()
    const expiryDate = new Date(now.getTime() + response.expires_in * 1000)
    localStorage.setItem(accessTokenExpiryKey, expiryDate.toString())

    localStorage.setItem(accessTokenKey, response.access_token)

    const verifier = localStorage.getItem(authCodeVerifierKey)
    if (verifier) {
        localStorage.removeItem(authCodeVerifierKey)
    }
    return {
        accessToken: response.access_token,
        accessTokenExpiry: expiryDate,
        idToken: idToken
    }
}


export const doAuthorizationCodeFlow = () => {
    const codeLocation = getAuthorizeUri(
        authorizeApiUri, '/callback',
        clientId, scope,
        usePkce, useState, useNonce)
    window.location.replace(codeLocation)
}

export const getAuthorizeUri = (
    authUrl,
    callBackPath,
    clientId,
    scope,
    usePkce,
    useState,
    useNonce
) => {
    let url = authUrl
    url += '?client_id=' + clientId
    url += '&response_type=code'
    if (scope) {
        url += '&scope=' + encodeURIComponent(scope)
    }

    url += '&redirect_uri=' + encodeURIComponent(buildRedirectUri(callBackPath))

    if (usePkce) {
        const verifier = generateCodeVerifier()
        const challenge = generateCodeChallenge(verifier)
        localStorage.setItem(authCodeVerifierKey, verifier)

        url += '&code_challenge=' + challenge
        url += '&code_challenge_method=S256'
    } else {
        localStorage.removeItem(authCodeVerifierKey)
    }

    if (useState) {
        const state = generateState()
        localStorage.setItem(authCodeStateKey, state)
        url += '&state=' + state
    } else {
        localStorage.removeItem(authCodeStateKey)
    }

    if (useNonce) {
        const nonce = generateNonce()
        localStorage.setItem(authCodeNonceKey, nonce)

        url += '&nonce=' + nonce
    } else {
        localStorage.removeItem(authCodeNonceKey)
    }
    return url
}

export const base64URLEncode = str => {
    return str
        .toString(Base64)
        .replace(/\+/g, '-')
        .replace(/\//g, '_')
        .replace(/=/g, '')
}

export const generateCodeVerifier = () => {
    return base64URLEncode(crypto.lib.WordArray.random(32));
}

export const generateCodeChallenge = (codeVerifier) => {
    return base64URLEncode(sha256(codeVerifier));
}

export const generateState = () => {
    return base64URLEncode(crypto.lib.WordArray.random(32));
}

export const generateNonce = () => {
    return base64URLEncode(crypto.lib.WordArray.random(32));
}


export const buildRedirectUri = path => {
    let pathMod = path
    if (!pathMod) {
        pathMod = ''
    }
    if (
        pathMod.toUpperCase().includes('HTTP://') ||
        pathMod.toUpperCase().includes('HTTPS://')
    ) {
        return pathMod
    } else {
        let hostName = window.location.hostname === 'localhost' ? '127.0.0.1': window.location.hostname
        return window.location.protocol+'//'+hostName+(window.location.port ? ':'+window.location.port: '') + pathMod
    }
}

export const getURIParameterByName = (name, url) => {
    name = name.replace(/[[\]]/g, '\\$&')
    const regex = new RegExp('[?&#]' + name + '(=([^&#]*)|&|#|$)')
    const results = regex.exec(url)
    if (!results) return null
    if (!results[2]) return ''
    return decodeURIComponent(results[2].replace(/\+/g, ' '))
}

export const parseJwt = (token: string) => {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    return decodeURIComponent(
        atob(base64)
            .split('')
            .map(function (c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
            })
            .join('')
    )
}

export const accessToken = () => {
   return localStorage.getItem(accessTokenKey)
}

export const refreshToken = () => {
    return localStorage.getItem(refreshTokenKey)
}

export const hasAccessToken = () => {
    return !!accessToken()
}

export const hasTokenExpired = () => {
    let tokenExpired: boolean = false
    const accessTokenExpiry: any = localStorage.getItem(accessTokenExpiryKey)
    if (accessTokenExpiry) {
        const now = new Date();
        const accessTokenExpiryDate = new Date(accessTokenExpiry)
        if (now >= accessTokenExpiryDate) {
            tokenExpired = true
        }
    }
    return tokenExpired
}

export const logout = () => {
    localStorage.removeItem(accessTokenKey)
    localStorage.removeItem(accessTokenExpiryKey)
    localStorage.removeItem(idTokenKey)
}


export class doLogoutFlow {
}

export class removeTokens {
}