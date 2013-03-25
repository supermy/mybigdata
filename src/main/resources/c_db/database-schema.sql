--    数据自动初始化
-- 配置数据库
drop table config;
create table config (
    id integer not null,
    key varchar(80) not null,
    value varchar(800) not null,
    constraint pk_config primary key (id)
);

-- 雇员测试数据库
drop table employees;
create table employees (
    id integer not null,
    name varchar(80) not null,
    salary integer not null,
    constraint pk_employee primary key (id)
);

drop table employees_esb;
create table employees_esb (
    id integer not null,
    name varchar(80) not null,
    salary integer not null,
    constraint pk_employee_esb primary key (id)
);

--spring esb 探测数据表
--primary key(att_code)
  
drop table attr_message;
create table attr_message ( 
  att_code varchar(20) not null, 
  parent_code varchar(20), 
  att_text varchar(100), 
  seq numeric(8, 0), 
  opt_date date, 
  mark varchar(1) default 'n', 
  constraint pk_attr_message primary key (att_code)
);