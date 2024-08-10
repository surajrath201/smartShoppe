ALTER TABLE `smartShoppe`.`vendor`
CHANGE COLUMN `vendor_name` `vendor_name` VARCHAR(255) NOT NULL ,
ADD UNIQUE INDEX `vendor_name_UNIQUE` (`vendor_name` ASC) VISIBLE;
;
