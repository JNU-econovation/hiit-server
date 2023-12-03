create table it_relation_tb
(
    id                         bigint       not null auto_increment,
    deleted                    bit          not null,
    it_relation_target_it_id   bigint       not null,
    it_relation_target_it_type varchar(255) not null,
    in_it_fk                   bigint       not null,
    primary key (id)
);
