INSERT INTO appeals(id, title, divide, detailed_appeal, buyer_id)
VALUES (1, 'Жалоба', 'Жалоба', 'Жалоба на качество услуг', 1),
       (2, 'Запрос', 'Запрос', 'Запрос на получение информации', 2);


INSERT INTO banners(id, image)
VALUES (1,
        'https://img.freepik.com/free-vector/fashion-banner-design-with-shirt-bag-camera-case_83728-1865.jpg?w=2000'),
       (2,
        'https://img.freepik.com/premium-vector/spring-fashion-sale-banner-design-template_2239-1180.jpg'),
       (3,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzoYY_fWeMf-11d6tXYNsqVRS2n-ajMGU4ZyRZmpMnt1hCadt1k_HgJeyZjAHp04KiwWs&usqp=CAU'),
       (4,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQU-YD9rtlaeNSMxFvT46Rqjcz1a3XlI2UcOjVF9vixjY0BtjqrLs5x2FaXJi5nsiQkvpE&usqp=CAU'),
       (5,
        'https://marketplace.canva.com/EAFVHstxnwk/1/0/1600w/canva-beige-aesthetic-exclusive-fashion-wear-collection-clothing-banner-BZb4KkCdNP0.jpg');

INSERT INTO brands(id,logo,name)
VALUES (1,'https://www.thebrandingjournal.com/wp-content/uploads/2019/05/chanel_logo_the_branding_journal.jpg','CHANEL'),
       (2,'https://logos-world.net/wp-content/uploads/2020/05/Zara-Logo.png','ZARA'),
       (3,'https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/H%26M-Logo.svg/2560px-H%26M-Logo.svg.png','H & M'),
       (4,'https://i.pinimg.com/originals/f6/fd/e9/f6fde98cfc481b755c77d129d105a885.jpg','POLO'),
       (5,'https://cdn.shopify.com/s/files/1/0388/3771/4989/files/DIOR_LOGO.png?v=1653483781','DIOR');

INSERT INTO buyers(id,date_of_birth,message_id,order_id,user_id,address,gender)
VALUES (1,'2003-03-18',1,1,1,'Область Чуй,город Бишкек,мкр Кок Жар,улица Молдокулова 10,подъезд 3, кв 10','FEMALE'),
       (2,'1999-09-09',1,2,2,'Область Ош,город Ноокат,улица Г.Айтиева 112,подъезд 5, кв 67','MALE'),
       (3,'2000-11-30',3,3,3,'Область Талас,город Талас,улица Чыныке бий 54,подъезд 11,кв 76 ','FEMALE'),
       (4,'1990-10-23',4,4,4,'Область Баткен,город Кадамжай,улица Ауэзова 200,подъезд 7,кв 46 ','MALE'),
       (5,'2002-12-12',5,5,5,'Область Чуй,город Бишкек,улица Садырбаева 1/5,подъезд 1, кв 5' ,'MALE');


INSERT INTO categories(id, name)
VALUES (1,'Мужской'),
       (2,'Женский'),
       (3,'Детский');
INSERT INTO




