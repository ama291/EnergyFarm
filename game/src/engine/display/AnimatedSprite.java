package engine.display;

import engine.util.GameClock;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class AnimatedSprite extends Sprite {

    private ArrayList<Animation> animations = new ArrayList<Animation>();
    private Boolean playing = true;
    private ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
    private Integer currentFrame = 12;
    private Integer startFrame = 12;
    private Integer endFrame = 15;
    static final int DEFAULT_ANIMATION_SPEED = 10;
    private Integer animationSpeed;
    private GameClock gameClock;
    private String currentId = "runRight";

    public AnimatedSprite(String id) {
        super(id);
        this.animationSpeed = DEFAULT_ANIMATION_SPEED;
        this.initGameClock();
        this.initializeFrames();
        this.initializeAnimations();
        super.setImage(frames.get(currentFrame));
    }

    public boolean getPlaying() {return this.playing; }
    public void setPlaying(boolean playing) { this.playing = playing; }
    public String getCurrentId() {
        return this.currentId;
    }
    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }
    public Integer getAnimationSpeed() {
        return this.animationSpeed;
    }
    public void setAnimationSpeed(Integer animationSpeed) {
        if (animationSpeed >= 1) {
            this.animationSpeed = animationSpeed;
        }
    }
    public void initGameClock() {
        if (this.gameClock == null) {
            this.gameClock = new GameClock();
        }
    }

    public void initializeFrames() {
        for (int i = 0; i < 4; i++) {
            this.frames.add(this.readImage("up_" + i + ".png"));
        }
        for (int i = 0; i < 4; i++) {
            this.frames.add(this.readImage("down_" + i + ".png"));
        }
        for (int i = 0; i < 4; i++) {
            this.frames.add(this.readImage("left_" + i + ".png"));
        }
        for (int i = 0; i < 4; i++) {
            this.frames.add(this.readImage("right_" + i + ".png"));
        }
    }

    public void initializeAnimations() {
        this.animations.add(new Animation("runUp", 0, 3));
        this.animations.add(new Animation("runDown", 4, 7));
        this.animations.add(new Animation("runLeft", 8, 11));
        this.animations.add(new Animation("runRight", 12, 15));
    }


    public Animation getAnimation(String id) {
        for (int x = 0; x < this.animations.size(); x++) {
            if(this.animations.get(x).getId() == id) {
                return this.animations.get(x);
            }
        }
        return null;
    }

    public void animate(Animation anim) {
        this.startFrame = anim.getStartFrame();
        this.endFrame = anim.getEndFrame();
    }

    public void animate(String id) {
        Animation anim = this.getAnimation(id);
        this.animate(anim);
    }

    public void animate(int startFrame, int endFrame) {
        this.startFrame = startFrame;
        this.endFrame = endFrame;
    }

    @Override
    public void draw(Graphics g) {
        if (gameClock.getElapsedTime() >= (600 / animationSpeed) && playing) {
            animate(getAnimation(currentId));
            if (currentFrame < startFrame || currentFrame > endFrame) {
                currentFrame = startFrame;
            }
            super.setImage(frames.get(currentFrame));
            currentFrame += 1;
            gameClock.resetGameClock();
        }
        super.draw(g);

    }

    public void stopAnimation(int frameNumber) {
        this.currentFrame = frameNumber;
        this.playing = false;
    }

    public void stopAnimation() {
        this.stopAnimation(this.startFrame);
    }
}
