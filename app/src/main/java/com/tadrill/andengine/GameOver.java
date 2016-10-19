package com.tadrill.andengine;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

/**
 * Created by tadrip on 10/18/16.
 */
public class GameOver extends BaseScene implements MenuScene.IOnMenuItemClickListener {
    private MenuScene menu;
    private final int MENU_PLAY = 0;
    private final int MENU_EXIT = 1;

    public GameOver() {
        createScene();
    }

    @Override
    public void createScene() {
        setBackground(new Background(Color.WHITE));
        createMenu();
    }

    private void createMenu() {
        menu = new MenuScene(camera);
        menu.setPosition(0,0);
        // Giving the menu items (the back and the go button) their id, as well as scaling
        // and their paths
        ResourceManager.getInstance().loadMenuResources();
        final IMenuItem playItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(MENU_PLAY, ResourceManager.getInstance().goButton, vbox), 1.2f, 1);
        final IMenuItem exitItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(MENU_EXIT, ResourceManager.getInstance().exitButton, vbox), 1.2f, 1);
        menu.addMenuItem(playItem);
        menu.addMenuItem(exitItem);
        menu.buildAnimations();
        menu.setBackgroundEnabled(false);

        playItem.setPosition(playItem.getX(), playItem.getY() + 125);
        exitItem.setPosition(exitItem.getX(), exitItem.getY() - 50);
        ResourceManager.getInstance().loadFonts();
        Text text = new Text(250, 40, ResourceManager.getInstance().mFont, "Game Over\nScore: " + ResourceManager.getInstance().score, new TextOptions(HorizontalAlign.CENTER), ResourceManager.getInstance().vbox);
        ResourceManager.getInstance().score = 0;
        menu.attachChild(text);
        menu.setOnMenuItemClickListener(this);
        this.setChildScene(menu);
    }
    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    @Override
    public void disposeScene() {
        ResourceManager.getInstance().unloadMenuResources();
    }

    @Override
    public boolean onMenuItemClicked(MenuScene scene, IMenuItem item,
                                     float localX, float localY) {
        switch(item.getID()) {
            case MENU_PLAY:
                SceneManager.getInstance().setGameScene();
                return true;
            case MENU_EXIT:
                System.exit(0);
                return true;
        }
        return false;
    }
}
