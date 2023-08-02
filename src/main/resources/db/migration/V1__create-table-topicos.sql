create table topicos (
    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(500) not null,
    fecha_creacion DATETIME,
    autor varchar(50) not null,
    curso varchar(100) not null,

    primary key (id)
);