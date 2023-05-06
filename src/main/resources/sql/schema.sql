drop table if exists rboard_content_info;
drop table if exists rboard;
drop table if exists rboard_counter;
drop table if exists rboard_word_info;

create table rboard (
    board_id bigint auto_increment,
    title varchar(100) not null,
    content varchar(2000) not null,
    create_dt datetime not null,
    update_dt datetime not null,
    primary key (board_id)
);

create table rboard_content_info (
    board_id bigint,
    word varchar(50) not null,
    word_total int not null,
    create_dt datetime not null,
    update_dt datetime not null,
    primary key (word),
    constraint rboard_content_info_board_id_fk foreign key (board_id) references rboard (board_id)
);

create table rboard_counter (
    total bigint default 0,
    create_dt datetime not null,
    update_dt datetime not null,
    primary key (total)
);

create table rboard_word_info (
    word varchar(50) not null,
    word_total bigint not null,
    create_dt datetime not null,
    update_dt datetime not null,
    primary key (word)
);
