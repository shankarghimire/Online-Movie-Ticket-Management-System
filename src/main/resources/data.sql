-- Configures Database Entities
/*Section to create tables related to security- User and Roles */
-- Table to store User credentials information
-- DROP TABLE  IF EXISTS user;
/*

INSERT INTO sec_user(user_name, encrypted_password, first_name, last_name,user_role,enabled)
VALUES('admin','$2a$10$xTQsUzaGgdw2AJHhnSt.OuBjmjzi9R8.FsOhdOxyOYHOKaFgYFSwe', 'Tom', 'Harry','ROLE_ADMIN', 1),
	('user','$2a$10$xTQsUzaGgdw2AJHhnSt.OuBjmjzi9R8.FsOhdOxyOYHOKaFgYFSwe', 'user', 'user','ROLE_USER', 1);


*/
-- Insert some sample data


/*
INSERT INTO sec_role(roleName)
VALUES('ROLE_ADMIN'),('ROLE_USER'),('ROLE_GUEST'),('ROLE_STUDENT');

INSERT INTO user_role(userId, roleId)
VALUES(1,1),(2,2),(3,3),(4,1),(4,2);


INSERT INTO movie(movieName, movieGenre, language, imagePath)
VALUES('Titanic','Love Story','English','/movie-posters/titanic.jpg'),
	('Avatar','Love Story','English','/movie-posters/avatar.jpg'),
	('Parba','Love Story','Nepali','/movie-posters/parba.jpg');

INSERT INTO theater (theaterName, address, city, state, phone)
VALUES('ABC CineComplex','123-Steels Ave.', 'Brampton', 'ON', '123-123-1234'),
('XYZ CineComplex','123-Gold Ave.', 'Toronto', 'ON', '321-321-4321');

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


INSERT INTO theater_seat (theaterId, seatNo)
VALUES (1,'A01'),(1,'A02'),(1,'A03'),(1,'A04'),(1,'A05'),(1,'A06'),(1,'A07'),(1,'A08'),(1,'A09'),(1,'A10'),
(1,'B01'),(1,'B02'),(1,'B03'),(1,'B04'),(1,'B05'),(1,'B06'),(1,'B07'),(1,'B08'),(1,'B09'),(1,'B10'),
(1,'C01'),(1,'C02'),(1,'C03'),(1,'C04'),(1,'C05'),(1,'C06'),(1,'C07'),(1,'C08'),(1,'C09'),(1,'C10'),
(1,'D01'),(1,'D02'),(1,'D03'),(1,'D04'),(1,'D05'),(1,'D06'),(1,'D07'),(1,'D08'),(1,'D09'),(1,'D10'),
(1,'E01'),(1,'E02'),(1,'E03'),(1,'E04'),(1,'E05'),(1,'E06'),(1,'E07'),(1,'E08'),(1,'E09'),(1,'E10');

INSERT INTO ticketDetails (theaterid, seatNo, showtime, ticketPurchaseDate, movieShowtimeDate, purchasedBy, amount, description)
VALUES(1, 'A01','8:30','2020-12-05','2020-12-06',1,12.5,'Ticket with discount pass');
*/
