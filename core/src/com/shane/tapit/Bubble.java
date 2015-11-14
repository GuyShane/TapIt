package com.shane.tapit;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by shane on 12/11/15.
 */
public class Bubble implements Pool.Poolable{
    private static final float HALO_MARGIN=5;
    private static final float HALO_SIZE=180;
    private float haloSpeed = 60;

    private float x,y;
    private int bubbleRadius=55;
    private float haloRadius=HALO_SIZE;
    private float bubbleTime;

    public Bubble() {
        this.x=MathUtils.random(bubbleRadius,Constants.VIEWPORT_WIDTH-bubbleRadius);
        this.y=MathUtils.random(bubbleRadius,Constants.VIEWPORT_HEIGHT-bubbleRadius);
        this.bubbleTime=(HALO_SIZE-bubbleRadius)/haloSpeed;
    }

    private void replace() {
        this.haloRadius=HALO_SIZE;
        this.x=MathUtils.random(bubbleRadius,Constants.VIEWPORT_WIDTH-bubbleRadius);
        this.y=MathUtils.random(bubbleRadius,Constants.VIEWPORT_HEIGHT-bubbleRadius);
    }

    public void draw(ShapeRenderer sr) {
        sr.setColor(0, 1, 0.6f, 1);
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.circle(x, y, bubbleRadius);
        sr.set(ShapeRenderer.ShapeType.Line);
        sr.circle(x, y, haloRadius);
    }

    public void update(float delta) {
        haloRadius-= haloSpeed *delta;
        if (haloRadius<=0) {
            haloRadius=HALO_SIZE;
        }
    }

    public boolean shouldDie(Vector2 point) {
        return (isTouched(point) && !isTouchValid()) || (haloRadius<bubbleRadius-HALO_MARGIN);
    }

    public boolean shouldPop(Vector2 point) {
        return isTouched(point) && isTouchValid();
    }

    private boolean isTouchValid() {
        return (haloRadius>=bubbleRadius-HALO_MARGIN && haloRadius<=bubbleRadius+HALO_MARGIN);
    }

    private boolean isTouched(Vector2 point) {
        return ((int)Math.sqrt(Math.pow((point.x-x),2)+Math.pow((point.y-y),2)))<=bubbleRadius;
    }

    @Override
    public void reset() {
        replace();
    }
}
