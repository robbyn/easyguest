
    create table ARTICLE (
        ID integer generated by default as identity (start with 1),
        CODE varchar(32),
        LABEL varchar(250),
        PRICE double,
        KEY_CODE integer,
        KEY_MODIFIERS integer,
        CATEGORY_ID integer,
        primary key (ID)
    );

    create table ARTICLE_CATEGORY (
        ID integer generated by default as identity (start with 1),
        NAME varchar(250),
        PARENT_ID integer,
        primary key (ID)
    );

    create table CUSTOMER (
        ID integer generated by default as identity (start with 1),
        TITLE varchar(32),
        COMPANY varchar(250),
        LASTNAME varchar(64),
        FIRSTNAME varchar(64),
        ADDRESS1 varchar(250),
        ADDRESS2 varchar(250),
        ZIP varchar(32),
        CITY varchar(64),
        STATE varchar(64),
        COUNTRY varchar(64),
        TELEPHONE varchar(64),
        MOBILE varchar(64),
        FAX varchar(64),
        EMAIL varchar(250),
        primary key (ID)
    );

    create table HOTEL (
        ID integer generated by default as identity (start with 1),
        RATING varchar(32),
        CHAIN varchar(64),
        NAME varchar(250),
        ADDRESS1 varchar(250),
        ADDRESS2 varchar(250),
        ZIP varchar(32),
        CITY varchar(64),
        STATE varchar(64),
        COUNTRY varchar(64),
        TELEPHONE varchar(64),
        FAX varchar(64),
        EMAIL varchar(250),
        WEB varchar(250),
        LOGO longvarbinary,
        primary key (ID)
    );

    create table INVOICE (
        ID integer generated by default as identity (start with 1),
        CREATION_DATE date,
        CUSTOMER_ID integer,
        HOTEL_ID integer,
        primary key (ID)
    );

    create table INVOICE_ITEM (
        ID integer generated by default as identity (start with 1),
        PRICE double,
        QUANTITY double,
        INVOICE_ID integer,
        ARTICLE_ID integer,
        POS integer,
        primary key (ID)
    );

    create table PERIOD (
        ID integer generated by default as identity (start with 1),
        FROM_DATE date,
        TO_DATE date,
        TARIFF_ID integer,
        primary key (ID)
    );

    create table RESERVATION (
        ID integer generated by default as identity (start with 1),
        FROM_DATE date,
        TO_DATE date,
        STATUS integer,
        INVOICE_ID integer,
        ROOM_ID integer,
        POS integer,
        primary key (ID)
    );

    create table ROOM (
        ID integer generated by default as identity (start with 1),
        HOTEL_ID integer,
        NUMBER integer,
        TYPE_ID integer,
        primary key (ID)
    );

    create table ROOM_TYPE (
        ID integer generated by default as identity (start with 1),
        NAME varchar(250),
        DESCRIPTION longvarchar,
        BASE_PRICE double,
        primary key (ID)
    );

    create table TARIFF (
        ID integer generated by default as identity (start with 1),
        NAME varchar(250),
        FACTOR double,
        COLOR integer,
        primary key (ID)
    );

    alter table ARTICLE 
        add constraint FKFF2458D6B91C35AC 
        foreign key (CATEGORY_ID) 
        references ARTICLE_CATEGORY;

    alter table ARTICLE_CATEGORY 
        add constraint FK74B11647D8DA3A80 
        foreign key (PARENT_ID) 
        references ARTICLE_CATEGORY;

    alter table INVOICE 
        add constraint FK9FA1CF0DF98EA9D6 
        foreign key (HOTEL_ID) 
        references HOTEL;

    alter table INVOICE 
        add constraint FK9FA1CF0D7F9B62DE 
        foreign key (CUSTOMER_ID) 
        references CUSTOMER;

    alter table INVOICE_ITEM 
        add constraint FK53B3BD05371E1396 
        foreign key (ARTICLE_ID) 
        references ARTICLE;

    alter table INVOICE_ITEM 
        add constraint FK53B3BD053FBB5436 
        foreign key (INVOICE_ID) 
        references INVOICE;

    alter table PERIOD 
        add constraint FK8C7669C12D94BE1E 
        foreign key (TARIFF_ID) 
        references TARIFF;

    alter table RESERVATION 
        add constraint FK2328D7AC39D4F7BE 
        foreign key (ROOM_ID) 
        references ROOM;

    alter table RESERVATION 
        add constraint FK2328D7AC3FBB5436 
        foreign key (INVOICE_ID) 
        references INVOICE;

    alter table ROOM 
        add constraint FK2678DBF98EA9D6 
        foreign key (HOTEL_ID) 
        references HOTEL;

    alter table ROOM 
        add constraint FK2678DB87EBE4D9 
        foreign key (TYPE_ID) 
        references ROOM_TYPE;
