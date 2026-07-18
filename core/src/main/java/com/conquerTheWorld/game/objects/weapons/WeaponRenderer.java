package com.conquerTheWorld.game.objects.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.conquerTheWorld.game.objects.characters.Player;
import com.conquerTheWorld.game.objects.weapons.swords.SwordAttack;
import com.conquerTheWorld.game.objects.weapons.swords.SwordType;

import java.util.EnumMap;
import java.util.Map;

/** Draws right-pointing pixel-art weapon sprites rotated around their grip pixel. */
public final class WeaponRenderer implements Disposable {
    private final Map<SwordType, Texture> textures = new EnumMap<>(SwordType.class);

    public WeaponRenderer() {
        for (SwordType swordType : SwordType.values()) {
            textures.put(swordType, loadOrCreateTexture(swordType));
        }
    }

    public void render(
        SpriteBatch batch,
        Player player,
        SwordAttack activeAttack
    ) {
        SwordType swordType = activeAttack == null
            ? player.getEquippedSwordType()
            : activeAttack.getSwordType();
        float angleDegrees = activeAttack == null
            ? player.getAimAngleDegrees()
            : activeAttack.getRenderAngleDegrees();

        Texture texture = textures.get(swordType);
        float handX = player.getRenderX() + player.getDrawWidth() * 0.5f;
        float handY = player.getRenderY() + player.getDrawHeight() * 0.48f;
        float drawX = handX - swordType.getGripX();
        float drawY = handY - swordType.getGripY();

        batch.draw(
            texture,
            drawX,
            drawY,
            swordType.getGripX(),
            swordType.getGripY(),
            swordType.getDrawWidth(),
            swordType.getDrawHeight(),
            1f,
            1f,
            angleDegrees,
            0,
            0,
            texture.getWidth(),
            texture.getHeight(),
            false,
            false
        );
    }

    private Texture loadOrCreateTexture(SwordType swordType) {
        Texture texture;
        if (Gdx.files.internal(swordType.getTexturePath()).exists()) {
            texture = new Texture(swordType.getTexturePath());
        } else {
            texture = createFallbackTexture(swordType);
            Gdx.app.log(
                "Weapons",
                "Using generated placeholder for missing " + swordType.getTexturePath()
            );
        }
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        return texture;
    }

    private Texture createFallbackTexture(SwordType swordType) {
        int width = Math.round(swordType.getDrawWidth());
        int height = Math.round(swordType.getDrawHeight());
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.CLEAR);
        pixmap.fill();

        int middle = height / 2;
        int handleEnd = Math.max(5, Math.round(swordType.getGripX()) + 3);
        pixmap.setColor(0.30f, 0.16f, 0.06f, 1f);
        pixmap.fillRectangle(0, Math.max(0, middle - 1), handleEnd, 3);
        pixmap.setColor(0.95f, 0.72f, 0.18f, 1f);
        pixmap.fillRectangle(handleEnd - 2, Math.max(0, middle - 3), 2, 7);

        int bladeStart = handleEnd;
        int bladeEnd = width - 2;
        int bladeThickness = swordType == SwordType.HEAVY ? 5 : 3;
        pixmap.setColor(0.72f, 0.78f, 0.88f, 1f);
        pixmap.fillRectangle(
            bladeStart,
            middle - bladeThickness / 2,
            bladeEnd - bladeStart,
            bladeThickness
        );
        pixmap.setColor(0.95f, 0.98f, 1f, 1f);
        pixmap.drawLine(bladeStart, middle - bladeThickness / 2, bladeEnd, middle);
        pixmap.drawLine(bladeStart, middle + bladeThickness / 2, bladeEnd, middle);

        Texture result = new Texture(pixmap);
        pixmap.dispose();
        return result;
    }

    @Override
    public void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
        textures.clear();
    }
}
