# Users schema
 
# --- !Ups
 
CREATE TABLE User (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    api_key varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO User(name, password, email, api_key) VALUES ('ayubu', '14235a12', 'aaron@aaron-m.co.nz', 'testapi');
 
CREATE TABLE Role (
	id bigint(20) NOT NULL AUTO_INCREMENT,
    role_type varchar(255) NOT NULL,
    user_id bigint(20) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO Role(user_id, role_type) SELECT id, 'ADMIN' FROM User WHERE name = 'ayubu';

CREATE TABLE Log (
	id bigint(20) NOT NULL AUTO_INCREMENT,
    user_id bigint(20) NOT NULL,
    log_type varchar(255) NOT NULL,
    body text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Manga (
	id bigint(20) NOT NULL AUTO_INCREMENT,
    user_id bigint(20) NOT NULL,
    added bigint(20) NOT NULL,
    updated bigint(20) NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Chapter (
	id bigint(20) NOT NULL AUTO_INCREMENT,
    user_id bigint(20) NOT NULL,
    manga_id bigint(20) NOT NULL,

    issue varchar(255) NOT NULL,
    url varchar(255) NOT NULL,
    host varchar(255) NOT NULL,
    added bigint(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Post (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    body text NOT NULL,
    postedAt date NOT NULL,
    user_id bigint(20) NOT NULL,
    to_ids tinyblob,
    PRIMARY KEY (id)
);


# --- !Downs

drop table User;
drop table Role;
drop table Log;
drop table Manga;
drop table Chapter;
drop table Post;