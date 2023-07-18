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

INSERT INTO users (id, email, first_name, last_name, password, phone_number, role)
VALUES (1, 'buyer@gmail.com',
        'Kanykei', 'Askarbekova',
        '$2a$12$XrJw9vXMuR0ati9/e4cq1e/0DRFK31.14bfsryyP3O/AydOZ2/hcG',
        '+996702666357', 'BUYER'),
--     пароль:buyer123

       (2, 'bb@gmail.com',
        'Saltanat', 'Nematilla kyzy',
        '$2a$12$gk0UcB15ppKCm8PImjZZ1.haHAYhX8kB5vIRoyiCBXfPzLv/pcqty',
        '+996702666357', 'BUYER'),
       --     пароль:buyer
       (3, 'admin@gmail.com',
        'Nuriza', 'Muratova',
        '$2a$12$xpnEsZCno3hC/flsQ8b3AOhyooQ7PHVtrpN9YWob5tpy47jNhS5uS',
        '+996990128880', 'ADMIN'),
--  пароль:admin123
       (4, 'seller@gmail.com',
        'Jiydegul', 'Jalilova',
        '$2a$12$ggPOFn4K3GLQPQrPU8dxNuRUvv/BXplU/oHUCQVT3DL63h0W9ESgS',
        '+996550232345', 'SELLER'),
-- пароль:seller123
       (5, 'ss@gmail.com',
        'Aiperi ', 'Toktosunova',
        '$2a$12$WzVj.06aj5xl62rdaFH1QOVHl7Zghlg3nEgrhtsWZkIlaMYnja5TO',
        '+996550232345', 'SELLER'),
-- пароль:seller

       (6, 'eliza@gmail.com',
        'Eliza', 'Ashyralieva',
        '$2a$12$xVYGJbzL.R9FvfgiEamtp.iyArd7jNVnAu7FekPw.0tITTlqCP12u',
        '+996550232345', 'SELLER');
-- пароль:se123

INSERT INTO buyers(id, date_of_birth, user_id, address, gender)
VALUES (1,
        '2003-03-18', 1,
        'Область Чуй,город Бишкек,мкр Кок Жар,улица Молдокулова 10,подъезд 3, кв 10', 'FEMALE'),
       (2,
        '1990-10-23', 2,
        'Область Баткен,город Кадамжай,улица Ауэзова 200,подъезд 7,кв 46 ', 'MALE');

INSERT INTO sellers(id, bic, itn, about_store, address, name_of_store, photo, store_logo, vendor_number, user_id)
VALUES (1, 'DEUTDEFF', '765-43-2109', 'Fashion Haven - ваш источник стильной и модной одежды', 'ТЦ ГУМ, город Бишкек', 'Fashion Haven', 'https://modnaya-krasivaya.ru/moda/2019/02/moda-vesna-leto-59.jpg', 'https://s.tmimgcdn.com/scr/800x500/183700/modern-shopping-business-logo-template_183766-original.jpg', 'VND12345', 4),
    (2, 'UBSWCHZH80A', '987-65-4321', 'Trendy Threads - магазин для тех, кто следит за модой', 'ТЦ ЦУМ, город Ош', 'Trendy Threads', 'https://modnaya-krasivaya.ru/moda/2019/02/moda-vesna-leto-59.jpg', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwhRFf7FljIdmi9O3IJF7w438B_ljoSVtmyA&usqp=CAU', 'SPLR789', 5),
    (3, 'KASITHBK', '444-44-4444', 'Classic Elegance - магазин, где классика встречает элегантность', 'ТЦ Asia Mall, город Бишкек', 'Classic Elegance', 'https://modnaya-krasivaya.ru/moda/2019/02/moda-vesna-leto-59.jpg', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSALIXFTV7zON37JghAVcdd6f_a1b-o9atknA&usqp=CAU', 'PVDR7890', 6);

INSERT INTO customers(id, first_name, last_name,
                      address, city, country, email,
                      phone_number, postal_code)
VALUES (1, 'Курстан', 'Эркинбаев',
        'Ош', 'Ош', 'Кыргызстан',
        'kurstan@gmail.com',
        '+996555990123', '723000'),
       (2, 'Нурадил', 'Жолдошов',
        'Талас', 'Талас', 'Кыргызстан',
        'nuradil@gmail.com',
        '+996703425039', '723000'),
       (3, 'Сыймык', 'Жумабек уулу',
        'Кара Суу', 'Ош', 'Кыргызстан',
        'syimyk@gmail.com',
        '+996990124556', '723040'),
       (4, 'Алтынбек', 'Шакиров',
        'Токтогул', 'Жалал Абад', 'Кыргызстан',
        'altynbek@gmail.com',
        '+996222345678', '724000'),
       (5, 'Адил', 'Айтбаев',
        'Бишкек', 'Бишкек', 'Кыргызстан',
        'adil@gmail.com',
        '+996990129090', '720000');

INSERT INTO orders(id, date_of_order, date_of_received, order_number, payment_type, status, total_price, with_delivery,
                   buyer_id, customer_id)
VALUES (1, '2022-12-30', '2023-01-23', '111',
        'ONLINE_BY_CARD', 'DELIVERED', 2000, TRUE, 1, 1),
       (2, '2023-12-21', '2023-10-05', '222',
        'OFFLINE_BY_CARD', 'DELIVERED', 4560, TRUE, 2, 2),
       (3, '2023-02-11', '2023-08-05', '333',
        'OFFLINE_BY_CASH', 'DELIVERED', 1999, FALSE, 1, 3),
       (4, '2023-06-15', '2023-04-12', '444',
        'OFFLINE_BY_CASH', 'DELIVERED', 870, FALSE, 2, 4),
       (5, '2023-02-11', '2023-08-05', '555',
        'ONLINE_BY_CARD', 'DELIVERED', 3690, TRUE, 2, 5);

INSERT INTO appeals(id, title, divide, detailed_appeal, buyer_id)
VALUES (1,
        'Жалоба', 'Жалоба', 'Жалоба на качество услуг', 1),
       (2,
        'Запрос', 'Запрос', 'Запрос на получение информации', 2);

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

INSERT INTO discounts(id, date_of_finish, date_of_start, percent)
VALUES (1,
        '2023-07-01', '2023-07-12', 70),
       (2,
        '2023-06-23', '2023-09-29', 50),
       (3,
        '2023-07-02', '2023-07-25', 25),
       (4,
        '2023-06-01', '2023-08-17', 60),
       (5,
        '2023-05-23', '2023-11-12', 15);

INSERT INTO products(id, articul, brand, composition,
                     date_of_change, date_of_create,
                     description, is_draft, manufacturer,
                     name, rating, season, style,
                     seller_id, sub_category_id)
VALUES (1, 'SKU12345', 'ZARA', 'Шерсть/Кашемир', '2023-03-03', '2023-01-23',
        'Приталенное платье с V-образным вырезом и длинными рукавами',
        FALSE, 'БайGo', 'Платье', 9.8, 'Летний', 'Вечерний', 2, 1),
       (2, 'CODE789', 'Calvin Klein', 'Хлопок/Полиэстер/Эластан',
        '2022-12-03', '2023-05-29',
        'Стильные шорты с высокой посадкой и широким поясом',
        TRUE, 'БайGo', 'Шорты', 9.8, 'Летний', 'Классический стиль', 1, 20),
       (3, 'ART123', 'Gucci', 'Полиэстер/Вискоза/Эластан', '2023-03-03', '2023-01-23',
        'Удобный свитшот с капюшоном и карманом-кенгуру',
        FALSE, 'БайGo', 'Свитшот', 9.8, 'Зимний', 'Спортивный', 3, 7),
       (4, 'T-9876', 'Adidas', 'Акрил/Нейлон', '2023-03-03', '2022-01-23',
        'Классическая рубашка с воротником и длинными рукавами',
        TRUE, 'БайGo', 'Рубашка', 9.8, 'Осенний', 'Бизнес-стиль', 2, 12),
       (5, '123ABC', 'H&M', '100% Хлопок', '2023-03-03', '2022-12-30',
        'Стильная футболка с круглым вырезом и короткими рукавами.',
        FALSE, 'БайGo', 'Футболка', 3.3, 'Летний', 'Уличный стиль', 1, 25);

INSERT INTO sub_products(id, color, color_hex_code, price, product_id, discount_id)
VALUES (1,
        'Черный', '#000000', 2000, 3, 1),
       (2,
        'Красный', '#FF0000', 5900, 1, 2),
       (3,
        'Фиолетовый', '#800080', 3999, 2, 3),
       (4,
        'Розовый', '#FFC0CB', 10500, 5, 4),
       (5,
        'Желтый', '#FFFF00', 6666, 4, 5);

INSERT INTO sub_product_images(sub_product_id, images)
VALUES (1,
        'https://nuself.ru/s3/public/sa/5y/sak5yjqiplta1qyxmmjhg4iysd5h6wf8piovhf5i.jpeg?width=850?width=224'),
       (2,
        'https://ladylike.ua/f/imagecache/product_list/womens-clothing/2023/plate_chernoe_v_rozovye_vetochki_0.jpg'),
       (3,
        'https://ae01.alicdn.com/kf/HTB1mlnnMNTpK1RjSZFMq6zG_VXaZ/Kiddie.jpg_220x220.jpg'),
       (4,
        'https://ladylike.ua/f/imagecache/product_list/img_1482.jpg'),
       (5,
        'https://ladylike.ua/f/imagecache/product_list/womens-clothing/2023/bryuki_letnie_bezhevye_pryamye_0.jpg');

INSERT INTO sizes(id, barcode, quantity, size, sub_product_id)
VALUES (1,
        59014, 1000, 'M', 1),
       (2,
        1234, 2340, 'S', 2),
       (3,
        1233, 999, 'L', 3),
       (4,
        1223, 7000, 'XL', 4),
       (5,
        3894, 200, 'XS', 5);

INSERT INTO buyers_baskets(buyer_id, sub_products_size_id)
VALUES (1, 2),
       (2, 3),
       (1, 1),
       (2, 1),
       (1, 4);

INSERT INTO buyers_favorites(buyer_id, sub_products_id)
VALUES (1, 1),
       (2, 2),
       (1, 3),
       (2, 3),
       (1, 1);

INSERT INTO buyers_last_views(buyer_id, sub_products_id)
VALUES (1, 1),
       (2, 2),
       (1, 3),
       (2, 4),
       (2, 5);


INSERT INTO warehouses(id, name, location)
VALUES (1, 'БайGo', 'Бишкек'),
       (2, 'Асман', 'Талас'),
       (3, 'Аю Гранд', 'Чуй'),
       (4, 'Амазон', 'Ош'),
       (5, 'Караван', 'Баткен');


INSERT INTO mailing_list_subscribers (id, email, is_sale, is_discount, is_new)
VALUES (1,
        'saltanat@gmail.com', TRUE, FALSE, TRUE),
       (2,
        'eliza@gmail.com', FALSE, FALSE, TRUE),
       (3,
        'user@gmail.com', TRUE, TRUE, TRUE),
       (4,
        'nuriza@gmail.com', FALSE, FALSE, FALSE),
       (5,
        'kanykei@gmail.com', TRUE, FALSE, FALSE);

INSERT INTO mailing_lists (id, name, image, description, date_of_start, date_of_finish)
VALUES (1,
        'Скидка', 'https://www.unisender.com/wp-content/uploads/2021/04/sms-rassylka.png',
        'Сезонные скидки на лучшие товары', '2023-06-29', '2023-07-29'),
       (2,
        'Распродажа', 'https://53news.ru/wp-content/uploads/2022/05/emailmarketing-min-1024x593-1.jpg',
        'Большая распродажа: скидки до 50%', '2023-05-12', '2023-07-12'),
       (3,
        'Новые товары', 'https://cdn.esputnik.com/photos/shares/EmailExamples/8%20march%20RU%20(8).jpg',
        'Трендовые новинки: узнайте о наших последних товарах', '2023-06-29', '2023-07-30');

INSERT INTO orders_sub_products_size(order_id, product_count, sub_products_size_id)
VALUES (1, 100, 2),
       (2, 240, 1),
       (3, 699, 3),
       (4, 349, 2),
       (5, 4593, 1);

INSERT INTO reviews(id, amount_of_like, answer, date_and_time, grade, text, buyer_id, product_id)
VALUES (1, 50, 'В наличии есть размер М?', '2023-07-13 12:00:00', 5,
        'Красивое платье доступно к продаже, доставка действует по всему Кыргызстану',
        1, 1),
       (2, 120, 'В наличии есть размер М?', '2023-07-13 12:00:00', 10,
        'Базовые майки любого сезона',
        2, 2),
       (3, 1000, 'Можно цену?', '2023-07-13 12:00:00', 4,
        'Образ в наличии',
        1, 4);

INSERT INTO review_images(review_id, images)
VALUES (1, 'https://ya-modnaya.ru/_pu/2/42477906.jpg'),
       (2, 'https://ya-modnaya.ru/_pu/2/42477906.jpg'),
       (3, 'https://ya-modnaya.ru/_pu/2/42477906.jpg');

INSERT INTO supplies(id, accepted_products, actual_date,
                     commission, created_at, planned_date,
                     quantity_of_products, status, supply_cost,
                     supply_number, delivery_type, seller_id, warehouse_id)
VALUES (1, 200, '2023-07-07', 300, '2023-07-13', '2023-05-28', 10000,
        'DELIVERED', 100, 'SUP2021001', 'КОРАБА', 1, 1),
       (2, 450, '2023-01-25', 300, '2023-07-13', '2023-05-28', 60400,
        'DELIVERED', 500, 'INV-2021-005', 'МОНОПАЛЛЕТЫ', 2, 2),
       (3, 100, '2022-07-12', 270, '2023-01-22', '2023-05-28', 23500,
        'DELIVERED', 1000, 'PO-202108-001', 'СУПЕРСЕЙФ', 3, 3),
       (4, 699, '2023-07-07', 690, '2023-07-13', '2023-04-28', 46700,
        'DELIVERED', 200, 'SUP2021001', 'КОРОБА', 1, 4),
       (5, 580, '2023-07-07', 1000, '2023-05-13', '2023-05-21', 2000,
        'DELIVERED', 740, 'INV-2021-005', 'КОРОБА', 3, 5);

INSERT INTO supply_products(id, quantity, size_id, supply_id)
VALUES (1, 100, 1, 1),
       (2, 490, 2, 2),
       (3, 999, 3, 3),
       (4, 1209, 4, 4),
       (5, 16830, 5, 5);

