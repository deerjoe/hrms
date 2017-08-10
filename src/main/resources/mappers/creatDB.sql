create table hrms_db.menu_tb
(
	id int not null
		primary key,
	parentId int null,
	text varchar(10) not null,
	icon varchar(255) null,
	url varchar(255) null,
	targetType varchar(20) null,
	isHeader tinyint(1) default '0' null,
	isOpen tinyint(1) default '0' null,
	constraint menu_id_uindex
	unique (id)
)
;