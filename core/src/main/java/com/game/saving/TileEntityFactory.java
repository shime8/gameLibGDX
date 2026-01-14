package com.game.saving;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.game.tileenttities.*;

public class TileEntityFactory {

    public static TileEntity createTileEntityFromJSON(JsonValue json) {
        String type = json.getString("type");

        switch (type) {
            case "Belt": {
                Belt belt = new Belt();
                belt.set(json.getInt("x"),json.getInt("y"));

                if (json.has("direction")) {
                    JsonValue dir = json.get("direction");
                    belt.direction = new Vector2(dir.getFloat("x"), dir.getFloat("y"));
                }
                return belt;
            }

            case "Assembler": {
                Assembler assembler = new Assembler();
                assembler.set(json.getInt("x"),json.getInt("y"));

                if (json.has("recipe") && !json.get("recipe").isNull()) {
                    assembler.setRecipe(RecipeFactory.createRecipeFromJSON(json.get("recipe")));
                }

                if (json.has("itemsIn") && !json.get("itemsIn").isNull()) {
                    if(!json.get("itemsIn").isEmpty())assembler.itemsIn = ItemFactory.createItemArrayFromJSON(json.get("itemsIn"));
                    System.out.println("itemsin"+assembler.itemsIn);
                }
                if (json.has("itemsOut") && !json.get("itemsOut").isNull()) {
                    if(!json.get("itemsOut").isEmpty())assembler.itemsOut = ItemFactory.createItemArrayFromJSON(json.get("itemsOut"));
                    System.out.println("itemsout"+assembler.itemsOut);

                }
                return assembler;
            }

            case "AssemblerT2": {
                AssemblerT2 assembler = new AssemblerT2();
                assembler.set(json.getInt("x"),json.getInt("y"));

                if (json.has("recipe") && !json.get("recipe").isNull()) {
                    assembler.setRecipe(RecipeFactory.createRecipeFromJSON(json.get("recipe")));
                }

                if (json.has("itemsIn") && !json.get("itemsIn").isNull()) {
                    assembler.itemsIn = ItemFactory.createItemArrayFromJSON(json.get("itemsIn"));
                }
                if (json.has("itemsOut") && !json.get("itemsOut").isNull()) {
                    assembler.itemsOut = ItemFactory.createItemArrayFromJSON(json.get("itemsOut"));
                }
                return assembler;
            }
            case "BuildPlace": {
                BuildPlace bp = new BuildPlace();
                bp.set(json.getInt("x"),json.getInt("y"));

                // WhatToBuild TileEntity
                if (json.has("WhatToBuild") && !json.get("WhatToBuild").isNull() ) {
                    bp.WhatToBuild = TileEntityFactory.createTileEntityFromJSON(json.get("WhatToBuild"));
                }

                // Recipe Array<Item>
                if (json.has("Recipe") && !json.get("Recipe").isNull()) {
                    bp.Recipe = ItemFactory.createItemArrayFromJSON(json.get("Recipe"));
                }

                return bp;
            }
            case "MetalOre":{

            }
            case "AssemblerPointer":{return null;}
            default:
                return null;
                //throw new RuntimeException("Unknown TileEntity type: " + type);
        }
    }
}
