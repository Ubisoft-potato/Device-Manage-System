DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `device`;
DROP TABLE IF EXISTS `reservation_device`;

create table authorities
(
    id        bigint      not null comment '主键'
        primary key,
    authority varchar(50) not null comment '权限名称',
    user_id   bigint      not null comment '用户id'
)
    comment '用户权限';

create table device
(
    id            bigint       not null comment '主键'
        primary key,
    name          varchar(512) not null comment '设备名称',
    type          varchar(128) not null comment '设备类型',
    serial_number varchar(512) not null comment '设备序列号',
    manager       bigint       not null comment '设备负责人',
    price         int          not null comment '租用单价',
    description   varchar(512) not null comment '设备描述',
    available     tinyint(1)   not null comment '当前是否可用',
    create_time   datetime     not null comment '创建时间'
)
    comment '设备信息';

create table reservation_device
(
    id                 bigint       not null comment '主键',
    device_id          bigint       not null comment '设备id',
    user_id            bigint       not null comment '用户id',
    state              varchar(18)  not null comment '预约状态',
    reservation_reason varchar(512) not null comment '预约原因',
    start_time         datetime     not null comment '使用开始时间',
    stop_time          datetime     not null comment '使用到期时间',
    fail_reason        varchar(512) not null comment '审核失败原因',
    create_time        datetime     not null comment '预约创建时间'
)
    comment '预约登记';

create table users
(
    id          bigint       not null comment '主键'
        primary key,
    username    varchar(512) not null comment '账号',
    password    varchar(512) not null comment '密码',
    work_id     varchar(50)  not null comment '学号或工号',
    real_name   varchar(512) not null comment '真实人名',
    tel_phone   varchar(18)  not null comment '电话',
    institute   varchar(50)  not null comment '所属学院',
    create_time datetime     not null comment '创建时间'
)
    comment '用户';

