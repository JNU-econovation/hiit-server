create table foo_tb
(
    id        bigint       not null auto_increment,
    create_at datetime(6)  not null,
    deleted   bit          not null,
    update_at datetime(6)  not null,
    foo_name  varchar(100) not null,
    primary key (id)
);


create table bar_tb
(
    id        bigint       not null auto_increment,
    create_at datetime(6)  not null,
    deleted   bit          not null,
    update_at datetime(6)  not null,
    version   bigint,
    bar_name  varchar(100) not null,
    foo_fk    bigint       not null,
    primary key (id)
);


