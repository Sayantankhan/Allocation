USE `testsdb`;

CREATE TABLE IF NOT EXISTS t_robots (
    id varchar(20) NOT NULL PRIMARY KEY,
    name varchar(20),
    is_active BOOLEAN NOT NULL,
    created_at timestamp
) ENGINE=InnoDB;
