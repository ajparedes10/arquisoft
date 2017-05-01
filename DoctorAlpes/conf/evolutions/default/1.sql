# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table consejoentity (
  id                            bigint not null,
  tipo                          varchar(255),
  fecha                         timestamp,
  mensaje                       varchar(255),
  medico_id                     bigint,
  historial_clinico_id          bigint,
  constraint pk_consejoentity primary key (id)
);
create sequence Consejo;

create table emergenciaentity (
  id                            bigint not null,
  descripcion                   varchar(255),
  fecha                         timestamp,
  ubicacion                     varchar(255),
  paciente_id                   bigint,
  medico_id                     bigint,
  historial_clinico_id          bigint,
  constraint pk_emergenciaentity primary key (id)
);
create sequence Emergencia;

create table encriptador_entity (
  mensaje_code                  varchar(255),
  hash_mensaje                  bytea,
  mensaje_decode                varchar(255)
);

create table historialmedicoentity (
  id                            bigint not null,
  paciente_id                   bigint,
  constraint uq_historialmedicoentity_paciente_id unique (paciente_id),
  constraint pk_historialmedicoentity primary key (id)
);
create sequence HistorialClinico;

create table historialdemedicionentity (
  id                            bigint not null,
  paciente_id                   bigint,
  constraint uq_historialdemedicionentity_paciente_id unique (paciente_id),
  constraint pk_historialdemedicionentity primary key (id)
);
create sequence HistorialDeMedicion;

create table lecturaentity (
  id                            bigint not null,
  fecha                         timestamp,
  frecuencia_cardiaca           integer,
  presion_sistolica             integer,
  presion_diastolica            integer,
  nivel_estres                  float,
  historial_medicion_id         bigint,
  constraint pk_lecturaentity primary key (id)
);
create sequence Lectura;

create table marcapasosentity (
  id                            bigint not null,
  modo                          varchar(255),
  intervalo_av                  integer,
  nivel_bateria                 integer,
  frecuencia                    integer,
  medico_id                     bigint,
  paciente_id                   bigint,
  constraint uq_marcapasosentity_paciente_id unique (paciente_id),
  constraint pk_marcapasosentity primary key (id)
);
create sequence Marcapasos;

create table medicoentity (
  id                            bigint not null,
  nombre                        varchar(255),
  tipo                          varchar(255),
  correo                        varchar(255),
  clave                         varchar(255),
  constraint uq_medicoentity_correo unique (correo),
  constraint pk_medicoentity primary key (id)
);
create sequence Medico;

create table medicoentity_pacienteentity (
  medicoentity_id               bigint not null,
  pacienteentity_id             bigint not null,
  constraint pk_medicoentity_pacienteentity primary key (medicoentity_id,pacienteentity_id)
);

create table pacienteentity (
  id                            bigint not null,
  nombre                        varchar(255),
  eps                           varchar(255),
  est                           varchar(255),
  marcapasos_id                 bigint,
  historial_mediciones_id       bigint,
  historial_clinico_id          bigint,
  constraint uq_pacienteentity_marcapasos_id unique (marcapasos_id),
  constraint uq_pacienteentity_historial_mediciones_id unique (historial_mediciones_id),
  constraint uq_pacienteentity_historial_clinico_id unique (historial_clinico_id),
  constraint pk_pacienteentity primary key (id)
);
create sequence Paciente;

create table pacienteentity_medicoentity (
  pacienteentity_id             bigint not null,
  medicoentity_id               bigint not null,
  constraint pk_pacienteentity_medicoentity primary key (pacienteentity_id,medicoentity_id)
);

alter table consejoentity add constraint fk_consejoentity_medico_id foreign key (medico_id) references medicoentity (id) on delete restrict on update restrict;
create index ix_consejoentity_medico_id on consejoentity (medico_id);

alter table consejoentity add constraint fk_consejoentity_historial_clinico_id foreign key (historial_clinico_id) references historialmedicoentity (id) on delete restrict on update restrict;
create index ix_consejoentity_historial_clinico_id on consejoentity (historial_clinico_id);

alter table emergenciaentity add constraint fk_emergenciaentity_paciente_id foreign key (paciente_id) references pacienteentity (id) on delete restrict on update restrict;
create index ix_emergenciaentity_paciente_id on emergenciaentity (paciente_id);

alter table emergenciaentity add constraint fk_emergenciaentity_medico_id foreign key (medico_id) references medicoentity (id) on delete restrict on update restrict;
create index ix_emergenciaentity_medico_id on emergenciaentity (medico_id);

alter table emergenciaentity add constraint fk_emergenciaentity_historial_clinico_id foreign key (historial_clinico_id) references historialmedicoentity (id) on delete restrict on update restrict;
create index ix_emergenciaentity_historial_clinico_id on emergenciaentity (historial_clinico_id);

alter table historialmedicoentity add constraint fk_historialmedicoentity_paciente_id foreign key (paciente_id) references pacienteentity (id) on delete restrict on update restrict;

alter table historialdemedicionentity add constraint fk_historialdemedicionentity_paciente_id foreign key (paciente_id) references pacienteentity (id) on delete restrict on update restrict;

alter table lecturaentity add constraint fk_lecturaentity_historial_medicion_id foreign key (historial_medicion_id) references historialdemedicionentity (id) on delete restrict on update restrict;
create index ix_lecturaentity_historial_medicion_id on lecturaentity (historial_medicion_id);

alter table marcapasosentity add constraint fk_marcapasosentity_medico_id foreign key (medico_id) references medicoentity (id) on delete restrict on update restrict;
create index ix_marcapasosentity_medico_id on marcapasosentity (medico_id);

alter table marcapasosentity add constraint fk_marcapasosentity_paciente_id foreign key (paciente_id) references pacienteentity (id) on delete restrict on update restrict;

alter table medicoentity_pacienteentity add constraint fk_medicoentity_pacienteentity_medicoentity foreign key (medicoentity_id) references medicoentity (id) on delete restrict on update restrict;
create index ix_medicoentity_pacienteentity_medicoentity on medicoentity_pacienteentity (medicoentity_id);

alter table medicoentity_pacienteentity add constraint fk_medicoentity_pacienteentity_pacienteentity foreign key (pacienteentity_id) references pacienteentity (id) on delete restrict on update restrict;
create index ix_medicoentity_pacienteentity_pacienteentity on medicoentity_pacienteentity (pacienteentity_id);

alter table pacienteentity add constraint fk_pacienteentity_marcapasos_id foreign key (marcapasos_id) references marcapasosentity (id) on delete restrict on update restrict;

alter table pacienteentity add constraint fk_pacienteentity_historial_mediciones_id foreign key (historial_mediciones_id) references historialdemedicionentity (id) on delete restrict on update restrict;

alter table pacienteentity add constraint fk_pacienteentity_historial_clinico_id foreign key (historial_clinico_id) references historialmedicoentity (id) on delete restrict on update restrict;

alter table pacienteentity_medicoentity add constraint fk_pacienteentity_medicoentity_pacienteentity foreign key (pacienteentity_id) references pacienteentity (id) on delete restrict on update restrict;
create index ix_pacienteentity_medicoentity_pacienteentity on pacienteentity_medicoentity (pacienteentity_id);

alter table pacienteentity_medicoentity add constraint fk_pacienteentity_medicoentity_medicoentity foreign key (medicoentity_id) references medicoentity (id) on delete restrict on update restrict;
create index ix_pacienteentity_medicoentity_medicoentity on pacienteentity_medicoentity (medicoentity_id);


# --- !Downs

alter table if exists consejoentity drop constraint if exists fk_consejoentity_medico_id;
drop index if exists ix_consejoentity_medico_id;

alter table if exists consejoentity drop constraint if exists fk_consejoentity_historial_clinico_id;
drop index if exists ix_consejoentity_historial_clinico_id;

alter table if exists emergenciaentity drop constraint if exists fk_emergenciaentity_paciente_id;
drop index if exists ix_emergenciaentity_paciente_id;

alter table if exists emergenciaentity drop constraint if exists fk_emergenciaentity_medico_id;
drop index if exists ix_emergenciaentity_medico_id;

alter table if exists emergenciaentity drop constraint if exists fk_emergenciaentity_historial_clinico_id;
drop index if exists ix_emergenciaentity_historial_clinico_id;

alter table if exists historialmedicoentity drop constraint if exists fk_historialmedicoentity_paciente_id;

alter table if exists historialdemedicionentity drop constraint if exists fk_historialdemedicionentity_paciente_id;

alter table if exists lecturaentity drop constraint if exists fk_lecturaentity_historial_medicion_id;
drop index if exists ix_lecturaentity_historial_medicion_id;

alter table if exists marcapasosentity drop constraint if exists fk_marcapasosentity_medico_id;
drop index if exists ix_marcapasosentity_medico_id;

alter table if exists marcapasosentity drop constraint if exists fk_marcapasosentity_paciente_id;

alter table if exists medicoentity_pacienteentity drop constraint if exists fk_medicoentity_pacienteentity_medicoentity;
drop index if exists ix_medicoentity_pacienteentity_medicoentity;

alter table if exists medicoentity_pacienteentity drop constraint if exists fk_medicoentity_pacienteentity_pacienteentity;
drop index if exists ix_medicoentity_pacienteentity_pacienteentity;

alter table if exists pacienteentity drop constraint if exists fk_pacienteentity_marcapasos_id;

alter table if exists pacienteentity drop constraint if exists fk_pacienteentity_historial_mediciones_id;

alter table if exists pacienteentity drop constraint if exists fk_pacienteentity_historial_clinico_id;

alter table if exists pacienteentity_medicoentity drop constraint if exists fk_pacienteentity_medicoentity_pacienteentity;
drop index if exists ix_pacienteentity_medicoentity_pacienteentity;

alter table if exists pacienteentity_medicoentity drop constraint if exists fk_pacienteentity_medicoentity_medicoentity;
drop index if exists ix_pacienteentity_medicoentity_medicoentity;

drop table if exists consejoentity cascade;
drop sequence if exists Consejo;

drop table if exists emergenciaentity cascade;
drop sequence if exists Emergencia;

drop table if exists encriptador_entity cascade;

drop table if exists historialmedicoentity cascade;
drop sequence if exists HistorialClinico;

drop table if exists historialdemedicionentity cascade;
drop sequence if exists HistorialDeMedicion;

drop table if exists lecturaentity cascade;
drop sequence if exists Lectura;

drop table if exists marcapasosentity cascade;
drop sequence if exists Marcapasos;

drop table if exists medicoentity cascade;
drop sequence if exists Medico;

drop table if exists medicoentity_pacienteentity cascade;

drop table if exists pacienteentity cascade;
drop sequence if exists Paciente;

drop table if exists pacienteentity_medicoentity cascade;

