drop TABLE if EXISTS  tasks;
create TABLE tasks (
    id int primary key auto_increment,
    description varchar(100) not null,
    done bit
)