DROP TABLE IF EXISTS rugbyPlayers;

CREATE TABLE rugbyPlayers (
  id VARCHAR(30) NOT NULL,
  name VARCHAR(50) NOT NULL,
  height int(5),
  weight int(5),
  rugbyPosition VARCHAR(5),
  PRIMARY KEY(id)
);
