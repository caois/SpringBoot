#用户表
create table t_user
(
    id       int(7) primary key auto_increment,
    username varchar(70),
    password varchar(30),
    email    varchar(70)
);

#省份表
create table t_province
(
    id           int(7) primary key auto_increment,
    name         varchar(60),
    tags         varchar(80),
    place_counts varchar(4)
);

#景点表
create table t_place
(
    id                 int(7) primary key auto_increment,
    name               varchar(60),
    pic_path           VARCHAR(100),
    peak_season_time   TIMESTAMP,
    peak_season_ticket DOUBLE(7, 2),
    off_season_ticket  DOUBLE(7, 2),
    introduction       VARCHAR(300) comment '简介',
    province_id        int(7) comment '指向省份id'
);

# 创建完发现 时间戳不是自动生成的。。
alter TABLE t_place
    modify peak_season_time TIMESTAMP default current_timestamp