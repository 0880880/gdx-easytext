package com.github.zeroeighteightzero.easytext;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.Objects;

public class FontData {

    public int size;
    public boolean mono = false;
    public boolean kerning = false;
    public FreeTypeFontGenerator.Hinting hinting = FreeTypeFontGenerator.Hinting.AutoMedium;

    public FontData(int fontsize) {
        this.size = fontsize;
    }

    public FontData(FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        size = parameter.size;
        mono = parameter.mono;
        kerning = parameter.kerning;
        hinting = parameter.hinting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FontData fontData = (FontData) o;
        return size == fontData.size && mono == fontData.mono;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, mono);
    }
}
