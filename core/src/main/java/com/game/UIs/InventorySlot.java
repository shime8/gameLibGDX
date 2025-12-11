package com.game.UIs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.game.items.Item;

public class InventorySlot extends Stack {
    public Image image;
    public Label amountLabel;

    public InventorySlot(Skin skin) {
        super();

        image = new Image(); // initially empty
        amountLabel = new Label("", skin);
        amountLabel.setColor(Color.WHITE);
        amountLabel.setFontScale(1.2f);

        // Align text to bottom-right
        amountLabel.setAlignment(Align.bottomRight);

        add(image);
        add(amountLabel);
    }

    public void setItem(Item item) {
        if (item == null) {
            image.setDrawable(null);
            amountLabel.setText("");
            return;
        }

        image.setDrawable(new TextureRegionDrawable(item.sprite.getTexture()));  // item.icon
        amountLabel.setText(String.valueOf(item.amount));
    }
}
