package com.github.zeroeighteightzero.easytext;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

public class Font implements Disposable {

    private int maxCacheSize;

    private FreeTypeFontGenerator generator;

    private HashMap<Integer, BitmapFont> fonts = new HashMap<>();

    public int sizeTolerance = 0;

    public Font(FileHandle file, int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
        generator = new FreeTypeFontGenerator(file);
    }

    public Font(FileHandle file) {
        this(file, 5);
    }

    public void createFont(FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        BitmapFont font = generator.generateFont(parameter);
        fonts.put(parameter.size, font);
    }

    public void createFont(int fontSize) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.color = Color.WHITE;
        BitmapFont font = generator.generateFont(parameter);
        fonts.put(parameter.size, font);
    }

    public void createFont(int... fontSizes) {
        for (int size : fontSizes) {
            createFont(size);
        }
    }

    private int fontSize;

    public BitmapFont getFont(int fontSize) {
        if (!fonts.containsKey(fontSize)) {
            boolean foundFont = false;
            if (sizeTolerance != 0) {
                int nearestSize = 0;
                for (Integer i : fonts.keySet()) {
                    if (fontSize > i - sizeTolerance && fontSize < i + sizeTolerance && (nearestSize - fontSize) < (i - fontSize)) {
                        nearestSize = i;
                        foundFont = true;
                    }
                }
                this.fontSize = nearestSize;
                if (nearestSize != 0)
                    return fonts.get(nearestSize);
            }
            if (!foundFont) {
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.size = fontSize;
                parameter.color = Color.WHITE;
                createFont(parameter);
            }
        }
        this.fontSize = fontSize;
        return fonts.get(fontSize);
    }

    public void draw(Batch batch, String text, int fontSize, float x, float y, Color color) {

        BitmapFont font = getFont(fontSize);

        font.setColor(color);
        font.getData().setScale((float) fontSize / (float) this.fontSize);
        font.draw(batch, text, x, y);

    }

    public void draw(Batch batch, String text, int fontSize, float x, float y, Color color, float targetWidth, boolean wrap, int halign) {

        BitmapFont font = getFont(fontSize);

        font.setColor(color);
        font.draw(batch, text, x, y, targetWidth, halign, wrap);

    }

    public Vector2 calculateTextSize(String text, int fontSize, Vector2 v) {

        GlyphLayout glyphLayout = new GlyphLayout(getFont(fontSize), text);
        return v.set(glyphLayout.width, glyphLayout.height);

    }

    @Override
    public void dispose() {
        for (BitmapFont font : fonts.values()) {
            font.dispose();
        }
        generator.dispose();
    }

}
