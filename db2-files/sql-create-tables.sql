-- SQL CREATE TABLE STATEMENTS WITH COMPLETE SCHEMA
-- THE STATEMENTS WERE EXECUTED ON REMOTE DB SERVER OF IBM DB2 THOUGH POWERSHELL/TERMINAL.

-- Step 1). CONNECT TO DB SERVER WITH GIVEN CREDENTIALS.
-- Step 2). IMPORT THIS FILE INTO THE REMOTE DB SERVER WITH THE COMMAND: db2 -vtf sql-create-tables.sql


CREATE TABLE benutzer (
	bnummer SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  	email VARCHAR(50) NOT NULL,
  	name VARCHAR(50) NOT NULL,
  	PRIMARY KEY (bnummer)
);

CREATE TABLE kurs (
  	kid SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  	name VARCHAR(50) NOT NULL,
  	beschreibungstext CLOB (1M) NOT NULL,
  	einschreibeschluessel VARCHAR(50),
	freiePlaetze SMALLINT NOT NULL CHECK (freiePlaetze BETWEEN 1 AND 100),
  	ersteller SMALLINT NOT NULL,
  	PRIMARY KEY (kid),
  	FOREIGN KEY (ersteller) REFERENCES benutzer(bnummer)
);

CREATE TABLE aufgabe (
  	kid SMALLINT NOT NULL,
	anummer SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY,
 	name VARCHAR(50) NOT NULL,
	beschreibung CLOB (1M) NOT NULL,
  	PRIMARY KEY (kid, anummer),
  	FOREIGN KEY (kid) REFERENCES kurs(kid)
);

CREATE TABLE abgabe (
  	aid SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  	abgabetext CLOB (1M) NOT NULL,
  	PRIMARY KEY (aid)
);

CREATE TABLE einreichen (
	bnummer SMALLINT NOT NULL,
  	kid SMALLINT NOT NULL,
  	anummer SMALLINT NOT NULL,
  	aid SMALLINT NOT NULL,
  	PRIMARY KEY (bnummer, kid, anummer, aid),
	FOREIGN KEY (bnummer) REFERENCES benutzer(bnummer),
	FOREIGN KEY (kid, anummer) REFERENCES aufgabe(kid, anummer),
	FOREIGN KEY (aid) REFERENCES abgabe(aid)
);

CREATE TABLE einschreiben (
  	bnummer SMALLINT NOT NULL,
  	kid SMALLINT NOT NULL,
  	datum timestamp DEFAULT CURRENT TIMESTAMP NOT NULL,
  	PRIMARY KEY (bnummer, kid),
  	FOREIGN KEY (bnummer) REFERENCES benutzer(bnummer),
  	FOREIGN KEY (kid) REFERENCES kurs(kid)
);

CREATE TABLE bewerten (
  	bnummer SMALLINT NOT NULL,
  	aid SMALLINT NOT NULL,
  	note SMALLINT NOT NULL CHECK (note BETWEEN 1 AND 5),
  	kommentar CLOB (1M),
  	PRIMARY KEY (bnummer, aid),
  	FOREIGN KEY (bnummer) REFERENCES benutzer(bnummer),
  	FOREIGN KEY (aid) REFERENCES abgabe(aid)
);
