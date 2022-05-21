-- auto-generated definition
create table user
(
    user_name     varchar(256)                       null comment '�û��ǳ�',
    id            bigint auto_increment comment 'id'
        primary key,
    user_account  varchar(256)                       null comment '�˺�',
    avatar_url    varchar(1024)                      null comment '�û�ͷ��',
    gender        tinyint                            null comment '�Ա�',
    user_password varchar(512)                       not null comment '����',
    phone         varchar(128)                       null comment '�绰',
    email         varchar(512)                       null comment '����',
    user_status   int      default 0                 not null comment '״̬ 0-����',
    create_time   datetime default CURRENT_TIMESTAMP null comment '����ʱ��',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '����ʱ��',
    is_delete     tinyint  default 0                 not null comment '�Ƿ�ɾ��',
    user_role     int      default 0                 not null comment '�û���ɫ 0����ͨ�û�  1�ǹ���Ա
',
    planet_code   varchar(512)                       null comment '������'
);