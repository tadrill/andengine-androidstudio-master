package com.tadrill.andengine;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;

public class MainActivity extends BaseGameActivity implements IOnSceneTouchListener {

    private Camera camera;

    @Override
    public EngineOptions onCreateEngineOptions() {
        camera = new Camera(0,0,800,480);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(800, 480), camera);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback cb) throws Exception {
        ResourceManager.prepareManager(getEngine(), this, camera, getVertexBufferObjectManager());
        cb.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback cb) throws Exception {
        SceneManager.getInstance().setMenuScene(cb);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public boolean onSceneTouchEvent(Scene s, TouchEvent event) {
        if (event.isActionDown()) {
            boolean res = SceneManager.getInstance().getCurrentScene().touch();
            return res;
        }
        return false;
    }
}


