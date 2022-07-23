import React from 'react';
import {Card, Container, Row, Spinner} from "react-bootstrap";
import {useAllProductsQuery} from "../../generated/graphql-types";
import {Link} from "react-router-dom";

const ProductList = () => {

    const { loading, data, error } = useAllProductsQuery()

    if (loading) {
        return <div className="d-flex justify-content-center">
            <Spinner animation="border" role="status">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    }

    if (error || !data) {
        return <div className="container">Error...</div>
    }

    return (
        <Container>
            <Row>
                {data.products.map(product => (
                    <Card key={product.id} className ="m-2" style={{ width: '18rem' }}>
                        <Card.Body>
                            <Card.Title><Link to={`/product/${product.id}`} className="text-primary text-decoration-none">
                                {product.name}</Link>
                            </Card.Title>
                            <Card.Text>
                                {product.description}
                            </Card.Text>
                        </Card.Body>
                    </Card>
                ))}
            </Row>
        </Container>
    )
}

export default ProductList;