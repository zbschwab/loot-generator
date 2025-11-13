package edu.grinnell.csc207.lootgenerator;

import java.util.ArrayList;

public class LootGenerator {
    /** The path to the dataset (either the small or large set). */
    private static final String DATA_SET = "data/small";

    // pre-loaded objects
    public class Monster {
        public String monsterClass;
        public String type;
        public String level;
        public String tc;

        public Monster(String monsterClass, String tc) {
            this.monsterClass = monsterClass;
            this.type = "Cow";
            this.level = "90";
            this.tc = tc;
        }
    }

    public class TreasureClass {
        String name;
        ArrayList<String> items;

        public TreasureClass(String name, ArrayList<String> items) {
            this.name = name;
            this.items = items;
        }
    }

    public class Prefix {
        String name;
        String mod1code;
        int mod1min;
        int mod1max;

        public Prefix(String name, String mod1code, int mod1min, int mod1max) {
            this.name = name;
            this.mod1code = mod1code;
            this.mod1min = mod1min;
            this.mod1max = mod1max;
        }
    }

    public class Suffix {
        String name;
        String mod1code;
        int mod1min;
        int mod1max;

        public Suffix(String name, String mod1code, int mod1min, int mod1max) {
            this.name = name;
            this.mod1code = mod1code;
            this.mod1min = mod1min;
            this.mod1max = mod1max;
        }
    }

    public class BaseItem {
        String name;
        int minac;
        int maxac;

        public BaseItem(String name, int minac, int maxac) {
            this.name = name;
            this.minac = minac;
            this.maxac = maxac;
        }
    }

    // generated objects
    public class Item {
        BaseItem base;
        int defense;
        Prefix prefix;
        Suffix suffix;

        public Item(BaseItem base, int defense, Prefix prefix, Suffix suffix) {
            this.base = base; 
            this.defense = defense;
            this.prefix = prefix;
            this.suffix = suffix;
        }
    }

    public static void main(String[] args) {
        System.out.println("This program kills monsters and generates loot!");
        // TOOD: Implement me!
    }
}
