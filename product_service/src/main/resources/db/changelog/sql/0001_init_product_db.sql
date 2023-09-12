create schema product;

create table product.product(
    id bigserial primary key ,
    name varchar not null ,
    description varchar,
    product_identifier uuid not null unique
)