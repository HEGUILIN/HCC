/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     3/16/2015 1:54:30 PM                         */
/*==============================================================*/


drop table if exists GRAPHS;

drop table if exists GROUP_CHAT;

drop table if exists GS;

drop table if exists SENTENCES;

drop table if exists US;

drop table if exists USERS;

/*==============================================================*/
/* Table: GRAPHS                                                */
/*==============================================================*/
create table GRAPHS
(
   GRAPH_ID             varchar(50) not null,
   GRAPH_LINK           varchar(100) not null,
   GRAPH_VOICE          varchar(100) not null,
   GRAPH_DESCRIPTION    varchar(50) not null,
   GRAPH_ELEMENT        int not null,
   FIRSTGRAPG_FOLLOWED_ID varchar(50),
   FIRSTGRAPH_FOLLOWED_FREQUENCY int,
   SECONDGRAPG_FOLLOWED_ID varchar(50),
   SECONDGRAPH_FOLLOWED_FREQUENCY int,
   THIRDGRAPH_FOLLOWED_ID varchar(50),
   THIRDGRAPH_FOLLOWED_FREQUENCY int,
   primary key (GRAPH_ID)
);

/*==============================================================*/
/* Table: GROUP_CHAT                                            */
/*==============================================================*/
create table GROUP_CHAT
(
   GROUP_CREATED_BY_ID  varchar(50) not null,
   GROUP_ID             varchar(50) not null,
   GROUP_CREATED_AT     decimal not null,
   GROUP_NAME           varchar(50),
   BEACON_ID            char(10),
   primary key (GROUP_ID)
);

/*==============================================================*/
/* Table: GS                                                    */
/*==============================================================*/
create table GS
(
   GRAPH_ID             varchar(50) not null,
   SENTENCE_ID          varchar(50) not null,
   GS_SEQUENCE          int,
   primary key (GRAPH_ID, SENTENCE_ID)
);

/*==============================================================*/
/* Table: SENTENCES                                             */
/*==============================================================*/
create table SENTENCES
(
   SENTENCE_ID          varchar(50) not null,
   SENTENCE_DESCRIPTION varchar(50) not null,
   SENTENCE_VOICE       varchar(100) not null,
   SENTENCE_FREQUENCY   int not null,
   primary key (SENTENCE_ID)
);

/*==============================================================*/
/* Table: US                                                    */
/*==============================================================*/
create table US
(
   ID                   varchar(50) not null,
   SENTENCE_ID          varchar(50) not null,
   US_FREQUENCY         int not null,
   US_CREATED_AT        decimal not null,
   primary key (ID, SENTENCE_ID)
);

/*==============================================================*/
/* Table: USERS                                                 */
/*==============================================================*/
create table USERS
(
   ID                   varchar(50) not null,
   EMAIL                varchar(50) not null,
   PASSWORD             varchar(50) not null,
   ADMIN                int not null,
   NAME                 varchar(50) not null,
   IMAGE                varchar(500) not null,
   CREATED_AT           decimal not null,
   ONLINE               int,
   IP                   varchar(20),
   PORT                 int,
   LOGIN_AT             decimal,
   primary key (ID)
);

alter table GROUP_CHAT add constraint FK_RELATIONSHIP_7 foreign key (GROUP_CREATED_BY_ID)
      references USERS (ID) on delete restrict on update restrict;

alter table GS add constraint FK_RELATIONSHIP_4 foreign key (GRAPH_ID)
      references GRAPHS (GRAPH_ID) on delete restrict on update restrict;

alter table GS add constraint FK_RELATIONSHIP_5 foreign key (SENTENCE_ID)
      references SENTENCES (SENTENCE_ID) on delete restrict on update restrict;

alter table US add constraint FK_RELATIONSHIP_1 foreign key (ID)
      references USERS (ID) on delete restrict on update restrict;

alter table US add constraint FK_RELATIONSHIP_2 foreign key (SENTENCE_ID)
      references SENTENCES (SENTENCE_ID) on delete restrict on update restrict;

