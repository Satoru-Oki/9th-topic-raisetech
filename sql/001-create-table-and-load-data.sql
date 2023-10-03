DROP TABLE IF EXISTS rugbyPlayers;

CREATE TABLE rugbyPlayers (
  id int unsigned AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  height int(5),
  weight int(5),
  posi VARCHAR(5),
  PRIMARY KEY(id)
);

INSERT INTO rugbyPlayers (name, height, weight, posi) VALUES ("稲垣啓太", 186, 116, "PR"),
                   ("クレイグ・ミラー", 186, 116, "PR"), ("シオネ・ハラシリ", 180, 120, "PR"),
                   ("堀江翔太", 180, 104, "HO"), ("ワーナー・ディアンズ", 202, 117, "LO"),
                   ("ジャック・コーネルセン", 195, 110, "LO"), ("姫野和樹", 187, 108, "No.8"),
                   ("リーチ・マイケル", 189, 113, "FL"), ("ピーター・ラブスカフニ", 189, 106, "FL"),
                   ("齋藤直人", 165, 73, "SH"), ("松田力也", 181, 92, "SO"),
                   ("ディラン・ライリー", 187, 102, "CTB"), ("ジョネ・ナイカブラ", 177, 95, "WTB"),
                   ("レメキ・ロマノラヴァ", 178, 96, "WTB"), ("セミシ・マシレワ", 184, 93, "WTB"),
                   ("松島幸太郎", 178, 87, "FB");
