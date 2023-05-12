create table current_order
(
    id              int            not null auto_increment primary key,
    name            varchar(50)    not null,
    quantity        int            not null,
    total_price     decimal(10, 2) not null,
    image           varchar(100)   not null,
    status          varchar(20)    not null,
    date_time       datetime       not null,
    expiration_time bigint         not null,
    company_id      int            not null,
    user_id         int            not null,
    menu_id         int            not null,

    FOREIGN KEY (company_id) REFERENCES company_table (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user_table (id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE
);
