package com.github.zeroeighteightzero.easytext;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game implements ApplicationListener {

    SpriteBatch batch;

    Font font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new Font(Gdx.files.internal("DMSerifDisplay-Regular.ttf"), 15);
        font.sizeTolerance = 16;
    }

    float time = 0;

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.WHITE);

        time += Gdx.graphics.getDeltaTime();

        batch.begin();

        int fontSize = (int) ((MathUtils.sin(time) + 1) * 60 + 12);

        font.draw(batch, "Hello, world!", fontSize, 600, 600, Color.BLACK);

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
