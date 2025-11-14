package edu.grinnell.csc207.lootgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

// import edu.grinnell.csc207.lootgenerator.LootGenerator.TreasureClass;

public class LootGenerator {
    /** The path to the dataset (either the small or large set). */
    private static final String DATA_SET = "data/small";

    // pre-loaded objects
    public static class Monster {
        public String monsterClass;
        public String type;
        public String level;
        public String tc;

        public Monster(String monsterClass, String type, String level, String tc) {
            this.monsterClass = monsterClass;
            this.type = type;
            this.level = level;
            this.tc = tc;
        }

        @Override
        public String toString() { // TEST
            return monsterClass + ", " + type + ", " + level + ", " + tc;
        }
    }

    public static class TreasureClass {
        String name;
        String[] items;

        public TreasureClass(String name, String[] items) {
            this.name = name;
            this.items = items;
        }

        @Override
        public String toString() { // TEST
            return name + ", " + items[0] + ", " + items[1] + ", " + items[2] + "\n";
        }
    }

    public static class BaseItem {
        String name;
        int minac;
        int maxac;
        int defense;

        public BaseItem(String name, int minac, int maxac) {
            this.name = name;
            this.minac = minac;
            this.maxac = maxac;
            this.defense = 0;
        }

        @Override
        public String toString() { // TEST
            return name + ", " + minac + ", " + maxac + "\n";
        }
    }

    public static class Item {
        String name;
        BaseItem baseItem;
        String modcode;
        int modval;
        Prefix prefix;
        Suffix suffix;

        public Item(BaseItem baseItem) {
            this.name = baseItem.name;
            this.baseItem = baseItem;
            this.modcode = null;
            this.modval = 0;
            this.prefix = null;
            this.suffix = null;
        }
    }

    public static class Prefix {
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

    public static class Suffix {
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

    private final ArrayList<Monster> monsters;
    private final HashMap<String, TreasureClass> TCmap;
    private final HashMap<String, BaseItem> baseMap;
    private final ArrayList<Prefix> prefixes;
    private final ArrayList<Suffix> suffixes;

    /**
     * load all game data sets
     * 
     * @throws FileNotFoundException if any file is missing
     */
    public LootGenerator() throws FileNotFoundException {
        monsters = loadMonsters(DATA_SET + "/monstats.txt");
        TCmap = loadTCs(DATA_SET + "/TreasureClassEx.txt");
        baseMap = loadBaseMap(DATA_SET + "/armor.txt");
        prefixes = loadPrefixes(DATA_SET + "/MagicPrefix.txt");
        suffixes = loadSuffixes(DATA_SET + "/MagicSuffix.txt");
    }

    public Monster pickMonster(ArrayList<Monster> monsters) {
        return monsters.get(rand.nextInt(monsters.size()));
    }

    private final Random rand = new Random();

    /**
     * load monsters from tab-delimited file
     * 
     * @param filePath path string to monstats.txt
     * @return list of monsters
     * @throws FileNotFoundException
     */
    public static ArrayList<Monster> loadMonsters(String filePath) throws FileNotFoundException {
        ArrayList<Monster> monsters = new ArrayList<>();
        Scanner s = new Scanner(new File(filePath)).useDelimiter("\t");

        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] row_data = line.split("\t");

            Monster m = new Monster(row_data[0], row_data[1], row_data[2], row_data[3]);
            monsters.add(m);
        }

        s.close();
        return monsters;
    }

    /**
     * load treasure classes from tab-delimited file
     * 
     * @param filePath path string to TreasureClassEx.txt
     * @return map of TC name -> TC
     * @throws FileNotFoundException
     */
    public static HashMap<String, TreasureClass> loadTCs(String filePath) throws FileNotFoundException {
        HashMap<String, TreasureClass> TCmap = new HashMap<>();
        Scanner s = new Scanner(new File(filePath)).useDelimiter("\t");

        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] row_data = line.split("\t");
            String[] item_data = new String[3];
            System.arraycopy(row_data, 1, item_data, 0, 3);

            TreasureClass tc = new TreasureClass(row_data[0], item_data);
            TCmap.put(row_data[0], tc);
        }

        s.close();
        return TCmap;
    }

    /**
     * load monsters from tab-delimited file
     * 
     * @param filePath path string to armor.txt
     * @return map of base item name -> base item
     * @throws FileNotFoundException
     */
    public static HashMap<String, BaseItem> loadBaseMap(String filePath) throws FileNotFoundException {
        HashMap<String, BaseItem> itemMap = new HashMap<>();
        Scanner s = new Scanner(new File(filePath)).useDelimiter("\t");

        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] row_data = line.split("\t");

            BaseItem item = new BaseItem(row_data[0], Integer.parseInt(row_data[1]), Integer.parseInt(row_data[2]));
            itemMap.put(row_data[0], item);
        }

        s.close();
        return itemMap;
    }

    /**
     * load prefixes from tab-delimited file
     * 
     * @param filePath path string to MagicPrefixes.txt
     * @return list of prefixes
     * @throws FileNotFoundException
     */
    public static ArrayList<Prefix> loadPrefixes(String filePath) throws FileNotFoundException {
        ArrayList<Prefix> prefixes = new ArrayList<>();
        Scanner s = new Scanner(new File(filePath)).useDelimiter("\t");

        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] row_data = line.split("\t");

            Prefix p = new Prefix(row_data[0], row_data[1], Integer.parseInt(row_data[2]),
                    Integer.parseInt(row_data[3]));
            prefixes.add(p);
        }

        s.close();
        return prefixes;
    }

    /**
     * load suffixes from tab-delimited file
     * 
     * @param filePath path string to MagicSuffixes.txt
     * @return list of suffixes
     * @throws FileNotFoundException
     */
    public static ArrayList<Suffix> loadSuffixes(String filePath) throws FileNotFoundException {
        ArrayList<Suffix> suffixes = new ArrayList<>();
        Scanner s = new Scanner(new File(filePath)).useDelimiter("\t");

        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] row_data = line.split("\t");

            Suffix suf = new Suffix(row_data[0], row_data[1], Integer.parseInt(row_data[2]),
                    Integer.parseInt(row_data[3]));
            suffixes.add(suf);
        }

        s.close();
        return suffixes;
    }

    /**
     * looks up TC associated with monster until base item found
     * 
     * @param monster
     * @param TCmap   treasure class lookup map
     * @param baseMap base item lookup map
     * @return selected base item
     */
    public BaseItem getTC(Monster monster, HashMap<String, TreasureClass> TCmap, HashMap<String, BaseItem> baseMap) {
        TreasureClass tc = TCmap.get(monster.tc);
        String tc_name = tc.items[rand.nextInt(tc.items.length)];

        return getTCHelper(tc_name, TCmap, baseMap);
    }

    /**
     * recursively look up treasure classes until base item found
     * 
     * @param tc_name initial TC name
     * @param TCmap   treasure class lookup map
     * @param baseMap base item lookup map
     * @return selected base item
     */
    public BaseItem getTCHelper(String tc_name, HashMap<String, TreasureClass> TCmap,
            HashMap<String, BaseItem> baseMap) {
        if (baseMap.containsKey(tc_name)) {
            return baseMap.get(tc_name);
        } else {
            TreasureClass tc = TCmap.get(tc_name);
            String tc_next = tc.items[rand.nextInt(tc.items.length)];
            return getTCHelper(tc_next, TCmap, baseMap);
        }
    }

    /**
     * randomly pick a defense value between base item's min and max
     * 
     * @param b base item to calculate defense for
     * @return modified base item
     */
    public BaseItem getBaseStats(BaseItem b) {
        int pts = rand.nextInt(b.minac, b.maxac + 1);
        b.defense += pts;
        return b;
    }

    /**
     * randomly pick a random stat modifier value between min and max
     * 
     * @param min
     * @param max
     * @return new stat value
     */
    public int modStats(int min, int max) {
        int mod = rand.nextInt(min, max + 1);
        return min + mod;
    }

    /**
     * apply random affix. 50% of selecting prefix and suffix
     * two 50/50 independent events --> 0.25 chance of each outcome
     * 
     * @param item item to modify
     */
    public void getAffix(Item item) {
        int r = rand.nextInt(4);

        if (r == 1 || r == 3) {
            int pr = rand.nextInt(prefixes.size());
            item.prefix = prefixes.get(pr);
            item.name = item.prefix.name + " " + item.name;
            item.modcode = item.prefix.mod1code;
            item.modval = modStats(item.prefix.mod1min, item.prefix.mod1min);
        }
        if (r == 2 || r == 3) {
            int sr = rand.nextInt(suffixes.size());
            item.suffix = suffixes.get(sr);
            item.name += " " + item.suffix.name;
            item.modcode = item.suffix.mod1code;
            item.modval = modStats(item.suffix.mod1min, item.suffix.mod1min);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        LootGenerator gen = new LootGenerator();
        Scanner s = new Scanner(System.in);

        boolean running = true;
        while (running) {
            Monster monster = gen.pickMonster(gen.monsters);

            System.out.println("\nFighting " + monster.monsterClass);
            System.out.println("You have slain " + monster.monsterClass + "!");
            System.out.println(monster.monsterClass + " dropped: \n");

            BaseItem baseItem = gen.getTC(monster, gen.TCmap, gen.baseMap);
            baseItem = gen.getBaseStats(baseItem);

            Item modItem = new Item(baseItem);
            gen.getAffix(modItem);

            System.out.println(modItem.name);
            System.out.println("Defense: " + baseItem.defense);
            if (modItem.modval != 0) {
                System.out.println(modItem.modval + " " + modItem.modcode);
            }

            System.out.print("\nFight again [y/n]? ");
            while (true) {
                String input = s.next();
                if ("y".equalsIgnoreCase(input)) {
                    break;
                } else if ("n".equalsIgnoreCase(input)) {
                    running = false;
                    break;
                } else {
                    System.out.print("Fight again [y/n]? ");
                }
            }
        }

        s.close();
    }
}
