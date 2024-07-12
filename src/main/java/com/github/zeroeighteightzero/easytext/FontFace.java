package com.github.zeroeighteightzero.easytext;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;

import java.util.HashMap;

public class FontFace implements Disposable {

    private int maxCacheSize;

    private FreeTypeFontGenerator generator;

    private HashMap<Integer, BitmapFont> fonts = new HashMap<>();

    private int sizeTolerance = 0;
    private boolean incremental = true;

    public FontFace(FileHandle file, int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
        generator = new FreeTypeFontGenerator(file);
    }

    public FontFace(FileHandle file) {
        this(file, 5);
    }

    public void createFont(int fontSize) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.color = Color.WHITE;
        parameter.incremental = incremental;
        BitmapFont font = generator.generateFont(parameter);
        font.setUseIntegerPositions(false);
        fonts.put(fontSize, font);
    }

    public void createFont(FreeTypeFontGenerator.FreeTypeFontParameter... parameters) {
        for (FreeTypeFontGenerator.FreeTypeFontParameter parameter : parameters) {
            BitmapFont font = generator.generateFont(parameter);
            font.setUseIntegerPositions(false);
            fonts.put(parameter.size, font);
        }
    }

    private int fontSize;

    public BitmapFont getFont(int fontSize) {
        if (!fonts.containsKey(fontSize)) {
            if (sizeTolerance != 0) {
                int nearestFontSize = 10000;
                for (int key : fonts.keySet()) {
                    if (fontSize > key - sizeTolerance && fontSize < key + sizeTolerance && (nearestFontSize - fontSize) < (key - fontSize)) {
                        nearestFontSize = key;
                    }
                }
                if (fontSize > nearestFontSize - sizeTolerance && fontSize < nearestFontSize + sizeTolerance) {
                    this.fontSize = nearestFontSize;
                    return fonts.get(nearestFontSize);
                }
            }
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = fontSize;
            parameter.color = Color.WHITE;
            createFont(parameter);
        }
        this.fontSize = fontSize;
        return fonts.get(fontSize);
    }

    public GlyphLayout draw(Batch batch, String text, int fontSize, float x, float y, Color color) {

        BitmapFont font = getFont(fontSize);

        font.setColor(color);
        font.getData().setScale((float) fontSize / (float) this.fontSize);
        return font.draw(batch, text, x, y);

    }

    public GlyphLayout draw(Batch batch, String text, int fontSize, float x, float y, Color color, float targetWidth, boolean wrap, int halign) {

        BitmapFont font = getFont(fontSize);

        font.setColor(color);
        font.getData().setScale((float) fontSize / (float) this.fontSize);
        return font.draw(batch, text, x, y, targetWidth, halign, wrap);

    }

    public Vector2 calculateTextSize(String text, int fontSize, Vector2 v) {
        GlyphLayout glyphLayout = new GlyphLayout(getFont(fontSize), text);
        return v.set(glyphLayout.width, glyphLayout.height);
    }

    public FontFace sizeTolerance(int value) {
        this.sizeTolerance = value;
        return this;
    }

    public int sizeTolerance() {
        return this.sizeTolerance;
    }

    public FontFace incremental(boolean value) {
        this.incremental = value;
        return this;
    }

    public boolean incremental() {
        return this.incremental;
    }

    @Override
    public void dispose() {
        for (BitmapFont font : fonts.values()) {
            font.dispose();
        }
        generator.dispose();
    }

}
