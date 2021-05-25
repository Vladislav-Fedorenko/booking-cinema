CREATE TABLE IF NOT EXISTS cinema (
    id         SERIAL      PRIMARY KEY,
    name       VARCHAR     NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS hall (
    id          SERIAL      PRIMARY KEY,
    cinema_id   INTEGER     NOT NULL     REFERENCES cinema,
    name        VARCHAR     NOT NULL,
    total_seats INTEGER     NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL,
    updated_at  TIMESTAMPTZ NOT NULL,
    UNIQUE (cinema_id, name)
);

CREATE TABLE IF NOT EXISTS movie_session (
    id         SERIAL      PRIMARY KEY,
    hall_id    INTEGER     NOT NULL     REFERENCES hall,
    datetime   TIMESTAMPTZ NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    UNIQUE (hall_id, datetime)
);

CREATE TYPE seat_status_enum AS ENUM ('BOOKED');

CREATE TABLE IF NOT EXISTS booking (
    id               SERIAL           PRIMARY KEY,
    movie_session_id INTEGER          NOT NULL     REFERENCES movie_session,
    seat_number      INTEGER          NOT NULL     CHECK (seat_number > 0),
    status           seat_status_enum NOT NULL,
    created_at       TIMESTAMPTZ      NOT NULL,
    updated_at       TIMESTAMPTZ      NOT NULL,
    UNIQUE(movie_session_id, seat_number, status)
);
