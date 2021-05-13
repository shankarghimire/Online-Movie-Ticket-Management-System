/*User table */
----------------------------------------
CREATE TABLE sec_user
( user_id BIGINT NOT NULL AUTO_INCREMENT,
user_name VARCHAR(50) NOT NULL,
encrypted_password VARCHAR(300),
first_name VARCHAR(50),
last_name VARCHAR(50),
user_role CHAR(20),
enabled TINYINT NOT NULL,
CONSTRAINT user_pk PRIMARY KEY (user_id),
CONSTRAINT user_uk UNIQUE (user_name)
);

INSERT INTO sec_user(user_name, encrypted_password, first_name, last_name,user_role,enabled)
VALUES('admin','$2a$10$xTQsUzaGgdw2AJHhnSt.OuBjmjzi9R8.FsOhdOxyOYHOKaFgYFSwe', 'Tom', 'Harry','ROLE_ADMIN', 1),
	('prof','$2a$10$xTQsUzaGgdw2AJHhnSt.OuBjmjzi9R8.FsOhdOxyOYHOKaFgYFSwe', 'Prof', 'Prof','ROLE_ADMIN', 1),
	('user','$2a$10$xTQsUzaGgdw2AJHhnSt.OuBjmjzi9R8.FsOhdOxyOYHOKaFgYFSwe', 'User', 'User','ROLE_USER', 1),
	('sheridan','$2a$10$xTQsUzaGgdw2AJHhnSt.OuBjmjzi9R8.FsOhdOxyOYHOKaFgYFSwe', 'Sheridan', 'Sheridan','ROLE_USER', 1);

---------------------------------------------------------------------------

/* Section to create tables related to Application data */
-- Create table Movie

CREATE TABLE tbl_movie
( Id INT NOT NULL AUTO_INCREMENT,
movie_name VARCHAR(100) NOT NULL,
movie_genre	CHAR(20),
language CHAR(30),
image_path CHAR(100),
show_time  VARCHAR(30),
CONSTRAINT movie_pk PRIMARY KEY (Id)
);


INSERT INTO tbl_movie(movie_name, movie_genre, language, image_path, show_time)
VALUES('Titanic','Love Story','English','titanic.jpg','8:30:00'),
	('Avatar','Love Story','English','avatar.jpg', '12:30:00'),
	('Parba','Love Story','Nepali','parba.jpg', '5:30:00');

----------------------------------------------------------------------------------

/*Theater Table */
-- Create table Theater
CREATE TABLE theater
(theaterId INT NOT NULL AUTO_INCREMENT,
theaterName VARCHAR(50) NOT NULL,
address CHAR(50),
city CHAR(50),
state CHAR(2),
phone CHAR(12),
CONSTRAINT theater_pk PRIMARY KEY (theaterId)
);

INSERT INTO theater (theaterName, address, city, state, phone)
VALUES('ABC CineComplex','123-Steels Ave.', 'Brampton', 'ON', '123-123-1234'),
('XYZ CineComplex','123-Gold Ave.', 'Toronto', 'ON', '321-321-4321');


----------------------------------------------------
-- Create table Seat
CREATE TABLE theater_seat
(id BIGINT NOT NULL AUTO_INCREMENT,
seat_no CHAR(5) NOT NULL,
CONSTRAINT theater_seat_pk PRIMARY KEY (ID),
CONSTRAINT theater_seat_uk UNIQUE (seat_no)
);

INSERT INTO theater_seat (seat_no)
VALUES 
('A01'),('A02'),('A03'),('A04'),('A05'),('A06'),('A07'),('A08'),('A09'),('A10'),
('B01'),('B02'),('B03'),('B04'),('B05'),('B06'),('B07'),('B08'),('B09'),('B10'),
('C01'),('C02'),('C03'),('C04'),('C05'),('C06'),('C07'),('C08'),('C09'),('C10'),
('D01'),('D02'),('D03'),('D04'),('D05'),('D06'),('D07'),('D08'),('D09'),('D10'),
('E01'),('E02'),('E03'),('E04'),('E05'),('E06'),('E07'),('E08'),('E09'),('E10');


----------------------------------------------
-- Create table Ticket
CREATE TABLE ticket
(id BIGINT NOT NULL AUTO_INCREMENT, 
movie_name VARCHAR(100),
theater_name VARCHAR(100),
seat_no	CHAR(5),
show_time CHAR(40),
show_date DATE,
ticket_category VARCHAR(50),
price decimal(5,2),
purchase_date DATE,
purchase_by CHAR(50),
CONSTRAINT ticket_pk PRIMARY KEY (id)
);

INSERT INTO ticket(movie_name, theater_name,seat_no, show_time, show_date, ticket_category, price, purchase_date, purchase_by)
VALUES
('Titanic', 'Sheridan Mulplex Theater', 'E09', '8:30', '2020-12-15', 'General Admission', 15.00, '2020-12-14', null),
('Avatar', 'Sheridan Mulplex Theater', 'E09', '12:30', '2020-12-15', 'General Admission', 15.00, '2020-12-14', null),
('Titanic', 'Sheridan Mulplex Theater', 'E10', '8:30', '2020-12-15', 'General Admission', 15.00, '2020-12-14', null),
('Avatar', 'Sheridan Mulplex Theater', 'E10', '12:30', '2020-12-15', 'General Admission', 15.00, '2020-12-14', null),
('Titanic', 'Sheridan Mulplex Theater', 'E01', '12:30', '2020-12-15', 'General Admission', 15.00, '2020-12-14', null);



/*Tables are no longer used in this project, but will be used later/*
/*
INSERT INTO theater_showtime (theaterId, showtime, price)
VALUES(1,'8:30',10.00),
(1,'11:30',15.00),
(1,'14:30',15.00),
(1,'18:30',15.00),
(1,'22:00',15.00),
(2,'11:30',12.00),
(2,'14:30',22.00),
(2,'18:30',22.00),
(2,'22:00',22.00);



INSERT INTO ticketDetails (theaterid, seatNo, showtime, ticketPurchaseDate, movieShowtimeDate, purchasedBy, amount, description)
VALUES(1, 'A01','8:30','2020-12-05','2020-12-06',1,12.5,'Ticket with discount pass');
*/
---------------------------------
/*
-- Table to store Role information
CREATE TABLE sec_role
(roleId BIGINT NOT NULL AUTO_INCREMENT,
roleName CHAR(20) NOT NULL,
CONSTRAINT role_pk PRIMARY KEY (roleId),
CONSTRAINT role_uk UNIQUE (roleName)
);


-- Table (Associated table) to hold maintain M:N relationship between 'User' and 'Role' tables
CREATE TABLE user_role
(sn BIGINT NOT NULL AUTO_INCREMENT,
userId BIGINT NOT NULL,
roleId BIGINT NOT NULL,
CONSTRAINT user_role_pk PRIMARY KEY (sn),
CONSTRAINT user_role_fk1 FOREIGN KEY (userId) REFERENCES sec_user(userId),
CONSTRAINT user_role_fk2 FOREIGN KEY (roleId) REFERENCES sec_role(roleId)
);
	
*/

-------------------------------------------
/*
-- Create table Theater_ShowTime_rate
CREATE TABLE theater_showtime
(theaterId INT NOT NULL,
showtime TIME NOT NULL,
price DECIMAL(5,2) NOT NULL,
CONSTRAINT theater_showtime_pk PRIMARY KEY (theaterId, showtime),
CONSTRAINT theater_showtime_fk FOREIGN KEY (theaterId) REFERENCES theater(theaterId)
);*/	