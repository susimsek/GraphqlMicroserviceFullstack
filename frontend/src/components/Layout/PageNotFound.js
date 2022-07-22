import React from "react";
import {Alert, Button} from "react-bootstrap";
import {Link} from "react-router-dom";

const PageNotFound = () => {
  return (
      <div className="container">
          <Alert variant="danger" className="text-center">
              The Page you're looking for was not found.
          </Alert>
          <Link to="/">
              <Button variant="primary" size="lg">Go Back</Button>
          </Link>
      </div>
  );
}

export default PageNotFound;
