CREATE TABLE `jdbc_ecouture`.`user` (
  `user_ID` INT NOT NULL AUTO_INCREMENT,
  `user_username` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(100) NOT NULL,
  `user_realname` VARCHAR(45) NULL,
  PRIMARY KEY (`user_ID`)
);

CREATE TABLE `jdbc_ecouture`.`brand` (
  `brand_ID` INT NOT NULL AUTO_INCREMENT,
  `brand_name` VARCHAR(45) NOT NULL,
  `brand_rating` DECIMAL(2,1) NOT NULL,
  PRIMARY KEY (`brand_ID`),
  CHECK (`brand_rating` >= 0.0 AND `brand_rating` <= 5.0)
);

CREATE TABLE `jdbc_ecouture`.`material` (
  `material_ID` INT NOT NULL AUTO_INCREMENT,
  `material_type` VARCHAR(45) NOT NULL,
  `material_decomp` DECIMAL(6,1) NOT NULL,
  `material_emission` DECIMAL(4,2) NOT NULL,
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
  `Percentage` INT NOT NULL,
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

INSERT INTO material (material_type, material_decomp, material_emission, material_water, material_energy, material_rating) VALUES
('Acrylic', 2400.0, 38.0, 115, 175, 2.4),
('Cotton', 5.0, 1.8, 15000, 60, 3.7),
('Elastane/Spandex/Lycra', 4800.0, 20.0, 110, 100, 2.3),
('Leather', 720.0, 61.1, 17000, 80, 2.1),
('Linen', 0.5, 16.7, 250, 22.5, 4.1),
('Nylon/Polyamide', 420.0, 7.9, 125, 95, 2.9),
('Organic Cotton', 5.0, 1.0, 2500, 46, 3.7),
('Polyester', 1320.0, 14.2, 150, 90, 2.4),
('Polyurethane', 12000.0, 6.4, 25, 100, 2.4),
('Rayon/Viscose', 1.5, 14.0, 8000, 12.5, 4.2),
('Silk', 30.0, 25.0, 1000, 35, 2.8),
('Wool', 13.5, 30.0, 170000, 25, 2.6);

INSERT INTO brand (brand_name, brand_rating) VALUES
('Other', 0),
('Abercrombie & Fitch', 2),
('Adidas', 2),
('Alo Yoga', 2),
('American Eagle', 2),
('Aritzia', 2),
('Athropologie', 2),
('Afends', 4),
('Brandy Melville', 1),
('CHNGE', 5),
('Charlotte Russe', 1),
('Cotton On', 2),
('DK Active', 5),
('Fashion Nova', 1),
('Forever21', 2),
('Francesca’s', 1),
('Free People', 2),
('Girlfriend Collective', 4),
('GAP', 3),
('Guess', 2),
('H&M', 2),
('Hollister', 3),
('Hot Topic', 1),
('J.Crew', 3),
('Levi Strauss & Co.', 4),
('Lululemon', 2),
('Motel Rocks', 1),
('Nasty Gal', 2),
('Nike', 3),
('Nordstrom', 2),
('Old Navy', 3),
('Organic Basics', 4),
('PacSun', 2),
('PrettyLittleThing', 2),
('Princess Polly', 2),
('Ralph Lauren', 4),
('Romwe', 1),
('Shein', 1),
('Steve Madden', 2),
('Stüssy', 1),
('Tilly’s', 1),
('Tommy Hilfiger', 3),
('Tripulse', 5),
('Under Armour', 2),
('Uniqlo', 3),
('Urban Outfitters', 2),
('Windsor', 1),
('Zara', 2),
('Zumiez', 2);
