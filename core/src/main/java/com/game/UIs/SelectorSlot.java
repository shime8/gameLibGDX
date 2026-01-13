package com.game.UIs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.game.items.Item;
import com.game.mechanics.Recipe;

public class SelectorSlot extends Stack{
    public Image BGimage;
    public Image image;
    public Recipe recipe;

    public SelectorSlot(Skin skin) {
        super();

        BGimage = new Image();
        BGimage.setDrawable(new TextureRegionDrawable(new Texture("items/itemSlot.png")));
        image = new Image();
        add(BGimage);
        add(image);
    }

    public void setItem(Item item) {
        if (item == null) {
            image.setDrawable(null);
            return;
        }
        image.setDrawable(new TextureRegionDrawable(item.sprite.getTexture()));  // item.icon
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
