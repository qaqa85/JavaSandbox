drop TABLE if EXISTS  tasks_events;
create TABLE tasks_events (
    id int primary key auto_increment,
    task_id int,
    occurrence datetime,
    name varchar(30)
)