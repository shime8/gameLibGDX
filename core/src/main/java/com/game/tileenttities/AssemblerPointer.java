package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.items.Item;

public class AssemblerPointer extends TileEntity implements CanCraft,CantPickup {
    public Assembler assembler;

    public AssemblerPointer(Assembler assembler, Vector2 vector) {
        this();
        this.assembler = assembler;
        set((int)vector.x,(int)vector.y);
    }

    public AssemblerPointer() {
        super();
        sprite = new Sprite(new Texture("items/nullitem.png") );
    }
    public AssemblerPointer(int x, int y) {
        this();
        set(x,y);
    }
    public AssemblerPointer(AssemblerPointer other) {
        super(other);
        this.assembler = other.assembler;
    }
    public String getname(){
            return "AssemblerPointer";
    }
    @Override
    public TileEntity clone() {
        return new AssemblerPointer(this);
    }

    @Override
    public void update(float delta) {

    }


    @Override
    public Item getAnyItem() {
        return assembler.getAnyItem();
    }

    @Override
    public boolean addItem(Item item) {
        return assembler.addItem(item);
    }

    @Override
    public Array<Item> ItemsOnBreak() {
        return assembler.ItemsOnBreak();
    }

    public void render(SpriteBatch batch){

    }

    @Override
    public Rectangle getBounds() {
        return assembler.getBounds();
    }
    public Rectangle getHighlightBounds() {return assembler.getHighlightBounds();}

    @Override
    public TileEntity pickupee(){
        return assembler;
    }
    public String getClassName(){
        return "AssemblerPointer";
    }
}
