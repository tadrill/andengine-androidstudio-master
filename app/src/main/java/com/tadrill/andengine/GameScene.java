package com.tadrill.andengine;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

/**
 * Created by tadrip on 10/15/16.
 */
public class GameScene extends BaseScene {
    private HUD gameHUD;
    private PhysicsWorld physicsWorld;
    ///// PLAYER BODY CONTAINS METHODS FOR A CONSTANT HORIZONTAL FORCE
    // playerBody.applyForce()
    private Body playerBody;
//    private Sprite playerSprite;
    private AnimatedSprite playerSprite;
    private boolean isJumping = true;
    private Text text;

    @Override
    public void createScene() {
        setBackground();
        createHUD();
        createPhysics();
        addPlayer();
        createTiles();
        this.setOnSceneTouchListener(ResourceManager.getInstance().activity);
        // camera follow player
//        camera.setChaseEntity(playerSprite);
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    @Override
    public void disposeScene() {
        ResourceManager.getInstance().unloadGameResources();
        camera.setHUD(null);
    }

    private void setBackground() {
        setBackground(new Background(Color.GREEN));
    }

    private void createHUD() {
        gameHUD = new HUD();
        camera.setHUD(gameHUD);
        text = new Text(75, 30, ResourceManager.getInstance().hudFont,
                "Score: " + ResourceManager.score, new TextOptions(HorizontalAlign.CENTER), ResourceManager.getInstance().vbox);
        gameHUD.attachChild(text);
    }

    private void createPhysics() {
        // gravity horizontal AND vertical for Vector2()
        physicsWorld = new MaxStepPhysicsWorld(60, new Vector2(0, SensorManager.GRAVITY_EARTH * 2), false);
        physicsWorld.setContactListener(createContactListener());
        registerUpdateHandler(physicsWorld);
    }

    private void addPlayer() {
        final FixtureDef playerFixtureDef = PhysicsFactory.createFixtureDef(0.5f, 0.0f, 0.0f);
        playerSprite = new AnimatedSprite(100, 150, ResourceManager.getInstance().player, ResourceManager.getInstance().vbox);
        playerSprite.animate(60);
        playerBody = PhysicsFactory.createBoxBody(physicsWorld, playerSprite, BodyDef.BodyType.DynamicBody, playerFixtureDef);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(playerSprite, playerBody, true, false));
        attachChild(playerSprite);
        playerBody.setLinearVelocity(0, 0);
    }


    private void createTiles() {
        ITextureRegion building = ResourceManager.getInstance().tileManager.getTileById(1).getTextureRegion();
        ResourceManager.getInstance().tileManager.getTileById(5).getInstance(25, camera.getHeight() - ResourceManager.getInstance().tileManager.getTileById(5).getHeight()).createBodyAndAttach(this, physicsWorld);
//        ResourceManager.getInstance().tileManager.getTileById(1).getInstance(125, camera.getHeight() - building.getHeight()).createBodyAndAttach(this, physicsWorld);
//        ResourceManager.getInstance().tileManager.getTileById(2).getInstance(375, camera.getHeight() - ResourceManager.getInstance().tileManager.getTileById(2).getHeight()).createBodyAndAttach(this, physicsWorld);
//        ResourceManager.getInstance().tileManager.getTileById(1).getInstance(630, camera.getHeight() - building.getHeight()).createBodyAndAttach(this, physicsWorld);
        addFirst();
    }

    private void addFirst() {
        ResourceManager.getInstance().tileManager.first(this, physicsWorld);
    }

    public boolean gameOver() {
        if (playerBody.getPosition().x < 0 || playerBody.getPosition( ).y + playerSprite.getHeight()
                > ResourceManager.getInstance().camera.getHeight()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean touch() {
        if (!isJumping)
            playerBody.applyLinearImpulse(new Vector2(0, -45f), playerBody.getPosition());
        return true;
    }

    private ContactListener createContactListener() {
        ContactListener contactListener = new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                isJumping = false;
                ResourceManager.getInstance().score++;
            }

            @Override
            public void endContact(Contact contact) {
                isJumping = true;
                gameHUD.detachChild(text);
                text = new Text(75, 30, ResourceManager.getInstance().hudFont,
                        "Score: " + ResourceManager.score, new TextOptions(HorizontalAlign.CENTER), ResourceManager.getInstance().vbox);
                gameHUD.attachChild(text);
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        };
        return contactListener;
    }
}