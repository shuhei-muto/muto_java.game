
package newgame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class DatabaseConnection {

    // ガチャの結果を表すクラス
	// 内部クラスではなく独立のクラスにするのが理想
    public static class GachaResult {
    	/* DBとクラスで変数の型に相違があります。type,rarityはDBではint */
        private int itemId;
        private String type;
        private String rarity;
        private String itemName;
        private int hp;
        private int attack;
        private int defense;
        private int agility;
        private int luck;
        private int probability;
        private int price;

        public GachaResult(int itemId, String type, String rarity, String itemName,
                           int hp, int attack, int defense, int agility, int luck,
                           int probability, int price) {
            this.itemId = itemId;
            this.type = type;
            this.rarity = rarity;
            this.itemName = itemName;
            this.hp = hp;
            this.attack = attack;
            this.defense = defense;
            this.agility = agility;
            this.luck = luck;
            this.probability = probability;
            this.price = price;
        }

        // Getterメソッド
        // それぞれの値を取得できる
        public int getItemId() {
            return itemId;
        }

        public String getType() {
            return type;
        }

        public String getRarity() {
            return rarity;
        }

        public String getItemName() {
            return itemName;
        }

        public int getHp() {
            return hp;
        }

        public int getAttack() {
            return attack;
        }

        public int getDefense() {
            return defense;
        }

        public int getAgility() {
            return agility;
        }

        public int getLuck() {
            return luck;
        }

        public int getProbability() {
            return probability;
        }

        public int getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return "Item ID: " + itemId + "\n" +
                   "Type: " + type + "\n" +
                   "Rarity: " + rarity + "\n" +
                   "Name: " + itemName + "\n" +
                   "HP: " + hp + "\n" +
                   "Attack: " + attack + "\n" +
                   "Defense: " + defense + "\n" +
                   "Agility: " + agility + "\n" +
                   "Luck: " + luck + "\n" +
                   "Probability: " + probability + "\n" +
                   "Price: " + price;
        }
    }

    // キャラクター名の結果を表すクラス
    // 内部クラスではなく、独立したクラスにするにが理想
    public static class CharanameResult {
        private String name;
        private int money;

        public CharanameResult(String name, int money) {
            this.name = name;
            this.money = money;
        }

        // Getterメソッド
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        @Override
        public String toString() {
            return "CharanameResult{" +
                   "name='" + name + '\'' +
                   ", money=" + money +
                   '}';
        }
    }

    /**
     * ガチャを引く処理
     * @param gachaNum ガチャの種類 0:銅, 1:銀, 2:金
     * @return ガチャの結果を返す。結果がない場合はnull。
     */
    public static GachaResult gacha(String gachaNum) {
//    	GachaResultは内部クラスのGachaResultクラス
        String url = "jdbc:sqlite:src/db/game.db";
        GachaResult result = null;

        try {
            // SQLite JDBCドライバをロード
            Class.forName("org.sqlite.JDBC");

            // データベースに接続
            try (Connection conn = DriverManager.getConnection(url)) {
                System.out.println("SQLiteデータベースに接続しました。");

                // ブロンズガチャを引いた時
                Random random = new Random();
                int randomNumber = random.nextInt(100) + 1;

                String query = "SELECT * FROM gacha "
                             + "LEFT JOIN item ON item.item_id = gacha.item_id "
                             + "WHERE gacha.id = ? AND gacha.probability >= ? "
                             + "ORDER BY gacha.probability ASC LIMIT 1;";

                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, Integer.parseInt(gachaNum)); // ガチャIDを設定
                    pstmt.setInt(2, randomNumber); // ランダム値を使用

                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            int itemId = rs.getInt("item_id");
                            String type = rs.getString("type");
                            String rarity = rs.getString("rarity");
                            String itemName = rs.getString("item_name");
                            int hp = rs.getInt("hp");
                            int attack = rs.getInt("attack");
                            int defense = rs.getInt("defense");
                            int agility = rs.getInt("agility");
                            int luck = rs.getInt("luck");
                            int probability = rs.getInt("probability");
                            int price = rs.getInt("price");

                            // GachaResult オブジェクトを作成
                            result = new GachaResult(itemId, type, rarity, itemName, hp, attack, 
                                                     defense, agility, luck, probability, price);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * charanameテーブルからランダムに1行を取得するメソッド
     * @return ランダムに取得したCharanameResultオブジェクト。データがない場合はnull。
     */
    public static CharanameResult charaname() {
//    	CharanameResultは内部クラスのCharanameResultクラス
        String url = "jdbc:sqlite:src/db/game.db";
        CharanameResult result = null;

        try {
            // SQLite JDBCドライバをロード
            Class.forName("org.sqlite.JDBC");

            // データベースに接続
            try (Connection conn = DriverManager.getConnection(url)) {
                System.out.println("SQLiteデータベースに接続しました。");

                // charaname テーブルからランダムな1行を取得
                // ORDER BY RANDOM()でランダムに並び替え、LIMIT 1で最初の1件のみ取得
                String query = "SELECT name, money FROM charaname ORDER BY RANDOM() LIMIT 1;";

                try (PreparedStatement pstmt = conn.prepareStatement(query);
                     ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("name");
                        int money = rs.getInt("money");

                        // CharanameResult オブジェクトを作成
                        result = new CharanameResult(name, money);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /* 単体確認用のmainメソッド,
     * 確認のために金銀銅全て記載
     * キャラ名も１つ取得して表示するようにした
     * 実際にしようするときはmainクラスは必要ない
     */
//    public static void main(String[] args) {
//        System.out.println("銅ガチャを引いています...");
//        // 例: 銅ガチャ（ガチャ種類 0）を引く
//        String a  = "0";
//        GachaResult result0 = gacha(a);
//        if (result0 != null) {
//            // result0のtoString()メソッドを呼ぶ
//            System.out.println("銅ガチャの結果: \n"  + result0);
//            //.get◯◯◯で値を表示できる。（GachaResultクラスで定義している）
//            System.out.println("指定した項目を表示する場合：" + result0.getItemName());
//        } else {
//            System.out.println("何も当たりませんでした。");
//        }
//
//
//        System.out.println("");
//
//        System.out.println("ランダムキャラクターを取得しています...");
//        // charanameテーブルからランダムな1行を取得
//        CharanameResult charanameResult = charaname();
//        if (charanameResult != null) {
//            // charanameResultのtoString()メソッドを呼ぶ
//            System.out.println("ランダムキャラクターの結果: \n" + charanameResult);
//        } else {
//            System.out.println("データの取得に失敗しました。");
//        }
//        //getName getMoneyで名前,所持金を表示できる。
//        System.out.println(charanameResult.getName());
//        System.out.println(charanameResult.getMoney());
//    }
}

