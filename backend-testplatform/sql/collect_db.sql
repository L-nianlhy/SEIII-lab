drop database if exists  collect_db;
create database collect_db;
USE collect_db;
# table user
# DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
    `uid` int (255) unsigned NOT NULL AUTO_INCREMENT,
    `uname` varchar (45) UNIQUE NOT NULL,
    `password` varchar (45) NOT NULL,
    `user_role` varchar (10) NOT NULL,
    `phone` varchar (30) UNIQUE NOT NULL,
    `ability` varchar(45) NOT NULL ,
    `preference` varchar(45) NOT NULL ,
    `device` varchar(45) NOT NULL ,
    `intro` varchar(255) NOT NULL ,
    PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

# LOCK TABLES `user` WRITE;
# INSERT INTO `user` VALUES (1,'user1','123456','worker','110','amateur','功能测试','Windows','noIntro');
# INSERT INTO `user` VALUES (2,'user2','123456','employer','120','NONE','NONE','NONE','12138');
# INSERT INTO `user` VALUES (3,'user3','123456','admin','911','NONE','NONE','NONE','12138');
# INSERT INTO `user` VALUES (4,'user4','123456','worker','138','normal','功能测试','Android','12138');
# UNLOCK TABLES;

# table task
# DROP TABLE IF EXISTS `task`;
CREATE TABLE IF NOT EXISTS `task` (
    `task_id` int (255) unsigned NOT NULL AUTO_INCREMENT,
    `uid` int (255) unsigned NOT NULL ,
    `task_name` varchar(45) UNIQUE NOT NULL ,
    `task_intro` varchar(255) NOT NULL ,
    `task_time` varchar(45) NOT NULL ,
    `task_type` varchar(45) NOT NULL ,
    `task_difficulty` varchar(45) NOT NULL ,
    `task_device` varchar(45) NOT NULL ,
    `num_of_worker` int(255) unsigned NOT NULL ,
    `num_of_need_worker` int(255) unsigned NOT NULL ,
    `task_app` varchar(255) NOT NULL ,
    `task_doc` varchar(255) NOT NULL ,
    PRIMARY KEY (`task_id`),
    FOREIGN KEY (`uid`) REFERENCES user(`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

# LOCK TABLES `task` WRITE;
# INSERT INTO `task` VALUES (1,2,'test1','测试任务1','2022-07-01','功能测试','amateur','Windows',0,5,'collect/天池平台使用说明.pdf','collect/天池平台使用说明.pdf');
# UNLOCK TABLES;


# table report
# DROP TABLE IF EXISTS `report`;
CREATE TABLE IF NOT EXISTS `report`   (
    `report_id` int(255) unsigned NOT NULL AUTO_INCREMENT,
    `uid` int(255) unsigned NOT NULL ,
    `task_id` int(255) unsigned NOT NULL ,
    `cooperation_id` int(255) unsigned NOT NULL ,
    `bug_photo` varchar(255) NOT NULL ,
    `bug_intro` varchar(255) NOT NULL ,
    `bug_step` varchar(255) NOT NULL ,
    `device_information` varchar(255) NOT NULL ,
    `time_stamp` varchar(255) NOT NULL ,
    PRIMARY KEY (`report_id`),
    FOREIGN KEY (`uid`) REFERENCES user(`uid`),
    FOREIGN KEY (`task_id`) REFERENCES task(`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

# LOCK TABLES `report` WRITE ;
# INSERT INTO `report` VALUES (1,1,1,0,'collect/Redis.png','进入个人页面程序卡死','从主页进入个人页面程序卡死','Windows','2022-03-31 16:25:31');
# INSERT INTO `report` VALUES (2,1,1,0,'collect/Redis.png','进入个人页面程序会出bug卡死','从主页进入个人页面程序卡死','Windows','2022-03-31 19:25:31');
# UNLOCK TABLES ;

# table order
DROP TABLE IF EXISTS `order`;
# DROP TABLE IF EXISTS `task`;
CREATE TABLE IF NOT EXISTS `order`    (
    `order_id` int(255) unsigned NOT NULL AUTO_INCREMENT,
    `task_id` int(255) unsigned NOT NULL ,
    `uid` int(255) unsigned NOT NULL,
    PRIMARY KEY (`order_id`),
    FOREIGN KEY (`task_id`) REFERENCES task(`task_id`),
    FOREIGN KEY (`uid`) REFERENCES user(`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

# LOCK TABLES `order` WRITE;
# INSERT INTO `order` VALUES (1, 1, 1);
# UNLOCK TABLES;

# table comment
DROP TABLE IF EXISTS `comment`;
CREATE TABLE IF NOT EXISTS `comment`    (
    `comment_id` int(255) unsigned NOT NULL AUTO_INCREMENT,
    `uid` int(255) unsigned NOT NULL ,
    `uname` varchar(255) NOT NULL ,
    `report_id` int(255) unsigned NOT NULL ,
    `score` int(10) unsigned NOT NULL ,
    `content` varchar(255) NOT NULL ,
    `time_stamp` varchar(255) NOT NULL ,
    PRIMARY KEY (`comment_id`),
    FOREIGN KEY (`uid`) REFERENCES user(`uid`),
    FOREIGN KEY (`report_id`) REFERENCES report(`report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

# table score
DROP TABLE IF EXISTS `score`;
CREATE TABLE IF NOT EXISTS `score`  (
    `uid` int(255) unsigned NOT NULL ,
    `total_score` int(255) unsigned NOT NULL ,
    FOREIGN KEY (`uid`) REFERENCES user(`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

# LOCK TABLES `score` WRITE;
# INSERT INTO `score` VALUES (1,0);
# INSERT INTO `score` VALUES (4,0);
# UNLOCK TABLES;