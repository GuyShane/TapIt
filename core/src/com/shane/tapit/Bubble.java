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
    private static final int BUBBLE_RADIUS =55;
    private float haloSpeed;

    private float x,y;
    private float haloRadius=HALO_SIZE;

    public Bubble() {
        this.x=MathUtils.random(BUBBLE_RADIUS,Constants.VIEWPORT_WIDTH- BUBBLE_RADIUS);
        this.y=MathUtils.random(BUBBLE_RADIUS,Constants.VIEWPORT_HEIGHT- BUBBLE_RADIUS);
    }

    public Bubble(float speed) {
        this.haloSpeed=speed;
        this.x=MathUtils.random(BUBBLE_RADIUS,Constants.VIEWPORT_WIDTH- BUBBLE_RADIUS);
        this.y=MathUtils.random(BUBBLE_RADIUS,Constants.VIEWPORT_HEIGHT- BUBBLE_RADIUS);
    }

    public void setSpeed(float speed) {
        haloSpeed=speed;
    }

    public static float getHaloDist() {
        return HALO_SIZE-BUBBLE_RADIUS;
    }

    private void replace() {
        this.haloRadius=HALO_SIZE;
        this.x=MathUtils.random(BUBBLE_RADIUS,Constants.VIEWPORT_WIDTH- BUBBLE_RADIUS);
        this.y=MathUtils.random(BUBBLE_RADIUS,Constants.VIEWPORT_HEIGHT- BUBBLE_RADIUS);
    }

    public void draw(ShapeRenderer sr) {
        sr.setColor(0, 1, 0.6f, 1);
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.circle(x, y, BUBBLE_RADIUS);
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
        return (isTouched(point) && !isTouchValid()) || (haloRadius< BUBBLE_RADIUS -HALO_MARGIN);
    }

    public boolean shouldPop(Vector2 point) {
        return isTouched(point) && isTouchValid();
    }

    private boolean isTouchValid() {
        return (haloRadius>= BUBBLE_RADIUS -HALO_MARGIN && haloRadius<= BUBBLE_RADIUS +HALO_MARGIN);
    }

    private boolean isTouched(Vector2 point) {
        return ((int)Math.sqrt(Math.pow((point.x-x),2)+Math.pow((point.y-y),2)))<= BUBBLE_RADIUS;
    }

    @Override
    public void reset() {
        replace();
    }
}
