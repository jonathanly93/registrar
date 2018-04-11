DROP DATABASE IF EXISTS registrar;
CREATE DATABASE registrar;
USE registrar;

SELECT 'CREATING DATABASE STRUCTURE' as 'INFO';

DROP TABLE IF EXISTS students,
                     courses,
		     studentcourses,
		     restrictions;

/*!50503 set default_storage_engine = InnoDB */;
/*!50503 select CONCAT('storage engine: ', @@default_storage_engine) as INFO */;

CREATE TABLE students (
    user_id		INT		NOT NULL,
    user_name		VARCHAR(30)	NOT NULL,    
    user_email		VARCHAR(30)	NOT NULL,
    user_password	VARCHAR(30)	NOT NULL
);

CREATE TABLE courses (
    course_id		INT		NOT NULL,
    course_name		VARCHAR(30)	NOT NULL,
    department		VARCHAR(30)	NOT NULL,
    credit		INT		NOT NULL
);

CREATE TABLE studentcourses (
    course_id		INT		NOT NULL,
    course_name		VARCHAR(30)	NOT NULL,
    department		VARCHAR(30)	NOT NULL,
    credit		INT		NOT NULL,
    grade		VARCHAR(1)	,
    activity		INT		NOT NULL,
    quarter		VARCHAR(30)	NOT NULL,
    user_id		INT		NOT NULL
);

CREATE TABLE restrictions (
    restriction		VARCHAR(30)	NOT NULL,
    description		VARCHAR(30)	NOT NULL,
    user_id		INT		NOT NULL
);

flush /*!50503 binary */ logs;

INSERT INTO `students` VALUES 
(1, 'Jon', 'jon@uchicago.edu', '12345'),
(2, 'Jeff', 'jeff@uchicago.edu', '12345'),
(3, 'Sam', 'sam@uchicago.edu', '12345');

INSERT INTO `restrictions` VALUES 
('Unpaid Bills', 'Unpaid $500', 1),
('Health Insurance', 'Complete Imunization Record', 1),
('Unpaid Bills', 'Unpiad $300', 3);

INSERT INTO `studentcourses` VALUES 
(1, 'Java', 'Computer Science', 3, 'A', 0, 'Spring 2015', 1),
(2, 'Python', 'Computer Science', 3, 'B', 0, 'Summer 2015', 1),
(3, 'Calculus', 'Mathematics', 5, 'A', 0,'Spring 2015', 1),
(3, 'Calculus', 'Mathematics', 5, 'A', 0,'Spring 2015', 2),
(3, 'Calculus', 'Mathematics', 5, ' ', 1,'Winter 2018', 3);
