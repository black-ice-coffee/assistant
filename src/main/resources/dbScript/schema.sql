
create schema assistant
  authorization assistant;

grant all on schema assistant to assistant;

-- table: "news"
create table "news"
(
  "url" text not null,
  "rss_url" text,
  "author" varchar(512),
  "title" varchar(1024),
  "content" text,
  "published_date" timestamp DEFAULT now(),
  constraint "news_pkey" primary key ("url" )
)
with (
oids=false
);
alter table "news"
  owner to assistant;

-- table: "rss"
create table "rss"
(
  "url" text not null,
  "title" varchar(1024),
  constraint "rss_pkey" primary key ("url" )
)
with (
oids=false
);
alter table "rss"
  owner to assistant;

-- table: "rss_group"
create table "rss_group"
(
  "id" char(36) not null,
  "title" varchar(1024),
  constraint "rss_group_pkey" primary key ("id" )
)
with (
oids=false
);
alter table "rss_group"
  owner to assistant;

-- table: "rss_group_item"
create table "rss_group_item"
(
  "id" char(36) not null,
  "url" text not null,
  "title" varchar(1024),
  constraint "rss_group_item_pkey" primary key ("id", "url" )
)
with (
oids=false
);
alter table "rss_group_item"
  owner to assistant;