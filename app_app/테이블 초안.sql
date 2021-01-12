create table tbl_mb(
	mb_id varchar2(30)primary key not null,
	mb_passwd varchar2(50),
	mb_name varchar2(20),
	mb_phonenum varchar2(2000),
	mb_birth DATE,
  mb_email VARCHAR2(30),
  mb_addPerson1 VARCHAR2(20),
  mb_addPerson2 VARCHAR2(20),
  mb_addPerson3 VARCHAR2(20)
);

create table tbl_loc(
  loc_id varchar2(30) primary key not null,
  loc_place varchar2(30),
  loc_track varchar2(30),
  loc_lati varchar2(30),
  loc_long varchar2(30),
  loc_date date
);