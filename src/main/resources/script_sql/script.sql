/*
CREATE ROLE email_system_tecnoweb
	WITH
	LOGIN
	CONNECTION LIMIT -1
	PASSWORD 'C8A659BBF4BB7760CE3FD899EFAEA119E648FD7502D52E30779CA0FA37837299';

CREATE DATABASE email_system_tecnoweb
	with owner=email_system_tecnoweb
	encoding='UTF8'
	tablespace=pg_default
	CONNECTION LIMIT=-1;
*/

create table usuario(
	ci int primary key,
	nombre varchar(255) not null,
	telefono varchar(8) not null,
	email varchar(255) not null,
	estado char(1) not null,		-- 1:enabled , 2:disabled
	contraseña text not null,
	direccion varchar(255) not null
);

create table secretaria(
	ci_secretaria int primary key,
	fecha_inicio date not null,		-- begin date < end date
	fecha_fin date not null,
	foreign key (ci_secretaria) references usuario(ci)
	on update cascade
	on delete cascade
);

create table directiva(
	ci_directiva int primary key,
	cargo varchar(30) not null,	-- presidente, vice presidente, sec. de hacienda, sec. deportes, sec. actas, vocal 1..
	fecha_inicio date not null,
	foreign key (ci_directiva) references usuario(ci)
	on update cascade
	on delete cascade
);

create table socio(
	ci_socio int primary key,
	fecha_afiliacion date not null,
	nro_puesto int not null,				-- nro de puesto
	tipo_socio char(1) not null,			-- '1':activo , '2':pasivo
	fecha_inicio date not null,
	foreign key (ci_socio) references usuario(ci)
	on update cascade
	on delete cascade
);

create table tipo_ingreso(
	codigo serial primary key,
	nombre varchar(20) not null
);

create table ingreso(
	nro_ingreso serial primary key,
	detalle varchar(255) not null,
	fecha_ingreso date not null,
	monto decimal(8,2) not null,
	cod_tipo_ingreso int not null,
	ci_secretaria int not null,
	foreign key (cod_tipo_ingreso) references tipo_ingreso(codigo)
	on update cascade
	on delete cascade,
	foreign key (ci_secretaria) references secretaria(ci_secretaria)
	on update cascade
	on delete cascade
);

create table acta_reunion(
	nro_acta serial primary key,
	fecha_reunion date not null,
	descripcion text not null,
	ci_secretaria int not null,
	foreign key (ci_secretaria) references secretaria(ci_secretaria)
	on update cascade
	on delete cascade
);

create table egreso(
	nro_egreso serial primary key,
	detalle varchar(255) not null,
	monto decimal(8,2) not null,
	fecha_egreso date not null,
	actor_receptor varchar(255) not null,
	ci_secretaria int not null,
	foreign key (ci_secretaria) references secretaria(ci_secretaria)
	on update cascade
	on delete cascade
);

create table aporte(
	id serial primary key,
	descripcion varchar(255) not null,
	fecha_inicio_pago date not null,
	cant_coutas int not null,
	monto decimal(8,2) not null,
	fecha_limite date not null
);

create table pago(
	nro_pago serial primary key,
	fecha_pago date not null,
	monto decimal(8,2) not null,
	comprobante varchar(255) null,
	monto_mora decimal(8,2) not null,
	ci_socio int not null,
	ci_secretaria int not null,
	id_aporte int not null,
	foreign key (ci_socio) references socio(ci_socio)
	on update cascade
	on delete cascade,
	foreign key (ci_secretaria) references secretaria(ci_secretaria)
	on update cascade
	on delete cascade,
	foreign key (id_aporte) references aporte(id)
	on update cascade
	on delete cascade
);

create table multa(
	id serial primary key,
	descripcion varchar(255) not null,
	monto decimal(8,2) not null
);

create table multa_pago(
	nro_pago int not null,
	id_multa int not null,
	primary key (nro_pago,id_multa),
	foreign key (nro_pago) references pago(nro_pago)
	on update cascade
	on delete cascade,
	foreign key (id_multa) references multa(id)
	on update cascade
	on delete cascade
);

create table multa_socio(
	ci_socio int not null,
	id_multa int not null,
	primary key (ci_socio, id_multa),
	foreign key (ci_socio) references socio(ci_socio)
	on update cascade
	on delete cascade,
	foreign key (id_multa) references multa(id)
	on update cascade
	on delete cascade
);

create table asistencia(
	id serial primary key,
	fecha_actividad date not null,
	actividad varchar(255) not null
);

create table asistencia_socio(
	id_asistencia int not null,
	ci_socio int not null,
	primary key (id_asistencia, ci_socio),
	foreign key (id_asistencia) references asistencia(id)
	on update cascade
	on delete cascade,
	foreign key (ci_socio) references socio(ci_socio)
	on update cascade
	on delete cascade
);