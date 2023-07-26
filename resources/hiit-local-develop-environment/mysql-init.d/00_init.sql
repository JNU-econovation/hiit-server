CREATE
    USER 'hiit-local'@'localhost' IDENTIFIED BY 'hiit-local';
CREATE
    USER 'hiit-local'@'%' IDENTIFIED BY 'hiit-local';

GRANT ALL PRIVILEGES ON *.* TO
    'hiit-local'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO
    'hiit-local'@'%';

CREATE
    DATABASE hiit DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE
    DATABASE batch DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;