SELECT * FROM uncommonhacksdb.songs;
SELECT * FROM uncommonhacksdb.fingerprints;

TRUNCATE table uncommonhacksdb.fingerprints;

SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE table uncommonhacksdb.songs;
SET FOREIGN_KEY_CHECKS = 1;