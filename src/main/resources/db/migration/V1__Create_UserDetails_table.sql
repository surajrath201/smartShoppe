CREATE TABLE `userDetails` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `f_name` VARCHAR(45) NOT NULL,
  `m_name` VARCHAR(45) NULL,
  `l_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(50) NULL,
  `country_code` INT UNSIGNED NOT NULL,
  `mobile_number` VARCHAR(45) NULL,
  `password` VARCHAR(45) NOT NULL,
  `type` ENUM('SUPERADMIN', 'ADMIN', 'CUSTOMER') NOT NULL,
  `dob` DATE NULL,
  `created_timestamp` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_timestamp` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `email_mobile_unq` (`email` ASC, `mobile_number` ASC) VISIBLE);