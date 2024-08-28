package newgame.util;

public class Status {
    private int hp;
    private int attack;
    private int defense;
    private int agility;
    private int luck;
    private String itemName;
    private int statusUpCount;//ステータスアップアイテム
    private int potionCount;//回復薬の数　∞️
    private int evolutionCount;//進化回数　0〜2

    public Status(int hp, int attack, int defense, int agility, int luck) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.agility = agility;
        this.luck = luck;
    }
    public Status(int hp, int attack, int defense, int agility, int luck, int statusUpCount, int potionCount, int evolutionCount) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.agility = agility;
        this.luck = luck;
        this.statusUpCount = statusUpCount;
        this.potionCount =  potionCount;
        this.evolutionCount = evolutionCount;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }
    
    public String getItemName() {
    	return this.itemName;
    }
    
    public void setItemName(String itemName) {
    	this.itemName = itemName;
    }
    public int getStatusUPCount() {
    	return this.statusUpCount;
    }
    public void setStatusUpCount(int statusUpCount) {
    	this.statusUpCount = statusUpCount;
    }
    
    public int getPotionCount() {
    	return this.potionCount;
    }
    public void setPotionCount(int potionCount) {
    	this.potionCount = potionCount;
    }
    
    public int getEvolitionCount() {
    	return this.evolutionCount;
    }
    public void setevolutionCount(int evolutionCount) {
    	this.evolutionCount = evolutionCount;
    }

    @Override
    public String toString() {
        return "HP: " + hp + "\n" +
        		"攻撃力: " + attack + "\n" + 
        		"防御力: " + defense + "\n" +
               "回避: " + agility + "\n" +
               "運: " + luck;
    }
}


