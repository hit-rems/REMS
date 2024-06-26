-- 使用数据库
use rems;
-- 找到当前表的最大id值
SELECT MAX(id) INTO @max_id FROM user;
SET @next_id = IFNULL(@max_id, 0) + 1;
SET @alter_stmt = CONCAT('ALTER TABLE user AUTO_INCREMENT = ', @next_id);
PREPARE stmt FROM @alter_stmt;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;


-- 找到当前表的最大id值
SELECT MAX(id) INTO @max_id FROM book;
SET @next_id = IFNULL(@max_id, 0) + 1;
SET @alter_stmt = CONCAT('ALTER TABLE book AUTO_INCREMENT = ', @next_id);
PREPARE stmt FROM @alter_stmt;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;


-- 找到当前表的最大id值
SELECT MAX(id) INTO @max_id FROM category;
SET @next_id = IFNULL(@max_id, 0) + 1;
SET @alter_stmt = CONCAT('ALTER TABLE category AUTO_INCREMENT = ', @next_id);
PREPARE stmt FROM @alter_stmt;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 找到当前表的最大id值
SELECT MAX(id) INTO @max_id FROM authorization;
SET @next_id = IFNULL(@max_id, 0) + 1;
SET @alter_stmt = CONCAT('ALTER TABLE authorization AUTO_INCREMENT = ', @next_id);
PREPARE stmt FROM @alter_stmt;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
