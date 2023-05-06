drop table if exists rboard_content_word_counter;
drop table if exists rboard;
drop table if exists rboard_total_counter;
drop table if exists rboard_use_word_counter;

create table rboard (
    id bigint auto_increment,
    title varchar(100) not null,
    content varchar(2000) not null,
    create_dt datetime not null,
    update_dt datetime not null,
    primary key (id)
);

create table rboard_content_word_counter (
    board_id bigint,
    word varchar(50) not null,
    word_total int not null,
    create_dt datetime not null,
    update_dt datetime not null,
    primary key (word),
    constraint rboard_content_word_counter_board_id_fk foreign key (board_id) references rboard (id)
);

create table rboard_total_counter (
    total bigint,
    create_dt datetime not null,
    update_dt datetime not null
);

create table rboard_use_word_counter (
    word varchar(50) not null,
    word_total bigint not null,
    create_dt datetime not null,
    update_dt datetime not null,
    primary key (word)
);
