CREATE TABLE user(
    id CHAR(8) NOT NULL,
    username VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL,
    created DATE,
    image_key CHAR(8),

    PRIMARY KEY(email)
);

CREATE TABLE watchlist(
    movie_id VARCHAR(32) NOT NULL,
    title VARCHAR(128) NOT NULL,
    tagline TEXT,
    overview TEXT,
    release_year CHAR(8),
    poster_url TEXT,
    rating FLOAT,
    countries TEXT,
    status ENUM('pend','done') DEFAULT 'pend',
    added DATE,
    email VARCHAR(128) NOT NULL,

    PRIMARY KEY(movie_id),

    CONSTRAINT fk_email
        FOREIGN KEY(email) REFERENCES user(email)
);

CREATE TABLE review(
    review_id INT AUTO_INCREMENT NOT NULL,
    email VARCHAR(128) NOT NULL,
    movie_id VARCHAR(32) NOT NULL,
    review_rating INT,
    review_text TEXT,
    review_added DATE,

    PRIMARY KEY(review_id),

    CONSTRAINT fk_movie_id
        FOREIGN KEY(movie_id) REFERENCES watchlist(movie_id)
);