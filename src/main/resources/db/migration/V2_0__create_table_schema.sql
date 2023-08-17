USE `testsdb`;

CREATE TABLE IF NOT EXISTS t_tasks (
    id varchar(20) NOT NULL PRIMARY KEY,
    description varchar(60),
    is_active BOOLEAN NOT NULL,
    created_at timestamp,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS t_orders (
    id varchar(20) NOT NULL PRIMARY KEY,
    table_id varchar(60),
    is_active BOOLEAN,
    created_at timestamp,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;