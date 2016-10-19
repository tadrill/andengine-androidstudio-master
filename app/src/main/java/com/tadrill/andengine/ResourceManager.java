package com.tadrill.andengine;

import android.graphics.Color;
import android.graphics.Typeface;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

/**
 * Created by tadrip on 10/15/16.
 */
public class ResourceManager {
    public static final ResourceManager INSTANCE = new ResourceManager();

    public Engine engine;
    public MainActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbox;

    private BuildableBitmapTextureAtlas menuAtlas;
    public ITextureRegion goButton;
    public ITextureRegion exitButton;
    private BuildableBitmapTextureAtlas gameAtlas;
//    public ITextureRegion player;
    public TiledTextureRegion player;
    private BuildableBitmapTextureAtlas tileAtlas;
    public ITextureRegion basic, thin, tall, shorty, starting;
    public IFont mFont;
    public Font hudFont;

    public TileManager tileManager;

    public static int score;
    public static int highScore;


    public void loadMenuResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        menuAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 2048, TextureOptions.NEAREST);
        goButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuAtlas, activity, "next.png");
        exitButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuAtlas, activity, "menu_quit.png");

        try {
            menuAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            menuAtlas.load();
        } catch (Exception e) {
            Debug.e(e);
        }
    }

    public void loadTileResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/tiles/");
        tileAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 2048, TextureOptions.BILINEAR);
        basic = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tileAtlas, activity, "basic.png");
        thin = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tileAtlas, activity, "thin.png");
        tall = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tileAtlas, activity, "tall.png");
        shorty = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tileAtlas, activity, "short.png");
        starting = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tileAtlas, activity, "start.png");
        try {
            tileAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            tileAtlas.load();
        } catch (Exception e) {
            Debug.e(e);
        }

    }

    public void unloadMenuResources() {
        menuAtlas.unload();
        menuAtlas = null;
    }

    public void loadGameResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        gameAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        player = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameAtlas, activity, "runner.png", 5, 2); //.createFromAsset(gameAtlas, activity, "player.png");

        try {
            gameAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            gameAtlas.load();
        } catch (Exception e) {
            Debug.e(e);
        }
    }

    public void unloadGameResources() {
        gameAtlas.unload();
        gameAtlas = null;
    }

    public void loadFonts() {
        this.mFont = FontFactory.create(activity.getFontManager(), activity.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 64);
        FontFactory.setAssetBasePath("font/");

        ITexture fontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        this.hudFont = FontFactory.createFromAsset(activity.getFontManager(), fontTexture, activity.getAssets(), "NeverwinterNights.ttf", 38, true, Color.DKGRAY);
        this.hudFont.load();
        this.mFont.load();

    }

    public void loadTileManager() {
        loadTileResources();
        tileManager = new TileManager(vbox);
    }

    public static void prepareManager(Engine engine, MainActivity activity, Camera camera, VertexBufferObjectManager vbox) {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbox = vbox;
        score = 0;
        highScore = 0;
    }

    public static ResourceManager getInstance() {
        return INSTANCE;
    }
}
