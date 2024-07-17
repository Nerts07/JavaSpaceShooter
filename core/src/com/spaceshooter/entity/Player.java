package com.spaceshooter.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player
{
    public Vector2 position;
    public Array<Sprite> bullets;

    private Sound awp;


    private static final int FRAME_COLS = 3;
    private static final int FRAME_ROWS = 1;

    private Animation<TextureRegion> idleAnimation;
    private float xp;
    private float yp;

    private Texture spriteSheet;
    public Sprite sprite;
    public Sprite BulletSprite;
    private float animTime;

    private float ScaleX;
    private float ScaleY;

    private float speed;
    private float bulletSpeed;
    private boolean Fired;

    private float timeSinceLastShot;
    private static final float SHOOT_INTERVAL = 0.7f;

    public Player(String img, Texture bul, float xp, float yp, float ScaleX, float ScaleY, float speed, float bulletSpeed)
    {
        this.position = new Vector2();
        this.bullets = new Array<>();

        this.ScaleX = ScaleX;
        this.ScaleY = ScaleY;

        this.speed = speed;
        this.bulletSpeed = bulletSpeed;

        this.timeSinceLastShot = 0f;

        awp = Gdx.audio.newSound(Gdx.files.internal("awp.mp3"));

        spriteSheet = new Texture(Gdx.files.internal(img));

        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
                spriteSheet.getWidth() / FRAME_COLS,
                spriteSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] idleFrames = new TextureRegion[FRAME_ROWS * FRAME_COLS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++)
        {
            for (int j = 0; j < FRAME_COLS; j++)
            {
                idleFrames[index++] = tmp[i][j];
            }
        }

        idleAnimation = new Animation<>(0.1f, idleFrames);
        animTime = 0f;

        BulletSprite = new Sprite(bul);
        sprite = new Sprite(idleFrames[0]);
        BulletSprite.setSize(16,16);


        sprite.setSize(sprite.getWidth() * ScaleX, sprite.getHeight() * ScaleY);
        sprite.setPosition(position.x, position.y);

    }

    public void update(float deltaTime, int screenWidth)
    {
        updateBullets(deltaTime);
        animTime += deltaTime;
        timeSinceLastShot += deltaTime;

        boolean moved = false;

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= speed * deltaTime;
            if (position.x < 0) {
                position.x = 0; // left block
            }
            moved = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.L)) {
            position.x += speed * deltaTime;
            if (position.x + sprite.getWidth() > screenWidth) {
                position.x = screenWidth - sprite.getWidth(); // right block
            }
            moved = true;
        }

        if (moved) {
            sprite.setPosition(position.x, position.y);
        }

        /*
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !Fired)
        {
            bullet.set(position.x + sprite.getWidth() / 2 - BulletSprite.getWidth() / 2, position.y + sprite.getHeight());
            Fired = true;
        }
        if (Fired)
        {
            bullet.y += bulletSpeed * deltaTime;
            if (bullet.y > Gdx.graphics.getHeight())
            {
                Fired = false;
            }
        }
        */
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && timeSinceLastShot >= SHOOT_INTERVAL  || Gdx.input.isKeyJustPressed(Input.Keys.UP)  && timeSinceLastShot >= SHOOT_INTERVAL) {
            fireBullet();
            timeSinceLastShot = 0f;
            awp.play(0.03f);
        }
    }

    private void fireBullet() {
        Sprite newBullet = new Sprite(BulletSprite);
        newBullet.setSize(16, 16);
        newBullet.setPosition(position.x + sprite.getWidth() / 2 - newBullet.getWidth() / 2, position.y + sprite.getHeight());
        bullets.add(newBullet);
    }

    private void updateBullets(float deltaTime) {
        for (int i = 0; i < bullets.size; i++) {
            Sprite bullet = bullets.get(i);
            bullet.translateY(bulletSpeed * deltaTime);
            if (bullet.getY() > Gdx.graphics.getHeight()) {
                bullets.removeIndex(i);
                i--; // element has been deleted
            }
        }
    }

    public void render(SpriteBatch batch)
    {
        TextureRegion currentFrame = idleAnimation.getKeyFrame(animTime, true);
        sprite.setRegion(currentFrame);
        sprite.draw(batch);
//        if (Fired)
//        {
//            BulletSprite.setPosition(bullet.x, bullet.y);
//            BulletSprite.draw(batch);
//        }
        for (Sprite bullet : bullets) {
            bullet.draw(batch);
        }
    }

    public void dispode()
    {
        spriteSheet.dispose();
    }

    public Rectangle getBoundingRectangle() {
        return sprite.getBoundingRectangle();
    }

    public Array<Rectangle> getBulletBoundingRectangles() {
        Array<Rectangle> bulletBounds = new Array<>();
        for (Sprite bullet : bullets) {
            bulletBounds.add(bullet.getBoundingRectangle());
        }
        return bulletBounds;
    }
    public void removeBullet(Sprite bullet) {
        bullets.removeValue(bullet, true);
    }
}