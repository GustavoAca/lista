CREATE TABLE LISTAS_COMPRA (
   	ID                   UUID                       NOT NULL,
    USUARIO_ID           UUID                       NOT NULL,
   	CREATED_DATE         TIMESTAMP WITH TIME ZONE   NOT NULL,
   	MODIFIED_DATE        TIMESTAMP WITH TIME ZONE   NOT NULL,
   	CREATED_BY           VARCHAR(100)               NOT NULL,
   	MODIFIED_BY          VARCHAR(100)               NOT NULL,
   	CONSTRAINT LISTAS_COMPRA_PK PRIMARY KEY ( ID )
);

CREATE TABLE ITENS (
    ID                   UUID                       NOT NULL,
	NOME                 VARCHAR(250)               NOT NULL,
	PESO                 VARCHAR(10)                NOT NULL,
	MARCA                VARCHAR(10)                NOT NULL,
    PRECO_ID             UUID                       NOT NULL,
    CREATED_DATE         TIMESTAMP WITH TIME ZONE   NOT NULL,
    MODIFIED_DATE        TIMESTAMP WITH TIME ZONE   NOT NULL,
    CREATED_BY           VARCHAR(100)               NOT NULL,
    MODIFIED_BY          VARCHAR(100)               NOT NULL,
    CONSTRAINT ITENS_PK PRIMARY KEY ( ID )
);

CREATE TABLE PRECOS (
    ID                   UUID                       NOT NULL,
    PRECO                NUMERIC(10,2)              NOT NULL,
    LOCAL_ID             UUID                       NOT NULL,
    CREATED_DATE         TIMESTAMP WITH TIME ZONE   NOT NULL,
    MODIFIED_DATE        TIMESTAMP WITH TIME ZONE   NOT NULL,
    CREATED_BY           VARCHAR(100)               NOT NULL,
    MODIFIED_BY          VARCHAR(100)               NOT NULL,
    CONSTRAINT PRECOS_PK PRIMARY KEY ( ID )
);

ALTER TABLE ITENS ADD CONSTRAINT ITENS_PRECO_FK FOREIGN KEY ( PRECO_ID ) REFERENCES PRECOS( ID );

CREATE TABLE ITENS_TEM_PRECOS (
    ITEM_ID            UUID                       NOT NULL,
    PRECO_ID           UUID                       NOT NULL,
    CONSTRAINT FK_ITENS_TEM_PRECOS FOREIGN KEY (ITEM_ID) REFERENCES ITENS(ID),
    CONSTRAINT FK_PRECO_ID FOREIGN KEY (PRECO_ID) REFERENCES PRECOS(ID)
);

CREATE TABLE ITENS_LISTA (
    ID                   UUID                       NOT NULL,
    QUANTIDADE           INT                        NOT NULL,
    ITEM_ID              UUID                       NOT NULL,
 	CREATED_DATE         TIMESTAMP WITH TIME ZONE   NOT NULL,
 	MODIFIED_DATE        TIMESTAMP WITH TIME ZONE   NOT NULL,
    CREATED_BY           VARCHAR(100)               NOT NULL,
 	MODIFIED_BY          VARCHAR(100)               NOT NULL,
 	CONSTRAINT ITENS_LISTA_PK PRIMARY KEY ( ID )
);