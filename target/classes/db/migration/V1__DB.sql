create sequence about_us_seq start 1 increment 1;
create sequence category_seq start 1 increment 1;
create sequence client_seq start 1 increment 1;
create sequence doctor_info_seq start 1 increment 1;
create sequence doctor_seq start 1 increment 1;
create sequence faq_seq start 1 increment 1;
create sequence file_seq start 1 increment 1;
create sequence interval_seq start 1 increment 1;
create sequence payment_seq start 1 increment 1;
create sequence payment_step_seq start 1 increment 1;
create sequence reservation_seq start 1 increment 1;
create sequence schedule_seq start 1 increment 1;
create sequence user_seq start 1 increment 1;
create sequence week_day_seq start 1 increment 1;
create sequence week_seq start 1 increment 1;
create sequence work_time_seq start 1 increment 1;
create table about_us
(
    id           int8         not null,
    create_date  timestamp    not null,
    updated_date timestamp    not null,
    header       varchar(255) not null,
    "order"      int4         not null,
    paragraph    TEXT         not null,
    primary key (id)
);
create table category
(
    id           int8         not null,
    create_date  timestamp    not null,
    updated_date timestamp    not null,
    description  TEXT         not null,
    image        varchar(255),
    name         varchar(255) not null,
    illness      varchar(1024),
    primary key (id)
);
create table client
(
    id           int8      not null,
    create_date  timestamp not null,
    updated_date timestamp not null,
    image        varchar(255),
    med_card_phone_number varchar(255),
    user_id      int8,
    primary key (id)
);
create table doctor
(
    id           int8      not null,
    create_date  timestamp not null,
    updated_date timestamp not null,
    bio          varchar(500),
    image        varchar(255),
    position     varchar(255),
    deleted     timestamp,
    user_id      int8      not null,
    primary key (id)
);
create table doctor_category
(
    category_id int8 not null,
    doctor_id   int8 not null,
    primary key (category_id, doctor_id)
);
create table doctor_info
(
    id                int8         not null,
    create_date       timestamp    not null,
    updated_date      timestamp    not null,
    end_date          date,
    info_type         varchar(255) not null,
    name              varchar(255),
    organization_name varchar(255),
    start_date        date         not null,
    doctor_id         int8,
    primary key (id)
);
create table faq
(
    id           int8      not null,
    create_date  timestamp not null,
    updated_date timestamp not null,
    answer       TEXT      not null,
    "order"      int4      not null,
    question     TEXT      not null,
    primary key (id)
);
create table file_info
(
    id           int8      not null,
    create_date  timestamp not null,
    updated_date timestamp not null,
    code         varchar(255),
    document_type    varchar(255) unique,
    file_name    varchar(255),
    url          varchar(255),
    primary key (id)
);
create table interval
(
    id           int8      not null,
    create_date  timestamp not null,
    updated_date timestamp not null,
    end_time     time      not null,
    start_time   time      not null,
    week_day_id  int8,
    primary key (id)
);
create table payment
(
    id           int8         not null,
    create_date  timestamp    not null,
    updated_date timestamp    not null,
    logo         varchar(255),
    name         varchar(255) not null,
    next_steps   varchar(510),
    primary key (id)
);
create table payment_step
(
    id           int8         not null,
    create_date  timestamp    not null,
    updated_date timestamp    not null,
    number       int4         not null,
    text         varchar(510) not null,
    payment_id   int8,
    primary key (id)
);
create table reservation
(
    id           int8             not null,
    create_date  timestamp        not null,
    updated_date timestamp        not null,
    comment      varchar(255),
    bill         varchar(255)     unique,
    end_time     time             not null,
    is_paid      bool default 'f' not null,
    phone_number varchar(17),
    start_time   time             not null,
    check_url    varchar(255),
    client_id    int8             not null,
    work_time_id int8,
    primary key (id)
);
create table schedule
(
    id            int8      not null,
    create_date   timestamp not null,
    updated_date  timestamp not null,
    current_week  int4,
    week_duration int4      not null,
    is_generated  bool      default false not null,
    doctor_id     int8      not null,
    primary key (id)
);
create table user_role
(
    user_id     int8 not null,
    authorities varchar(255)
);
create table users
(
    id                      int8             not null,
    create_date             timestamp        not null,
    updated_date            timestamp        not null,
    account_non_expired     bool default 't' not null,
    account_non_locked      bool default 't' not null,
    birth_date              date,
    credentials_non_expired bool default 't' not null,
    enabled                 bool default 'f' not null,
    firstname               varchar(50),
    lastname                varchar(50),
    password                varchar(255)     not null,
    patronymic              varchar(50),
    username                varchar(255)     not null,
    verification            varchar(255),
    firebase_token          varchar(255),
    primary key (id)
);
create table week
(
    id           int8      not null,
    create_date  timestamp not null,
    updated_date timestamp not null,
    week_order   int4      not null,
    schedule_id  int8,
    primary key (id)
);
create table week_day
(
    id            int8         not null,
    create_date   timestamp    not null,
    updated_date  timestamp    not null,
    week_day_name varchar(255) not null,
    week_id       int8,
    primary key (id)
);
create table work_time
(
    id           int8      not null,
    create_date  timestamp not null,
    updated_date timestamp not null,
    end_time     timestamp not null,
    start_time   timestamp not null,
    doctor_id    int8,
    primary key (id)
);

alter table client
    add constraint FKbxisi412kym1baqfr00rxd8yo foreign key (user_id) references users;
alter table doctor
    add constraint FK11wrxiolc8qa2e64s32xc2yy4 foreign key (user_id) references users;
alter table doctor_category
    add constraint FK3qokrjwt4mfgywr0tkbwgo8ro foreign key (doctor_id) references doctor;
alter table doctor_category
    add constraint FK24vsg13pcqv4f8oemft35buf0 foreign key (category_id) references category;
alter table doctor_info
    add constraint FK3i7xm1agjgccfa8ayelqvwgru foreign key (doctor_id) references doctor;
alter table interval
    add constraint FK1m2xk1evfagg1e0m8g08ymgxq foreign key (week_day_id) references week_day;
alter table payment_step
    add constraint FK42hmit4blpwsswr7ouuxyamws foreign key (payment_id) references payment;
alter table reservation
    add constraint FKoewar6f18rkn4iptr6da4oysv foreign key (client_id) references client;
alter table reservation
    add constraint FKhthnyd87yh0sumnitms19akv6 foreign key (work_time_id) references work_time;
alter table schedule
    add constraint FKqixlhugy7jvrwut9o2s6hqnu8 foreign key (doctor_id) references doctor;
alter table user_role
    add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users;
alter table week
    add constraint FKogu6ua3ff6j6v44ubicrhtqyr foreign key (schedule_id) references schedule;
alter table week_day
    add constraint FKpt5sn3x335stcjkkysxfh91c7 foreign key (week_id) references week;
alter table work_time
    add constraint FKrorujcxhv3obpikpxnttana0r foreign key (doctor_id) references doctor;