begin;
set client_encoding = 'utf-8';

create schema login;

-- create extension pgcrypto;

create table login.login (
  id serial primary key
  , usuario text not null
  , password text not null
  , constraint usuarioUnico unique (usuario)
);

comment on table login.login
is
'El usuario USUARIO tiene una contraseña PASSWORD después de aplicar un hash';

insert into login.login (usuario, password) values ('miguel', md5('miguel'))
                                                   , ('juan', md5('juan'))
                                                   , ('pedro', md5('pedro'));

create table login.usuario (
  id serial primary key
  , login_id int not null references login.login(id)
  , nombre text not null
  , correo text  constraint correo_correcto check (correo ~ E'^[\\w!#$%&\'*+/=?`{|}~^-]+(\\.[\\w!#$%&\'*+/=?`{|}~^-]+)*@[\\w-]+(\\.[\\w-]+)*$')
  , sexo char constraint sexo check (sexo = any(array['f'::char, 'm'::char]))
);

insert into login.usuario (login_id, nombre, correo, sexo) values (1, 'Miguel Piña', 'miguel_pinia@ciencias.unam.mx', 'm')
                                                                  , (2, 'Juan Perez', 'juan@gmail.com', 'm')
                                                                  , (3, 'Pedro Damián', 'pedro@gmail.com', 'm');
commit;
