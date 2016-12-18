
create schema if not EXISTS assistant
  authorization assistant;

grant all on schema assistant to assistant;

CREATE TABLE IF NOT EXISTS news
(
  url TEXT PRIMARY KEY NOT NULL,
  rss_url TEXT,
  author VARCHAR(512),
  title VARCHAR(1024),
  content TEXT,
  published_date TIMESTAMP DEFAULT now(),
  summary TEXT
);
CREATE TABLE IF NOT EXISTS  rss
(
  url TEXT PRIMARY KEY NOT NULL,
  title VARCHAR(1024),
  last_updated TIMESTAMP
);
CREATE TABLE IF NOT EXISTS  rss_group
(
  id CHAR(36) PRIMARY KEY NOT NULL,
  title VARCHAR(1024)
);
CREATE TABLE IF NOT EXISTS rss_group_item
(
  id CHAR(36) NOT NULL,
  url TEXT NOT NULL,
  title VARCHAR(1024),
  CONSTRAINT rss_group_item_pkey PRIMARY KEY (id, url)
);

CREATE TABLE IF NOT EXISTS note
(
  id CHAR(36) PRIMARY KEY NOT NULL,
  title TEXT,
  content TEXT,
  created_date TIMESTAMP DEFAULT now(),
  modified_date TIMESTAMP DEFAULT now()
);

alter table "news" owner to assistant;
alter table "rss" owner to assistant;
alter table "rss_group" owner to assistant;
alter table "rss_group_item" owner to assistant;
alter table "note" owner to assistant;