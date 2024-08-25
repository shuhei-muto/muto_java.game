package newgame.util;

import java.util.EnumMap;
import java.util.Map;
/*
 * 装備状態の管理をするクラス
 */
public class EquipmentManager {
    private Map<WeaponType, Boolean> equipmentMap;

    public EquipmentManager() {
        equipmentMap = new EnumMap<>(WeaponType.class);
        // 初期状態としてすべての装備を未装備に設定
        for (WeaponType type : WeaponType.values()) {
            equipmentMap.put(type, false);
        }
    }

    public void equipItem(WeaponType type) {
        if (!equipmentMap.get(type)) {
            equipmentMap.put(type, true);
            System.out.println(type + " を装備しました。");
        } else {
            System.out.println(type + " はすでに装備されています。");
        }
    }

    public void unequipItem(WeaponType type) {
        if (equipmentMap.get(type)) {
            equipmentMap.put(type, false);
            System.out.println(type + " を外しました。");
        } else {
            System.out.println(type + " は装備されていません。");
        }
    }

    public void printEquippedItems() {
        System.out.println("現在装備されているアイテム:");
        for (Map.Entry<WeaponType, Boolean> entry : equipmentMap.entrySet()) {
            if (entry.getValue()) {
                System.out.println(entry.getKey());
            }
        }
    }
}
