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

INSERT INTO brands(id, logo, name)
VALUES (1, 'https://www.thebrandingjournal.com/wp-content/uploads/2019/05/chanel_logo_the_branding_journal.jpg',
        'CHANEL'),
       (2, 'https://logos-world.net/wp-content/uploads/2020/05/Zara-Logo.png', 'ZARA'),
       (3, 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/H%26M-Logo.svg/2560px-H%26M-Logo.svg.png',
        'H & M'),
       (4, 'https://i.pinimg.com/originals/f6/fd/e9/f6fde98cfc481b755c77d129d105a885.jpg', 'POLO'),
       (5, 'https://cdn.shopify.com/s/files/1/0388/3771/4989/files/DIOR_LOGO.png?v=1653483781', 'DIOR');

INSERT INTO buyers(id, date_of_birth, message_id, order_id, user_id, address, gender)
VALUES (1, '2003-03-18', 1, 1, 1, 'Область Чуй,город Бишкек,мкр Кок Жар,улица Молдокулова 10,подъезд 3, кв 10',
        'FEMALE'),
       (2, '1999-09-09', 1, 2, 2, 'Область Ош,город Ноокат,улица Г.Айтиева 112,подъезд 5, кв 67', 'MALE'),
       (3, '2000-11-30', 3, 3, 3, 'Область Талас,город Талас,улица Чыныке бий 54,подъезд 11,кв 76 ', 'FEMALE'),
       (4, '1990-10-23', 4, 4, 4, 'Область Баткен,город Кадамжай,улица Ауэзова 200,подъезд 7,кв 46 ', 'MALE'),
       (5, '2002-12-12', 5, 5, 5, 'Область Чуй,город Бишкек,улица Садырбаева 1/5,подъезд 1, кв 5', 'MALE');


INSERT INTO categories(id, name)
VALUES (1,
        'Мужской'),
       (2,
        'Женский'),
       (3,
        'Детский');

INSERT INTO sub_categories(id, category_id, name)
VALUES (1, 1, 'Вся одежда'),
       (2, 1, 'Новинки'),
       (3, 1, 'Брюки и шорты'),
       (4, 1, 'Верхняя одежда'),
       (5, 1, 'Футболки и майки'),
       (6, 1, 'Одежда для дома'),
       (7, 1, 'Свитшоты и Худи'),
       (8, 1, 'Нижнее белье'),
       (9, 1, 'Костюмы и рубашки'),
       (10, 1, 'Джемперы и кардиганы'),
       (11, 1, 'Обувь'),
       (12, 1, 'Головные уборы'),
       (13, 1, 'Спортивная одежда'),
       (14, 1, 'Бестселлеры'),
       (15, 1, 'Распродажа'),
       (16, 1, 'Сумки'),
       (17, 2, 'Свитера и кофты'),
       (18, 2, 'Джемперы и кардиганы'),
       (19, 2, 'Платья'),
       (20, 2, 'Белье'),
       (21, 2, 'Блузки и рубашки'),
       (22, 2, 'Футболки и майки'),
       (23, 2, 'Верхняя одежда'),
       (24, 2, 'Одежда для дома'),
       (25, 2, 'Вся одежда'),
       (26, 2, 'Сумки'),
       (27, 2, 'Обувь'),
       (28, 2, 'Головные уборы и аксессуары'),
       (29, 2, 'Новинки'),
       (30, 2, 'Брюки и шорты'),
       (31, 2, 'Бестселлеры'),
       (32, 2, 'Распродажа'),
       (33, 2, 'Юбки'),
       (34, 3, 'Одежда для новорожденных'),
       (35, 3, 'Одежда для девочек'),
       (36, 3, 'Одежда для мальчиков');

INSERT INTO chats(id, time, seller_id)
VALUES (1, '2023-07-13 11:48:20'),
       (2, '2023-05-13 23:20:12'),
       (3, '2022-04-13 09:30:45'),
       (4, '2023-01-13 12:48:30'),
       (5, '2023-06-13 03:12:09');

INSERT INTO discounts(id, date_of_finish, date_of_start, percent)
VALUES (1, '2023-07-01', '2023-07-12', 70),
       (2, '2023-06-23', '2023-09-29', 50),
       (3, '2023-07-02', '2023-07-25', 25),
       (4, '2023-06-01', '2023-08-17', 60),
       (5, '2023-05-23', '2023-11-12', 15);

INSERT INTO warehouses(id,name,location)
VALUES (1,'БайGo','Бишкек'),
       (2,'Асман','Талас'),
       (3,'Аю Гранд','Чуй'),
       (4,'Амазон','Ош'),
       (5,'Караван','Баткен');

INSERT INTO users (id,email,first_name,last_name,password,phone_number,role)
VALUES (id,)




































