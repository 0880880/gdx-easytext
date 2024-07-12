package com.github.zeroeighteightzero.easytext;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.*;

import java.util.TreeMap;

public class FontFamily {

    public String name;
    public final FontFace[][] fonts = new FontFace[9][8];

    public FontFamily(String name) {
        this.name = name;
    }

    private static int getX(int weight) {
        return MathUtils.clamp((weight / 100) - 1, 0, 9);
    }

    private static int getY(float width) {
        if (MathUtils.isEqual(width,50f)) return 0;
        if (MathUtils.isEqual(width,62.5f)) return 1;
        if (MathUtils.isEqual(width,75f)) return 2;
        if (MathUtils.isEqual(width,87.5f)) return 3;
        if (MathUtils.isEqual(width,100f)) return 4;
        if (MathUtils.isEqual(width,112.5f)) return 5;
        if (MathUtils.isEqual(width,125f)) return 6;
        if (MathUtils.isEqual(width,150f)) return 7;
        if (MathUtils.isEqual(width,200f)) return 8;
        return 4;
    }

    private static int getWeight(int x) {
        return (x + 1) * 100;
    }

    public FontFace get(int weight, float width) {
        int x = getX(weight);
        int y = getY(width);
        if (fonts[x] != null && fonts[x][y] != null) return fonts[x][y];

        // TODO Width

        if (weight < 400) {
            for (int i = fonts.length - 1; i >= 0; i--) {
                if (fonts[i] == null) continue;
                if (getWeight(i) < weight) return fonts[i][y];
            }
            for (int i = 0; i < fonts.length; i++) {
                if (fonts[i] == null) continue;
                if (getWeight(i) > weight) return fonts[i][y];
            }
        } else if (weight > 500) {
            for (int i = 0; i < fonts.length; i++) {
                if (fonts[i] == null) continue;
                if (getWeight(i) > weight) return fonts[i][y];
            }
            for (int i = fonts.length - 1; i >= 0; i--) {
                if (fonts[i] == null) continue;
                if (getWeight(i) < weight) return fonts[i][y];
            }
        } else {
            for (int i = 0; i < fonts.length; i++) {
                if (fonts[i] == null) continue;
                if (getWeight(i) >= weight && getWeight(i) <= 500) return fonts[i][y];
            }
            for (int i = fonts.length - 1; i >= 0; i--) {
                if (fonts[i] == null) continue;
                if (getWeight(i) < weight) return fonts[i][y];
            }
            for (int i = 0; i < fonts.length; i++) {
                if (fonts[i] == null) continue;
                if (getWeight(i) > 500) return fonts[i][y];
            }
        }
        return null;
    }

    public FontFace get(int weight) {
        return get(weight, 100);
    }

    public FontFamily put(FontFace fontFace) {
        int x = getX(400); // 400/Regular
        int y = getY(100); // 400/Regular
        if (fonts[x] == null) fonts[x] = new FontFace[8];
        fonts[x][y] = fontFace;
        return this;
    }

    public FontFamily put(int weight, FontFace fontFace) {
        int x = getX(weight);
        int y = getY(100);
        if (fonts[x] == null) fonts[x] = new FontFace[8];
        fonts[x][y] = fontFace;
        return this;
    }

    public FontFamily put(int weight, float width, FontFace fontFace) {
        int x = getX(weight);
        int y = getY(width);
        if (fonts[x] == null) fonts[x] = new FontFace[8];
        fonts[x][y] = fontFace;
        return this;
    }

}
