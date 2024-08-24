package newgame.util;

public enum WeaponType {
	武器(0),
    鎧(1),
    盾(2),
    ブーツ(3),
    アクセサリー(4),
    永続ステ上げアイテム(5),
    回復アイテム(6),
    進化アイテム(7);

    private final int id;

    private WeaponType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static WeaponType fromId(int id) {
        for (WeaponType type : WeaponType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid id for WeaponType: " + id);
    }
}
