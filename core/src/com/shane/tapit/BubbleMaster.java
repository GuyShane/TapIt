package com.shane.tapit;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by shane on 13/11/15.
 */
public class BubbleMaster {
    private Pool<Bubble> bubblePool;
    private Array<Bubble> bubbles;
    private float waitTime;
    private float haloSpeed;
    private float timer=0;
    private int popped=0;

    public BubbleMaster() {
        haloSpeed=60;
        waitTime=Bubble.getHaloDist()/(haloSpeed*2);
        bubbles=new Array<Bubble>();
        bubblePool=new Pool<Bubble>() {
            @Override
            protected Bubble newObject() {
                return new Bubble(haloSpeed);
            }
        };
        addBubble();
    }

    private void speedUp() {
        haloSpeed*=1.2;
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
            speedUp();
            System.out.println("Speeding up");
            System.out.println("Speed: "+haloSpeed);
            System.out.println("Wait time: "+waitTime);
        }
    }

    public boolean shouldPop(Vector2 point) {
        Bubble activeBubble=bubbles.first();
        return activeBubble.shouldPop(point);
    }

    public boolean shouldDie(Vector2 point) {
        Bubble activeBubble=bubbles.first();
        return activeBubble.shouldDie(point);
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
