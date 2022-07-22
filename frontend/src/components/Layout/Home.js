import React from "react";
import {Container, Row} from "react-bootstrap";

const Home = () => {
  return (
      <div className="landing">
        <div className="light-overlay landing-inner text-dark">
          <Container>
            <Row>
              <div className="col-md-12 text-center">
                <h1 className="display-5 mb-4">Introduction To Graphql Microservice Fullstack App</h1>
              </div>
            </Row>
          </Container>
        </div>
      </div>
  );
}

export default Home;
