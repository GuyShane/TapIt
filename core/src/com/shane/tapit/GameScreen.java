package com.shane.tapit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by shane on 12/11/15.
 */
public class GameScreen extends ScreenAdapter {
    private Game game;
    private Camera camera;
    private Viewport viewport;
    private ShapeRenderer sr;
    private BubbleMaster bubbles;
    private InputHandler in;
    private AssetManager am;
    private Texture bgTexture;
    private SpriteBatch batch;
    private float y=0;

    public GameScreen(Game game) {
        am=new AssetManager();
        am.load("bg.png",Texture.class);
        this.game=game;
        am.finishLoading();
    }

    @Override
    public void show() {
        camera=new OrthographicCamera(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        camera.position.set(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_HEIGHT / 2, 0);
        camera.update();
        viewport=new FitViewport(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT,camera);
        sr=new ShapeRenderer();
        bubbles=new BubbleMaster();
        in=new InputHandler(viewport);
        batch=new SpriteBatch();
        bgTexture=am.get("bg.png");
    }

    @Override
    public void render(float delta) {
        y-=10*delta;
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(bgTexture,0,0,1280,6400,0,(int)y,640,3200,false,false);
        batch.end();
        if (in.isTouching() && in.isNewTouchEvent()) {
            if (bubbles.shouldPop(in.getTouch())) {
                bubbles.pop();
            }
            else if (bubbles.shouldDieBefore(in.getTouch())) {
                game.setScreen(new EndScreen(game));
                dispose();
            }
        }
        if (bubbles.shouldDieAfter()) {
            game.setScreen(new EndScreen(game));
            dispose();
        }
        bubbles.update(delta);
        in.update();
        sr.setProjectionMatrix(camera.projection);
        sr.setTransformMatrix(camera.view);
        sr.setAutoShapeType(true);
        sr.begin();
        drawLives();
        bubbles.draw(sr);
        sr.end();
    }

    private void drawLives() {
        sr.setColor(0, 0, 0, 1);
        sr.set(ShapeRenderer.ShapeType.Filled);
        for (int i=0;i<bubbles.getLives();i++) {
            int r=15;
            int dist=30;
            sr.circle(r*2+dist*(i+1),Constants.VIEWPORT_HEIGHT-(r+dist),r);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}
