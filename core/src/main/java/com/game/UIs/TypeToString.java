package com.game.UIs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;

public class TypeToString {

    private static ObjectMap<String, String> stringMap = new ObjectMap<>();
    private static boolean initialized = false;

// File format: Each line should be "ENUM_NAME=String Value"

    public static void init(String filePath) {

        try {
            FileHandle file = Gdx.files.internal(filePath);
            String content = file.readString();
            String[] lines = content.split("\n");

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Skip empty lines and comments
                }

                String[] parts = line.split("=", 2);

                if (parts.length == 2) {
                    stringMap.put(parts[0].trim(), parts[1].trim());
                }
            }
            System.out.println(stringMap);
            initialized = true;
        } catch (Exception e) {
            Gdx.app.error("TypeToString", "Error loading strings: " + e.getMessage());
        }
    }
    public static String get(Enum<?> enumValue) {
        if (!initialized) {
            Gdx.app.error("TypeToString", "Not initialized! Call init() first.");
            return enumValue.name();
        }

        String key = enumValue.name();
        if(stringMap.get(key, enumValue.name())!=null) {
            return stringMap.get(key, enumValue.name());
        }else{
            return key;
        }
    }
    public static void reload(String filePath) {
        stringMap.clear();
        initialized = false;
        init(filePath);
    }
    public static boolean isInitialized() {
        return initialized;
    }

    public enum Dictionary {
        Play,
        Settings,
        Quit,
        Pause,
        Resume,
        BackToMenu,
        Inventory,
        NullItem,
        MetalOre,
        Plate,
        Rod,
        Gear,
        Sand,
        Glass,
        Clay,
        Brick,
        Assembler,
        Belt,
        Chest,
        Creator,
        Deleter,
        Inserter,
        LongInserter,
        Miner
    }
}



// Example usage:

/*
// Example enum
enum WeaponType {
    SWORD,
    BOW,
    STAFF,
    DAGGER
}

// Example text file (strings.txt):
SWORD=Sharp Blade
BOW=Longbow
STAFF=Wizard's Staff
DAGGER=Poisoned Dagger

// In your game initialization:
TypeToString.init("strings.txt");

// Usage:
WeaponType weapon = WeaponType.SWORD;
String displayName = TypeToString.get(weapon); // Returns "Sharp Blade"
*/
