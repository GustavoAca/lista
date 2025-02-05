CREATE TABLE itens
(
    ID            UUID                     NOT NULL CONSTRAINT PK_ITENS_ID primary key,
    NOME          VARCHAR(250)             NOT NULL,
    PESO          VARCHAR(10)              NOT NULL,
    MARCA         VARCHAR(10)              NOT NULL,
    CREATED_DATE  TIMESTAMP WITH TIME ZONE NOT NULL,
    MODIFIED_DATE TIMESTAMP WITH TIME ZONE NOT NULL,
    CREATED_BY    VARCHAR(100)             NOT NULL,
    MODIFIED_BY   VARCHAR(100)             NOT NULL
);

CREATE TABLE PRECOS
(
    ID            UUID                     NOT NULL CONSTRAINT PK_PRECOS_ID primary key,
    VALOR         NUMERIC(10, 2)           NOT NULL,
    LOCAL_ID      UUID                     NOT NULL,
    ITEM_ID       UUID                     NOT NULL CONSTRAINT FK_PRECOS_ITEM_ID references itens ( ID ),
    CREATED_DATE  TIMESTAMP WITH TIME ZONE NOT NULL,
    MODIFIED_DATE TIMESTAMP WITH TIME ZONE NOT NULL,
    CREATED_BY    VARCHAR(100)             NOT NULL,
    MODIFIED_BY   VARCHAR(100)             NOT NULL
);

CREATE TABLE itens_lista
(
    ID                  UUID                        NOT NULL CONSTRAINT PK_ITENS_LISTA_ID primary key,
    ITEM_ID             UUID                        NOT NULL CONSTRAINT FK_ITENS_LISTA_ITEM_ID references itens ( ID ),
    LISTA_COMPRA_ID     UUID                        NOT NULL,
    PRECO               NUMERIC(10, 2)              NOT NULL,
    QUANTIDADE          INTEGER                     NOT NULL,
    CREATED_DATE        TIMESTAMP WITH TIME ZONE    NOT NULL,
    MODIFIED_DATE       TIMESTAMP WITH TIME ZONE    NOT NULL,
    CREATED_BY          VARCHAR(100)                NOT NULL,
    MODIFIED_BY         VARCHAR(100)                NOT NULL
);

