#temp1 测试数据库
CREATE DATABASE IF NOT EXISTS temp1;

USE temp1;

drop table if exists temp_user;

create table if not exists temp_user
(
    id bigint not null auto_increment comment '主键',
    name varchar(256) not null comment '姓名',
    point int not null comment '分数',
    PRIMARY KEY(id)
) ENGINE = InnoDB;

insert into temp_user(id, name, point)
values (1, 'xm', 2000),
       (2, 'xh', 2000),
       (3, 'xl', 2000);

CREATE TABLE `undo_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id` bigint(20) NOT NULL,
    `xid` varchar(100) NOT NULL,
    `context` varchar(128) NOT NULL,
    `rollback_info` longblob NOT NULL,
    `log_status` int(11) NOT NULL,
    `log_created` datetime NOT NULL,
    `log_modified` datetime NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


#temp2 测试数据库

CREATE DATABASE IF NOT EXISTS temp2;

USE temp2;

drop table if exists temp_user;

create table if not exists temp_user
(
    id bigint not null auto_increment comment '主键',
    name varchar(256) not null comment '姓名',
    point int not null comment '分数',
    PRIMARY KEY(id)
) ENGINE = InnoDB;

insert into temp_user(id, name, point)
values (1, 'xm', 2000),
       (2, 'xh', 2000),
       (3, 'xl', 2000);



CREATE TABLE `undo_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id` bigint(20) NOT NULL,
    `xid` varchar(100) NOT NULL,
    `context` varchar(128) NOT NULL,
    `rollback_info` longblob NOT NULL,
    `log_status` int(11) NOT NULL,
    `log_created` datetime NOT NULL,
    `log_modified` datetime NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;