-- Crear una base de datos llamada imagenes.
begin;

drop schema if exists documentos cascade;

create schema documentos;

create table documentos.mime_type (
  id serial primary key
  , nombre  text not null
  , extension text
  , constraint tipoUnico unique(nombre)
);

comment on table documentos.mime_type
is
'Representa el tipo NOMBRE mime con extensión EXTENSION.';

insert into documentos.mime_type (nombre, extension) values ('image/jpeg', 'jpg jpeg')
                                                            , ('image/bmp', 'bmp')
                                                            , ('image/png', 'png')
                                                            , ('application/pdf', 'pdf')
                                                            , ('application/msword', 'doc');

create table documentos.imagen (
  id serial primary key
  , mime_type_id int not null references documentos.mime_type(id)
  , nombre text not null
  , contenido bytea not null
  , ruta text not null
  , constraint nombreUnico unique(nombre)
  , constraint rutaUnica unique(ruta)
);

comment on table documentos.imagen
is
'La imagen NOMBRE se encuentra en la dirección RUTA y es de tipo MIME_TYPE_ID';


commit;
