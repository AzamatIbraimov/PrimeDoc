INSERT INTO about_us (id, create_date, updated_date, header, "order", paragraph)
VALUES ((SELECT nextval('about_us_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000', 'Test header',
        1, 'Cras sed felis eget velit aliquet sagittis id.'),
       ((SELECT nextval('about_us_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000', 'Fourth header',
        2, 'Id volutpat lacus laoreet non. Accumsan in nisl nisi scelerisque eu ultrices vitae.');
INSERT INTO about_us (id, create_date, updated_date, header, "order", paragraph)
VALUES((SELECT nextval('about_us_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000', 'Second header',
       4, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'),
      ((SELECT nextval('about_us_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000', 'Third header',
       3, 'Urna id volutpat lacus laoreet non curabitur gravida. Mi proin sed libero enim sed faucibus turpis in. Massa');
INSERT INTO faq (id, create_date, updated_date, answer, "order", question)
VALUES ((SELECT nextval('faq_seq')), '2020-07-03 19:27:51.000000', '2020-07-03 19:27:51.000000',
        'Prime Clinic – это современная поликлиника, оказывающая амбулаторный приём пациентов по широкому профилю медицинских услуг.',
        1, 'Расскажите о вашей клинике'),
       ((SELECT nextval('faq_seq')), '2020-07-03 19:27:51.000000', '2020-07-03 19:27:51.000000',
        '80% наших врачей – это специалисты со стажем работы более 15 лет. У нас работают 4 кандидата медицинских наук и один врач-кардиолог со степенью доктора медицинских наук.',
        2, 'Что у вас за специалисты?'),
       ((SELECT nextval('faq_seq')), '2020-07-03 19:27:51.000000', '2020-07-03 19:27:51.000000',
        'Здравствуйте, напишите в наш чат. Администратор всегда ответит)', 3,
        'Никак не могу зарегистрироваться на прием');
INSERT INTO category (id, create_date, updated_date, description, "name")
VALUES ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Специалист правильного питания',
        'Диетолог'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Женские заболевания',
        'Гинеколог'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Специалист по кожным заболеваниям',
        'Дерматолог'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Специалист общего профиля',
        'Терапевт'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Специалист по проблемам нервной системы',
        'Невролог'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Нарушение функций костно-мышечной системы',
        'Ортопед'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Специалист для детей и подростков',
        'Педиатр'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Специалист по заболеваниям ушей, горла и носа',
        'ЛОР'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Заболевания бронхо-легочной системы',
        'Пульмонолог'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Травмы суставов, мышц и костей',
        'Травматолог'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Травмы суставов, мышц и костей',
        'Реабилитолог'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Специалист по проблемам пищеварения',
        'Гастроэнтеролог'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Болезни сердца и сосудов',
        'Кардиолог'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Хирургическое лечение заболеваний и травм',
        'Хирург'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Специалист по мочеполовой системе',
        'Уролог'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Гормональные нарушения',
        'Эндокринолог'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000',
        'Нарушения иммунной системы',
        'Аллерголог'),
       ((SELECT nextval('category_seq')), '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000', 'Болезни глаз',
        'Офтальмолог');
INSERT INTO users (id, enabled, account_non_expired, account_non_locked, credentials_non_expired, create_date, updated_date, birth_date, firstname, lastname, password, patronymic, username)
VALUES ((SELECT nextval('user_seq')), true, true, true, true, '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000', '1945-12-06',
        'admin', 'adminov', '$2a$10$SMYgnNtAwpw6wFV57/IiL.xYAL5uhVfcMmXdgyt7BqVmB8S53e0um', 'adminovich', 'primecdoctor@gmail.com'),
        ((SELECT nextval('user_seq')), true, true, true, true, '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000', '2000-07-03',
        'Анеля', 'Максимова', '$2a$10$SMYgnNtAwpw6wFV57/IiL.xYAL5uhVfcMmXdgyt7BqVmB8S53e0um', 'Максимовна', '+996501280216'),
       ((SELECT nextval('user_seq')), true, true, true, true, '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000', '1999-12-06',
        'Жасмин', 'Аскарова', '$2a$10$SMYgnNtAwpw6wFV57/IiL.xYAL5uhVfcMmXdgyt7BqVmB8S53e0um', 'Асановна', '+996777889911'),
       ((SELECT nextval('user_seq')), true, true, true, true, '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000', '1972-12-06',
        'Анна', 'Маслова', '$2a$10$SMYgnNtAwpw6wFV57/IiL.xYAL5uhVfcMmXdgyt7BqVmB8S53e0um', 'Каримовна', '+996772192999'),
       ((SELECT nextval('user_seq')), true, true, true, true, '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000', '1988-12-06',
        'Мира', 'Кайратова', '$2a$10$SMYgnNtAwpw6wFV57/IiL.xYAL5uhVfcMmXdgyt7BqVmB8S53e0um', 'Асановна', '+996771726345'),
       ((SELECT nextval('user_seq')), true, true, true, true, '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000', '1984-12-06',
        'Фарзона', 'Фарзонова', '$2a$10$SMYgnNtAwpw6wFV57/IiL.xYAL5uhVfcMmXdgyt7BqVmB8S53e0um', 'Фарзонович', '+996700700700'),
       ((SELECT nextval('user_seq')), true, true, true, true, '2020-06-29 19:44:51.000000', '2020-06-29 19:44:51.000000', '1984-12-06',
        'Аман', 'Аманов', '$2a$10$SMYgnNtAwpw6wFV57/IiL.xYAL5uhVfcMmXdgyt7BqVmB8S53e0um', 'Аманович', '+996700800700');
INSERT INTO user_role (user_id, authorities)
VALUES  (1, 'ADMIN'),(2, 'CUSTOMER'),(3, 'CUSTOMER'),(4, 'CUSTOMER'),(5, 'CUSTOMER'),(6, 'USER'),(7, 'USER');
INSERT INTO client (id, create_date, updated_date, user_id)
VALUES  ((SELECT nextval('client_seq')), '2020-07-03 21:39:08.000000', '2020-07-03 21:39:08.000000', 6),
         ((SELECT nextval('client_seq')), '2020-07-03 21:39:08.000000', '2020-07-03 21:39:08.000000', 7);
INSERT INTO doctor (id, create_date, updated_date, bio, position, user_id)
VALUES ((SELECT nextval('doctor_seq')), '2020-07-03 21:47:15.000000', '2020-07-03 21:47:15.000000', 'Врач высшей категории Стаж 25 лет', 'Высший врач', 2),
       ((SELECT nextval('doctor_seq')), '2020-07-03 21:47:15.000000', '2020-07-03 21:47:15.000000', 'Врач высшей категории Стаж 25 лет', 'Высший врач', 3),
       ((SELECT nextval('doctor_seq')), '2020-07-03 21:47:15.000000', '2020-07-03 21:47:15.000000', 'Врач высшей категории Стаж 25 лет', 'Высший врач', 4),
       ((SELECT nextval('doctor_seq')), '2020-07-03 21:47:15.000000', '2020-07-03 21:47:15.000000', 'Врач высшей категории Стаж 25 лет', 'Высший врач', 5);
INSERT INTO doctor_info (id, create_date, updated_date, end_date, info_type, name, organization_name, start_date, doctor_id)
VALUES ((SELECT nextval('doctor_info_seq')), '2020-07-03 22:03:05.000000',
        '2020-07-03 22:03:05.000000',
        '2010-07-03', 'EXPERIENCE', 'Интернатура по специальности "Аллергология-иммунология"',
        'Российский университет дружбы народов', '2020-07-03', 1),
       ((SELECT nextval('doctor_info_seq')), '2020-07-03 22:03:05.000000',
        '2020-07-03 22:03:05.000000',
        '2009-07-03', 'REGALIA', 'Диплом по специальности "Терапия"',
        'Российская Медицинская академия последипломного образования', '2010-07-03', 1),
       ((SELECT nextval('doctor_info_seq')), '2020-07-03 22:03:05.000000',
        '2020-07-03 22:03:05.000000',
        '2010-07-03', 'EXPERIENCE', 'Интернатура по специальности "Аллергология-иммунология"',
        'Российский университет дружбы народов', '2020-07-03', 1),
       ((SELECT nextval('doctor_info_seq')), '2020-07-03 22:03:05.000000',
        '2020-07-03 22:03:05.000000',
        '2009-07-03', 'REGALIA', 'Диплом по специальности "Терапия"',
        'Российская Медицинская академия последипломного образования', '2010-07-03', 2),
       ((SELECT nextval('doctor_info_seq')), '2020-07-03 22:03:05.000000',
        '2020-07-03 22:03:05.000000',
        '2010-07-03', 'EXPERIENCE', 'Интернатура по специальности "Аллергология-иммунология"',
        'Российский университет дружбы народов', '2020-07-03', 2),
       ((SELECT nextval('doctor_info_seq')), '2020-07-03 22:03:05.000000',
        '2020-07-03 22:03:05.000000',
        '2009-07-03', 'REGALIA', 'Диплом по специальности "Терапия"',
        'Российская Медицинская академия последипломного образования', '2010-07-03', 2),
       ((SELECT nextval('doctor_info_seq')), '2020-07-03 22:03:05.000000',
        '2020-07-03 22:03:05.000000',
        '2010-07-03', 'EXPERIENCE', 'Интернатура по специальности "Аллергология-иммунология"',
        'Российский университет дружбы народов', '2020-07-03', 3),
       ((SELECT nextval('doctor_info_seq')), '2020-07-03 22:03:05.000000',
        '2020-07-03 22:03:05.000000',
        '2009-07-03', 'REGALIA', 'Диплом по специальности "Терапия"',
        'Российская Медицинская академия последипломного образования', '2010-07-03', 3);
INSERT INTO doctor_info (id, create_date, updated_date, end_date, info_type, organization_name, start_date, doctor_id)
VALUES ((SELECT nextval('doctor_info_seq')), '2020-07-03 22:03:05.000000',
        '2020-07-03 22:03:05.000000',
        '1993-07-03', 'REGALIA',
        'Актюбинский государственный медицинский институт', '2000-07-03', 3),
       ((SELECT nextval('doctor_info_seq')), '2020-07-03 22:03:05.000000',
        '2020-07-03 22:03:05.000000',
        '1993-07-03', 'REGALIA',
        'Актюбинский государственный медицинский институт', '2000-07-03', 4),
       ((SELECT nextval('doctor_info_seq')), '2020-07-03 22:03:05.000000',
        '2020-07-03 22:03:05.000000',
        '1993-07-03', 'REGALIA',
        'Актюбинский государственный медицинский институт', '2000-07-03', 4),
       ((SELECT nextval('doctor_info_seq')), '2020-07-03 22:03:05.000000',
        '2020-07-03 22:03:05.000000',
        '1993-07-03', 'REGALIA',
        'Актюбинский государственный медицинский институт', '2000-07-03', 4);

INSERT INTO doctor_category (doctor_id, category_id)
VALUES (1, 1),
       (1, 2),
       (1, 9),
       (1, 12),
       (2, 1),
       (2, 5),
       (2, 13),
       (3, 1),
       (3, 3),
       (3, 6),
       (3, 18),
       (4, 1),
       (4, 2),
       (4, 11);
INSERT INTO schedule (id, create_date, updated_date, current_week, week_duration, doctor_id)
VALUES ((SELECT nextval('schedule_seq')), '2020-07-04 14:12:37.000000', '2020-07-04 14:12:37.000000',
        1, 1, 1),
       ((SELECT nextval('schedule_seq')), '2020-07-04 14:12:37.000000', '2020-07-04 14:12:37.000000',
        1, 2, 2),
       ((SELECT nextval('schedule_seq')), '2020-07-04 14:12:37.000000', '2020-07-04 14:12:37.000000',
        1, 3, 3);


INSERT INTO public.payment (id, create_date, updated_date, name, next_steps)
VALUES ((SELECT nextval('payment_seq')), '2020-07-28 17:23:48.000000', '2020-07-28 17:23:50.000000', 'MegaPay',
        'После оплаты, отправьте чек в чат. Врач-администратор сделает подтверждение приема.');
INSERT INTO public.payment (id, create_date, updated_date, name, next_steps)
VALUES ((SELECT nextval('payment_seq')), '2020-07-28 17:31:49.000000', '2020-07-28 17:31:51.000000', 'О!Деньги',
        'В поле "Сумма" введите 250 и нажмите на кнопку "Оплатить". В поле "Введите реквизит" ничего не надо вводить - там по умолчанию будет стоять ваш номер телефона;');
INSERT INTO public.payment (id, create_date, updated_date, name, next_steps)
VALUES ((SELECT nextval('payment_seq')), '2020-07-28 17:33:04.000000', '2020-07-28 17:33:09.000000', 'Balance',
        'После оплаты, отправьте чек в чат. Врач-администратор сделает подтверждение приема.');


INSERT INTO public.payment_step (id, create_date, updated_date, number, text, payment_id)
VALUES ((SELECT nextval('payment_step_seq')), '2020-07-28 17:35:00.000000', '2020-07-28 17:35:10.000000', 1,
        'Откройте мобильное приложение MegaPay;', 1);
INSERT INTO public.payment_step (id, create_date, updated_date, number, text, payment_id)
VALUES ((SELECT nextval('payment_step_seq')), '2020-07-28 17:36:03.000000', '2020-07-28 17:36:04.000000', 2,
        'Нажмите на кнопку "Оплата услуг";', 1);
INSERT INTO public.payment_step (id, create_date, updated_date, number, text, payment_id)
VALUES ((SELECT nextval('payment_step_seq')), '2020-07-28 17:36:39.000000', '2020-07-28 17:36:41.000000', 3,
        'Найдите раздел "Здоровье" и выберете его;', 1);
INSERT INTO public.payment_step (id, create_date, updated_date, number, text, payment_id)
VALUES ((SELECT nextval('payment_step_seq')), '2020-07-28 17:37:01.000000', '2020-07-28 17:37:02.000000', 4,
        'В открывшемся окне появится "PrimeDoc". Нажмите на него;', 2);
INSERT INTO public.payment_step (id, create_date, updated_date, number, text, payment_id)
VALUES ((SELECT nextval('payment_step_seq')), '2020-07-28 17:39:12.000000', '2020-07-28 17:39:13.000000', 5,
        'В поле "Реквизит" напишите своё Ф.И.О. В поле "Сумма оплаты" введите 250 и нажмите на кнопку "Оплатить".', 2);
INSERT INTO public.payment_step (id, create_date, updated_date, number, text, payment_id)
VALUES ((SELECT nextval('payment_step_seq')), '2020-07-28 17:39:39.000000', '2020-07-28 17:39:43.000000', 1,
        'Откройте мобильное приложение О Деньги!;', 2);
INSERT INTO public.payment_step (id, create_date, updated_date, number, text, payment_id)
VALUES ((SELECT nextval('payment_step_seq')), '2020-07-28 17:40:25.000000', '2020-07-28 17:40:25.000000', 2,
        'В нижнем меню нажмите на кнопку "О!Деньги";', 3);
INSERT INTO public.payment_step (id, create_date, updated_date, number, text, payment_id)
VALUES ((SELECT nextval('payment_step_seq')), '2020-07-28 17:40:53.000000', '2020-07-28 17:40:56.000000', 3,
        'Далее нажмите на кнопку "Поиск по каталогу" и впишите туда "PrimeDoc";', 3);
INSERT INTO public.payment_step (id, create_date, updated_date, number, text, payment_id)
VALUES ((SELECT nextval('payment_step_seq')), '2020-07-28 17:41:50.000000', '2020-07-28 17:41:54.000000', 4,
        'Появится "PrimeDoc". Нажмите на него;', 3);
INSERT INTO public.payment_step (id, create_date, updated_date, number, text, payment_id)
VALUES ((SELECT nextval('payment_step_seq')), '2020-07-28 17:42:16.000000', '2020-07-28 17:42:16.000000', 5,
        ' В поле "Сумма" введите 250 и нажмите на кнопку "Оплатить". В поле "Введите реквизит" ничего не надо вводить - там по умолчанию будет стоять ваш номер телефона;',
        3);


UPDATE about_us
SET create_date  = '2020-06-29 19:44:51.000000',
    updated_date = '2020-06-29 19:44:51.000000',
    header       = 'Наши контакты',
    "order"      = 2,
    paragraph    = '+996 777 988 978
+996 556 988 978
+996 312 988 978
office@prime.kg'
WHERE id = 2;
UPDATE about_us
SET create_date  = '2020-06-29 19:44:51.000000',
    updated_date = '2020-06-29 19:44:51.000000',
    header       = 'Наш адрес',
    "order"      = 3,
    paragraph    = 'г.Бишкек, ул.Боконбаева 202 (пер.ул.Уметалиева)'
WHERE id = 3;
UPDATE about_us
SET create_date  = '2020-06-29 19:44:51.000000',
    updated_date = '2020-06-29 19:44:51.000000',
    header       = 'Что такое PrimeDoc?',
    "order"      = 1,
    paragraph    = 'PrimeDoc - это сервис для удалённых онлайн-консультаций с опытными врачами. Вы можете выбрать нужного врача и проконсультироваться с ним через телефон, планшет или ноутбук. При этом, ваше расположение не имеет значения.'
WHERE id = 1;
DELETE
FROM about_us
WHERE id = 4;



