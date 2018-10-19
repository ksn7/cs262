--
-- This SQL script builds a monopoly database, deleting any pre-existing version.
--
-- @author kvlinden
-- @version Summer, 2015
--

-- Drop previous versions of the tables if they they exist, in reverse order of foreign keys.
DROP TABLE IF EXISTS PropPlayerGame;
DROP TABLE IF EXISTS PlayerGame;
DROP TABLE IF EXISTS Game;
DROP TABLE IF EXISTS Player;
DROP TABLE IF EXISTS Property;

-- Create the schema.
CREATE TABLE Game (
	ID integer PRIMARY KEY, 
	time timestamp
	);

CREATE TABLE Player (
	ID integer PRIMARY KEY, 
	emailAddress varchar(50) NOT NULL,
	name varchar(50)
	);

CREATE TABLE PlayerGame (
	gameID integer REFERENCES Game(ID), 
	playerID integer REFERENCES Player(ID),
	score integer,
	cash integer,
	location varchar(50)
	);
	
CREATE TABLE Property (
	ID integer PRIMARY KEY,
	name varchar(50)
	);
	
CREATE TABLE PropPlayerGame (
	propID integer REFERENCES Property(ID),
	playerID integer REFERENCES Player(ID),
	gameID integer REFERENCES Game(ID),
	houses integer,
	hotels integer
	);
	

-- Allow users to select data from the tables.
GRANT SELECT ON Game TO PUBLIC;
GRANT SELECT ON Player TO PUBLIC;
GRANT SELECT ON PlayerGame TO PUBLIC;
GRANT SELECT ON Property TO PUBLIC;
GRANT SELECT ON PropPlayerGame TO PUBLIC;

-- Add sample records.
INSERT INTO Game VALUES (1, '2006-06-27 08:00:00');
INSERT INTO Game VALUES (2, '2006-06-28 13:20:00');
INSERT INTO Game VALUES (3, '2006-06-29 18:41:00');

INSERT INTO Player(ID, emailAddress) VALUES (1, 'me@calvin.edu');
INSERT INTO Player VALUES (2, 'king@gmail.edu', 'The King');
INSERT INTO Player VALUES (3, 'dog@gmail.edu', 'Dogbreath');

INSERT INTO PlayerGame VALUES (1, 1, 0.00, 0, 'In Jail');
INSERT INTO PlayerGame VALUES (1, 2, 0.00, 0, 'Free Parking');
INSERT INTO PlayerGame VALUES (1, 3, 2350.00, 600, 'Vermont Avenue');
INSERT INTO PlayerGame VALUES (2, 1, 1000.00, 420, 'Baltic Avenue');
INSERT INTO PlayerGame VALUES (2, 2, 0.00, 0, 'Short Line');
INSERT INTO PlayerGame VALUES (2, 3, 500.00, 200, 'Water Works');
INSERT INTO PlayerGame VALUES (3, 2, 0.00, 0, 'Park Place');
INSERT INTO PlayerGame VALUES (3, 3, 5500.00, 870, 'Boardwalk');

INSERT INTO Property VALUES (1, 'Go');

INSERT INTO PropPlayerGame VALUES (1, 2, 1, 0, 0);

SELECT COUNT(*) FROM PropPlayerGame;
SELECT * FROM PlayerGame;