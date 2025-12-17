package com.game.tileenttities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.game.items.Gear;
import com.game.items.Item;
import com.game.mechanics.Recipe;

import java.util.Objects;

import static com.game.main.Main.itemEntityManager;

public class Assembler extends TileEntity implements CanCraft{
    Recipe recipe;
    public Array<Item> itemsIn;
    public Array<Item> itemsOut;
    public BitmapFont font;

    public void SetCrafting(Array<Item> itemsIn, Array<Item> itemsOut){
        this.itemsIn = itemsIn;
        this.itemsOut = itemsOut;
        for (Item i : itemsIn) {
            i.amount = 0;
        }
        for (Item i : itemsOut) {
            i.amount = 0;
        }
    }

    public Assembler(){
        super();
        sprite = new Sprite(new Texture("tiles/assembler.png") );
        name = "Assembler";
        //debug
        Array<Item> input = new Array<>();

        Array<Item> output = new Array<>();


    }
    public Assembler(int x, int y) {
        this();
        set(x,y);
        sprite.setSize(bounds.width, bounds.height);
        sprite.setOriginCenter();
        sprite.setPosition(x, y);
    }
    public Assembler(Assembler other){
        super(other);
        this.itemsIn = other.itemsIn;
        this.itemsOut = other.itemsOut;
        this.setRecipe(other.recipe);
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(0.05f);
    }

    @Override
    public TileEntity clone() {
        return new Assembler(this);
    }

    @Override
    public void update(float delta) {
        if(itemsIn != null && itemsOut != null){
            boolean canICraft = true;
            for (int x = 0; x < itemsIn.size; x++) {
                Item i = itemsIn.get(x);
                if (i.amount < recipe.itemsCIn.get(x).amount) {
                    canICraft = false;
                    break;
                }
            }
            if (canICraft) {
                for (int x = 0; x < itemsIn.size; x++) {
                    Item i = itemsIn.get(x);
                    i.amount -= recipe.itemsCIn.get(x).amount;
                }
                for (int x = 0; x < itemsOut.size; x++) {
                    Item i = itemsOut.get(x);
                    i.amount += recipe.itemsCOut.get(x).amount;
                }
            }
        }
    }
    @Override
    public void placingUpdate() {}
    @Override
    public Item getAnyItem() {
        if(itemsOut != null){
            for (int i = 0; i < itemsOut.size; i++) {
                if (itemsOut.get(i) != null && itemsOut.get(i).amount != 0) {
                    Item item = new Item(itemsOut.get(i));
                    itemsOut.get(i).amount--;
//                    if (itemsOut.get(i).amount == 0) {
//                        itemsOut.set(i, null);
//                    }
                    item.amount = 1;
                    return item;
                }
            }
        }
        return null;
    }

    @Override
    public boolean addItem(Item item) {
        if(itemsIn != null){
            for (int i = 0; i < itemsIn.size; i++) {
                if (itemsIn.get(i) != null && Objects.equals(itemsIn.get(i).name, item.name)) {
                    Item temp = itemsIn.get(i);
                    temp.amount += item.amount;
                    itemsIn.set(i, temp);
                    return true;
                }
            }
            for (int i = 0; i < itemsIn.size; i++) {
                if (itemsIn.get(i) == null) {
                    itemsIn.set(i, item);
                    return true;
                }
            }

        }
        return false;
    }
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        //if(itemsIn != null)for(Item i : itemsIn){if(i != null){font.draw(batch,String.valueOf(i.amount), x, y);}}
        //if(recipe != null){font.draw(batch,String.valueOf(recipe.itemsCIn.get(0).amount), x, y-1);}
        //if(recipe != null){font.draw(batch,String.valueOf(recipe.itemsCOut.get(0).amount), x, y-2);}

    }
    public Item getItemIn(int index) {
        return itemsIn.get(index);
    }
    public void setItemIn(int index, Item item) {
        itemsIn.set(index, item);
    }

    public int getSize(){
        return 1;
    }
    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
        if(recipe!=null) {
            Array<Item> clonedIn = new Array<>(recipe.itemsCIn.size);
            for (Item i : recipe.itemsCIn) {
                clonedIn.add(new Item(i));
            }

            Array<Item> clonedOut = new Array<>(recipe.itemsCOut.size);
            for (Item i : recipe.itemsCOut) {
                clonedOut.add(new Item(i));
            }
            SetCrafting(clonedIn, clonedOut);
        }
    }

}
