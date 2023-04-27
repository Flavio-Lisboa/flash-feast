create table company_table
(
    id       int          not null auto_increment primary key,
    cnpj     varchar(14)  not null,
    name     varchar(50)  not null,
    email    varchar(50)  not null,
    password varchar(60)  not null,
    phone    varchar(11)  not null,
    role     varchar(12)  not null,
    logo     varchar(100) not null
);
