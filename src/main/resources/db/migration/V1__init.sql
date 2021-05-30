CREATE TABLE products (id  serial, title varchar(100), price int, views int default (0),primary key (id));

INSERT INTO products (title, price ) values
('Product1', 10),
('Product2', 10),
('Product3', 10),
('Product4', 10),
('Product5', 10),
('Product6', 10),
('Product7', 10),
('Product8', 10),
('Product9', 10),
('Product10', 10),
('Product11', 10),
('Product12', 10),
('Product13', 10),
('Product14', 10),
('Product15', 10),
('Product16', 10),
('Product17', 10),
('Product18', 10),
('Product19', 10),
('Product20', 10),
('Product21', 10),
('Product22', 10),
('Product23', 10),
('Product24', 10),
('Product25', 10),
('Product26', 10),
('Product27', 10),
('Product28', 10),
('Product29', 10),
('Product30', 10);
update products set price=id*10;

CREATE TABLE users (
    id          serial,
    username    varchar(50) not null,
    password    varchar(100) not null,
    name        varchar(100) not null,
    email       varchar(50) not null,
    primary key (id)
    );

create table roles(
    id          serial,
    name        varchar(50) default null,
    primary key (id)
) ;

create table users_roles(
        user_id         INT NOT NULL,
        role_id         INT NOT NULL,
    primary key(user_id, role_id),

    constraint FK_USER_ID_01 FOREIGN KEY (user_id)
    references users (id)
    on delete no action on update no action ,

    constraint  FK_USER_ID foreign key (role_id)
    references roles (id)
    on delete no action on update no action
    );
insert into roles (name)
values ('ROLE_USER'), ('ROLE_ADMIN');

insert into users ( username, password, name, email)
values ('admin','$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i','Bob Johnson','bob1@gmail.com'),
 ('admin100','$2a$10$9cyIMxY14.RwRbfEOf.wx.L25cW2Pwg4hfMMPtekk.O75T7J/lWqW','Bob Johnson','bob2@gmail.com');

insert into users_roles (user_id, role_id)
values
       (1,1),
       (1,2),
       (2,1),
       (2,2);