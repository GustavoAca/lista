CREATE TABLE PRECOS (
    ID                   UUID                       NOT NULL,
    PRECO                NUMERIC(10,2)              NOT NULL,
    LOCAL_ID             UUID                       NOT NULL,
    CREATED_DATE         TIMESTAMP WITH TIME ZONE   NOT NULL,
    MODIFIED_DATE        TIMESTAMP WITH TIME ZONE   NOT NULL,
    CREATED_BY           VARCHAR(100)               NOT NULL,
    MODIFIED_BY          VARCHAR(100)               NOT NULL,
    CONSTRAINT PK_PRECOS_ID PRIMARY KEY ( ID )
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
    CONSTRAINT FK_ITENS_PRECO_ID FOREIGN KEY ( PRECO_ID ) REFERENCES PRECOS( ID ),
    CONSTRAINT PK_ITENS_ID PRIMARY KEY ( ID )
);

CREATE TABLE ITENS_TEM_PRECOS (
    ITEM_ID            UUID                       NOT NULL,
    PRECO_ID           UUID                       NOT NULL,
    CONSTRAINT FK_ITENS_TEM_PRECOS_ITEM_ID FOREIGN KEY ( ITEM_ID ) REFERENCES ITENS( ID ),
    CONSTRAINT FK_ITENS_TEM_PRECOS_PRECO_ID FOREIGN KEY ( PRECO_ID ) REFERENCES PRECOS( ID )
);

CREATE TABLE ITENS_LISTA (
    ID                   UUID                       NOT NULL,
    QUANTIDADE           INT                        NOT NULL,
    ITEM_ID              UUID                       NOT NULL,
    PRECO                NUMERIC(10,2)              NOT NULL,
 	CREATED_DATE         TIMESTAMP WITH TIME ZONE   NOT NULL,
 	MODIFIED_DATE        TIMESTAMP WITH TIME ZONE   NOT NULL,
    CREATED_BY           VARCHAR(100)               NOT NULL,
 	MODIFIED_BY          VARCHAR(100)               NOT NULL,
 	CONSTRAINT FK_ITENS_LISTA_ITEM_ID FOREIGN KEY ( ITEM_ID ) REFERENCES ITENS( ID ),
 	CONSTRAINT PK_ITENS_LISTA_ID PRIMARY KEY ( ID )
);