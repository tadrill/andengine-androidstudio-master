package com.tadrill.andengine;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface;

/**
 * Created by tadrip on 10/15/16.
 */
public class SceneManager {

    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene currentScene;

    private Engine engine = ResourceManager.getInstance().engine;

    private static final SceneManager INSTANCE = new SceneManager();

    public enum SceneType {
        SCENE_MENU,
        SCENE_GAME,
    }

    public void setGameScene() {
        ResourceManager.getInstance().loadGameResources();
        ResourceManager.getInstance().loadTileManager();
        gameScene = new GameScene();
        setScene(gameScene);
        currentScene.createScene();

    }
    public void setMenuScene(IGameInterface.OnCreateSceneCallback cb) {
        ResourceManager.getInstance().loadMenuResources();
        menuScene = new MainMenuScene();

        setScene(menuScene);

        currentScene.createScene();
        cb.onCreateSceneFinished(menuScene);
    }

    public void setScene(BaseScene scene) {
        if (currentScene != null) {
            currentScene.disposeScene();
        }
        engine.setScene(scene);
        currentScene = scene;
    }

    public void setScene(SceneType type) {
        switch (type) {
            case SCENE_GAME:
                setScene(menuScene);
                break;
            case SCENE_MENU:
                setScene(gameScene);
                break;
        }
    }

    public BaseScene getCurrentScene() {
        return currentScene;
    }


    public static SceneManager getInstance() {
        return INSTANCE;
    }
}
