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

    private HashMap<FontData, BitmapFont> fonts = new HashMap<>();

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
        font.setUseIntegerPositions(true);
        fonts.put(new FontData(parameter), font);
    }

    public void createFont(FreeTypeFontGenerator.FreeTypeFontParameter... parameters) {
        for (FreeTypeFontGenerator.FreeTypeFontParameter parameter : parameters) {
            createFont(parameter);
        }
    }

    private int fontSize;

    public BitmapFont getFont(int fontSize) {
        if (!fonts.containsKey(fontSize)) {
            boolean foundFont = false;
            if (sizeTolerance != 0) {
                FontData nearestData = null;
                for (FontData data : fonts.keySet()) {
                    if (fontSize > data.size - sizeTolerance && fontSize < data.size + sizeTolerance && ((nearestData != null ? nearestData.size : 0) - fontSize) < (data.size - fontSize)) {
                        nearestData = data;
                        foundFont = true;
                    }
                }
                if (nearestData != null) {
                    this.fontSize = nearestData.size;
                    return fonts.get(nearestData);
                }
            }
            if (!foundFont) {
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.size = fontSize;
                parameter.color = Color.WHITE;
                createFont(parameter);
            }
        }
        this.fontSize = fontSize;
        return fonts.get(new FontData(fontSize));
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
