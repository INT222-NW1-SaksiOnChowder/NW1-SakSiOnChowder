use SAS;

insert into Categories values ();
insert into Categories(categoryName) values ("ทุนการศึกษา");
insert into Categories(categoryName) values ("หางาน");
insert into Categories(categoryName) values ("ฝึกงาน");

insert into Announcements(announcementTitle,announcementDescription,announcementDisplay,categoryId) 
values ('บริษัท เน็ตเซอร์พลัส จำกัด รับสมัครงาน 2 ตำแหน่ง','บริษัท เน็ตเซอร์พลัส จำกัด เปิดรับสมัครงาน 2 ตำแหน่ง Application Support และ Customer Support','N',3);

insert into Announcements(announcementTitle,announcementDescription,closeDate,announcementDisplay,categoryId) 
values ('รายชื่อนักศึกษาที่ได้รับทุนการศึกษาประเภท "ทุนจ้างงาน" 2/2565','คณะ ฯ ประกาศรายชื่อนักศึกษาที่ได้รับทุนการศึกษาประเภท "ทุนจ้างงาน" ประจําภาคการศึกษา 2/2565','2023-05-31 11:00:00','Y',2);

insert into Announcements(announcementTitle,announcementDescription,publishDate,announcementDisplay,categoryId) 
values ('แนวปฎิบัติการสอบออนไลน์ พ.ศ. 2565','ประกาศมหาวิทยาลัยเทคโนโลยีพระจอมเกล้าธนบุรี เรื่องแนวทางปฎิบัติการสอบออนไลน์ พ.ศ. 2565', '2023-01-26 23:00:00','Y',1);

insert into Announcements(announcementTitle,announcementDescription,publishDate,closeDate,announcementDisplay,categoryId) 
values ('กิจกรรมพี่อ้อย พี่ฉอด On Tour 2566','ขอเชิญนักศึกษาทุกชั้นปี เข้าร่วมกิจกรรมพี่อ้อย พี่ฉอด On Tour','2023-04-18 23:00:00','2023-05-08 11:00:00','Y',1);

