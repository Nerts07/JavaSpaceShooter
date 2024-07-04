package com.spaceshooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.spaceshooter.entity.Player;
import com.spaceshooter.entity.enemy;

public class MainGame extends ApplicationAdapter
{
	SpriteBatch batch;
	String img;
	String enemTexture;
	private Player player;
	private enemy enemies;
	public Texture backgroundTexture;
	public Sprite backgroundSprite;
	private Music music;



	@Override
	public void create ()
	{
		batch = new SpriteBatch();

		backgroundTexture = new Texture("Background.png");
		backgroundSprite =new Sprite(backgroundTexture);

		img = "mainPerson.png";
		Texture bul = new Texture(Gdx.files.internal("bullet.png"));
		enemTexture = "enemyDefault.png";

		player = new Player(img, bul, backgroundSprite.getWidth() / 2 - 64, 0, 4f, 4f,600, 800);
		enemies = new enemy(enemTexture, backgroundSprite.getWidth() - 98, backgroundSprite.getHeight() - 128);

		music = Gdx.audio.newMusic(Gdx.files.internal("Die For Me.mp3"));
		music.setVolume(0.2f);
		music.setLooping(false);
		music.play();
	}

	@Override
	public void render ()
	{
		ScreenUtils.clear(0, 0, 0, 1);

		float deltaTime = Gdx.graphics.getDeltaTime();
		int screenWidth = Gdx.graphics.getWidth();


		Rectangle playerBounds = player.getBoundingRectangle();
		Array<Rectangle> bulletBounds = player.getBulletBoundingRectangles();


		if (playerBounds.overlaps(new Rectangle(0, 0, screenWidth, Gdx.graphics.getHeight()))) {

		}

		for (Rectangle bulletBound : bulletBounds) {
			if (bulletBound.overlaps(new Rectangle(0, 0, screenWidth, Gdx.graphics.getHeight()))) {

			}
		}


		player.update(deltaTime, screenWidth);
		enemies.update(deltaTime);
		batch.begin();
		batch.draw(backgroundSprite, 0, 0);
		player.render(batch);
		enemies.render(batch);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		player.dispode();
		music.dispose();
	}

}
