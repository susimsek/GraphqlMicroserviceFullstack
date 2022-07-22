import axios from "axios";
import {clientId, clientSecret} from "../constants/oauth2";


export const getAccessToken = (tokenUrl, params) => {
    return axios.post(tokenUrl, new URLSearchParams(params), {
        auth: {
            username: clientId,
            password: clientSecret
        }
    })
}