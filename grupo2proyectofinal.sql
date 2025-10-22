-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-10-2025 a las 00:06:26
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `grupo2proyectofinal`
--
CREATE DATABASE IF NOT EXISTS `grupo2proyectofinal` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `grupo2proyectofinal`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `codCli` int(11) NOT NULL,
  `dni` varchar(15) NOT NULL,
  `nombreCompleto` varchar(100) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `afecciones` text DEFAULT NULL,
  `estado` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consultorio`
--

CREATE TABLE `consultorio` (
  `nroConsultorio` int(11) NOT NULL,
  `usos` text DEFAULT NULL,
  `equipamiento` text DEFAULT NULL,
  `apto` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `diadespa`
--

CREATE TABLE `diadespa` (
  `codPack` int(11) NOT NULL,
  `fechaHora` datetime NOT NULL,
  `preferencias` text DEFAULT NULL,
  `monto` decimal(10,2) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT 1,
  `codCli` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `instalacion`
--

CREATE TABLE `instalacion` (
  `codInstal` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `detalleUso` text DEFAULT NULL,
  `precio30m` decimal(10,2) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `masajista`
--

CREATE TABLE `masajista` (
  `matricula` int(11) NOT NULL,
  `nombreCompleto` varchar(100) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `especialidad` enum('facial','corporal','relajacion','estetico') NOT NULL,
  `estado` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sesion`
--

CREATE TABLE `sesion` (
  `codSesion` int(11) NOT NULL,
  `fechaHoraInicio` datetime NOT NULL,
  `fechaHoraFin` datetime NOT NULL,
  `estado` tinyint(1) DEFAULT 1,
  `codTratam` int(11) DEFAULT NULL,
  `nroConsultorio` int(11) DEFAULT NULL,
  `matricula` int(11) DEFAULT NULL,
  `codPack` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sesion_instalacion`
--

CREATE TABLE `sesion_instalacion` (
  `codSesion` int(11) NOT NULL,
  `codInstal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tratamiento`
--

CREATE TABLE `tratamiento` (
  `codTratam` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `tipo` enum('facial','corporal','relajacion','estetico') NOT NULL,
  `detalle` text DEFAULT NULL,
  `duracion` int(11) DEFAULT NULL,
  `costo` decimal(10,2) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`codCli`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- Indices de la tabla `consultorio`
--
ALTER TABLE `consultorio`
  ADD PRIMARY KEY (`nroConsultorio`);

--
-- Indices de la tabla `diadespa`
--
ALTER TABLE `diadespa`
  ADD PRIMARY KEY (`codPack`),
  ADD KEY `codCli` (`codCli`);

--
-- Indices de la tabla `instalacion`
--
ALTER TABLE `instalacion`
  ADD PRIMARY KEY (`codInstal`);

--
-- Indices de la tabla `masajista`
--
ALTER TABLE `masajista`
  ADD PRIMARY KEY (`matricula`);

--
-- Indices de la tabla `sesion`
--
ALTER TABLE `sesion`
  ADD PRIMARY KEY (`codSesion`),
  ADD KEY `codTratam` (`codTratam`),
  ADD KEY `nroConsultorio` (`nroConsultorio`),
  ADD KEY `matricula` (`matricula`),
  ADD KEY `codPack` (`codPack`);

--
-- Indices de la tabla `sesion_instalacion`
--
ALTER TABLE `sesion_instalacion`
  ADD PRIMARY KEY (`codSesion`,`codInstal`),
  ADD KEY `codInstal` (`codInstal`);

--
-- Indices de la tabla `tratamiento`
--
ALTER TABLE `tratamiento`
  ADD PRIMARY KEY (`codTratam`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `codCli` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `consultorio`
--
ALTER TABLE `consultorio`
  MODIFY `nroConsultorio` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `diadespa`
--
ALTER TABLE `diadespa`
  MODIFY `codPack` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `instalacion`
--
ALTER TABLE `instalacion`
  MODIFY `codInstal` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sesion`
--
ALTER TABLE `sesion`
  MODIFY `codSesion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tratamiento`
--
ALTER TABLE `tratamiento`
  MODIFY `codTratam` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `diadespa`
--
ALTER TABLE `diadespa`
  ADD CONSTRAINT `diadespa_ibfk_1` FOREIGN KEY (`codCli`) REFERENCES `cliente` (`codCli`);

--
-- Filtros para la tabla `sesion`
--
ALTER TABLE `sesion`
  ADD CONSTRAINT `sesion_ibfk_1` FOREIGN KEY (`codTratam`) REFERENCES `tratamiento` (`codTratam`),
  ADD CONSTRAINT `sesion_ibfk_2` FOREIGN KEY (`nroConsultorio`) REFERENCES `consultorio` (`nroConsultorio`),
  ADD CONSTRAINT `sesion_ibfk_3` FOREIGN KEY (`matricula`) REFERENCES `masajista` (`matricula`),
  ADD CONSTRAINT `sesion_ibfk_4` FOREIGN KEY (`codPack`) REFERENCES `diadespa` (`codPack`);

--
-- Filtros para la tabla `sesion_instalacion`
--
ALTER TABLE `sesion_instalacion`
  ADD CONSTRAINT `sesion_instalacion_ibfk_1` FOREIGN KEY (`codSesion`) REFERENCES `sesion` (`codSesion`),
  ADD CONSTRAINT `sesion_instalacion_ibfk_2` FOREIGN KEY (`codInstal`) REFERENCES `instalacion` (`codInstal`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
