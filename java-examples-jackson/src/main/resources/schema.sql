-- Java Examples | Jackson

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id BIGINT(19) NOT NULL AUTO_INCREMENT,
  email VARCHAR(255) NOT NULL DEFAULT '',
  password_salt CHAR(4) NOT NULL DEFAULT '',
    -- Salt for password
  password_hash CHAR(40) NOT NULL DEFAULT '',
    -- SHA-1 (salt + password)
  nickname VARCHAR(255) NOT NULL DEFAULT '',
  deleted TINYINT(1) NOT NULL DEFAULT 0,
    -- 0: No, 1: Yes
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  PRIMARY KEY (id),
  KEY (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS articles;
CREATE TABLE articles (
  id BIGINT(19) NOT NULL AUTO_INCREMENT,
  author_id BIGINT(19) NOT NULL DEFAULT 0,
    -- Key to look up users
  title VARCHAR(255) NOT NULL DEFAULT '',
  body TEXT,
  status TINYINT(1) NOT NULL DEFAULT 0,
    -- 0: Draft, 1: Published
  deleted TINYINT(1) NOT NULL DEFAULT 0,
    -- 0: No, 1: Yes
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  PRIMARY KEY (id),
  KEY (author_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

