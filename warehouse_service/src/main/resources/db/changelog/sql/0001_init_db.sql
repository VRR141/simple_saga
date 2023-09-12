create schema warehouse;

create table warehouse.warehouse(
    id bigserial primary key ,
    warehouse_identifier uuid not null unique
);

create table warehouse.warehouse_product(
    warehouse_id bigint references warehouse.warehouse(id) not null ,
    product_uuid uuid not null,
    amount integer not null check ( amount >= 0 ),
    primary key (warehouse_id, product_uuid)
);