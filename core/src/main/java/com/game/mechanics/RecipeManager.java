package com.game.mechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.game.items.*;
import com.game.tileenttities.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RecipeManager {
    public Array<Recipe> recipes;

    public RecipeManager(){
        setRecipes();
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
        }
    }
    public void setRecipes(){
        recipes = new Array<>();
        FileHandle fileHandle = Gdx.files.internal("Recipes/recipes.txt");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(fileHandle.read()))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] io = line.split(";");

                Array<Item> itemsCIn = StringsToItemArrays(io[1]);
                Array<Item> itemsCOut = StringsToItemArrays(io[0]);
                float time = Float.parseFloat(io[2]);
                if(time==0.0f){time=1f;}
                recipes.add(new Recipe(itemsCIn,itemsCOut,time));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Recipe getRecipe(int i){
        return recipes.get(i);
    }
    public Array<Item> StringsToItemArrays(String string){
        String[] parts = string.split(",");
        Array<Item> items = new Array<>();
        for (int i = 0; i < parts.length; i++) {
            // Remove leading/trailing spaces
            String part = parts[i].trim();
            // Split name and number
            String[] tokens = part.split("\\s+");
            Item itemTemp = StringToItem(tokens[0]);
            itemTemp.amount = Integer.parseInt(tokens[1]);
            items.add(itemTemp);
        }
        return items;
    }
    public Item StringToItem(String string){
        Map<String, Item> itemMap = new HashMap<>();

        itemMap.put("gear", new Gear(0));
        itemMap.put("assembler", new Item(0, new Assembler()));
        itemMap.put("belt", new Item(0, new Belt()));
        itemMap.put("chest", new Item(0, new Chest()));
        itemMap.put("creator", new Item(0, new Creator()));
        itemMap.put("deleter", new Item(0, new Deleter()));
        itemMap.put("inserter", new Item(0, new Inserter()));
        itemMap.put("metalOre", new Item(0, new MetalOre()));
        itemMap.put("plate", new Plate(0));
        itemMap.put("rod", new Rod(0));
        itemMap.put("clay", new Item(0, new ClayOre()));
        itemMap.put("brick", new Brick(0));
        itemMap.put("sand", new Sand(0));
        itemMap.put("glass", new Glass(0));
        Item item = itemMap.get(string);

        if (item == null) {
            return new NullItem(0);
        }

        return item;
    }

    public int length(){
        return recipes.size;
    }


}
