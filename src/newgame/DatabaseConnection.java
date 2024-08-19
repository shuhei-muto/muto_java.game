//package newgame;
//
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Random;
//
//
////ガチャの結果を表すクラス
//public static class GachaResult {
//    private int itemId;
//    private String type;
//    private String rarity;
//    private String itemName;
//    private int hp;
//    private int attack;
//    private int defense;
//    private int agility;
//    private int luck;
//    private int probability;
//    private int price;
//
//    public GachaResult(int itemId, String type, String rarity, String itemName,
//                       int hp, int attack, int defense, int agility, int luck,
//                       int probability, int price) {
//        this.itemId = itemId;
//        this.type = type;
//        this.rarity = rarity;
//        this.itemName = itemName;
//        this.hp = hp;
//        this.attack = attack;
//        this.defense = defense;
//        this.agility = agility;
//        this.luck = luck;
//        this.probability = probability;
//        this.price = price;
//    }
//
//    // Getterメソッド
//    public int getItemId() {
//        return itemId;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public String getRarity() {
//        return rarity;
//    }
//
//    public String getItemName() {
//        return itemName;
//    }
//
//    public int getHp() {
//        return hp;
//    }
//
//    public int getAttack() {
//        return attack;
//    }
//
//    public int getDefense() {
//        return defense;
//    }
//
//    public int getAgility() {
//        return agility;
//    }
//
//    public int getLuck() {
//        return luck;
//    }
//
//    public int getProbability() {
//        return probability;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    @Override
//    public String toString() {
//        return "Item ID: " + itemId + "\n" +
//               "Type: " + type + "\n" +
//               "Rarity: " + rarity + "\n" +
//               "Name: " + itemName + "\n" +
//               "HP: " + hp + "\n" +
//               "Attack: " + attack + "\n" +
//               "Defense: " + defense + "\n" +
//               "Agility: " + agility + "\n" +
//               "Luck: " + luck + "\n" +
//               "Probability: " + probability + "\n" +
//               "Price: " + price;
//    }
//
//
///**
// * ガチャを引く処理
// * @param gachaNum ガチャの種類 0:銅, 1:銀, 2:金
// * @return ガチャの結果を返す。結果がない場合はnull。
// */
//public static GachaResult gacha(String gachaNum) { 
//    String url = "jdbc:sqlite:src/db/game.db";
//    GachaResult result = null;
//    
//    try {
//        // SQLite JDBCドライバをロード
//        Class.forName("org.sqlite.JDBC");
//        // データベースに接続
//        try (Connection conn = DriverManager.getConnection(url)) {
//            System.out.println("SQLiteデータベースに接続しました。");
//            // ブロンズガチャを引いた時
//            Random random = new Random();
//            int randomNumber = random.nextInt(100) + 1;
//            String query = "SELECT * FROM gacha "
//                         + "LEFT JOIN item ON item.item_id = gacha.item_id "
//                         + "WHERE gacha.id = ? AND gacha.probability >= ? "
//                         + "ORDER BY gacha.probability ASC LIMIT 1;";
//    
//            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//                pstmt.setInt(1, Integer.parseInt(gachaNum)); // ガチャIDを設定
//                pstmt.setInt(2, randomNumber); // ランダム値を使用
//                
//                try (ResultSet rs = pstmt.executeQuery()) {
//                    if (rs.next()) {
//                        int itemId = rs.getInt("item_id");
//                        String type = rs.getString("type");
//                        String rarity = rs.getString("rarity");
//                        String itemName = rs.getString("item_name");
//                        int hp = rs.getInt("hp");
//                        int attack = rs.getInt("attack");
//                        int defense = rs.getInt("defense");
//                        int agility = rs.getInt("agility");
//                        int luck = rs.getInt("luck");
//                        int probability = rs.getInt("probability");
//                        int price = rs.getInt("price");
//
//                        // GachaResult オブジェクトを作成
//                        result = new GachaResult(itemId, type, rarity, itemName, hp, attack, 
//                                                 defense, agility, luck, probability, price);
//                    }
//                }
//            }
//        } 
//    	}catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//    return result;
//}
///* 単体確認用のmainメソッド,
// * 確認のために金銀銅全て記載
// * 
// */
//public static void main(String[] args) {
//    System.out.println("銅ガチャを引いています...");
//    // 例: 銅ガチャ（ガチャ種類 0）を引く
//    GachaResult result0 = gacha("0");
//    if (result0 != null) {
//    	//result0のtoString()メソッドを呼ぶ
//        System.out.println("銅ガチャの結果: \n"  + result0);
//    } else {
//        System.out.println("何も当たりませんでした。");
//    }
//
//    System.out.println("");
//
//    System.out.println("銀ガチャを引いています...");
//    // 例: 銀ガチャ（ガチャ種類 1）を引く
//    GachaResult result1 = gacha("1");
//    if (result1 != null) {
//    	//result1のtoString()メソッドを呼ぶ
//        System.out.println("銀ガチャの結果: \n" + result1);
//    } else {
//        System.out.println("何も当たりませんでした。");
//    }
//    System.out.println("");
//
//    System.out.println("金ガチャを引いています...");
//    // 例: 金ガチャ（ガチャ種類 2）を引く
//    GachaResult result2 = gacha("2");
//    if (result2 != null) {
//    	//result2のtoString()メソッドを呼ぶ
//        System.out.println("金ガチャの結果: \n" + result2);
//    } else {
//        System.out.println("何も当たりませんでした。");
//    }
//
//
//}
//}
//                