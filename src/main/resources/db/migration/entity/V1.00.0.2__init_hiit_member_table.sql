create table hiit_member_tb
(
    id                                bigint       not null auto_increment,
    create_at                         datetime(6)  not null,
    deleted                           bit          not null,
    update_at                         datetime(6)  not null,
    hiit_member_certification_id      varchar(255) not null,
    hiit_member_certification_subject varchar(255) not null,
    hiit_member_nick_name             varchar(255) not null,
    notification_consent              bit,
    hiit_member_profile               varchar(255) not null,
    hiit_member_resource              json,
    status                            varchar(255),
    primary key (id)
);
