-- Step 3). AFTER IMPORTING "sql-create-table.sql" FILE, IMPORT THIS FILE INTO REMOTE DB SEVER THROUGH COMMAND: db2 -td@ -v -f sql-triggers.sql

CREATE TRIGGER neueEinschreibung
AFTER INSERT ON kurs
REFERENCING NEW AS neu
FOR EACH ROW MODE DB2SQL
BEGIN ATOMIC
	DECLARE eintritt timestamp;
	SET eintritt = CURRENT_TIMESTAMP;
	INSERT INTO einschreiben(bnummer, kid, datum) VALUES (neu.ersteller, neu.kid, eintritt);
END@
