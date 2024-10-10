CREATE TABLE `jdbc_ecouture`.`user` (
  `user_ID` INT NOT NULL AUTO_INCREMENT,
  `user_username` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(43) NOT NULL,
  `user_realname` VARCHAR(45) NULL,
  PRIMARY KEY (`user_ID`)
);

CREATE TABLE `jdbc_ecouture`.`brand` (
  `brand_ID` INT NOT NULL AUTO_INCREMENT,
  `brand_name` VARCHAR(45) NOT NULL,
  `brand_description` VARCHAR(160) NULL,
  `brand_rating` DECIMAL(2,1) NOT NULL,
  PRIMARY KEY (`brand_ID`),
  CHECK (`brand_rating` >= 1.0 AND `brand_rating` <= 5.0)
);

CREATE TABLE `jdbc_ecouture`.`material` (
  `material_ID` INT NOT NULL AUTO_INCREMENT,
  `material_type` VARCHAR(45) NOT NULL,
  `material_decomp` DECIMAL(3,2) NOT NULL,
  `material_emission` DECIMAL(2,1) NOT NULL,
  `material_water` INT NOT NULL,
  `material_energy` INT NOT NULL,
  `material_rating` DECIMAL(2,1) NOT NULL,
  PRIMARY KEY (`material_ID`),
  CHECK (`material_rating` >= 1.0 AND `material_rating` <= 5.0)
);

CREATE TABLE `jdbc_ecouture`.`clothes` (
  `clothes_ID` INT NOT NULL AUTO_INCREMENT,
  `clothes_name` VARCHAR(45) NOT NULL,
  `clothes_type` VARCHAR(45) NOT NULL,
  `clothes_acquisition` VARCHAR(45) NOT NULL,
  `brand_ID` INT NULL,
  `user_ID` INT NOT NULL,
  PRIMARY KEY (`clothes_ID`),
  FOREIGN KEY (`brand_ID`) REFERENCES `jdbc_ecouture`.`brand` (`brand_ID`) ON DELETE CASCADE,
  FOREIGN KEY (`user_ID`) REFERENCES `jdbc_ecouture`.`user` (`user_ID`) ON DELETE CASCADE
);

CREATE TABLE `jdbc_ecouture`.`clothes_material` (
  `clothes_ID` INT NOT NULL,
  `material_ID` INT NOT NULL,
  FOREIGN KEY (`clothes_ID`) REFERENCES `jdbc_ecouture`.`clothes` (`clothes_ID`) ON DELETE CASCADE,
  FOREIGN KEY (`material_ID`) REFERENCES `jdbc_ecouture`.`material` (`material_ID`) ON DELETE CASCADE
);

CREATE TABLE `jdbc_ecouture`.`post` (
  `post_ID` INT NOT NULL AUTO_INCREMENT,
  `post_title` VARCHAR(100) NOT NULL,
  `post_content` VARCHAR(2000) NOT NULL,
  `post_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_ID` INT NOT NULL,
  PRIMARY KEY (`post_ID`),
  FOREIGN KEY (`user_ID`) REFERENCES `jdbc_ecouture`.`user` (`user_ID`) ON DELETE CASCADE
);

CREATE TABLE `jdbc_ecouture`.`comment` (
  `comment_ID` INT NOT NULL AUTO_INCREMENT,
  `comment_content` VARCHAR(500) NOT NULL,
  `comment_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `post_ID` INT NOT NULL,
  `user_ID` INT NOT NULL,
  PRIMARY KEY (`comment_ID`),
  FOREIGN KEY (`post_ID`) REFERENCES `jdbc_ecouture`.`post` (`post_ID`) ON DELETE CASCADE,
  FOREIGN KEY (`user_ID`) REFERENCES `jdbc_ecouture`.`user` (`user_ID`) ON DELETE CASCADE
);
