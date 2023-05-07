create table company_location
(
    company_id     int         not null primary key,
    zip_code       varchar(8)  not null,
    state          varchar(50) not null,
    city           varchar(50) not null,
    neighborhood   varchar(50) not null,
    street         varchar(50) not null,
    company_number varchar(10) not null,
    FOREIGN KEY (company_id) REFERENCES company_table (id) ON DELETE CASCADE
);