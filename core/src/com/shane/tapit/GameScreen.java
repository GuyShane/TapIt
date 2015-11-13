package com.shane.tapit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by shane on 12/11/15.
 */
public class GameScreen extends ScreenAdapter {
    private Camera camera;
    private Viewport viewport;
    private ShapeRenderer sr;
    private Bubble bubble;
    private InputHandler in;

    public GameScreen() {

    }

    @Override
    public void show() {
        camera=new OrthographicCamera(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        camera.position.set(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_HEIGHT / 2, 0);
        camera.update();
        viewport=new FitViewport(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT,camera);
        sr=new ShapeRenderer();
        bubble=new Bubble(100,100);
        in=new InputHandler(viewport);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (in.isTouching()) {
            if (bubble.isTapping(in.getTouch())) {
                bubble.setPosition(MathUtils.random(100,1100),MathUtils.random(100,700));
            }
        }
        bubble.update(delta);
        sr.setProjectionMatrix(camera.projection);
        sr.setTransformMatrix(camera.view);
        sr.setAutoShapeType(true);
        sr.begin();
        bubble.draw(sr);
        sr.end();
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
