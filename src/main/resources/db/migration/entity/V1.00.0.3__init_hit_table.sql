create table hit_tb
(
    id         bigint       not null auto_increment,
    create_at  datetime(6)  not null,
    deleted    bit          not null,
    update_at  datetime(6)  not null,
    hit_hitter varchar(255) not null,
    status     varchar(255),
    with_fk    bigint       not null,
    primary key (id)
);

