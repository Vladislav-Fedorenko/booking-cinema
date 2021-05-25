INSERT INTO cinema (name, created_at, updated_at)
VALUES
    ('Cinema 1', now(), now()),
    ('Cinema 2', now(), now()),
    ('Cinema 3', now(), now());

INSERT INTO hall (cinema_id, name, total_seats, created_at, updated_at)
VALUES
    (1, 'Hall 1', 10, now(), now()), -- 'Cinema 1', 'Hall 1', 10 seats
    (1, 'Hall 2', 10, now(), now()), -- 'Cinema 1', 'Hall 2', 10 seats
    (2, 'Hall 1', 10, now(), now()), -- 'Cinema 2', 'Hall 1', 10 seats
    (2, 'Hall 2', 10, now(), now()), -- 'Cinema 2', 'Hall 2', 10 seats
    (3, 'Hall 1', 10, now(), now()); -- 'Cinema 3', 'Hall 1', 10 seats

INSERT INTO movie_session (hall_id, datetime, created_at, updated_at)
VALUES
    (1, make_timestamptz(2021, 06, 01, 10, 00, 0), now(), now()), -- 'Cinema 1', 'Hall 1', '2021-06-01 10:00:00'
    (2, make_timestamptz(2021, 06, 02, 10, 00, 0), now(), now()), -- 'Cinema 1', 'Hall 2', '2021-06-02 10:00:00'
    (3, make_timestamptz(2021, 06, 01, 12, 00, 0), now(), now()), -- 'Cinema 2', 'Hall 1', '2021-06-01 12:00:00'
    (4, make_timestamptz(2021, 06, 01, 14, 00, 0), now(), now()), -- 'Cinema 2', 'Hall 2', '2021-06-01 14:00:00'
    (5, make_timestamptz(2021, 06, 03, 10, 00, 0), now(), now()); -- 'Cinema 3', 'Hall 1', '2021-06-03 10:00:00'

INSERT INTO booking (movie_session_id, seat_number, status, created_at, updated_at)
VALUES
    (1, 3, 'BOOKED', now(), now()), -- 'Cinema 1', 'Hall 1', '2021-06-01 10:00:00', seat = 3
    (1, 4, 'BOOKED', now(), now()), -- 'Cinema 1', 'Hall 1', '2021-06-01 10:00:00', seat = 4
    (1, 5, 'BOOKED', now(), now()), -- 'Cinema 1', 'Hall 1', '2021-06-01 10:00:00', seat = 5
    (2, 1, 'BOOKED', now(), now()), -- 'Cinema 1', 'Hall 2', '2021-06-02 10:00:00', seat = 1
    (2, 2, 'BOOKED', now(), now()), -- 'Cinema 1', 'Hall 2', '2021-06-02 10:00:00', seat = 2
    (3, 9, 'BOOKED', now(), now()), -- 'Cinema 2', 'Hall 1', '2021-06-01 12:00:00', seat = 9
    (4, 8, 'BOOKED', now(), now()), -- 'Cinema 2', 'Hall 2', '2021-06-01 14:00:00', seat = 8
    (5, 5, 'BOOKED', now(), now()); -- 'Cinema 3', 'Hall 1', '2021-06-03 10:00:00', seat = 5
