package com.conquerTheWorld.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.conquerTheWorld.game.objects.RenderableObject;
import com.conquerTheWorld.game.worlds.World1;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private World1 world1;

    private int screenX;
    private int screenY;
    private int screenWidth;
    private int screenHeight;

    @Override
    public void create() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        world1 = new World1();

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        world1.update(delta);

        Gdx.gl.glClearColor(
            Constants.CLEAR_R,
            Constants.CLEAR_G,
            Constants.CLEAR_B,
            Constants.CLEAR_A
        );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glViewport(screenX, screenY, screenWidth, screenHeight);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        for (RenderableObject object : world1.getRenderables()) {
            object.render(batch);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        int scaleX = width / Constants.WORLD_WIDTH;
        int scaleY = height / Constants.WORLD_HEIGHT;

        int scale = Math.min(scaleX, scaleY);
        scale = Math.max(1, scale);

        screenWidth = Constants.WORLD_WIDTH * scale;
        screenHeight = Constants.WORLD_HEIGHT * scale;

        screenX = (width - screenWidth) / 2;
        screenY = (height - screenHeight) / 2;

        camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
    }

    @Override
    public void dispose() {
        batch.dispose();
        world1.dispose();
    }
}