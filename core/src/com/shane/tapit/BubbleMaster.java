package com.shane.tapit;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    private double bubbleTimer =0;
    private int popped=0;
    private int levelCount=0;
    private int lives=3;
    private int level=1;
    private boolean paused=false;
    private double pauseTime=0;
    private boolean done=false;

    public BubbleMaster() {
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

    private void setLevel(int level) {
        levelCount=0;
        if (level<1) {
            this.level=1;
        }
        else if (level>8) {
            this.level=8;
        }
        else {
            this.level = level;
        }
        setSpeed();
    }

    private void levelDown() {
        setLevel(level - 1);
    }

    private void levelUp() {
        setLevel(level+1);
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
        levelCount++;
        if (levelCount==10) {
            levelUp();
        }
    }

    public boolean shouldPop(Vector2 point) {
        return bubbles.first().shouldPop(point);
    }

    public boolean shouldDieBefore(Vector2 point) {
        if (bubbles.first().shouldDieBefore(point)) {
            if (lives>=0) {
                lives--;
                pause();
                levelDown();
                return false;
            }
            else {
                return true;
            }
        }
        return done;
    }

    public boolean shouldDieAfter() {
        if (bubbles.first().shouldDieAfter()) {
            if (lives>=0) {
                lives--;
                pause();
                levelDown();
                return false;
            }
            else {
                return true;
            }
        }
        return done;
    }

    public void draw(ShapeRenderer sr) {
        for (int i=bubbles.size-1;i>=0;i--) {
            bubbles.get(i).draw(sr);
        }
    }

    private void pause() {
        paused=true;
        pauseTime=2;
    }

    public void update(float delta) {
        if (!paused) {
            for (Bubble b : bubbles) {
                b.update(delta);
            }
            bubbleTimer += delta;
            if (bubbleTimer > waitTime) {
                bubbleTimer = 0;
                addBubble();
            }
        }
        else {
            pauseTime-=delta;
            if (pauseTime<0){
                if (lives<0) {
                    done=true;
                }
                else {
                    paused = false;
                    bubbles.clear();
                    bubbleTimer = 0;
                    addBubble();
                }
            }
        }
    }
}
