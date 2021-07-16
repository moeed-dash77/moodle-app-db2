--Step 4). AFTER IMPORTING BOTH "sql-create-tables.sql" and "sql-trigger.sql" FILES, IMPORT THIS FILE WITH COMMAND ON TERMINAL/POWERSHELL: db2 -vtf "sql-dummy-data.sql"


INSERT INTO benutzer (email, name) VALUES
	('dummy@dummy.com', 'DummyUser'),
	('alan@turing.com', 'Alan Turing'),
	('donald@eKnuth.com', 'DonaldEKnuth'),
	('tim@bernersLee.com', 'Tim Berners Lee'),
	('elon@musk.com', 'Elon Musk'),
	('jeff@bezos.com', 'Jeff Bezos')
;

INSERT INTO kurs (name, beschreibungstext, einschreibeschluessel, freiePlaetze, ersteller)  VALUES
	('Datenbanken', 'Einfuehrung in relationale Datenbanken', 'db2', 50, 5),
	('Information Mining', 'Data Mining Basics', 'im', 30, 6),
	('Information Retrieval', 'IR Basics', 'ir', 20, 5),
	('Information Engineering', 'Daten vs Information vs Wissen', NULL, 60, 4),
	('Deep Learning 101', 'Learn the new AI technologies', NULL, 80, 3),
	('Arts', 'History about Arts', 'arts', 10, 2),
	('Social Sciences', 'Social Sciences for Beginners', NULL, 2, 1)
;

INSERT INTO aufgabe (kid, name, beschreibung)  VALUES
	(1, 'ER-Modellierung', 'Was ist Unterschied zwischen Entität und Relation?'),
	(1, 'Relationale Algebra', 'Was ist Unterschied zwischen Projetion und Selektion?'),
	(1, 'SQL', 'Wofür steht SQL?'),
	(1, 'ACID', 'Was bedeut Atomicity?'),
	(2, 'Naive Bayes', 'Was ist naive Annahme in Naive Bayes?'),
	(2, 'SVM', 'Ist SVM ein linearer Klassifikator?'),
	(3, 'TF-IDF', 'Was macht IDF?'),
	(3, 'K-Means', 'Wie wählt man K aus?')
;
	
INSERT INTO einschreiben (bnummer, kid) VALUES
	(6, 1),
	(6, 3),
	(6, 4),
	(1, 2),
	(1, 3),
	(2, 1),
	(2, 2)
;

INSERT INTO abgabe (abgabetext)  VALUES
	('Relation beschreibt Beziehungen zwischen Entitäten'),
	('Projektion wählt Spalten aus und Selektion wählt Zeilen aus'),
	('Structured Query Language'),
	('Inverse Dokument Häufigkeit von einem Wort'),
	('Am besten mit der Elbow-Methode..'),
	('Irgendwas mit Inverse, oder?'),
	('Trial and Error :)')
;

INSERT INTO einreichen (bnummer, kid, anummer, aid)  VALUES
	(6, 1, 1, 1),
	(6, 1, 2, 2),
	(6, 1, 3, 3),
	(6, 3, 7, 4),
	(6, 3, 8, 5),
	(1, 3, 7, 6),
	(1, 3, 8, 7)
;

INSERT INTO bewerten (bnummer, aid, note, kommentar) VALUES
	(5, 1, 2, 'Und was ist eine Entität?'),
	(5, 2, 1, 'Richtig!'),
	(5, 3, 1, 'Richtig!'),
	(5, 4, 2, 'Stimmt'),
	(5, 5, 3, 'Ja, aber wie genau?'),
	(5, 6, 5, 'Falsch'),
	(5, 7, 4, 'Naja :)'),
	(2, 1, 1, 'Super'),
	(2, 2, 1, 'Korrekt!'),
	(2, 3, 1, 'Bravo!')
;
