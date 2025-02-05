create table itens
(
    id            uuid                     not null constraint pk_itens_id primary key,
    nome          varchar(250)             not null,
    peso          varchar(10)              not null,
    marca         varchar(10)              not null,
    created_date  timestamp with time zone not null,
    modified_date timestamp with time zone not null,
    created_by    varchar(100)             not null,
    modified_by   varchar(100)             not null
);

create table precos
(
    id            uuid                     not null constraint pk_precos_id primary key,
    valor         numeric(10, 2)           not null,
    local_id      uuid                     not null,
    item_id       uuid                     not null constraint fk_precos_item_id references itens ( id ),
    created_date  timestamp with time zone not null,
    modified_date timestamp with time zone not null,
    created_by    varchar(100)             not null,
    modified_by   varchar(100)             not null
);

create table itens_lista
(
    id            uuid                     not null constraint pk_itens_lista_id primary key,
    item_id       uuid                     not null constraint fk_itens_lista_item_id references itens ( id ),
    lista_compra_id  uuid                   not null,
    preco         numeric(10, 2)           not null,
    quantidade    integer                  not null,
    created_date  timestamp with time zone not null,
    modified_date timestamp with time zone not null,
    created_by    varchar(100)             not null,
    modified_by   varchar(100)             not null
);

