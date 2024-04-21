package com.github.zeroeighteightzero.easytext;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class Text {

    private Vector2 position = new Vector2();
    private Font font;
    private int fontSize = 16;
    private float maxWidth = Integer.MAX_VALUE;
    private boolean wrap = false;
    private Color color = Color.BLACK;
    private String text;
    private int align = Align.center;

    public Text(Font font) {
        this.font = font;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAlign(int align) {
        this.align = align;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setMaxWidth(float maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    public void draw(Batch batch) {

        font.draw(batch, text, fontSize, position.x, position.y, color, maxWidth, wrap, align);

    }

}
