use topic_api;
alter table topicos add constraint unique_titulo unique (titulo);
alter table topicos add constraint unique_mensaje unique (mensaje);