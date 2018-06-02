drop table if exists t_department;
create table t_department(
	id int not null auto_increment,
	name varchar(20),
	primary key(id)
);

drop table if exists t_employee;
create table t_employee(
	id int not null auto_increment,
	lastName varchar(20),
	email varchar(20),
	gender char(1) not null default '0',
	primary key(id)
);

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;