use SAS;

insert into Categories values ();
insert into Categories(categoryName) values ("ทุนการศึกษา");
insert into Categories(categoryName) values ("หางาน");
insert into Categories(categoryName) values ("ฝึกงาน");

insert into Announcements(announcementTitle,announcementDescription,announcementDisplay,categoryId, viewCount) 
values ('บริษัท เน็ตเซอร์พลัส จำกัด รับสมัครงาน 2 ตำแหน่ง','บริษัท เน็ตเซอร์พลัส จำกัด เปิดรับสมัครงาน 2 ตำแหน่ง Application Support และ Customer Support','N',3,0);

insert into Announcements(announcementTitle,announcementDescription,closeDate,announcementDisplay,categoryId, viewCount) 
values ('รายชื่อนักศึกษาที่ได้รับทุนการศึกษาประเภท "ทุนจ้างงาน" 2/2565','คณะ ฯ ประกาศรายชื่อนักศึกษาที่ได้รับทุนการศึกษาประเภท "ทุนจ้างงาน" ประจําภาคการศึกษา 2/2565','2023-05-31 11:00:00','Y',2,0);

insert into Announcements(announcementTitle,announcementDescription,publishDate,announcementDisplay,categoryId, viewCount) 
values ('แนวปฎิบัติการสอบออนไลน์ พ.ศ. 2565','ประกาศมหาวิทยาลัยเทคโนโลยีพระจอมเกล้าธนบุรี เรื่องแนวทางปฎิบัติการสอบออนไลน์ พ.ศ. 2565', '2023-01-26 23:00:00','Y',1,0);

insert into Announcements(announcementTitle,announcementDescription,publishDate,closeDate,announcementDisplay,categoryId, viewCount) 
values ('กิจกรรมพี่อ้อย พี่ฉอด On Tour 2566','ขอเชิญนักศึกษาทุกชั้นปี เข้าร่วมกิจกรรมพี่อ้อย พี่ฉอด On Tour','2023-04-18 23:00:00','2023-05-08 11:00:00','Y',1,0);

TRUNCATE TABLE Announcements;
TRUNCATE TABLE Categories;

insert into Announcements (announcementTitle, announcementDescription,categoryId,publishDate,closeDate, announcementDisplay)
	values('(PBI8-9) Title 1','(PBI8-9) Descripition 1',1,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
		  ('(PBI8-9) Title 2','(PBI8-9) Descripition 2',1,'2023-06-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','Y'),
		  ('(PBI8-9) Title 3','(PBI8-9) Descripition 3',1,null,'2023-12-31 18:00:00+07:00','Y'),
		  ('(PBI8-9) Title 4','(PBI8-9) Descripition 4',1,null,'2023-05-01 18:00:00+07:00','Y'),
		  ('(PBI8-9) Title 5','(PBI8-9) Descripition 5',2,'2023-05-01 06:00:00+07:00','2023-05-02 18:00:00+07:00','Y'),
		  ('(PBI8-9) Title 6','(PBI8-9) Descripition 6',2,'2023-05-01 06:00:00+07:00',null,'Y'),
		  ('(PBI8-9) Title 7','(PBI8-9) Descripition 7',2,'2023-06-01 06:00:00+07:00',null,'Y'),
		  ('(PBI8-9) Title 8','(PBI8-9) Descripition 8',2,null,null,'Y'),
		  ('(PBI8-9) Title 9','(PBI8-9) Descripition 9',2,'2023-05-01 06:00:00+07:00','2023-12-31 18:00:00+07:00','N') ;