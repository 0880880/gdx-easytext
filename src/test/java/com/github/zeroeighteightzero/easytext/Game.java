package com.github.zeroeighteightzero.easytext;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Game extends ApplicationAdapter {

    private SpriteBatch batch;
    private ScreenViewport viewport;

    private FontFace fontFace;

    private float x = 0;
    private float y = 0;
    private float dirX = 1;
    private float dirY = 1;
    private boolean fullscreen = false;

    private Color color = randomColor();

    private int fontSize = 72;
    private boolean allowOverflow = false;

    private Color randomColor() {
        return new Color(1,1,1,1).fromHsv(MathUtils.random(0,360), .6f, 1f);
    }

    @Override
    public void create() {

        batch = new SpriteBatch();
        viewport = new ScreenViewport();

        fontFace = new FontFace(Gdx.files.internal("DMSerifDisplay-Regular.ttf"), 32).sizeTolerance(32); // https://fonts.google.com/specimen/DM+Serif+Display

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            if (fullscreen) Gdx.graphics.setWindowedMode(800, 600);
            else Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            fullscreen = !fullscreen;
            x = 0;
            y = 0;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) allowOverflow = !allowOverflow;

        ScreenUtils.clear(Color.BLACK);

        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        x += dirX * 8;
        y += dirY * 8;

        GlyphLayout layout = fontFace.draw(batch, "Hello, world!", fontSize, x, y, color, 0, false, Align.center);

        if (x + layout.width / 2f >= viewport.getWorldWidth() / 2f) {
            dirX = -dirX;
            color = randomColor();
            fontSize++;
        }
        if (y >= viewport.getWorldHeight() / 2f) {
            dirY = -dirY;
            color = randomColor();
            fontSize++;
        }
        if (x - layout.width / 2f <= -viewport.getWorldWidth() / 2f) {
            dirX = -dirX;
            color = randomColor();
            fontSize--;
        }
        if (y - layout.height <= -viewport.getWorldHeight() / 2f) {
            dirY = -dirY;
            color = randomColor();
            fontSize++;
        }

        if (!allowOverflow && fontSize > 96) fontSize = 72;

        batch.end();

    }

    @Override
    public void dispose() {
        fontFace.dispose();
    }
}
