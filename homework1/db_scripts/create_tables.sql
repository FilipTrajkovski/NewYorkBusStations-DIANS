CREATE SCHEMA IF NOT EXISTS `buses`;

CREATE TABLE IF NOT EXISTS `buses`.`bus` (
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(60) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `buses`.`bus_station` (
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(120) NOT NULL,
    `longitude` VARCHAR(20) NOT NULL,
    `latitude` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `buses`.`bus_network` (
    `bus_id` BIGINT(19) NOT NULL,
    `bus_station_id` BIGINT(19) NOT NULL,
    PRIMARY KEY (`bus_id`, `bus_station_id`),
    INDEX `fk_bus_station` (`bus_station_id`),
    CONSTRAINT `fk_bus` FOREIGN KEY (`bus_id`) REFERENCES `buses`.`bus` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT `fk_bus_station` FOREIGN KEY (`bus_station_id`) REFERENCES `buses`.`bus_station` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS `buses`.`data_info` (
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT,
    `data_hash` VARCHAR(255) NOT NULL,
    `updated_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`)
);
