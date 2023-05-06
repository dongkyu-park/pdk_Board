drop table if exists rboard_content_word_counter;
drop table if exists rboard;
drop table if exists rboard_counter;
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
    word_count int not null,
    create_dt datetime not null,
    update_dt datetime not null,
    constraint rboard_content_word_counter_board_id_fk foreign key (board_id) references rboard (id),
    key idx_word(word)
);

create table rboard_counter (
    b_count bigint,
    create_dt datetime not null,
    update_dt datetime not null
);

create table rboard_use_word_counter (
    word varchar(50) not null,
    word_count bigint not null,
    create_dt datetime not null,
    update_dt datetime not null,
    primary key (word),
    key idx_word_count(word_count)
);
