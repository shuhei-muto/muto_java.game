package newgame.util;

public class Status {
    private int hp;
    private int attack;
    private int defense;
    private int agility;
    private int luck;

    public Status(int hp, int attack, int defense, int agility, int luck) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.agility = agility;
        this.luck = luck;
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

    @Override
    public String toString() {
        return "HP: " + hp + "\n" +
        		"攻撃力: " + attack + "\n" + 
        		"防御力: " + defense + "\n" +
               "回避: " + agility + "\n" +
               "運: " + luck;
    }
}
