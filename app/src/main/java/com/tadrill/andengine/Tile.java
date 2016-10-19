package com.tadrill.andengine;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by tadrip on 10/16/16.
 */
public class Tile extends Sprite {
    private final String name;
    private final int id;
    private final float density, elastic, friction;

    public Tile(String name, int id, float x, float y, float density, float elastic, float friction, ITextureRegion texture, VertexBufferObjectManager vbox) {
        super(x, y, texture, vbox);
        this.id = id;
        this.name = name;
        this.density = density;
        this.elastic = elastic;
        this.friction = 0;

    }
    public void createBodyAndAttach(Scene scene, PhysicsWorld physicsWorld) {
        final FixtureDef tileFixtureDef = PhysicsFactory.createFixtureDef(density, elastic, friction);
        Body body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyDef.BodyType.KinematicBody, tileFixtureDef);
        scene.attachChild(this);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, true));
        body.setLinearVelocity(-5f, 0);
    }

    public String getName() {
        return name;

    }

    public int getId() {
        return id;

    }

    public Tile getInstance(float x, float y) {
        return new Tile(name, id, x, y, density, elastic, friction, getTextureRegion(), getVertexBufferObjectManager());

    }
}
