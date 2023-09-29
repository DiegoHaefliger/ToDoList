create table USUARIO (
  	id              INT     PRIMARY KEY     AUTO_INCREMENT,
  	name            varchar(200)    not null,
    login           varchar(200)    not null,
    password        varchar(200)    not null,
    role            varchar(100)    not null
);

create table CATEGORIA (
  	id              INT     PRIMARY KEY     AUTO_INCREMENT,
  	descricao       varchar(200)    not null
);

create table TAREFA (
  	id              INT     PRIMARY KEY     AUTO_INCREMENT,
  	id_user         INT             not null,
    id_categoria    INT             not null,
  	titulo          varchar(100)    not null,
  	descricao       varchar(200)    not null,
  	concluido       BOOLEAN         not null,
  	prioridade      INT             null,
  	data_inicio     TIMESTAMP       not null,
  	data_fim        TIMESTAMP       null
);

ALTER TABLE TAREFA ADD FOREIGN KEY (id_user) REFERENCES USUARIO(id);
ALTER TABLE TAREFA ADD FOREIGN KEY (id_categoria) REFERENCES CATEGORIA(id);