use SAS;

truncate Categories;
insert into Categories values ();
insert into Categories(categoryName) values ("ทุนการศึกษา");
insert into Categories(categoryName) values ("หางาน");
insert into Categories(categoryName) values ("ฝึกงาน");

truncate Announcements;
insert into Announcements (announcementTitle,announcementDescription, categoryId, publishDate, closeDate, announcementDisplay) values 
('(PBI10) Title 0','(PBI10) Description 0',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 1','(PBI10) Description 1',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 2','(PBI10) Description 2',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 3','(PBI10) Description 3',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 4','(PBI10) Description 4',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 5','(PBI10) Description 5',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 6','(PBI10) Description 6',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 7','(PBI10) Description 7',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 8','(PBI10) Description 8',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 9','(PBI10) Description 9',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 10','(PBI10) Description 10',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 11','(PBI10) Description 11',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 12','(PBI10) Description 12',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 13','(PBI10) Description 13',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 14','(PBI10) Description 14',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 16','(PBI10) Description 16',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 17','(PBI10) Description 17',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 18','(PBI10) Description 18',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 19','(PBI10) Description 19',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 20','(PBI10) Description 20',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 21','(PBI10) Description 21',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 22','(PBI10) Description 22',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 23','(PBI10) Description 23',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 24','(PBI10) Description 24',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 25','(PBI10) Description 25',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 26','(PBI10) Description 26',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 27','(PBI10) Description 27',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 28','(PBI10) Description 28',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 29','(PBI10) Description 29',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 30','(PBI10) Description 30',1,null,'2023-12-31 18:00:00+07:00','Y'),
('(PBI10) Title 31','(PBI10) Description 31',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 32','(PBI10) Description 32',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 33','(PBI10) Description 33',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 34','(PBI10) Description 34',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 35','(PBI10) Description 35',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 36','(PBI10) Description 36',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 37','(PBI10) Description 37',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 38','(PBI10) Description 38',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 39','(PBI10) Description 39',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 40','(PBI10) Description 40',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 41','(PBI10) Description 41',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 42','(PBI10) Description 42',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 43','(PBI10) Description 43',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 44','(PBI10) Description 44',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 45','(PBI10) Description 45',2,'2023-05-01 06:00:00+07:00',null,'Y'),
('(PBI10) Title 46','(PBI10) Description 46',2,null,null,'Y'),
('(PBI10) Title 47','(PBI10) Description 47',2,null,null,'Y'),
('(PBI10) Title 48','(PBI10) Description 48',2,null,null,'Y'),
('(PBI10) Title 49','(PBI10) Description 49',2,null,null,'Y'),
('(PBI10) Title 50','(PBI10) Description 50',2,null,null,'Y'),
('(PBI10) Title 51','(PBI10) Description 51',2,null,null,'Y'),
('(PBI10) Title 52','(PBI10) Description 52',2,null,null,'Y'),
('(PBI10) Title 53','(PBI10) Description 53',2,null,null,'Y'),
('(PBI10) Title 54','(PBI10) Description 54',2,null,null,'Y'),
('(PBI10) Title 55','(PBI10) Description 55',2,null,null,'Y'),
('(PBI10) Title 56','(PBI10) Description 56',2,null,null,'Y'),
('(PBI10) Title 57','(PBI10) Description 57',2,null,null,'Y'),
('(PBI10) Title 58','(PBI10) Description 58',2,null,null,'Y'),
('(PBI10) Title 59','(PBI10) Description 59',2,null,null,'Y'),
('(PBI10) Title 60','(PBI10) Description 60',2,null,null,'Y'),
('(PBI10) Title 61','(PBI10) Description 61',3,null,'2023-05-01 18:00:00+07:00','Y'),
('(PBI10) Title 62','(PBI10) Description 62',3,'2023-05-01 06:00:00+07:00','2023-05-01 18:00:00+07:00','Y'),
('(PBI10) Title 63','(PBI10) Description 63',3,null,'2023-05-01 18:00:00+07:00','N');

truncate Users;
insert into Users(username,name,password,email,role,createdOn,updatedOn) values("sanit","Sanit Sirisawatvatana","$argon2id$v=19$m=4096,t=3,p=1$fabZIRZZrUuxRrnXoGkMjA$DZ90F9+3/rnHGKY/YmyQoZwOToS4mJbuLYZkpXQt2VY","sanit.sir@kmutt.ac.th","admin","2023-08-15 08:00:00+07:00","2023-08-15 08:00:00+07:00");
insert into Users(username,name,password,email,role,createdOn,updatedOn) values("pornthip","Pornthip Sirijutikul","$argon2id$v=19$m=4096,t=3,p=1$mZdjYXK6+s34Zd6PeVQG2Q$S9TGRhlIw2l7kIFYyPLJk6T5TDNHYa90Z/4gHubUNE8","pornthip.sri@kmutt.ac.th","announcer","2023-08-15 09:30:00+07:00","2023-08-15 09:30:00+07:00");
insert into Users(username,name,password,email,role,createdOn,updatedOn) values("jaruwan_w","Jaruwan Maneesart","$argon2id$v=19$m=4096,t=3,p=1$pTop1432FBQ8IlbizoaWsQ$6tG5y9QZ5uOz7qQe41vSwdr6wwnmLU9RFWrORpRaLdo","jaruwan.wee@kmutt.ac.th","announcer","2023-08-16 08:00:00+07:00","2023-08-16 08:00:00+07:00");
insert into Users(username,name,password,email,role,createdOn,updatedOn) values("vichchuda","Vichchuda Tedoloh","$argon2id$v=19$m=4096,t=3,p=1$XV/IqnCROw+/qCsQrD918g$bkcBkt4zmphxiVdYN0f2IvH8YW10jaHG9Sp7jmijycU","vichchuda.ted@kmutt.ac.th","announcer","2023-08-16 09:30:00+07:00","2023-08-16 09:30:00+07:00");
