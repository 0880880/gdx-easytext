package com.github.zeroeighteightzero.easytext;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game implements ApplicationListener {

    SpriteBatch batch;

    Font font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new Font(Gdx.files.internal("Roboto-Black.ttf"), 20);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.WHITE);

        batch.begin();

        for (int i = 0; i < 5; i++) {
            font.draw(batch, "Hello, world!", 8 * i, 100, 100 + i * (i * 4), Color.BLACK);
        }

        batch.end();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
