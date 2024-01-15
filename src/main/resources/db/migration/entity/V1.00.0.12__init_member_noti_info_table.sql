create table member_noti_info_tb
(
    id                      bigint       not null auto_increment,
    create_at               datetime(6)  not null,
    deleted                 bit          not null,
    update_at               datetime(6)  not null,
    member_noti_info_device varchar(255) not null,
    hiit_member_fk          bigint       not null,
    primary key (id)
);
