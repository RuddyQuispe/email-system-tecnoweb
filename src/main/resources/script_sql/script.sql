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
	direccion varchar(255) not null,
	tipo_usuario char(1) not null	-- P:socio , D:directivo , S:secretari@
);

create table secretaria(
	ci_secretaria int primary key,
	fecha_inicio date not null,		-- begin date < end date
	fecha_fin date not null,
	foreign key (ci_secretaria) references usuario(ci)
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

create table ingreso(
	nro_ingreso serial primary key,
	detalle varchar(255) not null,
	fecha_ingreso date not null,
	monto decimal(8,2) not null,
	ci_secretaria int not null,
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
	monto decimal(8,2) not null,
	fecha_limite date not null,
	porcentaje_mora smallint
);

create table pago(
	nro_pago serial primary key,
	fecha_pago date not null,
	monto_total decimal(8,2) not null,
	comprobante varchar(255) null,
	ci_socio int not null,
	ci_secretaria int not null,
	foreign key (ci_socio) references socio(ci_socio)
	on update cascade
	on delete cascade,
	foreign key (ci_secretaria) references secretaria(ci_secretaria)
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

create table aporte_pago(
	nro_pago int not null,
	id_aporte int not null,
	monto_mora decimal(8,2) not null,
	primary key (nro_pago,id_aporte),
	foreign key (nro_pago) references pago(nro_pago)
	on update cascade
	on delete cascade,
	foreign key (id_aporte) references aporte(id)
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

insert into usuario values(9719822, 'zuleny', '76431901', 'zuleny.cr@gmail.com', '1', '11235813', 'Avenida Banzer', 'S');
insert into usuario values(9719823, 'stephani', '68629092', 'stephani.hc.97@gmail.com', '1', '11235813', 'Avenida Pirai', 'P');
insert into usuario(ci,nombre,telefono,email,estado,contraseña,direccion,tipo_usuario) values (6868161, 'Jose Pereira', '68686965', 'jose@gmail.com', '0','123456', 'Av. La Barranca #45', 'S');

insert into secretaria values(9719822, '2021-12-12', '2022-12-12');

insert into socio values(9719823, '12-12-2020', 12, '1', '12-08-2021');
insert into socio(ci_socio, fecha_afiliacion, nro_puesto, tipo_socio, fecha_inicio) values (6868161, '2021-01-08', 45, '1', '2021-01-08');

insert into ingreso(detalle, fecha_ingreso, monto, ci_secretaria) values
('recibo de donacion de autoridades municipales', '10-10-2021', 500, 9719822),
('ingreso por aportes voluntarios', '15-10-2021', 150, 9719822),
('ingreso de ventas de mercado', '20-10-2021', 300, 9719822),
('ingreso de efectivo en ek servicio de baño publico', '30-10-2021', 2500, 9719822);

insert into acta_reunion(fecha_reunion,descripcion,ci_secretaria) values
('21-12-2021', 'Lorem ipsum dolor sit amet consectetur adipiscing elit justo, urna nulla penatibus vivamus cras nostra eget, est fusce eu montes curabitur facilisi egestas. Pellentesque habitasse tempor at rutrum consequat, vulputate cras quis nullam sed lacinia, vel curae ad mus. Hac sagittis dictumst volutpat nisi sociosqu quam senectus scelerisque aptent, semper habitasse lacus nunc fusce mus consequat suscipit, nulla donec vulputate hendrerit accumsan sociis dis taciti. Aptent lectus tincidunt quam laoreet posuere a suscipit proin arcu, placerat tristique egestas cubilia bibendum vehicula sagittis penatibus taciti donec, tellus accumsan aliquet id facilisi imperdiet sem hendrerit. Taciti magnis leo netus nascetur litora velit rhoncus orci feugiat, curabitur hac fringilla enim porta luctus urna aptent in, fermentum hendrerit praesent augue turpis at eu senectus. Tristique leo nisi odio pellentesque nibh, taciti tortor aliquam fringilla mollis magnis, erat suspendisse accumsan hendrerit.',9719822);

insert into egreso(detalle,monto,fecha_egreso,actor_receptor,ci_secretaria) values
('pago de servico basico de luz', 1500, '8-12-2021','Empresa CRE', 9719822),
('pago de servico basico de agua', 1200, '8-12-2021','Empresa Saguapac', 9719822);

insert into aporte (descripcion,fecha_inicio_pago,monto,fecha_limite, porcentaje_mora) values
('Aporte por fiestas de navidad', '3-12-2021', 50, '20-12-2021', 3),
('Aporte por aniversario', '1-9-2021', 30, '20-9-2021', 3);

insert into pago (fecha_pago,monto_total,comprobante,ci_socio,ci_secretaria) values
('1-12-2021', 120, '873291047', 9719823, 9719822),
('10-12-2021', 170, '872721047', 9719823, 9719822);

insert into multa (descripcion,monto) values
('Multa por faltar a reunion', 20),
('Multa por no salir a vender', 100);

insert into multa_pago values
(1, 1),
(2, 2);

insert into aporte_pago values
(1, 1, 1.5),
(1, 2, 0.9)

insert into multa_socio values
(9719823, 1),
(9719823, 2);

insert into asistencia(fecha_actividad,actividad) values
('1-12-2021', 'Reunion Semanal'),
('3-12-2021', 'Reunion Para definir nueva directiva');

insert into asistencia_socio values
(1, 9719823),
(2, 9719823);

--procedures and functions
create or replace function get_name_by_userci(ci_usuario integer)returns varchar(255) as
$BODY$
begin
	return (select nombre from usuario u where ci = ci_usuario);
end $BODY$ language plpgsql;

create or replace function insert_aporte_pago()returns trigger as
$BODY$
declare monto_aporte decimal(8,2) = (select monto from aporte a where a.id = new.id_aporte);
declare monto_mora decimal(8,2) = new.monto_mora;
begin
	update pago set monto_total = monto_total + monto_aporte + monto_mora where nro_pago = new.nro_pago;
	return new;
end $BODY$ language plpgsql;

create trigger t_insert_aporte_pago after insert
on aporte_pago
for each row
	execute procedure insert_aporte_pago();


create or replace function delete_aporte_pago()returns trigger as
$BODY$
declare monto_aporte decimal(8,2) = (select monto from aporte a where a.id = old.id_aporte);
declare monto_mora decimal(8,2) = old.monto_mora;
begin
	update pago set monto_total = monto_total - monto_aporte - monto_mora where nro_pago = old.nro_pago;
	return old;
end $BODY$ language plpgsql;

create trigger t_delete_aporte_pago after delete
on aporte_pago
for each row
	execute procedure delete_aporte_pago();


create or replace function insert_multa_pago()returns trigger as
$BODY$
declare monto_multa decimal(8,2) = (select monto from multa where id = new.id_multa);
begin
	update pago set monto_total = monto_total + monto_multa where nro_pago = new.nro_pago;
	return new;
end $BODY$ language plpgsql;

create trigger t_insert_multa_pago after insert
on multa_pago
for each row
	execute procedure insert_multa_pago();


create or replace function delete_multa_pago()returns trigger as
$BODY$
declare monto_multa decimal(8,2) = (select monto from multa where id = old.id_multa);
begin
	update pago set monto_total = monto_total - monto_multa where nro_pago = old.nro_pago;
	return old;
end $BODY$ language plpgsql;

create trigger t_delete_multa_pago after delete
on multa_pago
for each row
	execute procedure delete_multa_pago();