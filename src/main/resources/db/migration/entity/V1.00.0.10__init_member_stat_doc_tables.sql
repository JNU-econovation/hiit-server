create table member_stat_doc
(
    id                   varchar(255) not null,
    create_at            datetime(6)  not null,
    update_at            datetime(6)  not null,
    member_stat_resource json,
    primary key (id)
);