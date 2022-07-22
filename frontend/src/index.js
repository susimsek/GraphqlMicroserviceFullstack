import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AuthTokenProvider} from "./components/Login/AuthTokenProvider";
import {BrowserRouter as Router} from "react-router-dom";
import Header from "./components/Layout/Header";
import {createApolloClient} from "./apollo";
import {ApolloProvider} from "@apollo/client";

const client = createApolloClient();

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <ApolloProvider client={client}>
        <Router>
            <AuthTokenProvider>
                <Header />
                <App />
            </AuthTokenProvider>
        </Router>
    </ApolloProvider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
