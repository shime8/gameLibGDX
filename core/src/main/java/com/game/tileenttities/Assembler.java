package com.game.tileenttities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.items.Gear;
import com.game.items.Item;
import com.game.mechanics.Recipe;

import java.util.Objects;

import static com.game.main.Main.itemEntityManager;
import static com.game.main.Main.tileEntityManager;

public class Assembler extends TileEntity implements CanCraft{
    Recipe recipe;
    public Array<Item> itemsIn;
    public Array<Item> itemsOut;
    public BitmapFont font;
    public float speed;
    public float accumulator;

    public void SetCrafting(Array<Item> itemsIn, Array<Item> itemsOut){

        for (Item i : itemsIn) {
            i.amount = 0;
        }
        for (Item i : itemsOut) {
            i.amount = 0;
        }
        this.itemsIn = itemsIn;
        this.itemsOut = itemsOut;
        accumulator = recipe.time/speed;
    }

    public Assembler(){
        super();
        sprite = new Sprite(new Texture("tiles/assembler.png") );
        name = "Assembler";
        speed = 1f;
        Array<Item> input = new Array<>();
        Array<Item> output = new Array<>();

    }
    @Override
    public Rectangle getBounds(){
        return new Rectangle(this.x-1,this.y-1,3,3);
    }
    public Assembler(int x, int y) {
        this();
        set(x,y);
        //sprite.setSize(bounds.width, bounds.height);
        //sprite.setOriginCenter();
        //sprite.setPosition(x, y);
    }
    public Assembler(Assembler other){
        super(other);
        this.itemsIn = other.itemsIn;
        this.itemsOut = other.itemsOut;
        this.setRecipe(other.recipe);
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(0.05f);
        this.speed = other.speed;
        this.accumulator = 0f;
    }

    @Override
    public TileEntity clone() {
        return new Assembler(this);
    }

    @Override
    public void update(float delta) {
        if(canICraft()) {
            if(accumulator <= 0.001f){
                accumulator = recipe.time/speed;
                craft(true);
            }else{
                accumulator -= delta;
            }

            //System.out.println(craftProgress());
        }

    }

    public boolean canICraft(){
        if(itemsIn != null && itemsOut != null){
            if(itemsOut.first() != null && itemsOut.first().amount>=maxIOitems){
                return false;
            }

            boolean canICraft = true;
            for (int x = 0; x < itemsIn.size; x++) {
                Item i = itemsIn.get(x);
                if (i.amount < recipe.itemsCIn.get(x).amount) {
                    canICraft = false;
                    break;
                }
            }
            return canICraft;
        }
        return false;
    }
    public void craft(boolean canICraft){
        if(canICraft && itemsIn != null && itemsOut != null){
                for (int x = 0; x < itemsIn.size; x++) {
                    Item i = itemsIn.get(x);
                    i.amount -= recipe.itemsCIn.get(x).amount;
                }
                for (int x = 0; x < itemsOut.size; x++) {
                    Item i = itemsOut.get(x);
                    i.amount += recipe.itemsCOut.get(x).amount;
                }
                accumulator = recipe.time/speed;
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
            for (int i = 0; i < itemsIn.size; i++) {
                if (itemsIn.get(i) != null && Objects.equals(itemsIn.get(i).name, item.name)) {
                    if(itemsIn.get(i).amount>=maxIOitems){
                        return false;
                    }
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
        if(itemsOut!=null && itemsOut.first()!=null){
            Sprite craft = itemsOut.first().sprite;
            if(itemsOut.first().Tile != null){craft = itemsOut.first().Tile.sprite;}
            craft.setBounds(x + 0.2f, y + 0.2f, 0.6f, 0.6f);
            craft.draw(batch);
        }
        //if(itemsIn != null)for(Item i : itemsIn){if(i != null){font.draw(batch,String.valueOf(i.amount), x, y);}}
        //if(recipe != null){font.draw(batch,String.valueOf(recipe.itemsCIn.get(0).amount), x, y-1);}
        //if(recipe != null){font.draw(batch,String.valueOf(recipe.itemsCOut.get(0).amount), x, y-2);}

    }

    @Override
    public void shapeRender(ShapeRenderer shapeR) {
        Rectangle rect = new Rectangle(x - 0.5f, y + 1f, 2f, 0.25f);
        shapeR.begin(ShapeRenderer.ShapeType.Filled);
        if(recipe!=null && craftProgress()!=0f) {
            shapeR.setColor(Color.LIGHT_GRAY);
            shapeR.rect(rect.x + rect.width * (craftProgress()), rect.y, rect.width * (1f - craftProgress()), rect.height);
            shapeR.setColor(Color.WHITE);
            shapeR.rect(rect.x, rect.y, rect.width * craftProgress(), rect.height);
        }else{
            shapeR.setColor(Color.LIGHT_GRAY);
            shapeR.rect(rect.x,rect.y,rect.width,rect.height);
        }
        shapeR.end();
    }

    public Item getItemIn(int index) {
        return itemsIn.get(index);
    }
    public void setItemIn(int index, Item item) {
        itemsIn.set(index, item);
    }

    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
        if(recipe!=null) {
            Recipe cloned = cloneRecipe(this.recipe);
            SetCrafting(cloned.itemsCIn, cloned.itemsCOut);
        }
    }
    public Recipe cloneRecipe(Recipe recipe){
        Array<Item> clonedIn = new Array<>(recipe.itemsCIn.size);
        for (Item i : recipe.itemsCIn) {
            clonedIn.add(new Item(i));
        }

        Array<Item> clonedOut = new Array<>(recipe.itemsCOut.size);
        for (Item i : recipe.itemsCOut) {
            clonedOut.add(new Item(i));
        }
        return new Recipe(clonedIn, clonedOut, recipe.time);
    }
    @Override
    public Array<Vector2> checkWhenPlacing(){
        Array<Vector2> tiles = new Array<>();
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                tiles.add(new Vector2(this.x+i,this.y+j));
            }
        }
        return tiles;
    }

    @Override
    public void placeOtherTiles(){
        for (Vector2 vector : checkWhenPlacing()){
            if(vector.x != (float)this.x || vector.y != (float)this.y)tileEntityManager.addEntity(new AssemblerPointer(this,vector));
        }
    }
    @Override
    public void removeOtherTiles(){
        for (Vector2 vector : checkWhenPlacing()){
            if(vector.x != (float)this.x || vector.y != (float)this.y)tileEntityManager.removeEntity(tileEntityManager.getEntityAt((int)vector.x,(int)vector.y));
        }
    }

    public float craftProgress(){
        if(itemsOut.first().amount>=maxIOitems){
            return 1;
        }else {
            return Math.min(1f, Math.max(0f, 1f - ((accumulator * speed) / recipe.time)));
        }
    }
}
