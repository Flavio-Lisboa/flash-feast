create table user_table
(
    id         int         not null auto_increment primary key,
    first_name varchar(20) not null,
    last_name  varchar(20) not null,
    email      varchar(50) not null,
    password   varchar(60) not null,
    phone      varchar(11) not null,
    cpf        varchar(11) not null,
    role       varchar(9)  not null
);