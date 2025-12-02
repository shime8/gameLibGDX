package com.game.tileenttities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.game.items.Gear;
import com.game.items.Item;

import java.util.Objects;

import static com.game.main.Main.itemEntityManager;

public class Assembler extends TileEntity implements CanCraft{
    public Array<Item> itemsIn;
    public Array<Item> itemsOut;
    public BitmapFont font;

    public void SetCrafting(Array<Item> itemsIn, Array<Item> itemsOut){
        this.itemsIn = itemsIn;
        this.itemsOut = itemsOut;
    }

    public Assembler(){
        super();
        sprite = new Sprite(new Texture("tiles/assembler.png") );
        name = "Assembler";
        //debug
        Array<Item> input = new Array<>();
        input.add(new Gear(0));

        Array<Item> output = new Array<>();
        output.add(new Item(0,new Belt()));

        SetCrafting(input, output);

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
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(0.1f);
    }

    @Override
    public TileEntity clone() {
        return new Assembler(this);
    }

    @Override
    public void update(float delta) {
        if(itemsIn != null && itemsOut != null){
            boolean aretheyone = true;
            for (Item i : itemsIn) {
                if (i.amount == 0) {
                    aretheyone = false;
                }
            }
            if (aretheyone) {
                for (Item i : itemsIn) {
                    i.amount--;
                }
                for (Item i : itemsOut) {
                    i.amount++;
                }
            }
        }
    }

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
            boolean added = false;
            for (int i = 0; i < itemsIn.size; i++) {
                if (itemsIn.get(i) != null && Objects.equals(itemsIn.get(i).name, item.name)) {
                    Item temp = itemsIn.get(i);
                    temp.amount += item.amount;
                    itemsIn.set(i, temp);
                    added = true;
                    return true;
                }
            }
            if (!added) {
                for (int i = 0; i < itemsIn.size; i++) {
                    if (itemsIn.get(i) == null) {
                        itemsIn.set(i, item);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if(itemsIn != null)for(Item i : itemsIn){if(i != null){font.draw(batch,String.valueOf(i.amount), x, y);}}
    }

}
