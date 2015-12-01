package com.shane.tapit;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by shane on 12/11/15.
 */
public class Bubble implements Pool.Poolable{
    private static final double HALO_MARGIN=10;
    private static final double HALO_SIZE=200;
    private static final int BUBBLE_RADIUS =55;
    private double haloSpeed;
    private boolean live;

    private double x,y;
    private double haloRadius=HALO_SIZE;

    public Bubble(double speed) {
        this.live=true;
        this.haloSpeed=speed;
        this.x=MathUtils.random(BUBBLE_RADIUS,Constants.VIEWPORT_WIDTH- BUBBLE_RADIUS);
        this.y=MathUtils.random(BUBBLE_RADIUS,Constants.VIEWPORT_HEIGHT- BUBBLE_RADIUS);
    }

    public void setSpeed(double speed) {
        haloSpeed=speed;
    }

    public static double getHaloDist() {
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
        sr.circle((float) x, (float) y, BUBBLE_RADIUS);
        sr.setColor(0,0,0,1);
        sr.set(ShapeRenderer.ShapeType.Line);
        sr.circle((float)x, (float)y, (float)haloRadius);
    }

    public void update(double delta) {
        haloRadius-= haloSpeed *delta;
        if (haloRadius<=0) {
            haloRadius=HALO_SIZE;
        }
    }

    public boolean shouldDieBefore(Vector2 point) {
        boolean b=(isTouched(point) && !isTouchValid());
        if (live && b) {
            live=false;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean shouldDieAfter() {
        boolean b=(haloRadius<BUBBLE_RADIUS-HALO_MARGIN);
        if (live && b) {
            live=false;
            return true;
        }
        else {
            return false;
        }
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
