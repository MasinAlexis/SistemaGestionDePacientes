CREATE DATABASE `bdfisiocir`;

USE `bdfisiocir`;

CREATE TABLE `bd_gestion_de_pagos` (
  `id_gestion_de_pagos` bigint NOT NULL AUTO_INCREMENT,
  `cantidad_sesiones_pagadas` int DEFAULT NULL,
  `dni` int DEFAULT NULL,
  `fecha_pago` datetime(6) DEFAULT NULL,
  `importe_pagado` double DEFAULT NULL,
  `observaciones` varchar(1000) DEFAULT NULL,
  `tratamiento` bigint DEFAULT NULL,
  PRIMARY KEY (`id_gestion_de_pagos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bd_paciente` (
  `id_paciente` bigint NOT NULL AUTO_INCREMENT,
  `baja_logica` int DEFAULT NULL,
  `dni` int DEFAULT NULL,
  `historia_clinica` varchar(5000) DEFAULT NULL,
  `nombre_apellido` varchar(250) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id_paciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bd_precio_turno` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha_de_implementacion` datetime(6) DEFAULT NULL,
  `precio_sesion` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bd_tratamiento_paciente` (
  `id_tratamiento` bigint NOT NULL AUTO_INCREMENT,
  `bonos` varchar(200) DEFAULT NULL,
  `cantidad_de_turnos` int DEFAULT NULL,
  `control_de_sesiones_pagas` int DEFAULT NULL,
  `dni` int DEFAULT NULL,
  `estampillado` varchar(200) DEFAULT NULL,
  `fecha_tratamiento` datetime(6) DEFAULT NULL,
  `obra_social` varchar(100) DEFAULT NULL,
  `observaciones_de_tratamiento` varchar(3000) DEFAULT NULL,
  `tratamiento` varchar(1000) DEFAULT NULL,
  `tratamiento_concluido` int DEFAULT NULL,
  PRIMARY KEY (`id_tratamiento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `usuario` (
  `id_usuario` bigint NOT NULL AUTO_INCREMENT,
  `email_usuario` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `rol_usuario` varchar(10) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `resumen_semanal` (
  `id_resumen_semanal` bigint NOT NULL AUTO_INCREMENT,
  `dia_semana_fin` datetime(6) DEFAULT NULL,
  `dia_semana_inicio` datetime(6) DEFAULT NULL,
  `total_semanal` double DEFAULT NULL,
  PRIMARY KEY (`id_resumen_semanal`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- La contraseña indicada corresponde a 1234
INSERT INTO `bdfisiocir`.`usuario` (`id_usuario`,`email_usuario`,`password`,`rol_usuario`,`username`)
	   VALUES(1,'AlexisMasin86@gmail.com','$2a$10$PpSiX8kyeMYYFNtQhM5ZY.eTUJvJ8FEsk4gDwwMLyrvBLDRmagdpC','ROLE_ADMIN','masinalexis');

-- Indices para los siguientes atributos (Análisis hecho en cuanto al funcionamiento de las API's)
-- FindPacienteByDni(Paciente) : 14 llamados
-- BuscarPorId(TratamientoPAciente): 6 llamadas
--                  Indice para dni(Paciente)
--                  Indice para idTratamiento(TratamientoPaciente)

CREATE INDEX idx_dni ON bd_paciente (dni);
CREATE INDEX idx_id_tratamiento ON bd_tratamiento_paciente (id_tratamiento);