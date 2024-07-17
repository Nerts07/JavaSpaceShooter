package com.spaceshooter.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class enemy
{
    private Vector2 position;
    public Sprite sprite;
    private Texture spriteSheetForEnemy;

    private float XE;
    private float YE;
    private float animTime;

    private static final int FRAME_COLS = 2;
    private static final int FRAME_ROWS = 1;

    private float timeLapse;
    public boolean Alive = true;

    private Animation<TextureRegion> enemyIdleAnimation;

    public enemy(Vector2 vector2, String enemTexture /* float XE, float YE */)
    {
        this.position = vector2;

        spriteSheetForEnemy = new Texture(Gdx.files.internal(enemTexture));

        TextureRegion[][] enemTMP = TextureRegion.split(spriteSheetForEnemy,
                spriteSheetForEnemy.getWidth() / FRAME_COLS,
                spriteSheetForEnemy.getHeight() / FRAME_ROWS);

        TextureRegion[] enemyIdle = new TextureRegion[FRAME_ROWS * FRAME_COLS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++)
        {
            for (int j = 0; j < FRAME_COLS; j++)
            {
                enemyIdle[index++] = enemTMP[i][j];
            }
        }
        enemyIdleAnimation = new Animation<>(0.5f, enemyIdle);
        animTime = 0f;
        sprite = new Sprite(enemyIdle[0]);
        sprite.setSize(64, 64);
        sprite.setPosition(position.x, position.y);

        timeLapse = 0f;
    }

    public void update(float deltaTime)
    {
        timeLapse += deltaTime;
        animTime += deltaTime;


    }


    public void render(SpriteBatch batch)
    {
        TextureRegion currentFrame = enemyIdleAnimation.getKeyFrame(animTime, true);
        sprite.setRegion(currentFrame);
        sprite.draw(batch);
    }

    public Rectangle getBoundingRectangle() {
        return sprite.getBoundingRectangle();
    }

    public void dispose() {
        spriteSheetForEnemy.dispose();
    }
}