package com.shane.tapit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by shane on 12/11/15.
 */
public class InputHandler {
    private Viewport viewport;

    public InputHandler(Viewport viewport) {
        this.viewport=viewport;
    }

    public boolean isTouching() {
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    }

    public Vector2 getTouch() {
        return viewport.unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));
    }
}
