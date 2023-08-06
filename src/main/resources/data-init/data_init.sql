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

INSERT INTO users (id, email, password, phone_number, role)
VALUES
--     пароль:Buyer123
(1, 'buyer@gmail.com',
 '$2a$12$oU/NczE1jY6mFXp5hAvlCutEGhtEMs6EP3G5m/l9vpFc8TlTam3DS',
 '+996702666357', 'BUYER'),

--     пароль:Buyer00
(2, 'bb@gmail.com',
 '$2a$12$OvPx7qpK9cO9JE6oTPfWBeQH/6WKZZub4oP76S7aX./CgSgpGUasK',
 '+996702666357', 'BUYER'),

--  пароль:Admin123
(3, 'admin@gmail.com',
 '$2a$12$L67vHDQc6nq8XP.HfuIbBeK6f29ah2PpiEBSyjjBXifW4dFP8kDye',
 '+996990128880', 'ADMIN'),

-- пароль:Seller123
(4, 'seller@gmail.com',
 '$2a$12$Q77myfBp/yyrW143tis01eZrSYL3CKhN9JxMBllslZNm56gyO14/i',
 '+996550232345', 'SELLER'),

-- пароль:Seller00
(5, 'ss@gmail.com',
 '$2a$12$NLvlWRZ0v4utE/cX2bbKtevG8T8sSHGubfQ2wrMABEeY1UNl0ngbW',
 '+996550232345', 'SELLER'),

-- пароль:Seller88
(6, 'eliza@gmail.com',
 '$2a$12$IZQGU1pFgWxX5M1LHJVrI.0FeYWfw3j.DxWRjVjARLRViDFObuHF.',
 '+996550232345', 'SELLER');

INSERT INTO buyers(id, full_name, date_of_birth, user_id, address, gender)
VALUES (1, 'Kanykei Askarbekova',
        '2003-03-18', 1,
        'Область Чуй,город Бишкек,мкр Кок Жар,улица Молдокулова 10,подъезд 3, кв 10', 'FEMALE'),
       (2, 'Saltanat Nematilla kyzy',
        '1990-10-23', 2,
        'Область Баткен,город Кадамжай,улица Ауэзова 200,подъезд 7,кв 46 ', 'MALE');

INSERT INTO sellers(id, first_name, last_name, bic, itn, about_store, address,
                    name_of_store, store_logo,
                    vendor_number, user_id)
VALUES (1, 'Jiydegul', 'Jalilova', 'DEUTDEFF', '765-43-2109', 'Fashion Haven - ваш источник стильной и модной одежды',
        'ТЦ ГУМ, город Бишкек',
        'Fashion Haven',
        'https://s.tmimgcdn.com/scr/800x500/183700/modern-shopping-business-logo-template_183766-original.jpg',
        'VND12345', 4),
       (2, 'Aiperi', 'Toktosunova', 'UBSWCHZH80A', '987-65-4321',
        'Trendy Threads - магазин для тех, кто следит за модой',
        'ТЦ ЦУМ, город Ош',
        'Trendy Threads',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwhRFf7FljIdmi9O3IJF7w438B_ljoSVtmyA&usqp=CAU',
        'SPLR789', 5),
       (3, 'Eliza', 'Ashyralieva', 'KASITHBK', '444-44-4444',
        'Classic Elegance - магазин, где классика встречает элегантность',
        'ТЦ Asia Mall, город Бишкек', 'Classic Elegance',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSALIXFTV7zON37JghAVcdd6f_a1b-o9atknA&usqp=CAU',
        'PVDR7890', 6);

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
        '+996990129090', '720000'),
       (6, 'Илим', 'Шабданов',
        'Бишкек', 'Бишкек', 'Кыргызстан',
        'ilim@gmail.com',
        '+996555967807', '720000'),
       (7, 'Кутман', 'Келсинбеков',
        'Бишкек', 'Бишкек', 'Кыргызстан',
        'kutman@gmail.com',
        '+996222095698', '720000'),
       (8, 'Айгерим', 'Токторбаева',
        'Нарын', 'Нарын', 'Кыргызстан',
        'aigerim@gmail.com',
        '+996700123456', '724100'),
       (9, 'Эркин', 'Амантаев',
        'Ыссык-Куль', 'Каракол', 'Кыргызстан',
        'erkin@gmail.com',
        '+996777987654', '722300'),
       (10, 'Гулзина', 'Тургунбаева',
        'Чолпон-Ата', 'Иссык-Куль', 'Кыргызстан',
        'gulzina@gmail.com',
        '+996555111222', '722160'),
       (11, 'Айбек', 'Раимкулов',
        'Каракол', 'Иссык-Куль', 'Кыргызстан',
        'aybek@gmail.com',
        '+996700112233', '722160'),
       (12, 'Назгул', 'Алиева',
        'Бишкек', 'Бишкек', 'Кыргызстан',
        'nazgul@gmail.com',
        '+996555987654', '720010'),
       (13, 'Рустам', 'Темиров',
        'Ош', 'Ош', 'Кыргызстан',
        'rustam@gmail.com',
        '+996770123456', '723010'),
       (14, 'Асель', 'Абдырайымова',
        'Токмок', 'Чуй', 'Кыргызстан',
        'asel@gmail.com',
        '+996555999888', '722010'),
       (15, 'Тимур', 'Турсунов',
        'Джалал-Абад', 'Джалал-Абад', 'Кыргызстан',
        'timur@gmail.com',
        '+996700987654', '715010');

INSERT INTO orders(id, date_of_order, date_of_received, order_number,
                   payment_type, status, total_price, with_delivery,
                   buyer_id, customer_id)
VALUES (1, '2022-12-30', '2023-01-23', '11',
        'ONLINE_BY_CARD', 'CANCELED', 2000, TRUE, 2, 1),
       (2, '2023-12-21', '2023-10-05', '22',
        'OFFLINE_BY_CARD', 'DELIVERED', 4560, TRUE, 1, 2),
       (3, '2023-02-11', '2023-03-05', '33',
        'OFFLINE_BY_CASH', 'CANCELED', 1999, FALSE, 2, 3),
       (4, '2023-01-15', '2023-02-12', '44',
        'OFFLINE_BY_CASH', 'DELIVERED', 870, FALSE, 1, 4),
       (5, '2023-02-01', '2023-02-05', '55',
        'ONLINE_BY_CARD', 'DELIVERED', 3690, TRUE, 1, 5),
       (6, '2023-05-24', '2023-05-29', '66',
        'OFFLINE_BY_CARD', 'SHIPPED', 500, FALSE, 2, 6),
       (7, '2023-04-10', '2023-05-09', '77',
        'OFFLINE_BY_CASH', 'DELIVERED', 4500, FALSE, 1, 7),
       (8, '2023-06-17', '2023-07-05', '71',
        'ONLINE_BY_CARD', 'DELIVERED', 7700, TRUE, 1, 8),
       (9, '2023-07-12', '2023-07-27', '23',
        'OFFLINE_BY_CASH', 'DELIVERED', 50000, FALSE, 1, 9),
       (10, '2023-01-29', '2023-03-05', '86',
        'OFFLINE_BY_CARD', 'DELIVERED', 90000, TRUE, 1, 10),
       (11, '2023-02-25', '2023-03-11', '12',
        'ONLINE_BY_CARD', 'DELIVERED', 7890, FALSE, 1, 11),
       (12, '2023-04-22', '2023-05-01', '22',
        'OFFLINE_BY_CASH', 'SHIPPED', 1240, TRUE, 1, 12),
       (13, '2023-07-11', '2023-09-05', '131',
        'OFFLINE_BY_CARD', 'DELIVERED', 2900, FALSE, 1, 13),
       (14, '2023-04-19', '2023-04-25', '28',
        'ONLINE_BY_CARD', 'CANCELED', 5000, FALSE, 2, 14),
       (15, '2023-06-06', '2023-07-07', '17',
        'ONLINE_BY_CARD', 'SHIPPED', 9999, FALSE, 1, 15);

INSERT INTO appeals(id, title, divide, detailed_appeal, buyer_id)
VALUES (1,
        'Жалоба', 'Жалоба', 'Жалоба на качество услуг', 1),
       (2,
        'Запрос', 'Запрос', 'Запрос на получение информации', 1);

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
       (17, 2, 'Свитера и кофты и Худи '),
       (18, 2, 'Джемперы и кардиганы'),
       (19, 2, 'Платья'),
       (20, 2, 'Белье'),
       (21, 2, 'Блузки и рубашки'),
       (22, 2, 'Футболки и майки'),
       (23, 2, 'Верхняя одежда'),
       (24, 2, 'Спортивная одежда'),
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
        '2023-05-23', '2023-11-12', 15),
       (6,
        '2023-06-15', '2023-07-05', 40),
       (7,
        '2023-07-10', '2023-08-20', 55),
       (8,
        '2023-06-05', '2023-06-30', 30),
       (9,
        '2023-08-01', '2023-08-31', 20),
       (10,
        '2023-09-05', '2023-09-30', 10);


INSERT INTO products(id, articul, brand, composition,
                     date_of_change, date_of_create,
                     description, is_draft, manufacturer,
                     name, rating, season, style,
                     seller_id, sub_category_id)
VALUES (1, 'SKU12345', 'ZARA', 'Шерсть/Кашемир', '2023-03-03', '2023-01-23',
        'Приталенное платье с V-образным вырезом и длинными рукавами',
        FALSE, 'БайGo', 'Платье', 9.8, 'Летний', 'Вечерний', 1, 19),
       (2, 'CODE789', 'Calvin Klein', 'Хлопок/Полиэстер/Эластан',
        '2022-12-03', '2023-05-29',
        'Стильные шорты с высокой посадкой и широким поясом',
        TRUE, 'БайGo', 'Шорты', 9.8, 'Летний', 'Классический стиль', 1, 30),
       (3, 'ART123', 'Gucci', 'Полиэстер/Вискоза/Эластан', '2023-03-03', '2023-01-23',
        'Удобный свитшот с капюшоном и карманом-кенгуру',
        FALSE, 'БайGo', 'Свитшот', 9.8, 'Зимний', 'Спортивный', 1, 7),
       (4, 'QRS789', 'Puma', 'Синтетика/Резина', '2023-12-20', '2023-11-20',
        'Стильные мужские кроссовки для повседневной носки', FALSE, 'БайGo',
        'Повседневные кроссовки', 8.6, 'Летний', 'Уличный стиль', 2, 11),
       (5, '123ABC', 'H&M', '100% Хлопок', '2023-03-03', '2022-12-30',
        'Стильная футболка с круглым вырезом и короткими рукавами',
        FALSE, 'БайGo', 'Футболка', 3.3, 'Летний', 'Уличный стиль', 1, 22),
       (6, 'ABC123', 'Disney', 'Хлопок/Полиэстер', '2024-01-10', '2023-12-10',
        'Симпатичный детский комбинезон с изображением персонажей Disney', FALSE, 'БайGo',
        'Детский комбинезон', 9.2, 'Осенний', 'Повседневный', 1, 36),
       (7, 'DEF456', 'Carter\s', 'Хлопок', '2024-01-20', '2024-01-01',
        'Мягкий и удобный детский боди с принтом', TRUE, 'БайGo',
        'Детское боди', 9.5, 'Всесезонный', 'Повседневный', 1, 34),
       (8, 'KLM123', 'Adidas', 'Кожа/Синтетика', '2023-12-01', '2023-11-01',
        'Классические мужские кеды со шнуровкой', FALSE, 'БайGo',
        'Кеды', 8.7, 'Всесезонный', 'Уличный стиль', 1, 11),
       (9, 'JKL234', 'GapKids', 'Хлопок/Полиэстер', '2024-02-10', '2024-02-01',
        'Теплый детский свитер с ярким принтом', TRUE, 'БайGo',
        'Детский свитер', 9.1, 'Зимний', 'Повседневный', 1, 36),
       (10, 'HIJ890', 'Nike', 'Текстиль/Синтетика', '2023-11-20', '2023-10-20',
        'Легкие и комфортные женские кроссовки для бега', TRUE, 'БайGo',
        'Беговые кроссовки', 9.4, 'Летний', 'Спортивный', 1, 27),
       (11, 'YZA789', 'Balenciaga', 'Кожа/Текстиль', '2023-11-01', '2023-10-01',
        'Модные женские кроссовки с контрастными вставками и подошвой-платформой', FALSE, 'БайGo',
        'Кроссовки', 9.6, 'Летний', 'Уличный стиль', 1, 27),
       (12, 'GHI123', 'Burberry', 'Шерсть/Кашемир/Ангора', '2023-09-15', '2023-08-30',
        'Стильное пальто с поясом и воротником-стойкой', FALSE, 'БайGo',
        'Пальто', 9.2, 'Зимний', 'Классический стиль', 1, 4),
       (13, 'JKL456', 'Dolce & Gabbana', 'Шелк/Полиэстер/Эластан', '2023-09-10', '2023-08-20',
        'Эксклюзивный вечерний наряд с вышивкой и открытой спиной', FALSE, 'БайGo',
        'Вечернее платье', 9.7, 'Осенний', 'Вечерний', 1, 19),
       (14, 'MNO789', 'GAP', 'Хлопок/Эластан', '2023-10-01', '2023-09-10',
        'Комфортные джинсовые бермуда с застежкой на молнию и пуговицу', TRUE, 'БайGo',
        'Джинсовые бермуда', 8.7, 'Летний', 'Уличный стиль', 1, 30),
       (15, 'PQR123', 'Prada', 'Кожа', '2023-09-20', '2023-09-01',
        'Стильный женский кошелек с принтом и множеством отделений', TRUE, 'БайGo',
        'Кошелек', 8.4, 'Всесезонный', 'Модный', 1, 28);

INSERT INTO sub_products(id, color, color_hex_code, price, product_id, discount_id)
VALUES (1,
        'Черный', '#000000', 2000, 1, null),
       (2,
        'Красный', '#FF0000', 5900, 1, 2),
       (3,
        'Фиолетовый', '#800080', 3999, 1, null),
       (4,
        'Розовый', '#FFC0CB', 10500, 2, 4),
       (5,
        'Желтый', '#FFFF00', 6666, 2, 5),
       (6,
        'Синий', '#0000FF', 8500, 2, 6),
       (7,
        'Зеленый', '#008000', 2500, 3, null),
       (8,
        'Оранжевый', '#FFA500', 3900, 3, 10),
       (9,
        'Желтый', '#FFFF00', 5500, 4, 2),
       (10,
        'Коричневый', '#A52A2A', 7200, 5, null),
       (11,
        'Серый', '#808080', 3500, 6, 9),
       (12,
        'Белый', '#FFFFFF', 4500, 6, 1),
       (13,
        'Коралловый', '#FF7F50', 5900, 7, 3),
       (14,
        'Салатовый', '#7FFF00', 2800, 7, 5),
       (15,
        'Серебристый', '#C0C0C0', 6200, 8, 7),
       (16,
        'Сиреневый', '#DA70D6', 4100, 9, null),
       (17,
        'Голубой', '#00BFFF', 3600, 10, null),
       (18,
        'Коричневый', '#8B4513', 5300, 10, 2),
       (19,
        'Желтозеленый', '#ADFF2F', 2900, 10, 5),
       (20,
        'Оливковый', '#808000', 3800, 11, 7),
       (21,
        'Фуксия', '#FF00FF', 4700, 11, 9),
       (22,
        'Персиковый', '#FFDAB9', 6200, 12, 1),
       (23,
        'Гранатовый', '#800000', 5400, 12, 3),
       (24,
        'Бирюзовый', '#40E0D0', 4600, 13, null),
       (25,
        'Мятный', '#98FF98', 3100, 13, 3),
       (26,
        'Кремовый', '#FFFDD0', 5000, 13, 9),
       (27,
        'Темно-синий', '#00008B', 4200, 14, 4),
       (28,
        'Лаймовый', '#00FF00', 3700, 14, 4),
       (29,
        'Серебристо-серый', '#C0C0C0', 4800, 15, null),
       (30,
        'Рыжий', '#FF4500', 3300, 15, null);

INSERT INTO sub_product_images(sub_product_id, images)
VALUES (1,
        'https://ae01.alicdn.com/kf/S58fe73d6948e44ab99daa45c4cf38f7d8.jpg'),
       (2,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQU7sOTy7Nh601_lPNtZY-gxPTmyeFnKWvuv2M9LlwwAGdjVxMLU2zqL6Hqz7MUDG19P0c&usqp=CAU'),
       (3,
        'https://ae04.alicdn.com/kf/Sd51425271c4c4500a7febe5559d6a41a9.jpg_640x640.jpg'),
       (4,
        'https://alitools.io/ru/showcase/image?url=https%3A%2F%2Fae01.alicdn.com%2Fkf%2FH68ed3f2c1f37437a881ffbce47f36655A.jpg_480x480.jpg'),
       (5,
        'https://cdn.laredoute.ru/products/1/d/0/1d05d12870312db7650d865c29a0b330.jpg'),
       (6,
        'https://ae04.alicdn.com/kf/S76b47001d824440e98c0f0a55bc350b1K.jpg_640x640.jpg'),
       (7,
        'https://svs.proatribut.ru/uploads/20180906/36_ID49823.jpg'),
       (8,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQwl-wBaTBg82S8mG_IywIYnjPLHQhUXZcdg&usqp=CAU'),
       (9,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcem2Tdfox6vOudydf7u7bmrT-_okY0mdLkQ&usqp=CAU'),
       (10,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYvJrR5YUMRFTxfXrIDA2hRlwjxUmVhA7XJANjQs7cv3PwrkDH2cBTksdqm_Tv-OMm-Og&usqp=CAU'),
       (11,
        'https://content.rozetka.com.ua/goods/images/original/61613267.jpg'),
       (12,
        'https://swbaby.ru/userfls/shop/large/4/38985_petit-bebe-kombinezon-s-kapyushon.jpg'),
       (13,
        'https://content2.rozetka.com.ua/goods/images/original/81325899.jpg'),
       (14,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJLH0uvuixZ9HJMUImFge6pHQn7AZMnQmm9A&usqp=CAU'),
       (15,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0cgoQfuuEiMLZxaCWkLhqo2hvAdXYs_1cPg&usqp=CAU'),
       (16,
        'https://ir.ozone.ru/s3/multimedia-i/c300/6648130098.jpg'),
       (17,
        'https://basket-05.wb.ru/vol741/part74189/74189201/images/c516x688/1.jpg'),
       (18,
        'https://cf.shopee.tw/file/dd9e8aef98bb30ce95b477bde483aebb'),
       (19,
        'https://images.shafastatic.net/502615598'),
       (20,
        'https://image-thumbs.shafastatic.net/991822872_310_430'),
       (21,
        'https://images.shafastatic.net/100463257'),
       (22,
        'https://smartcasuals.ru/wp-content/uploads/2021/09/DSC_1473.jpg'),
       (23,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR05oF3ha146qSngrUPI8ojaYqjWVVq5fwCKFgmMo8A70pIXgMGRLdTEhx6cnTHU3dyXHk&usqp=CAU'),
       (24,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQz3HZH9jtqZ_tNndIPqEke9w5_0tI5KkfmNHUvEFETvPF7ENtVJTD921fAt7M99Xjnghc&usqp=CAU'),
       (25,
        'https://tessuti-ital.ru/upload/resize_cache/iblock/bfe/600_600_14d9f661248b15d0b6eac66e4ee6fe37e/bfe95474df05ebdc743398ff3e36e176.jpg'),
       (26,
        'https://персонаж.рф/wp-content/uploads//2021/04/shyolkovye-platya-foto-2.jpg'),
       (27,
        'https://ae01.alicdn.com/kf/H7ba2a85421be4efcaa83e8c1cb42bb86D.jpg'),
       (28,
        'https://zara-ru.com/images/detailed/1893/5862157520_2_1_1.jpg'),
       (29,
        'https://legrand-collection.com.ua/images/detailed/4/MR-182-143_N_4qgn-35.jpg'),
       (30,
        'https://ae04.alicdn.com/kf/S8c0b35df733d4f10a46df8f61d8f167dH.jpg_480x480.jpg');

INSERT INTO sizes(id, barcode, quantity, size, sub_product_id)
VALUES (1,
        1111, 100, 'M', 1),
       (2,
        1112, 2340, 'S', 1),
       (3,
        1113, 999, 'L', 1),
       (4,
        1114, 7000, 'XL', 2),
       (5,
        1115, 200, 'XS', 2),
       (6,
        1116, 50, 'XXL', 2),
       (7,
        1117, 300, 'L', 2),
       (8,
        1118, 120, 'S', 3),
       (9,
        1119, 80, 'M', 3),
       (10,
        1120, 150, 'XL', 3),
       (11,
        1121, 200, 'M', 3),
       (12,
        1122, 150, 'L', 3),
       (13,
        1123, 100, 'S', 4),
       (14,
        1124, 50, 'XL', 4),
       (15,
        1125, 80, 'XXL', 5),
       (16,
        1126, 120, 'S', 5),
       (17,
        1127, 90, 'M', 5),
       (18,
        1128, 70, 'L', 5),
       (19,
        1129, 40, 'XL', 5),
       (20,
        1130, 60, 'XXL', 6),
       (21,
        2211, 180, 'M', 6),
       (22,
        2212, 130, 'L', 7),
       (23,
        1343, 110, 'S', 7),
       (24,
        6543, 70, 'XL', 7),
       (25,
        6789, 100, 'XXL', 8),
       (26,
        7890, 220, 'S', 8),
       (27,
        8901, 160, 'M', 8),
       (28,
        9012, 140, 'L', 8),
       (29,
        0123, 90, '40', 9),
       (30,
        2345, 120, '42', 9),
       (31,
        2432, 80, '39', 9),
       (32,
        5542, 120, 'L', 10),
       (33,
        2234, 150, 'S', 10),
       (34,
        5564, 100, '2 года', 11),
       (35,
        2340, 70, '1,5 года', 11),
       (36,
        0076, 90, '6-9 месяцев', 12),
       (37,
        0034, 110, '0-1 месяц', 13),
       (38,
        0023, 160, '3 месяца', 13),
       (39,
        0021, 200, '6-9 месяцев', 14),
       (40,
        2367, 140, '2-3 месяца', 14),
       (41,
        9944, 120, '40', 15),
       (42,
        3333, 80, '5 года', 16),
       (43,
        2246, 100, '3 года', 16),
       (44,
        0885, 70, '36', 17),
       (45,
        2000, 90, '37', 18),
       (46,
        9863, 220, '38', 18),
       (47,
        2674, 160, '39', 19),
       (48,
        0284, 140, '35', 19),
       (49,
        9270, 90, '38', 20),
       (50,
        0055, 120, '37', 20),
       (51,
        9999, 90, '36', 21),
       (52,
        8888, 130, 'L', 22),
       (53,
        8765, 160, 'S', 22),
       (54,
        2382, 120, 'XL', 23),
       (55,
        4372, 80, 'XXL', 23),
       (56,
        8234, 100, 'S', 24),
       (57,
        7234, 120, 'M', 24),
       (58,
        4527, 170, 'L', 25),
       (59,
        1029, 210, 'XL', 25),
       (60,
        2012, 150, 'XXL', 26),
       (61,
        0345, 130, 'M', 26),
       (62,
        0345, 90, 'L', 27),
       (63,
        3272, 110, 'S', 28),
       (64,
        7856, 80, 'XL', 28),
       (65,
        8356, 100, 'маленький', 29),
       (66,
        5243, 230, 'большой', 29),
       (67,
        2725, 170, 'средний', 29),
       (68,
        3835, 150, 'маленький', 30),
       (69,
        2547, 100, 'средний', 30),
       (70,
        8466, 130, 'большой', 30);

INSERT INTO buyers_baskets(buyer_id, sub_products_size_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (1, 16),
       (1, 17),
       (1, 18),
       (1, 19),
       (1, 20),
       (1, 21),
       (1, 22),
       (1, 23),
       (1, 24),
       (1, 25),
       (1, 26),
       (1, 27),
       (1, 28),
       (1, 29),
       (1, 30),
       (1, 31),
       (1, 32),
       (1, 33),
       (1, 34),
       (1, 35),
       (1, 36),
       (1, 37),
       (1, 38),
       (1, 39),
       (1, 40),
       (1, 41),
       (1, 42),
       (1, 43),
       (1, 44),
       (1, 45),
       (1, 46),
       (1, 47),
       (1, 48),
       (1, 49),
       (1, 50),
       (1, 51),
       (1, 52),
       (1, 53),
       (1, 54),
       (1, 55),
       (1, 56),
       (1, 57),
       (1, 58),
       (1, 59),
       (1, 60),
       (1, 61),
       (1, 62),
       (1, 63),
       (1, 64),
       (1, 65),
       (1, 66),
       (1, 67),
       (2, 68),
       (2, 69),
       (2, 70);

INSERT INTO buyers_favorites(buyer_id, sub_products_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (1, 16),
       (1, 17),
       (1, 18),
       (1, 19),
       (1, 20),
       (1, 21),
       (1, 22),
       (1, 23),
       (1, 24),
       (1, 25),
       (1, 26),
       (1, 27),
       (1, 28),
       (1, 29),
       (1, 30),
       (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5);

INSERT INTO buyers_last_views(buyer_id, sub_products_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (1, 16),
       (1, 17),
       (1, 18),
       (1, 19),
       (1, 20),
       (1, 21),
       (1, 22),
       (1, 23),
       (1, 24),
       (1, 25),
       (1, 26),
       (1, 27),
       (1, 28),
       (1, 29),
       (1, 30),
       (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5);

INSERT INTO warehouses(id, name, location, transit_cost)
VALUES (1, 'БайGo', 'Бишкек', 1200.00),
       (2, 'Асман', 'Талас', 2300.00),
       (3, 'Аю Гранд', 'Чуй', 1200.00),
       (4, 'Амазон', 'Ош', 1200.00),
       (5, 'Караван', 'Баткен', 1200.00),
       (6, 'Феникс', 'Нарын', 1200.00),
       (7, 'Искра', 'Джалал-Абад', 1200.00),
       (8, 'Мега-Склад', 'Токмок', 1200.00),
       (9, 'Глобус', 'Каракол', 1200.00),
       (10, 'Альфа', 'Ыссык-Куль', 1200.00),
       (11, 'Логистикс', 'Талдыкорган', 1200.00),
       (12, 'Экспресс-Склад', 'Жезказган', 1200.00),
       (13, 'Урал-Транс', 'Актобе', 1200.00),
       (14, 'Восток-Логистика', 'Атырау', 1200.00),
       (15, 'Запад-Карго', 'Уральск', 1200.00);

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

INSERT INTO orders_sizes(order_id, product_count, size_id)
VALUES (1, 100, 1),
       (2, 240, 2),
       (3, 699, 3),
       (4, 349, 4),
       (5, 793, 5),
       (6, 111, 6),
       (7, 24, 7),
       (8, 49, 8),
       (9, 39, 9),
       (10, 493, 10),
       (11, 100, 11),
       (12, 2670, 12),
       (13, 299, 13),
       (14, 349, 14),
       (15, 563, 15);

INSERT INTO reviews(id, amount_of_like, answer, date_and_time, grade, text, buyer_id, product_id)
VALUES (1, 50, 'В наличии есть размер М?', '2023-07-13 12:00:00', 5,
        'Красивое платье доступно к продаже, доставка действует по всему Кыргызстану',
        1, 1),
       (2, 120, 'В наличии есть размер М?', '2023-07-13 12:00:00', 5,
        'Базовые футболки',
        1, 5),
       (3, 1000, 'Можно цену?', '2023-07-13 12:00:00', 4,
        'Образ в наличии',
        1, 12),
       (4, 75, 'Есть ли размер L в наличии?', '2023-07-13 12:00:00', 2,
        'Стильные шорты', 1, 2),
       (5, 250, 'Есть ли скидки на данный товар?', '2023-07-13 12:00:00', 3,
        'Вечернее платье для особых случаев', 2, 13);

INSERT INTO review_images(review_id, images)
VALUES (1, 'https://ae01.alicdn.com/kf/S58fe73d6948e44ab99daa45c4cf38f7d8.jpg'),
       (2,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYvJrR5YUMRFTxfXrIDA2hRlwjxUmVhA7XJANjQs7cv3PwrkDH2cBTksdqm_Tv-OMm-Og&usqp=CAU'),
       (3, 'https://smartcasuals.ru/wp-content/uploads/2021/09/DSC_1473.jpg'),
       (4,
        'https://alitools.io/ru/showcase/image?url=https%3A%2F%2Fae01.alicdn.com%2Fkf%2FH68ed3f2c1f37437a881ffbce47f36655A.jpg_480x480.jpg'),
       (5, 'https://персонаж.рф/wp-content/uploads//2021/04/shyolkovye-platya-foto-2.jpg');

INSERT INTO supplies(id, accepted_products, actual_date,
                     commission, created_at, planned_date,
                     quantity_of_products, status, supply_cost,
                     supply_number, supply_type, seller_id, warehouse_id)
VALUES (1, 200, '2023-07-07', 300, '2023-07-13', '2023-05-28', 10000,
        'DELIVERED', 100, 'SUP2021001', 'MONO_PALLETS', 1, 1),
       (2, 450, '2023-01-25', 300, '2023-07-13', '2023-05-28', 60400,
        'DELIVERED', 500, 'INV-2021-005', 'MONO_PALLETS', 2, 2),
       (3, 100, '2022-07-12', 270, '2023-01-22', '2023-05-28', 23500,
        'DELIVERED', 1000, 'PO-202108-001', 'SUPER_SAFE', 3, 3),
       (4, 699, '2023-07-07', 690, '2023-07-13', '2023-04-28', 46700,
        'DELIVERED', 200, 'SUP2021001', 'BOX', 1, 4),
       (5, 580, '2023-07-07', 1000, '2023-05-13', '2023-05-21', 2000,
        'DELIVERED', 740, 'INV-2021-005', 'BOX', 3, 5);

INSERT INTO supply_products(id, quantity, size_id, supply_id)
VALUES (1, 100, 1, 1),
       (2, 232, 3, 1),
       (3, 22, 4, 1),
       (4, 992, 5, 1),
       (5, 454, 6, 1),
       (6, 21, 7, 2),
       (7, 321, 8, 2),
       (8, 234, 9, 2),
       (9, 999, 10, 3),
       (10, 999, 11, 3),
       (11, 999, 12, 3),
       (12, 156, 13, 4),
       (13, 129, 14, 4),
       (14, 79, 15, 4),
       (15, 130, 16, 5),
       (16, 168, 17, 5),
       (17, 330, 18, 5);

INSERT INTO news (id, topic, description, date)
VALUES (1, 'Новая коллекция',
        'Стильные аксессуары: Освежите свой образ с нашей новой коллекцией модных аксессуаров.' ||
        ' Очки, ремни, шарфы и многое другое!', '2023-07-24'),
       (2, 'Скидка',
        'Специальное предложение: Весь август действует скидка 20% на все платья. Не упустите возможность обновить свой летний гардероб!',
        '2023-07-20'),
       (3, 'Акция',
        'Внимание, продавцы! Мы запускаем акцию "Приведи друга"! Пригласите нового продавца на наш платформу и получите скидку 50% на комиссию за первые 3 месяца работы',
        '2023-07-26'),
       (4, 'Акция',
        'Для наших самых активных продавцов! С 15 июля по 15 августа проводится акция "Бонус за выручку". Продавцы, которые достигнут определенного объема продаж, получат дополнительный бонус на свой баланс.',
        '2023-07-25'),
       (5, 'Коллекция',
        'Запуск новой категории: Мы добавили новую категорию "Эксклюзивные коллекции". Приглашаем всех продавцов представить свои уникальные дизайны и стильные модели',
        '2023-07-25');



INSERT INTO surveys(id, survey_type, title)
VALUES (1,'FOR_BUYERS', 'survey1' ),
       (2, 'FOR_SELLERS', 'survey2');

INSERT INTO questions(id, title, survey_id)
VALUES (1, 'Какова ваша общая удовлетворенность использованием нашей интернет-платформы для покупок?', 1),
       (2, 'Как бы вы оценили удобство использования нашей интернет-платформы для поиска и выбора товаров?', 1),
       (3, 'Оцените качество представленной информации о товарах на нашей интернет-платформе.', 1),
       (4, 'Насколько удовлетворены процессом оформления заказа и оплаты на нашей интернет-платформе?', 1),
       (5, 'Какова ваша общая оценка процесса доставки и получения заказанных товаров?', 1),
       (6, 'Как бы вы оценили удобство использования нашей интернет-платформы для добавления и управления товарами?', 2),
       (7, 'Оцените качество предоставленных инструментов и функциональности для управления продажами на нашей интернет-платформе.', 2),
       (8, 'Насколько удовлетворены процессом обработки заказов и управления их выполнением на нашей интернет-платформе?', 2),
       (9, 'Какова ваша общая оценка процесса взаимодействия с нашей поддержкой клиентов?', 2),
       (10, 'Какова ваша общая оценка эффективности нашей интернет-платформы в достижении ваших продажных целей?', 2);

INSERT INTO options(id, option_order, title, question_id)
VALUES (1, 1, 'Доволен', 1),
       (2, 2, 'Нейтрален', 1),
       (3, 3, 'Недоволен', 1),
       (4, 4, 'title4', 2),
       (5, 5, 'title5', 2),
       (6, 6, 'title6', 2),
       (7, 7, 'title6', 3),
       (8, 8, 'title6', 3),
       (9, 9, 'title6', 3),
       (10, 10, 'title6', 4);

INSERT INTO answers(id, full_name, phone_number, rating, suggestion_or_comment)
VALUES (1, 'Nuriza', '+996700600600', 5, 'suggestion or comments1'),
       (2, 'Kanykei', '+996700600500', 5, 'suggestion or comments2'),
       (3, 'Jhiydegul', '+996700600400', 5, 'suggestion or comments3'),
       (4, 'Eliza', '+996700600300', 5, 'suggestion or comments4'),
       (5, 'Saltanat', '+996700600200', 5, 'suggestion or comments5');


