package newgame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Random;

public class DatabaseConnection {
    public static void main(String[] args) {
        // データベースのファイルパス
        String url = "jdbc:sqlite:src/db/game.db";

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
                    pstmt.setInt(1, 0); // ここでガチャIDを設定
                    pstmt.setInt(2, randomNumber); // ランダム値を使用

                    try (ResultSet aaaa = pstmt.executeQuery()) {
                        // カラム名を表示
                        ResultSetMetaData rsmd = aaaa.getMetaData();
                        int columnCount = rsmd.getColumnCount();
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.print(rsmd.getColumnName(i) + "\t");
                        }
                        System.out.println();

                        // データを表示
                        while (aaaa.next()) {
                            for (int i = 1; i <= columnCount; i++) {
                                System.out.print(aaaa.getString(i) + "\t");
                            }
                            System.out.println();
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
