create table menu
(
    id                 int            not null auto_increment primary key,
    name               varchar(50)    not null,
    category           varchar(20)    not null,
    description        varchar(200),
    available_quantity int            not null,
    price              decimal(10, 2) not null,
    image              varchar(100)   not null,
    company_id         int            not null,

    FOREIGN KEY (company_id) REFERENCES company_table (id) ON DELETE CASCADE
);