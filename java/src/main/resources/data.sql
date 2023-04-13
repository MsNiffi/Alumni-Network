INSERT INTO person (person_id, first_name, last_name, user_name, work_status, bio, fun_fact, picture_url)
VALUES ('DW6j9KGRlFOs4Hyy4vFqAzHpGFs1', 'Peter', 'Pan', 'peterpan', 'Working for Komplett as a frontend dev',
        'I just like to write CSS dude.', 'You do not need glasses if you C#', 'peterpan'),
       ('b', 'Kari', 'Normann', 'KariNor', 'Working at DNB as a backend dev', 'I just like to write Java dude.',
        'Java mastery is my superpower', 'picture2'),
       ('c', 'John', 'Doe', 'johndoe', 'Studying .NET at Noroff', 'The Illuminati is real.', 'Is your fridge running?',
        'picture3');

INSERT INTO gang (gang_name, gang_description, is_private)
VALUES ('Archers', 'Group for interested archers', true),
       ('The Programmer', 'Group for those interested in programming', false),
       ('Training 2023', 'Group for training buddies', true),
       ('Research Exchange',
        'This group is for postgraduates who are interested in exchanging research ideas and collaborating with other ' ||
        'researchers. Whether you''re looking for feedback on your current research project or want to join a team for ' ||
        'a new research endeavor, this group is the perfect place to connect with other postgraduate researchers.',
        false),
       ('Career Networking',
        'Looking to connect with other postgraduates who are in your field? This group is for you! Connect with other ' ||
        'postgraduates who share your interests and ambitions and get advice on job search strategies, industry trends, and more.',
        false),
       ('Coding and Technology',
        'If you''re a postgraduate studying computer science or interested in coding and technology, this group is for you. ' ||
        'Connect with other students who are passionate about technology and get help with programming challenges, ' ||
        'discuss the latest technology trends, and share resources for learning new coding skills.',
        false);

INSERT INTO post(body, published_on, title, updated_on, sender_person_id, target_gang_gang_id)
VALUES ('Come and say hi to my first post! Or what do you say archers?', '2023-03-02 12:33:10.000000',
        'My first post', '2023-03-02 12:33:10.000000', 'DW6j9KGRlFOs4Hyy4vFqAzHpGFs1', 1),
       ('This is a test post! Wohoo!', '2023-03-05 12:33:10.000000', 'Test post', '2023-03-05 12:33:10.000000', 'b', 1),
       ('My content is too powerful', '2023-03-06 12:33:10.000000', 'Contentful post', '2023-03-06 12:33:10.000000',
        'DW6j9KGRlFOs4Hyy4vFqAzHpGFs1', 2),
       ('Hi everyone, I''m working on my research proposal and would love some feedback. My topic is X and my research question is Y. Any input or suggestions would be greatly appreciated. Thank you!',
        '2023-03-23T09:00:00', 'Seeking feedback on my research proposal', '2023-03-23T10:30:00', 'b', 4),
       ('Hi everyone! I am in my final year of postgraduate studies in the field of marketing, and I am currently looking for job opportunities. I am seeking advice on job search strategies, and any tips that have worked for you in the past. Thank you in advance for your help!',
        '2023-03-22T16:45:00', 'Job search strategy advice needed', '2023-03-22T18:10:00', 'c', 5),

       ('# GFM

## Autolink literals

www.example.com, https://example.com, and contact@example.com.

## Strikethrough

~one~ or ~~two~~ tildes.

## Table

| a | b  |  c |  d  |
| - | :- | -: | :-: |

## Tasklist

* [ ] to do
* [x] done', Date(now()), 'My github flavored markdown!', Date(now()), 'DW6j9KGRlFOs4Hyy4vFqAzHpGFs1', 4);

INSERT INTO person_gang (person_id, gang_id)
VALUES ('DW6j9KGRlFOs4Hyy4vFqAzHpGFs1', 3),
       ('DW6j9KGRlFOs4Hyy4vFqAzHpGFs1', 2),
       ('b', 2),
       ('b', 3),
       ('b', 1),
       ('c', 1),
       ('c', 2);

INSERT INTO post(body, published_on, title, updated_on, reply_parent, sender_person_id, target_gang_gang_id)
VALUES ('Hello fellow archers', '2023-03-02 12:33:10.000000', 'Hi!', '2023-03-02 12:33:10.000000', 1, 'b', 1);

INSERT INTO topic (topic_description, topic_name)
VALUES ('For those who love technology', 'Technology'),
       ('For those who love running, walking and being in nature', 'Nature'),
       ('For those who love gaming', 'Gaming'),
       ('Whether you''re looking for career advice, tips on networking, or information on industry trends, ' ||
        'this topic is for you. Connect with other postgraduates who are interested in professional development and ' ||
        'share your knowledge and experience.',
        'Professional Development'),
       ('Encourage members to share their research proposals and seek feedback from other postgraduates, ' ||
        'creating a collaborative environment for improving research skills.',
        'Research Proposals');

INSERT INTO event (allow_guests, end_time, event_description, event_name, start_time, time_created,
                   time_updated, creator_id_person_id)
VALUES (true, '2023-03-10 10:25:25.000000', 'Come and game with the creator of RUST', 'Gaming with RUST',
        '2023-03-07 10:25:25.000000', '2023-03-03 10:25:25.000000', '2023-03-03 10:30:25.000000',
        'DW6j9KGRlFOs4Hyy4vFqAzHpGFs1'),
       (true, '2023-03-10 10:25:25.000000', 'Walk in nature with nature lovers', 'Nature walk',
        '2023-03-07 10:25:25.000000', '2023-03-03 10:25:25.000000', '2023-03-03 10:30:25.000000', 'b'),
       (true, '2023-03-10 10:25:25.000000', 'Programming videogames', 'Programming video games',
        '2023-03-07 10:25:25.000000', '2023-03-03 10:25:25.000000', '2023-03-03 10:30:25.000000', 'c'),
       (true, '2023-03-10 10:25:25.000000', 'Test Event Yeah Baby', 'We Test Stuff',
        '2023-03-07 10:25:25.000000', '2023-03-03 10:25:25.000000', '2023-03-03 10:30:25.000000', 'b');

INSERT INTO post(body, published_on, title, updated_on, sender_person_id, target_event_event_id)
VALUES ('Developing games with #RUST is a game-changer! Its high-performance and memory safety make it the perfect language for creating fast and secure gaming experiences.', '2023-03-02 12:33:10.000000', 'RUST is great!', '2023-03-02 12:33:10.000000', 'b', 1);

INSERT INTO person_topic
VALUES ('DW6j9KGRlFOs4Hyy4vFqAzHpGFs1', 1),
       ('DW6j9KGRlFOs4Hyy4vFqAzHpGFs1', 3),
       ('b', 2),
       ('b', 1);

INSERT INTO person_event
VALUES ('DW6j9KGRlFOs4Hyy4vFqAzHpGFs1', 1),
       ('b', 2),
       ('DW6j9KGRlFOs4Hyy4vFqAzHpGFs1', 4);

INSERT INTO topic_post
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (7, 1),
       (7, 2),
       (4, 5),
       (6, 1);

INSERT INTO event_gang
VALUES (1, 1),
       (2, 3);

INSERT INTO event_topic
VALUES (1, 1),
       (2, 2);

INSERT INTO event_user_invite
VALUES (1, 'b'),
       (2, 'c');

/* Inserts for DM seeding */
INSERT INTO post(title, body, updated_on, published_on, sender_person_id, target_person_person_id)
VALUES ('My first DM', 'Hi Peter Pan. This is my first PM to you. How are you this afternoon? Hope to see you soon',
        Date(now()), Date(now()), 'b', 'DW6j9KGRlFOs4Hyy4vFqAzHpGFs1'),
       ('Hi Kari', 'Hi Kari. So nice to hear from you. I"m fine, and I hope you"re too. Can we meet this weekend?',
        Date(now()), Date(now()), 'DW6j9KGRlFOs4Hyy4vFqAzHpGFs1', 'b'),
       ('Peter', 'Hi Peter Pan, this is an important message for you. Your car"s extended warranty has run out. ' ||
                 'And you need to update this. Please give me a call on +1 555-555-333',
        Date(now()), Date(now()), 'c', 'DW6j9KGRlFOs4Hyy4vFqAzHpGFs1'),
       ('Important notice!', 'This is John from Microsoft. I see that you recently got a virus on your computer. ' ||
                             'Call me on +1 555-555-032 to get help',
        Date(now()), Date(now()), 'c', 'DW6j9KGRlFOs4Hyy4vFqAzHpGFs1');