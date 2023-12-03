create table registered_it_tb
(
    id                       bigint       not null auto_increment,
    create_at                datetime(6)  not null,
    deleted                  bit          not null,
    update_at                datetime(6)  not null,
    registered_it_end_time   time         not null,
    registered_it_start_time time         not null,
    registered_it_topic      varchar(255) not null,
    primary key (id)
);
