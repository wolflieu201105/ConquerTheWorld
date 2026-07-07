package com.conquerTheWorld.game.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TileSet {
    private final Texture texture;

    public TileSet(String path) {
        texture = new Texture(path);
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    public TextureRegion getTile(int x, int y, int width, int height) {
        return new TextureRegion(texture, x, y, width, height);
    }

    public void dispose() {
        texture.dispose();
    }
}