/*==============================================================*/
/* DBMS name:      MySQL 4.0                                    */
/* Created on:     8/25/2017 4:36:40 PM                         */
/*==============================================================*/


drop table if exists AUTOR;

drop index RELATIONSHIP_2_FK on LIBRO;

drop table if exists LIBRO;

drop index RELATIONSHIP_3_FK on SERVICIOS;

drop index RELATIONSHIP_1_FK on SERVICIOS;

drop table if exists SERVICIOS;

drop table if exists USUARIO;

/*==============================================================*/
/* Table: AUTOR                                                 */
/*==============================================================*/
create table AUTOR
(
   ID_AUTOR                       int                        NOT   NULL AUTO_INCREMENT,
   NOMBRE_AUTOR                   varchar(50)                    not null,
   PAIS                           varchar(40)                    not null,
   primary key (ID_AUTOR)
)
;

/*==============================================================*/
/* Table: LIBRO                                                 */
/*==============================================================*/
create table LIBRO
(
   ID_LIBRO                       int                       NOT     NULL AUTO_INCREMENT,
   ID_AUTOR                       int,
   NOMBRE_LIBRO                   varchar(50)                    not null,
   COD_LIBRO                      varchar(30)                    not null,
   primary key (ID_LIBRO)
)
;

/*==============================================================*/
/* Index: RELATIONSHIP_2_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_2_FK on LIBRO
(
   ID_AUTOR
);

/*==============================================================*/
/* Table: SERVICIOS                                             */
/*==============================================================*/
create table SERVICIOS
(
   ID_SERVICIO                    int                     NOT       NULL AUTO_INCREMENT,
   ID_USUARIO                     int,
   ID_LIBRO                       int,
   FECHA_SERVICIO                 varchar(40)                    not null,
   TIPO_SERVICIO                  varchar(40)                    not null,
   primary key (ID_SERVICIO)
)
;

/*==============================================================*/
/* Index: RELATIONSHIP_1_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_1_FK on SERVICIOS
(
   ID_USUARIO
);

/*==============================================================*/
/* Index: RELATIONSHIP_3_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_3_FK on SERVICIOS
(
   ID_LIBRO
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO
(
   ID_USUARIO                     int                         NOT   NULL AUTO_INCREMENT,
   NOMBRE                        varchar(40)                    not null,   
   CLAVE                       varchar(30)                    not null,
   primary key (ID_USUARIO)
)
;

alter table LIBRO add constraint FK_RELATIONSHIP_2 foreign key (ID_AUTOR)
      references AUTOR (ID_AUTOR) on delete cascade on update cascade;

alter table SERVICIOS add constraint FK_RELATIONSHIP_1 foreign key (ID_USUARIO)
      references USUARIO (ID_USUARIO) on delete cascade on update cascade;

alter table SERVICIOS add constraint FK_RELATIONSHIP_3 foreign key (ID_LIBRO)
      references LIBRO (ID_LIBRO) on delete cascade on update cascade;

