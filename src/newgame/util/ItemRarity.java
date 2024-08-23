package newgame.util;

public enum ItemRarity {
    R(0),
    SR(1),
    SSR(2),
    UR(3);

    private final int id;

    private ItemRarity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ItemRarity fromId(int id) {
        for (ItemRarity rarity : ItemRarity.values()) {
            if (rarity.getId() == id) {
                return rarity;
            }
        }
        throw new IllegalArgumentException("Invalid id for ItemRarity: " + id);
    }
}
