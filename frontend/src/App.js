import React from "react";
import './App.css';
import Home from "./components/Layout/Home";
import {Route, Routes} from "react-router-dom";
import PageNotFound from "./components/Layout/PageNotFound";
import Login from "./components/Login/Login";
import Callback from "./components/Login/Callback";
import {PrivateRoute} from "./components/Login/PrivateRoute";
import ProductList from "./components/Product/ProductList";
import ProductOverview from "./components/Product/ProductOverview";
import {Container} from "react-bootstrap";


const App = () => {

    return (
            <div className="App">
                <Container>
                    <Routes>
                        <Route exact path="/" element={<PrivateRoute />}>
                            <Route exact path="/" element={<Home />} />
                            <Route exact path="/products" element={<ProductList />} />
                            <Route exact path="/product/:id" element={<ProductOverview/>} />
                            <Route path="*" element={<PageNotFound />} />
                        </Route>
                            <Route exact path="/callback" element={<Callback />} />
                            <Route path="/login" element={<Login />} />
                    </Routes>
                </Container>
            </div>
    );
}

export default App;
