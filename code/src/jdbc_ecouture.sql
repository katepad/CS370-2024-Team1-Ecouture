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
  `brand_description` VARCHAR(160) NULL,
  `brand_rating` DECIMAL(2,1) NOT NULL,
  PRIMARY KEY (`brand_ID`),
  CHECK (`brand_rating` >= 1.0 AND `brand_rating` <= 5.0)
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
  `Percentage` DECIMAL(5,2) NOT NULL,
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

INSERT INTO brand (brand_name, brand_description, brand_rating) VALUES
('Abercrombie & Fitch', 'Enter description here.', 2),
('Adidas', 'Enter description here.', 2),
('Alo Yoga', 'Enter description here.', 2),
('American Eagle', 'Enter description here.', 2),
('Aritzia', 'Enter description here.', 2),
('Athropologie', 'Enter description here.', 2),
('Afends', 'Enter description here.', 4),
('Brandy Melville', 'Enter description here.', 1),
('CHNGE', 'Enter description here.', 5),
('Charlotte Russe', 'Enter description here.', 1),
('Cotton On', 'Enter description here.', 2),
('DK Active', 'Enter description here.', 5),
('Fashion Nova', 'Enter description here.', 1),
('Forever21', 'Enter description here.', 2),
('Francesca’s', 'Enter description here.', 1),
('Free People', 'Enter description here.', 2),
('Girlfriend Collective', 'Enter description here.', 4),
('GAP', 'Enter description here.', 3),
('Guess', 'Enter description here.', 2),
('H&M', 'Enter description here.', 2),
('Hollister', 'Enter description here.', 3),
('Hot Topic', 'Enter description here.', 1),
('J.Crew', 'Enter description here.', 3),
('Levi Strauss & Co.', 'Enter description here.', 4),
('Lululemon', 'Enter description here.', 2),
('Motel Rocks', 'Enter description here.', 1),
('Nasty Gal', 'Enter description here.', 2),
('Nordstrom', 'Enter description here.', 2),
('Old Navy', 'Enter description here.', 3),
('Organic Basics', 'Enter description here.', 4),
('PacSun', 'Enter description here.', 2),
('PrettyLittleThing', 'Enter description here.', 2),
('Princess Polly', 'Enter description here.', 2),
('Ralph Lauren', 'Enter description here.', 4),
('Romwe', 'Enter description here.', 1),
('Shein', 'Enter description here.', 1),
('Steve Madden', 'Enter description here.', 2),
('Tilly’s', 'Enter description here.', 1),
('Tommy Hilfiger', 'Enter description here.', 3),
('Tripulse', 'Enter description here.', 5),
('Under Armour', 'Enter description here.', 2),
('Uniqlo', 'Enter description here.', 3),
('Urban Outfitters', 'Enter description here.', 2),
('Zara', 'Enter description here.', 2),
('Zumiez', 'Enter description here.', 2);
