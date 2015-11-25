package com.shane.tapit;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by shane on 13/11/15.
 */
public class BubbleMaster {
    private static final double HALO_SPEED=60;

    private Pool<Bubble> bubblePool;
    private Array<Bubble> bubbles;
    private double waitTime;
    private double haloSpeed;
    private double timer=0;
    private int popped=0;
    private int lives;
    private int level;

    public BubbleMaster() {
        lives=3;
        level=1;
        setSpeed();
        bubbles=new Array<Bubble>();
        bubblePool=new Pool<Bubble>() {
            @Override
            protected Bubble newObject() {
                return new Bubble(haloSpeed);
            }
        };
        addBubble();
    }

    public int getLives() {
        return lives;
    }

    private void levelUp() {
        if (level<8) {
            level++;
        }
        setSpeed();
    }

    private void setSpeed() {
        haloSpeed=HALO_SPEED*Math.pow(1.2,level-1);
        waitTime=Bubble.getHaloDist()/(haloSpeed*2);
    }

    private void addBubble() {
        Bubble b=bubblePool.obtain();
        b.setSpeed(haloSpeed);
        bubbles.add(b);
    }

    public void pop() {
        bubblePool.free(bubbles.removeIndex(0));
        popped++;
        if (popped%10==0) {
            levelUp();
        }
    }

    public boolean shouldPop(Vector2 point) {
        Bubble activeBubble=bubbles.first();
        return activeBubble.shouldPop(point);
    }

    public boolean shouldDie(Vector2 point) {
        Bubble activeBubble=bubbles.first();
        if (activeBubble.shouldDie(point)) {
            if (lives>0) {
                lives--;
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    public void draw(ShapeRenderer sr) {
        for (Bubble b: bubbles) {
            b.draw(sr);
        }
    }

    public void update(float delta) {
        for (Bubble b: bubbles) {
            b.update(delta);
        }
        timer+=delta;
        if (timer>waitTime) {
            timer=0;
            addBubble();
        }
    }
}
