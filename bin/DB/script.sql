drop database if exists pucEspacos;

CREATE DATABASE pucEspacos;

USE pucEspacos;

CREATE TABLE tbCampuses (
    campusId Varchar(40) PRIMARY KEY,
    campusName Varchar(50) UNIQUE,
    createdDate Datetime,
    updatedDate Datetime
);

CREATE TABLE tbSchools (
    schoolId Varchar(40) PRIMARY KEY,
    nameSchool Varchar(50),
    createdDate Datetime,
    updatedDate Datetime
);

CREATE TABLE tbBuildings (
    buildingId Varchar(40) PRIMARY KEY,
    campusId Varchar(40),
    schoolId Varchar(40),
    buildingName Varchar(50),
    createdDate Datetime,
    updatedDate Datetime,
    foreign key (campusId) references tbCampuses(campusId),
    foreign key (schoolId) references tbSchools(schoolId)
);

CREATE TABLE tbFacilityTypes (
    facilityTypeId Varchar(40) PRIMARY KEY,
    facilityTypeDescription Varchar(70) UNIQUE
);

CREATE TABLE tbFacilities (
    facilityId Varchar(40) PRIMARY KEY, 
    buildingId Varchar(40),
    facilityTypeId Varchar(40),
    isActive Bool,
    facilityName Varchar(50),
    capacity Int,
    note Varchar(300),
    updatedDate Datetime,
    createdDate Datetime,
    foreign key (buildingId) references tbBuildings(buildingId),
    foreign key (facilityTypeId) references tbFacilityTypes(facilityTypeId)
);

CREATE TABLE tbAssets (
    assetId Varchar(40) PRIMARY KEY,
    assetDescription Varchar(50) UNIQUE
);

CREATE TABLE tbFacilityAssets (
    facilityId Varchar(40),
    assetId Varchar(40),
    quantity Int,
    createdDate Datetime,
    updatedDate Datetime,
    PRIMARY KEY (facilityId, assetId),
    foreign key (facilityId) references tbFacilities(facilityId),
    foreign key (assetId) references tbAssets(assetId)
);

CREATE TABLE tbUsers (
    userId Varchar(40) PRIMARY KEY,
    schoolId Varchar(40),
    email Varchar(50) UNIQUE,
    password Varchar(50),
    userName Varchar(100),
    userType Enum('Secretário','Docente','Discente','Administrador'),
    isActive Bool,
    createdDate Datetime,
    updatedDate Datetime,
    foreign key (schoolId) references tbSchools(schoolId)
);

CREATE TABLE tbReservations (
    reservationId Varchar(40) PRIMARY KEY,
    requestingUserId  Varchar(40),
    responsibleUserId Varchar(40),
    facilityId Varchar(40),
    reservationStatus Enum('Solicitada','Ativa','Cancelada','Em Andamento','Finalizada'),
    reservationPurpose Enum('Aula','Palestra','Lazer','Evento','Estudo','Outro'),
    checkinDate Datetime,
    checkoutDate Datetime,
    createdDate Datetime,
    updatedDate Datetime,
    foreign key (requestingUserId) references tbUsers(userId),
    foreign key (responsibleUserId) references tbUsers(userId),
    foreign key (facilityId) references tbFacilities(facilityId)
);

# ------------------------------Definição UUID---------------------------------------------------
-- Definição das constantes para campusId
SET @curitiba_uuid = '2c897776-8803-49e5-a7a9-5230af79a1a3';
SET @londrina_uuid = 'f09c1739-7130-42e2-88b2-f969161811b3';

-- Definição das constantes para schoolId
SET @belas_artes_id = '6aa77de0-cfd4-43d8-9b72-2e340ee3aa4e';
SET @direito_id = '43c96ad4-ba36-434c-87b2-06c3bc8983d4';
SET @educacao_humanidades_id = 'd9c7db2c-9d4f-465e-9521-b4afa88869f5';
SET @medicina_ciencias_vida_id = '6b72ea20-8ba4-4559-8900-3dc92ad0baf7';
SET @negocios_id = '2666e2e0-8e72-416b-9198-61eceb2a143d';
SET @politecnica_id = 'f5086573-db57-43ef-9fb1-6838d6f1280b';

-- Definição das constantes para buildingId
SET @amarelo_uuid = 'ebb44a25-b7f1-408b-bbde-f829326ae23b';
SET @azul_uuid = '376d163d-e00b-40e6-8857-f402e2feb6a8';
SET @verde_uuid = '24ae3160-4d20-4ff4-9449-8a04103f3b12';
SET @laranja_uuid = '1d5b7102-8342-490e-8cd2-7d120bd9d77e';
SET @vermelho5_uuid = '99b804af-68a1-409b-96e4-19e79a9100c9';
SET @vermelho6_uuid = '4c4f833b-cb09-430f-985d-61edc02fe8da';
SET @usina_piloto_uuid = '09393867-bff7-4be9-96f4-e262d5ea04de';
SET @eletrica_uuid = 'b2c555b6-2f89-45c3-91a8-b946a28a3a16';
SET @mecanica_uuid = '69827e00-eb69-4137-bf06-be4fd0c00dce';
SET @complexo_esportivo_uuid = '207955d9-b5b9-4727-9ac0-aee43b326476';
SET @ginasio_uuid = '2e864359-4977-4b2a-bd96-180a3aeff0c5';

-- Definição das constantes para facilityTypeId
SET @quadra_uuid = '0423b3e6-1244-4cf6-836e-e052d4b6ddb8';
SET @auditorio_uuid = '67ad98ea-f161-4047-977b-73b6090dd5b6';
SET @sala_estudo_uuid = 'afea0592-c942-4178-b967-012b1bfb50e7';
SET @lab_informatica_uuid = 'f58d3ad6-17f4-4f13-9cab-96a3caef3b3b';
SET @sala_aula_uuid = 'ceb542b3-26de-4c9c-bfd3-a57f06f6fc14';

-- Definição das constantes para ativo_id
SET @ventilador_id = 'a96e61e3-1b29-420d-9a50-96227cf74664';
SET @cadeira_id = '9f03a1a6-992c-46a1-8c8e-eafee56dab62';
SET @projetor_id = '59d57938-b93a-4c12-9963-d5ce949368ad';
SET @quadro_branco_id = '30fd6c86-70e1-4308-acd1-f9b7c8673748';
SET @quadro_negro_id = '0cad06ac-51a0-4be0-8e66-3f8d3652393a';
SET @computador_id = 'c499ca7a-4fe1-4fc5-8399-26f31357f3b8';
SET @mesa_id = '9d3979ca-d7fb-4817-b909-0c0338623174';

-- Definição das constantes para userId
SET @joao_id = '13a3c1a5-47cd-4930-89ad-6fcc604c0363';
SET @joana_id = 'c0c9a632-f200-4554-81d1-f79d6a4778ac';
SET @maria_id = 'b2cb9978-4a50-4393-9985-eb8fad46f5f1';
SET @pedro_id = 'eaeead9d-75fe-48e9-a4b5-d2faaa796101';
SET @camila_id = 'd8cd1c16-4960-461d-a781-5d51e6e20105';
SET @lucia_id = '212270a5-cc6f-4040-a149-b44efc24e1a8';
SET @dimitri_id = '168600d3-ee42-4274-8756-80a4110e7d8b';

-- Definição das constantes para facilityId
SET @espaco1 = '90c7e06f-bb73-454e-8114-0b7e556677a6';
SET @espaco2 = '250677f9-3122-44bf-bde9-1b2f9f81b35c';
SET @espaco3 = 'd8ccc4e3-7636-4d35-a7ac-b18231ed8ee5';
SET @espaco4 = 'eebe82c7-eb90-4d82-b70d-6f7fafd2f758';
SET @espaco5 = 'eebe82c7-eb90-4d82-b70d-347fafd27854';

-- Definição das constantes para reservationId
SET @reserva1 = '0cbe9db4-6bf3-43d8-bb73-9a2e2cb9fe3f';
SET @reserva2 = '61646500-1c40-43f8-a3bd-31b7a5681ed7';
SET @reserva3 = '9b119a18-663a-4318-a0a4-75779c122e6a';

# ------------------------------Inserções---------------------------------------------------

-- Inserção Campus
INSERT INTO tbCampuses (campusId, campusName, createdDate, updatedDate)
VALUES 
	(@curitiba_uuid, 'Curitiba', NOW(), NOW()),
	(@londrina_uuid, 'Londrina', NOW(), NOW());
    
-- Inserção Escolas
INSERT INTO tbSchools (schoolId, nameSchool, createdDate, updatedDate) 
VALUES
	(@belas_artes_id, 'Belas Artes', NOW(), NOW()),
	(@direito_id, 'Direito', NOW(), NOW()),
	(@educacao_humanidades_id, 'Educação e Humanidades', NOW(), NOW()),
	(@medicina_ciencias_vida_id, 'Medicina e Ciências da Vida - EMCV', NOW(), NOW()),
	(@negocios_id, 'Negócios', NOW(), NOW()),
	(@politecnica_id, 'Politécnica', NOW(), NOW());

-- Inserção Blocos Com Escola
INSERT INTO tbBuildings (buildingId, campusId, schoolId, buildingName, createdDate, updatedDate)
VALUES 
	(@amarelo_uuid, @curitiba_uuid, @educacao_humanidades_id, '1 - Amarelo', NOW(), NOW()),
	(@verde_uuid, @curitiba_uuid, @medicina_ciencias_vida_id, '3 - Verde', NOW(), NOW()),
	(@laranja_uuid, @curitiba_uuid, @negocios_id, '4 - Laranja', NOW(), NOW()),
	(@vermelho5_uuid, @curitiba_uuid, @direito_id, '5 - Vermelho', NOW(), NOW()),
	(@vermelho6_uuid, @curitiba_uuid, @medicina_ciencias_vida_id, '6 - Vermelho', NOW(), NOW()),
	(@usina_piloto_uuid, @curitiba_uuid, @politecnica_id, '7 - Usina Piloto', NOW(), NOW()),
	(@eletrica_uuid, @curitiba_uuid, @politecnica_id, '8 - Elétrica', NOW(), NOW()),
	(@mecanica_uuid, @curitiba_uuid, @politecnica_id, '9 - Mecânica', NOW(), NOW());

-- Inserção Blocos Sem Escola
INSERT INTO tbBuildings (buildingId, campusId, buildingName, createdDate, updatedDate)
VALUES 
	(@azul_uuid, @curitiba_uuid, '2 - Azul', NOW(), NOW()),
	(@complexo_esportivo_uuid, @curitiba_uuid, 'Complexo Esportivo', NOW(), NOW()),
	(@ginasio_uuid, @curitiba_uuid, 'Ginásio', NOW(), NOW());

-- Inserção Tipos de Espaços
INSERT INTO tbFacilityTypes (facilityTypeId, facilityTypeDescription) 
VALUES 
    (@quadra_uuid, 'Quadra Esportiva'),
    (@auditorio_uuid, 'Auditório'),
    (@sala_estudo_uuid, 'Sala de Estudo'),
    (@lab_informatica_uuid, 'Laboratório de Informática'),
    (@sala_aula_uuid, 'Sala de Aula');
    
-- Inserção Espaços
INSERT INTO tbFacilities (facilityId, buildingId, facilityTypeId, isActive, facilityName, capacity, note, updatedDate, createdDate)
VALUES 
	(@espaco1, @azul_uuid,  @lab_informatica_uuid, 1, 'Sala Grace Hopper', 80, 'Janela qubrada.', NOW(), NOW()),
    (@espaco2, @azul_uuid,  @sala_aula_uuid, 1, 'Sala de Aula 17', 30, null, NOW(), NOW()),
    (@espaco3, @vermelho5_uuid,  @sala_aula_uuid, 1, 'Araça 303', 80, null, NOW(), NOW()),
    (@espaco4, @ginasio_uuid,  @quadra_uuid, 1, 'Quadra de volêi femenina 1', null, null, NOW(), NOW()),
    (@espaco5, @amarelo_uuid,  @auditorio_uuid, 1, 'Sala de realizada aumentada', 25, null, NOW(), NOW()); 
   
-- Inserção Ativos
INSERT INTO tbAssets (assetId, assetDescription)
VALUES 
    (@ventilador_id, 'Ventilador'),
    (@cadeira_id, 'Cadeira'),
    (@projetor_id, 'Projetor'),
    (@quadro_branco_id, 'Quadro Branco'),
    (@quadro_negro_id, 'Quadro Negro'),
    (@computador_id, 'Computador'),
    (@mesa_id, 'Mesas');
   
-- Inserção Ativos Espaços
INSERT INTO tbFacilityAssets (facilityId, assetId, quantity, updatedDate, createdDate)
VALUES 
	(@espaco1, @ventilador_id, 3, NOW(), NOW()),
	(@espaco1, @cadeira_id, 20, NOW(), NOW()),
	(@espaco2, @quadro_branco_id, 2, NOW(), NOW()),
	(@espaco3, @computador_id, 35, NOW(), NOW()),
	(@espaco3, @projetor_id, 2, NOW(), NOW()),
	(@espaco3, @mesa_id, 20, NOW(), NOW());

-- Inserção Usuáios Sem Escola
INSERT INTO tbUsers ( userId, email, password, userName, userType, isActive, createdDate, updatedDate)
VALUES 
	(@maria_id, 'maria@pucpr.edu.br', 'Password.123', 'Maria', 'Discente', 1, NOW(), NOW()),
	(@pedro_id, 'pedro@pucpr.edu.br', 'Password.123', 'Pedro', 'Discente', 1, NOW(), NOW()),
	(@lucia_id, 'lucia@pucpr.br', 'Password.123', 'Lucia', 'Docente', 1, NOW(), NOW()),
	(@camila_id, 'camila@pucpr.br', 'Password.123', 'Camila', 'Administrador', 1, NOW(), NOW()),
    (@dimitri_id, 'dimitri@pucpr.br', 'Password.123', 'Dimitri', 'Administrador', 1, NOW(), NOW());

-- Inserção Usuáios Com Escola
INSERT INTO tbUsers (userId, schoolId, email, password, userName, userType, isActive, createdDate, updatedDate)
VALUES 
	(@joao_id, @direito_id, 'joao@pucpr.br', 'Password.123', 'João', 'Secretário', 1, NOW(), NOW()),
	(@joana_id, @educacao_humanidades_id, 'joana@pucpr.br', 'Password.123', 'Joana', 'Secretário', 1, NOW(), NOW());
    

-- Inserção Reserva
INSERT INTO tbReservations (reservationId, requestingUserId, responsibleUserId, facilityId, reservationStatus, reservationPurpose, checkinDate, checkoutDate, createdDate, updatedDate)
VALUES 
	(@reserva1, @maria_id, @maria_id, @espaco1, 'Solicitada', 'Aula', '2024-03-24 07:50:00', '2024-03-24 09:20:00', NOW(), NOW()),
    (@reserva2, @pedro_id, @maria_id, @espaco2, 'Solicitada', 'Palestra', '2024-03-27 13:25:00', '2024-03-27 16:00:00', NOW(), NOW());
    
   select * from tbReservations;
    
-- Comandos Seleção
-- Ver Status das minhas reservas
select u.userName, r.reservationStatus
from tbUsers u, tbReservations r
where r.requestingUserId = u.userId AND
	  u.userId = @maria_id;

-- Ver quantas reservas que sou responsavel
select u.userName, count(*)
from tbUsers u, tbReservations r
where r.responsibleUserId = u.userId AND
	  u.userId = @maria_id;
      
-- Comandos Atualizar
-- Desativar Usuario
update tbUsers
set isActive = false
where userId = @maria_id;