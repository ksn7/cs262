--
-- SQL queries for the monopoly database
--
-- @author Kelsey Brouwer (ksn7)
-- @version October 26, 2018
--


-- List of all games ordered by date, most recent first
--SELECT * 
--  FROM Game
--	ORDER BY time DESC;

-- List of all games that happened in the past week
--SELECT *
--  FROM Game
--	WHERE time > now()-interval '7 days';

-- List of all players who have non-NULL names
--SELECT *
--	FROM Player
--	WHERE name IS NOT NULL;

-- List of IDs who have some game score larger than 2000
--SELECT ID 
--	FROM Player, PlayerGame
--	WHERE Player.ID = PlayerGame.playerID
--		AND score > 2000;

-- List of players who have gmail accounts
--SELECT *
--	FROM Player
--	WHERE emailAddress LIKE '%@gmail%';

-- Retrieve all of "The King"'s game scores in decreasing order
--SELECT score
--	FROM Player, PlayerGame
--	WHERE name = 'The King'
--		AND Player.ID = PlayerGame.playerID
--	ORDER BY score DESC;

-- Retrieve the name of the winner of the game played on 2006-06-28 13:20:00
--SELECT name
--	FROM Player, PlayerGame, Game
--	WHERE Player.ID = PlayerGame.playerID
--		AND Game.ID = PlayerGame.gameID
--		AND time = '2006-06-28 13:20:00'
--	ORDER BY score DESC
--	LIMIT 1;

-- 8.2.c: What does P1.ID < P2.ID do in the last example query?
--SELECT P1.name
--FROM Player AS P1, Player AS P2
--WHERE P1.name = P2.name
--  AND P1.ID < P2.ID;
-- This clause prevents sql from comparing a player instance with itself
--   and return the instance thinking that it has found two different 
--   players with the same name

-- 8.2.d: You might want to join a table to itself if foreign keys point to entries
--   in the same table. For example, if a table of employees lists who each person
--   reports to (who is another employee), then you would need to join the table
--   to itself to find the name of the person who they report to, along with other
--   similar information.