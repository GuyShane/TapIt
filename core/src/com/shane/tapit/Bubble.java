package com.shane.tapit;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.awt.Point;

/**
 * Created by shane on 12/11/15.
 */
public class Bubble {
    private float x,y;
    private float haloSpeed=50;
    private int bubbleRadius=55;
    private float haloRadius=170;

    public Bubble(float x, float y) {
        this.x=x;
        this.y=y;
    }

    public void setPosition(float x, float y) {
        this.haloRadius=170;
        this.x=x;
        this.y=y;
    }

    public void draw(ShapeRenderer sr) {
        sr.setColor(0, 1, 0.6f, 1);
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.circle(x, y, bubbleRadius);
        sr.set(ShapeRenderer.ShapeType.Line);
        sr.circle(x, y, haloRadius);
    }

    public void update(float delta) {
        haloRadius-=haloSpeed*delta;
        if (haloRadius<bubbleRadius) {
            haloRadius=170;
        }
    }

    public boolean isTapping(Vector2 point) {
        return ((int)Math.sqrt(Math.pow((point.x-x),2)+Math.pow((point.y-y),2)))<=bubbleRadius;
    }
}
