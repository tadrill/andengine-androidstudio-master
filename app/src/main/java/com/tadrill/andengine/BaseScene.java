package com.tadrill.andengine;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


public abstract class BaseScene extends Scene {
    protected Engine engine;
    protected VertexBufferObjectManager vbox;
    protected MainActivity activity;
    protected Camera camera;

    public BaseScene() {
        this.engine = ResourceManager.getInstance().engine;
        this.vbox = ResourceManager.getInstance().vbox;
        this.camera = ResourceManager.getInstance().camera;
        this.activity = ResourceManager.getInstance().activity;
    }

    protected Sprite createSprite(float x, float y, ITextureRegion region, VertexBufferObjectManager vbox) {
        Sprite sprite = new Sprite(x, y, region, vbox) {
            @Override
            protected void preDraw(GLState glState, Camera camera) {
                super.preDraw(glState, camera);
                glState.enableDither();
            }
        };
        return sprite;
    }

    protected boolean touch() {
        return false;
    }

    protected boolean gameOver() { return false; }

    public abstract void createScene();

    public abstract void onBackPressed();

    public abstract void disposeScene();

}
