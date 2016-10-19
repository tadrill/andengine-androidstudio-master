package com.tadrill.andengine;

/**
 * Created by tadrip on 10/17/16.
 */

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import org.andengine.extension.physics.box2d.PhysicsWorld;

import java.util.Iterator;

public class MaxStepPhysicsWorld extends PhysicsWorld {
    public static final int STEPSPERSECOND_DEFAULT = 60;
    private final float mStepLength;
    public MaxStepPhysicsWorld(final int pStepsPerSecond, final Vector2 pGravity, final boolean pAllowSleep) {
        super(pGravity, pAllowSleep);
        this.mStepLength = 1.0f / pStepsPerSecond;
    }

    public MaxStepPhysicsWorld(final int pStepsPerSecond, final Vector2 pGravity, final boolean pAllowSleep, final int pVelocityIterations, final int pPositionIterations) {
        super(pGravity, pAllowSleep, pVelocityIterations, pPositionIterations);
        this.mStepLength = 1.0f / pStepsPerSecond;
    }

    @Override
    public void onUpdate(final float pSecondsElapsed) {
        this.mRunnableHandler.onUpdate(pSecondsElapsed);

        float stepLength = pSecondsElapsed;
        if(pSecondsElapsed>= this.mStepLength){
            stepLength = this.mStepLength;
        }
        this.mWorld.step(stepLength, this.mVelocityIterations, this.mPositionIterations);

        this.mPhysicsConnectorManager.onUpdate(pSecondsElapsed);

        // delete tiles way off screen
        Iterator<Body> it = this.getBodies();
        while (it.hasNext()) {
            Body b = it.next();
            if (b.getPosition().x < -200) {
                b.setActive(false);
                this.destroyBody(b);
            }
        }
        if (SceneManager.getInstance().getCurrentScene().gameOver()) {
            SceneManager.getInstance().setScene(new GameOver());
        }
        // Add next tile if possible
        ResourceManager.getInstance().tileManager.addTileToScene(this);
    }

}
