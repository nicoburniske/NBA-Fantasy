# NBA-Fantasy

EER Diagram:

![alt text](https://github.com/nicoburniske/NBA-Fantasy/blob/master/examples/EER-Diagram.png)

Login Screen:

![alt text](https://github.com/nicoburniske/NBA-Fantasy/blob/master/examples/Login%20Screen.PNG)

Player View:

![alt text](https://github.com/nicoburniske/NBA-Fantasy/blob/master/examples/Player%20View.PNG)

League View:

![alt text](https://github.com/nicoburniske/NBA-Fantasy/blob/master/examples/LeagueView.PNG)

Example Usage:

![alt text](https://github.com/nicoburniske/NBA-Fantasy/blob/master/examples/usage.gif)

## CRUD OPERATIONS ##

CREATE:

By creating a new user, one creates a new user_team entry in the database.

READ:

There are many different read operation being made.

One example is the "All Current Players" Table

The "All Current Players" table shows all the players who participated in the 2018-2019 NBA season. This is done by reading the data available in the player_season table in the database. In the case that players were traded during the season, players can play for multiple different teams in a given season. This is taken into account when displaying the players, their teams and their statistics.

UPDATE:

By adding the selected player, we are updating the team that the given player is on.

DELETE:

By deleting your account, we are deleting an entry in the user_team table. All players who were on your team are made available. Other users in the league can now add them to their team.
