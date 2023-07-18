CREATE TABLE HOTEL (
   ID BIGINT NOT NULL AUTO_INCREMENT,
   NAME VARCHAR(255) NOT NULL,
   ROOMS_AVAILABLE INT NOT NULL,
   MAX_CAPACITY INT NOT NULL,
   LOCATION VARCHAR(255) NOT NULL,
   PRIMARY KEY (ID)
);

insert into HOTEL (NAME, LOCATION, MAX_CAPACITY, ROOMS_AVAILABLE) values('prince', 'yotsuya', 1000, 10);
insert into HOTEL (NAME, LOCATION, MAX_CAPACITY, ROOMS_AVAILABLE) values('park hyatt', 'shinjuku', 1000, 900);
insert into HOTEL (NAME, LOCATION, MAX_CAPACITY, ROOMS_AVAILABLE) values('hilton', 'shibuya', 500, 100);
insert into HOTEL (NAME, LOCATION, MAX_CAPACITY, ROOMS_AVAILABLE) values('conrad', 'ginza', 1000, 1000);
insert into HOTEL (NAME, LOCATION, MAX_CAPACITY, ROOMS_AVAILABLE) values('imperial', 'Roppongi', 1000, 0);

commit;