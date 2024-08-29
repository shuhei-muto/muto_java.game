package newgame.util;

import java.util.EnumMap;
import java.util.Map;

public class EquipmentManager {
    private Map<WeaponType, Status> equipmentMap;

    public EquipmentManager() {
        equipmentMap = new EnumMap<>(WeaponType.class);
        // 初期状態としてすべての装備を未装備に設定
        for (WeaponType type : WeaponType.values()) {
            equipmentMap.put(type, null);
        }
    }

    public void equipItem(WeaponType type, Status newStatus) {
        Status oldStatus = equipmentMap.get(type);
        
          //コンソールに表示する
//        if (oldStatus != null) {
//            System.out.println(type + " を上書き装備します。");
//            System.out.println("以前のステータス: " + oldStatus);
//            System.out.println("新しいステータス: " + newStatus);
//            System.out.println("ステータスの変化: HP " + (newStatus.getHp() - oldStatus.getHp()) +
//                               ", Attack " + (newStatus.getAttack() - oldStatus.getAttack()) +
//                               ", Defense " + (newStatus.getDefense() - oldStatus.getDefense()));
//        } else {
//            System.out.println(type + " を新たに装備します。");
//            System.out.println("新しいステータス: " + newStatus);
//        }

        equipmentMap.put(type, newStatus);
    }

    public void printEquippedItems() {
        System.out.println("現在装備されているアイテム:");
        for (Map.Entry<WeaponType, Status> entry : equipmentMap.entrySet()) {
            if (entry.getValue() != null) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
        }
    }
    
    // 特定のWeaponTypeの装備品を取得するメソッド
    public Status getEquippedItem(WeaponType type) {
        return equipmentMap.get(type);
    }
}
