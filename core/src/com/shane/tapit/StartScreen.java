package com.shane.tapit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by shane on 15/11/15.
 */
public class StartScreen extends ScreenAdapter {
    private Game game;
    private AssetManager am;
    private Stage stage;

    private Texture play;
    private Texture playPressed;

    public StartScreen(Game game) {
        this.game=game;
        am=new AssetManager();
        am.load("play.png", Texture.class);
        am.load("play_pressed.png",Texture.class);
        am.finishLoading();
    }

    @Override
    public void show() {
        stage=new Stage(new FitViewport(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        play = am.get("play.png");
        playPressed = am.get("play_pressed.png");

        ImageButton button=new ImageButton(new TextureRegionDrawable(new TextureRegion(play)),
                new TextureRegionDrawable(new TextureRegion(playPressed)));
        button.setPosition(Constants.VIEWPORT_WIDTH/2,Constants.VIEWPORT_HEIGHT/4, Align.center);
        button.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new GameScreen());
                dispose();
            }
        });
        stage.addActor(button);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
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
        play.dispose();
        playPressed.dispose();
        stage.dispose();
        am.dispose();
    }
}
