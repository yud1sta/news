CREATE TABLE IF NOT EXISTS `topic` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(255),
    `description` varchar(255),
    `create_time` datetime,
    `update_time` datetime,
    `delete_time` datetime,
    `is_deleted` bit
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `tags` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(255),
    `description` varchar(255),
    `create_time` datetime,
    `update_time` datetime,
    `delete_time` datetime,
    `is_deleted` bit
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `news` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `content` text,
    `topic_id` INT NOT NULL,
    `status` ENUM('draft', 'deleted', 'publish'),
    `create_time` datetime,
    `update_time` datetime,
    `delete_time` datetime,
    `is_deleted` bit,
    FOREIGN KEY(topic_id) REFERENCES topic(id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `news_tags` (
    `news_id` INT NOT NULL,
    `tags_id` INT NOT NULL ,
    FOREIGN KEY(news_id) REFERENCES news(id),
    FOREIGN KEY(tags_id) REFERENCES tags(id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;