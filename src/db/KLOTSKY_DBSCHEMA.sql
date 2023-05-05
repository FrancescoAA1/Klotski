CREATE TABLE `DISPOSITION` (
  `disposition_id` integer PRIMARY KEY,
  `schema` varchar(255),
  `original` boolean
);

CREATE TABLE `MATCHES` (
  `match_id` integer PRIMARY KEY,
  `name` varchar(255),
  `last_disposition` integer,
  `score` integer
);

ALTER TABLE `DISPOSITION` ADD FOREIGN KEY (`disposition_id`) REFERENCES `MATCHES` (`last_disposition`);
