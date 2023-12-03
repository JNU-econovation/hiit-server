create table in_it_tb
(
    id               bigint       not null auto_increment,
    create_at        datetime(6)  not null,
    deleted          bit          not null,
    update_at        datetime(6)  not null,
    in_it_day_code   varchar(255) not null,
    in_it_resolution varchar(15)  not null,
    in_it_status     varchar(255) not null,
    in_it_title      varchar(15)  not null,
    hiit_member_fk   bigint       not null,
    primary key (id)
);
