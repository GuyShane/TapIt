package com.shane.tapit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by shane on 15/11/15.
 */
public class StartScreen extends ScreenAdapter {
    private Stage stage;

    @Override
    public void show() {
        stage=new Stage(new FitViewport(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        TextButton button=new TextButton("Play",new Skin());
        button.setPosition(Constants.VIEWPORT_WIDTH/2,Constants.VIEWPORT_HEIGHT/2, Align.center);
        button.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                System.out.println("Pressed");
            }
        });
        stage.addActor(button);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void dispose() {

    }
}
