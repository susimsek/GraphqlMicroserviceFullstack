import React from 'react';
import {Badge, Card, Container, ListGroup, Row, Spinner} from "react-bootstrap";
import {useProductAndReviewsQuery} from "../../generated/graphql-types";
import {useParams} from "react-router";
import StarRating from "./StarRating";

const ProductOverview = () => {

    const { id } = useParams();

    const { loading, data, error } = useProductAndReviewsQuery({
        variables: {
            id
        },
    })

    if (loading) {
        return <div className="d-flex justify-content-center">
            <Spinner animation="border" role="status">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    }

    if (error) {
        return <Container>Error...</Container>
    }

    return (
        <Container>
            <Row>
                <Card className="mb-4">
                    <Card.Header className="text-center text-primary">Overview</Card.Header>
                    <Card.Body>
                        <Card.Title>{data.product.name}</Card.Title>
                        <div className="mb-2">
                            <Badge bg="secondary">{data.product.reviews.length}</Badge> Review
                        </div>
                        <Card.Text>{data.product.description}</Card.Text>
                    </Card.Body>
                </Card>
                <Card>
                    <Card.Header className="text-center text-primary">Reviews</Card.Header>
                    <ListGroup variant="flush">
                        {data.product.reviews.length === 0 && <ListGroup.Item>There are no reviews</ListGroup.Item>}
                        {data.product.reviews.map(review => (
                                <ListGroup.Item key={review.id}>
                                    <>
                                        {review.text}
                                        <StarRating rating={review.starRating}/>
                                    </>
                                </ListGroup.Item>
                        ))}
                    </ListGroup>
                </Card>
            </Row>
        </Container>
    )
}

export default ProductOverview;