-- Database: artdb

-- DROP DATABASE IF EXISTS artdb;
-- Таблиця користувачів
CREATE TABLE "User" (
  "id" SERIAL PRIMARY KEY,
  "username" VARCHAR(50) UNIQUE NOT NULL,
  "email" VARCHAR(255) UNIQUE NOT NULL,
  "password_hash" TEXT NOT NULL,
  "bio" TEXT,
  "profile_picture_url" TEXT,
  "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблиця проектів
CREATE TABLE "projects" (
  "id" SERIAL PRIMARY KEY,
  "name" VARCHAR(50) NOT NULL,
  "description" TEXT,
  "media_url" TEXT,
  "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблиця постів
CREATE TABLE "posts" (
  "id" SERIAL PRIMARY KEY,
  "user_id" INTEGER NOT NULL,
  "media_url" TEXT NOT NULL,
  "post_text" TEXT,
  "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  "updated_at" TIMESTAMP
);

-- Таблиця учасників проектів
CREATE TABLE "project_members" (
  "id" SERIAL PRIMARY KEY,
  "project_id" INTEGER NOT NULL,
  "user_id" INTEGER NOT NULL,
  "role" VARCHAR(50) DEFAULT 'member',
  "joined_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE ("project_id", "user_id")
);

-- Таблиця лайків
CREATE TABLE "likes" (
  "id" SERIAL PRIMARY KEY,
  "user_id" INTEGER NOT NULL,
  "post_id" INTEGER NOT NULL,
  UNIQUE ("user_id", "post_id")
);

-- Таблиця тегів
CREATE TABLE "tags" (
  "id" SERIAL PRIMARY KEY,
  "name" VARCHAR(50) UNIQUE NOT NULL
);

-- Таблиця зв'язку постів і тегів
CREATE TABLE "post_tags" (
  "tag_id" INTEGER NOT NULL,
  "post_id" INTEGER NOT NULL,
  PRIMARY KEY ("tag_id", "post_id")
);

-- Таблиця чатів
CREATE TABLE "chats" (
  "id" SERIAL PRIMARY KEY,
  "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблиця учасників чатів
CREATE TABLE "chat_members" (
  "id" SERIAL PRIMARY KEY,
  "chat_id" INTEGER NOT NULL,
  "user_id" INTEGER NOT NULL,
  "joined_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE ("chat_id", "user_id")
);

-- Таблиця повідомлень
CREATE TABLE "messages" (
  "id" SERIAL PRIMARY KEY,
  "chat_id" INTEGER NOT NULL,
  "sender_id" INTEGER NOT NULL,
  "content" TEXT NOT NULL,
  "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблиця коментарів
CREATE TABLE "comments" (
  "id" SERIAL PRIMARY KEY,
  "post_id" INTEGER NOT NULL,
  "user_id" INTEGER NOT NULL,
  "content" TEXT NOT NULL,
  "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблиця активності
CREATE TABLE "activity_logs" (
  "id" SERIAL PRIMARY KEY,
  "user_id" INTEGER NOT NULL,
  "action" VARCHAR(50) NOT NULL,
  "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Додавання зовнішніх ключів

-- Зв'язок постів з користувачами
ALTER TABLE "posts" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");

-- Зв'язок учасників проектів з користувачами та проектами
ALTER TABLE "project_members" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");
ALTER TABLE "project_members" ADD FOREIGN KEY ("project_id") REFERENCES "projects" ("id");

-- Зв'язок лайків з користувачами та постами
ALTER TABLE "likes" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");
ALTER TABLE "likes" ADD FOREIGN KEY ("post_id") REFERENCES "posts" ("id");

-- Зв'язок тегів постів з тегами та постами
ALTER TABLE "post_tags" ADD FOREIGN KEY ("tag_id") REFERENCES "tags" ("id");
ALTER TABLE "post_tags" ADD FOREIGN KEY ("post_id") REFERENCES "posts" ("id");

-- Зв'язок учасників чатів з чатами та користувачами
ALTER TABLE "chat_members" ADD FOREIGN KEY ("chat_id") REFERENCES "chats" ("id");
ALTER TABLE "chat_members" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");

-- Зв'язок повідомлень з чатами та відправниками
ALTER TABLE "messages" ADD FOREIGN KEY ("chat_id") REFERENCES "chats" ("id");
ALTER TABLE "messages" ADD FOREIGN KEY ("sender_id") REFERENCES "User" ("id");

-- Зв'язок коментарів з постами та користувачами
ALTER TABLE "comments" ADD FOREIGN KEY ("post_id") REFERENCES "posts" ("id");
ALTER TABLE "comments" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");

-- Зв'язок логів активності з користувачами
ALTER TABLE "activity_logs" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");