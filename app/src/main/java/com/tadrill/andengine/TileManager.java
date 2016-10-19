package com.tadrill.andengine;

import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by tadrip on 10/16/16.
 */
public class TileManager {
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private Random r = new Random();
    private Tile lastAdded;
    private Tile toAdd;

    private Tile canAddTile() {
        // cover all cases between lastAdded and toAdd
        int xcord = (int) lastAdded.getX();
        int camWidth = (int) ResourceManager.getInstance().camera.getWidth();
        int camHeight = (int) ResourceManager.getInstance().camera.getHeight();
        Tile temp = toAdd;

        int tileDistance = (int) (-xcord - lastAdded.getWidth() + camWidth);

        int space = 50; // wanted to space out everything evenly to more easily test movement speeds/gravity values.
        int distance1to1 = 125 + space;
        int distance1to2 = 100 + space;
        int distance1to3 = 65 + space;
        int distance1to4 = 100 + space;

        int distance2to1 = 135+ space;
        int distance2to2 = 120+ space;
        int distance2to3 = 80+ space;
        int distance2to4 = 40+ space;

        int distance3to1 = 150+ space;
        int distance3to2 = 155+ space;
        int distance3to3 = 125+ space;
        int distance3to4 = 40+ space;

        int distance4to1 = 110+ space;
        int distance4to2 = 120+ space;
        int distance4to3 = 75 + space;
        int distance4to4 = 125+ space;

        // lastAdded was tile basic
        if (lastAdded.getId() == 1) {
            if ((toAdd.getId() == 1 && tileDistance > distance1to1) ||
                    (toAdd.getId() == 2 && tileDistance > distance1to2) ||
                    (toAdd.getId() == 3 && tileDistance > distance1to3) ||
                    (toAdd.getId() == 4 && tileDistance > distance1to4)) {
                toAdd = null;
                return temp;
            }

        // lastAdded was tile thin
        } else if (lastAdded.getId() == 2) {
            if ((toAdd.getId() == 1 && tileDistance > distance2to1) ||
                    (toAdd.getId() == 2 && tileDistance > distance2to2) ||
                    (toAdd.getId() == 3 && tileDistance > distance2to3) ||
                    (toAdd.getId() == 4 && tileDistance > distance2to4)) {
                toAdd = null;
                return temp;
            }
        }
        // lastAdded was tile thin
        else if (lastAdded.getId() == 3) {
            if ((toAdd.getId() == 1 && tileDistance > distance3to1) ||
                    (toAdd.getId() == 2 && tileDistance > distance3to2) ||
                    (toAdd.getId() == 3 && tileDistance > distance3to3) ||
                    (toAdd.getId() == 4 && tileDistance > distance3to4)) {
                toAdd = null;
                return temp;
            }
        } else if (lastAdded.getId() == 4) {
            if ((toAdd.getId() == 1 && tileDistance > distance4to1) ||
                    (toAdd.getId() == 2 && tileDistance > distance4to2) ||
                    (toAdd.getId() == 3 && tileDistance > distance4to3) ||
                    (toAdd.getId() == 4 && tileDistance > distance4to4)) {
                toAdd = null;
                return temp;
            }
        }
        return null;
    }

    public void addTileToScene(MaxStepPhysicsWorld physicsWorld) {
        Tile t = ResourceManager.getInstance().tileManager.canAddTile();
        if (t != null) {
            lastAdded = t.getInstance(800, ResourceManager.getInstance().camera.getHeight() - t.getHeight());
            lastAdded.createBodyAndAttach(SceneManager.getInstance().getCurrentScene(), physicsWorld);
//            Log.d("addingTile...", "should be just 1"); // and is. glitch not happening here
            pickNextTile();
        }
    }

    public void pickNextTile() {
        toAdd = this.getTileById(randomBetween(1, 4));
    }

    private int randomBetween(int min, int max) {
        return r.nextInt((max - min) + 1) + min;
    }


    public TileManager(VertexBufferObjectManager vbox) {
        tiles.add(new Tile("basic", 1, 0, 0, 5f, 0f, 0, ResourceManager.getInstance().basic, vbox));
        tiles.add(new Tile("thin", 2, 0, 0, 5f, 0f, 0, ResourceManager.getInstance().thin, vbox));
        tiles.add(new Tile("tall", 3, 0, 0, 5f, 0f, 0, ResourceManager.getInstance().tall, vbox));
        tiles.add(new Tile("short", 4, 0, 0, 5f, 0f, 0, ResourceManager.getInstance().shorty, vbox));
        tiles.add(new Tile("start", 5, 0, 0, 5f, 0f, 0, ResourceManager.getInstance().starting, vbox));
        Tile t = this.getTileById(1);
        lastAdded = t.getInstance(ResourceManager.getInstance().camera.getWidth() + 75,
                ResourceManager.getInstance().camera.getHeight() - t.getHeight());
        pickNextTile();
    }

    public Tile getTileById(int id) {
        for (Tile t : tiles) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }


    public void first(Scene s, PhysicsWorld physicsWorld) {
        lastAdded.createBodyAndAttach(s, physicsWorld);
    }
}
