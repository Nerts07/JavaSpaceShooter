package com.spaceshooter.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class bullet
{
    private Sprite sprite;
    private float speed;
    private Texture bulletTexture;

    public bullet(Texture texture, float x, float y, float width, float height, float speed) {
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(x, y);
        this.sprite.setSize(width, height);
        this.speed = speed;
    }

    public void update(float deltaTime) {
        sprite.translateY(speed * deltaTime);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean isOutOfScreen() {
        return sprite.getY() > com.badlogic.gdx.Gdx.graphics.getHeight();
    }

    public Rectangle getBoundingRectangle() {
        return sprite.getBoundingRectangle();
    }
}
