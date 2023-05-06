create table user_location
(
    user_id      int         not null primary key,
    state        varchar(50) not null,
    city         varchar(50) not null,
    street       varchar(50) not null,
    house_number varchar(10) not null,
    FOREIGN KEY (user_id) REFERENCES user_table (id) ON DELETE CASCADE
);