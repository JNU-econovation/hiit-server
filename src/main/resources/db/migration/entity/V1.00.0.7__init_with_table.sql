create table with_tb
(
    id           bigint      not null auto_increment,
    create_at    datetime(6) not null,
    deleted      bit         not null,
    update_at    datetime(6) not null,
    with_content varchar(20) not null,
    in_it_fk     bigint      not null,
    primary key (id)
);
