delete from rodovia;
delete from veiculo;

insert into rodovia (tipo_via, custo_km_rodado) values ('PAVIMENTADA', .54);
insert into rodovia (tipo_via, custo_km_rodado) values ('NAO_PAVIMENTADA', .62);

insert into veiculo (nome, multiplicador_custo) values ('Caminhão baú', 1.00);
insert into veiculo (nome, multiplicador_custo) values ('Caminhão caçamba', 1.05);
insert into veiculo (nome, multiplicador_custo) values ('Carreta', 1.12);
