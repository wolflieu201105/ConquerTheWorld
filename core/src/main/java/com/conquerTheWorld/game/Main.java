package com.conquerTheWorld.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.conquerTheWorld.game.network.discovery.DiscoveredServer;
import com.conquerTheWorld.game.network.discovery.LanDiscoveryResponder;
import com.conquerTheWorld.game.network.discovery.LanServerDiscovery;
import com.conquerTheWorld.game.network.config.NetworkConfig;
import com.conquerTheWorld.game.network.connection.UdpGameClient;
import com.conquerTheWorld.game.network.connection.UdpGameServer;
import com.conquerTheWorld.game.objects.weapons.swords.SwordAttack;
import com.conquerTheWorld.game.objects.core.Hitbox;
import com.conquerTheWorld.game.objects.characters.Player;
import com.conquerTheWorld.game.objects.core.RenderableObject;
import com.conquerTheWorld.game.objects.core.Prop;
import com.conquerTheWorld.game.objects.weapons.swords.SwordType;
import com.conquerTheWorld.game.objects.weapons.WeaponRenderer;
import com.conquerTheWorld.game.world.GameWorld;
import com.conquerTheWorld.game.world.WorldLayout;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer debugRenderer;
    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;
    private WeaponRenderer weaponRenderer;
    private final Vector3 mouseWorld = new Vector3();
    private final Vector3 labelPoint = new Vector3();

    private GameWorld gameWorld;
    private UdpGameClient networkClient;
    private UdpGameServer embeddedServer;
    private LanDiscoveryResponder embeddedDiscovery;
    private BitmapFont uiFont;
    private GlyphLayout glyphLayout;
    private boolean showHitboxes;
    private boolean hitboxTogglePressed;

    private int screenX;
    private int screenY;
    private int screenWidth;
    private int screenHeight;

    @Override
    public void create() {
        batch = new SpriteBatch();
        debugRenderer = new ShapeRenderer();
        weaponRenderer = new WeaponRenderer();
        uiFont = new BitmapFont();
        uiFont.getRegion().getTexture().setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );
        glyphLayout = new GlyphLayout();
        showHitboxes = NetworkConfig.shouldShowHitboxes();

        camera = new OrthographicCamera();
        uiCamera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.CAMERA_VIEW_WIDTH, Constants.CAMERA_VIEW_HEIGHT);

        if (NetworkConfig.isClientMode()) {
            try {
                startMultiplayer();
            } catch (IOException exception) {
                stopEmbeddedServer();
                throw new GdxRuntimeException("Could not start multiplayer", exception);
            }
        } else {
            gameWorld = new GameWorld();
        }

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void startMultiplayer() throws IOException {
        String serverHost;
        int serverPort;

        if (NetworkConfig.isHostMode()) {
            startEmbeddedServer();
            String bindHost = NetworkConfig.getServerBindHost();
            serverHost = "0.0.0.0".equals(bindHost) ? "127.0.0.1" : bindHost;
            serverPort = embeddedServer.getLocalAddress().getPort();
        } else {
            serverHost = NetworkConfig.getServerHost();
            serverPort = NetworkConfig.getServerPort();
            if (NetworkConfig.shouldDiscoverServer()) {
                DiscoveredServer selected = discoverServer();
                serverHost = selected.getAddress().getHostAddress();
                serverPort = selected.getGamePort();
                Gdx.app.log("Network", "Discovered " + selected);
            }
        }

        networkClient = new UdpGameClient(
            serverHost,
            serverPort,
            NetworkConfig.getClientBindHost(),
            NetworkConfig.getClientPort(),
            NetworkConfig.getCharacterType(),
            NetworkConfig.getSwordType()
        );
        networkClient.start();
        gameWorld = new GameWorld(networkClient);

        Gdx.app.log(
            "Network",
            NetworkConfig.getMode().toUpperCase() + " client "
                + networkClient.getLocalAddress() + " -> " + networkClient.getServerAddress()
        );
    }

    private void startEmbeddedServer() throws IOException {
        UdpGameServer server = new UdpGameServer(
            NetworkConfig.getServerBindHost(),
            NetworkConfig.getServerPort()
        );
        LanDiscoveryResponder discovery;
        try {
            discovery = new LanDiscoveryResponder(
                "0.0.0.0",
                NetworkConfig.getDiscoveryPort(),
                server.getLocalAddress().getPort(),
                NetworkConfig.getServerName(),
                server::getConnectedPlayerCount
            );
        } catch (IOException exception) {
            server.close();
            throw exception;
        }

        embeddedServer = server;
        embeddedDiscovery = discovery;
        embeddedServer.start();
        embeddedDiscovery.start();
        Gdx.app.log(
            "Network",
            "Hosting '" + NetworkConfig.getServerName() + "' on "
                + embeddedServer.getLocalAddress()
        );
    }

    private DiscoveredServer discoverServer() throws IOException {
        List<DiscoveredServer> servers = LanServerDiscovery.discover(
            NetworkConfig.getDiscoveryPort(),
            NetworkConfig.getDiscoveryTimeoutMillis()
        );
        if (servers.isEmpty()) {
            throw new IOException(
                "No LAN host found. Start one game with game.mode=host before joining."
            );
        }
        return servers.get(0);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        updateHitboxToggle();
        updateCameraFollow();
        camera.update();
        updateMouseAim();
        gameWorld.update(delta);
        updateCameraFollow();

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

        for (RenderableObject object : gameWorld.getRenderables()) {
            object.render(batch);
        }

        renderEquippedWeapons();
        batch.end();

        renderDebugHitboxes();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderUi();
    }

    private void renderDebugHitboxes() {
        if (!showHitboxes) {
            return;
        }

        int localPlayerId = networkClient == null ? 0 : networkClient.getPlayerId();
        debugRenderer.setProjectionMatrix(camera.combined);
        Gdx.gl.glLineWidth(2f);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (Prop prop : gameWorld.getProps()) {
            if (prop.isSolid()) {
                debugRenderer.setColor(Color.MAGENTA);
                debugRenderer.rect(
                    prop.getGameX(),
                    prop.getGameY(),
                    prop.getCollisionWidth(),
                    prop.getCollisionDepth()
                );
            }
        }

        if (networkClient == null) {
            renderPlayerHitboxes(gameWorld.getPlayer(), Color.GREEN);
        } else {
            for (Map.Entry<Integer, Player> entry : gameWorld.getNetworkPlayers().entrySet()) {
                renderPlayerHitboxes(
                    entry.getValue(),
                    entry.getKey() == localPlayerId ? Color.GREEN : Color.YELLOW
                );
            }
        }

        for (SwordAttack attack : gameWorld.getAttacks().values()) {
            boolean localAttack = attack.getOwnerPlayerId() == localPlayerId;
            if (attack.getSwordType() == SwordType.HEAVY) {
                debugRenderer.setColor(localAttack ? Color.BLUE : Color.RED);
            } else {
                debugRenderer.setColor(localAttack ? Color.CYAN : Color.ORANGE);
            }
            renderHitboxes(attack.getGameX(), attack.getGameY(), attack.getHitboxes());
        }
        debugRenderer.end();
        Gdx.gl.glLineWidth(1f);
    }

    private void renderPlayerHitboxes(Player player, Color color) {
        if (player != null) {
            debugRenderer.setColor(color);
            renderHitboxes(player.getGameX(), player.getGameY(), player.getHitboxes());
        }
    }

    private void renderHitboxes(float entityX, float entityY, List<Hitbox> hitboxes) {
        for (Hitbox hitbox : hitboxes) {
            debugRenderer.rect(
                entityX + hitbox.getXOffset(),
                entityY + hitbox.getYOffset(),
                0f,
                0f,
                hitbox.getWidth(),
                hitbox.getHeight(),
                1f,
                1f,
                hitbox.getRotationDegrees()
            );
        }
    }

    private void renderUi() {
        uiCamera.update();
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();

        if (networkClient == null) {
            Player localPlayer = gameWorld.getPlayer();
            if (localPlayer != null) {
                renderPlayerLabel(
                    localPlayer,
                    "YOU - " + localPlayer.getCharacterType().getDisplayName()
                );
            }
        } else {
            int localPlayerId = networkClient.getPlayerId();
            for (Map.Entry<Integer, Player> entry : gameWorld.getNetworkPlayers().entrySet()) {
                Player visiblePlayer = entry.getValue();
                String label = entry.getKey() == localPlayerId
                    ? "YOU (P" + entry.getKey() + ") - "
                        + visiblePlayer.getCharacterType().getDisplayName()
                    : "P" + entry.getKey() + " - "
                        + visiblePlayer.getCharacterType().getDisplayName();
                renderPlayerLabel(visiblePlayer, label);
            }

            String role = NetworkConfig.isHostMode() ? "HOST" : "JOIN";
            String status = networkClient.isConnected()
                ? role + " | You: P" + localPlayerId
                    + " | Players: " + gameWorld.getNetworkPlayers().size()
                : role + " | Connecting...";
            drawOutlined(status, screenX + 8f, screenY + screenHeight - 10f, false);
        }

        drawOutlined(
            "F3 Hitboxes: " + (showHitboxes ? "ON" : "OFF"),
            screenX + 8f,
            screenY + 22f,
            false
        );
        batch.end();
    }

    private void renderPlayerLabel(Player player, String label) {
        labelPoint.set(
            player.getRenderX() + player.getDrawWidth() * 0.5f,
            player.getRenderY() + player.getDrawHeight() + 7f,
            0f
        );
        camera.project(labelPoint, screenX, screenY, screenWidth, screenHeight);
        drawOutlined(label, labelPoint.x, labelPoint.y, true);
    }

    private void drawOutlined(String text, float x, float y, boolean centered) {
        glyphLayout.setText(uiFont, text);
        float drawX = centered ? Math.round(x - glyphLayout.width * 0.5f) : Math.round(x);
        float drawY = Math.round(y);
        uiFont.setColor(Color.BLACK);
        uiFont.draw(batch, text, drawX - 1f, drawY);
        uiFont.draw(batch, text, drawX + 1f, drawY);
        uiFont.draw(batch, text, drawX, drawY - 1f);
        uiFont.draw(batch, text, drawX, drawY + 1f);
        uiFont.setColor(Color.WHITE);
        uiFont.draw(batch, text, drawX, drawY);
    }

    private void renderEquippedWeapons() {
        if (networkClient == null) {
            Player localPlayer = gameWorld.getPlayer();
            if (localPlayer != null) {
                weaponRenderer.render(
                    batch,
                    localPlayer,
                    gameWorld.getActiveAttackForPlayer(0)
                );
            }
            return;
        }

        for (Map.Entry<Integer, Player> entry : gameWorld.getNetworkPlayers().entrySet()) {
            weaponRenderer.render(
                batch,
                entry.getValue(),
                gameWorld.getActiveAttackForPlayer(entry.getKey())
            );
        }
    }

    private void updateMouseAim() {
        int mouseX = MathUtils.clamp(Gdx.input.getX(), screenX, screenX + screenWidth - 1);
        int mouseY = MathUtils.clamp(
            Gdx.input.getY(),
            Gdx.graphics.getHeight() - screenY - screenHeight,
            Gdx.graphics.getHeight() - screenY - 1
        );
        mouseWorld.set(mouseX, mouseY, 0f);
        camera.unproject(mouseWorld, screenX, screenY, screenWidth, screenHeight);
        gameWorld.setLocalAimTarget(mouseWorld.x, mouseWorld.y);
    }

    private void updateHitboxToggle() {
        boolean pressed = Gdx.input.isKeyPressed(Input.Keys.F3);
        if (pressed && !hitboxTogglePressed) {
            showHitboxes = !showHitboxes;
            Gdx.app.log("Debug", "Hitbox overlay " + (showHitboxes ? "enabled" : "disabled"));
        }
        hitboxTogglePressed = pressed;
    }

    private void updateCameraFollow() {
        Player localPlayer = gameWorld == null ? null : gameWorld.getPlayer();
        float targetX = localPlayer == null
            ? WorldLayout.getWorldWidth() * 0.5f
            : localPlayer.getGameX() + localPlayer.getDrawWidth() * 0.5f;
        float targetY = localPlayer == null
            ? WorldLayout.getWorldHeight() * 0.5f
            : localPlayer.getGameY() + localPlayer.getDrawHeight() * 0.5f;

        float halfWidth = camera.viewportWidth * camera.zoom * 0.5f;
        float halfHeight = camera.viewportHeight * camera.zoom * 0.5f;
        camera.position.set(
            clampCameraAxis(targetX, halfWidth, WorldLayout.getWorldWidth()),
            clampCameraAxis(targetY, halfHeight, WorldLayout.getWorldHeight()),
            0f
        );
    }

    private float clampCameraAxis(float target, float halfView, float worldSize) {
        if (halfView * 2f >= worldSize) {
            return worldSize * 0.5f;
        }
        return MathUtils.clamp(target, halfView, worldSize - halfView);
    }

    @Override
    public void resize(int width, int height) {
        int logicalWidth = Math.round(Constants.CAMERA_VIEW_WIDTH);
        int logicalHeight = Math.round(Constants.CAMERA_VIEW_HEIGHT);
        int scaleX = width / logicalWidth;
        int scaleY = height / logicalHeight;

        int scale = Math.min(scaleX, scaleY);
        scale = Math.max(1, scale);

        screenWidth = logicalWidth * scale;
        screenHeight = logicalHeight * scale;

        screenX = (width - screenWidth) / 2;
        screenY = (height - screenHeight) / 2;

        camera.setToOrtho(false, Constants.CAMERA_VIEW_WIDTH, Constants.CAMERA_VIEW_HEIGHT);
        uiCamera.setToOrtho(false, width, height);
        float fontScale = MathUtils.clamp(height / 720f, 1f, 1.75f);
        uiFont.getData().setScale(fontScale);
        updateCameraFollow();
    }

    @Override
    public void dispose() {
        if (networkClient != null) {
            networkClient.close();
        }
        stopEmbeddedServer();
        uiFont.dispose();
        batch.dispose();
        debugRenderer.dispose();
        weaponRenderer.dispose();
        gameWorld.dispose();
    }

    private void stopEmbeddedServer() {
        if (embeddedDiscovery != null) {
            embeddedDiscovery.close();
            embeddedDiscovery = null;
        }
        if (embeddedServer != null) {
            embeddedServer.close();
            embeddedServer = null;
        }
    }
}
