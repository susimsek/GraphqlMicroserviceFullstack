import React from "react";
import {Container, Nav, Navbar} from "react-bootstrap";
import logo from '../../assets/logo.png';
import {useAuthContext} from "../Login/AuthTokenProvider";

const Header = () => {
    const [,updateAuthInfo, isLoggedIn] = useAuthContext();

    const onLogoutSuccess = () => {
        updateAuthInfo(null);
    }

    let links =(!isLoggedIn()) ? (
        <Nav className="ms-auto">
            <Nav.Link href="/login">Login</Nav.Link>
        </Nav>
    ) : (
        <Nav className="ms-auto">
            <Nav.Link onClick={onLogoutSuccess}>Logout</Nav.Link>
        </Nav>
    );
  return (
      <Navbar
              collapseOnSelect
              bg="primary"
              variant="dark"
              expand ="sm"
              className="mb-3 py-0">
          <Container>
              <Navbar.Brand href="/">
                  <img
                      alt="logo"
                      src={logo}
                      width="30"
                      height="30"
                      className="d-inline-block align-top"
                  />{' '}
                  Graphql
              </Navbar.Brand>
              <Navbar.Toggle className="m-2"/>
              <Navbar.Collapse id="responsive-navbar-nav">
                  <Nav className="me-auto">
                      <Nav.Link href="/">Home</Nav.Link>
                      <Nav.Link href="/products">Products</Nav.Link>
                  </Nav>
                  {links}
              </Navbar.Collapse>
          </Container>
      </Navbar>
  );
}

export default Header;
