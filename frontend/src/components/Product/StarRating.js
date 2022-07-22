const StarRating = (props) => {
    const {rating} = props;
    return (
        <div className="star-rating">
            {[...Array(5)].map((star, index) => {
                index += 1;
                return (
                    <button
                        id="rating-button"
                        type="button"
                        key={index}
                        className={index <= rating ? "on" : "off"}
                    >
                        <span className="star">&#9733;</span>
                    </button>
                );
            })}
        </div>
    );
};

export default StarRating;