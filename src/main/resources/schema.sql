DROP TABLE IF EXISTS student;

CREATE TABLE student (
  studentID INT AUTO_INCREMENT  PRIMARY KEY,
  first_Name VARCHAR(250) NOT NULL,
  last_Name VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS academy_Class;

CREATE TABLE academy_Class (
  code INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS assignment;

CREATE TABLE assignment (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  studentID INT NOT NULL,
  code INT NOT NULL
);