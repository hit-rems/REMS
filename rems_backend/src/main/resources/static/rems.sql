-- 删除数据库（存在则删除rems）
drop database if exists rems;

-- 创建数据库
create database rems;

-- 使用数据库
use rems;

/*-- 删除表（存在则删除department）
drop table if exists department;
-- 单位表
create table department(
                           id int unsigned primary key auto_increment comment 'ID',
                           name varchar(32) not null comment '单位名称'
) comment '单位表';*/

-- 删除表（存在则删除user）
drop table if exists user;
-- 用户表
create table user (
                      id int unsigned primary key auto_increment comment 'ID',
                      username varchar(20) not null unique comment '用户名',
                      password varchar(32) not null comment '密码',
                      nickname varchar(128)  default '' comment '昵称',
                      department varchar(128) default '' comment '所属单位',
                      type varchar(128) default '' comment '账号类型 [管理员] 或者 [普通用户]'
--                       constraint fk_department_user foreign key (department) references department(id)-- 外键约束
) comment '用户表';

-- 删除表（存在则删除equipment）
drop table if exists equipment;
-- 设备表
create table equipment(
                         id int unsigned primary key comment '设备号',
                         type varchar(128) default '' comment '设备类型',
                         status varchar(10) not null comment '状态：只能是[空闲] 或者 [占用]',
                         department varchar(128) default '' comment '所属单位',
                         discard varchar(10) not null comment '是否报废：只能是[运行] 或者 [报废]',
                         create_time datetime not null comment '购置日期',
                         update_time datetime not null comment '最新维护日期',
                         url varchar(128) not null comment '设备图片'
--                          constraint fk_department_equipment foreign key (department) references department(id)-- 外键约束
);

