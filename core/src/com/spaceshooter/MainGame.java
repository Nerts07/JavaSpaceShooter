package com.spaceshooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.spaceshooter.entity.Player;
import com.spaceshooter.entity.enemy;
import com.spaceshooter.manager.SoundEngine;

public class MainGame extends ApplicationAdapter
{
	SpriteBatch batch;
	String img;
	String enemTexture;
	private Player player;
	private enemy[] enemies;
	public Texture backgroundTexture;
	public Sprite backgroundSprite;

	public SoundEngine soundEngine;
	private Music music;

	int enemyWidth = 10;
	int enemyHeight = 4;
	int spacingEnemy = 80;


	@Override
	public void create ()
	{
		batch = new SpriteBatch();

		backgroundTexture = new Texture("Background.png");
		backgroundSprite = new Sprite(backgroundTexture);

		img = "mainPerson.png";
		Texture bul = new Texture(Gdx.files.internal("bullet.png"));
		enemTexture = "enemyDefault.png";

		player = new Player(img, bul, backgroundSprite.getWidth() / 2 - 64, 0, 4f, 4f,500, 800);
//		enemies = new enemy[](enemTexture, backgroundSprite.getWidth() - 98, backgroundSprite.getHeight() - 128);
		enemies = new enemy[enemyWidth * enemyHeight];

		int i = 0;
		for (int y = 0; y < enemyHeight; y++)
		{
			for (int x = 0; x < enemyWidth; x++)
			{
				Vector2 position = new Vector2(x* spacingEnemy, y*spacingEnemy);
				position.x += backgroundSprite.getWidth() / 2;
				position.y += backgroundSprite.getHeight();

				position.x -= (enemyWidth / 2) * spacingEnemy;
				position.y -= (enemyHeight) * spacingEnemy;

				enemies[i] = new enemy(position, enemTexture);
				i++; // Increment 'i' to move to the next position in the array
			}
		}

//		music = Gdx.audio.newMusic(Gdx.files.internal("Die For Me.mp3"));

		soundEngine = new SoundEngine();
		playRandomMusic();
	}

	public void playRandomMusic()
	{
		if (music != null)
		{
			music.stop();
			music.dispose();
		}

		music = soundEngine.getRandomMusic();
		music.setVolume(0.2f);
		music.setLooping(false);
		music.play();

		music.setOnCompletionListener(new Music.OnCompletionListener()
		{
			@Override
			public void onCompletion(Music music)
			{
				playRandomMusic();
			}

		});
	}

	@Override
	public void render ()
	{
		ScreenUtils.clear(0, 0, 0, 1);

		float deltaTime = Gdx.graphics.getDeltaTime();
		int screenWidth = Gdx.graphics.getWidth();


		Rectangle playerBounds = player.getBoundingRectangle();
		Array<Rectangle> bulletBounds = player.getBulletBoundingRectangles();


		if (playerBounds.overlaps(new Rectangle(0, 0, screenWidth, Gdx.graphics.getHeight())))
		{

		}

		for (Rectangle bulletBound : bulletBounds)
		{
			if (bulletBound.overlaps(new Rectangle(0, 0, screenWidth, Gdx.graphics.getHeight())))
			{

			}
		}


		player.update(deltaTime, screenWidth);
//		enemies.update(deltaTime);
		checkCollisions();
		batch.begin();
		batch.draw(backgroundSprite, 0, 0);
		player.render(batch);
		/*
		for (int i = 0; i < enemies.length; i++)
		{
			if (enemies[i].Alive)
			{
				if (player.BulletSprite.getBoundingRectangle().overlaps(enemies[i].sprite.getBoundingRectangle()))
				{
					player.BulletSprite.setY(1000);
					enemies[i].Alive = false;
					break;
				}
			}
		}

		for (int i = 0; i < enemies.length; i++)
		{
			if (enemies[i].Alive)
			{
				enemies[i].update(deltaTime);
				enemies[i].render(batch);
			}
		}
//		enemies.render(batch);
		*/
		for (enemy enemy : enemies) {
			if (enemy.Alive) {
				enemy.update(deltaTime);
				enemy.render(batch);
			}
		}
		batch.end();
	}


	private void checkCollisions() {
		Array<Rectangle> bulletBounds = player.getBulletBoundingRectangles();
		Array<Sprite> bulletsToRemove = new Array<>();

		for (enemy enemy : enemies) {
			if (enemy.Alive) {
				for (int i = 0; i < bulletBounds.size; i++) {
					Rectangle bulletBound = bulletBounds.get(i);
					if (bulletBound.overlaps(enemy.getBoundingRectangle())) {
						enemy.Alive = false;
//						bulletBounds.removeValue(bulletBound, false);
						bulletsToRemove.add(player.bullets.get(i));
						break;
					}
				}
			}
		}

		for (Sprite bullet : bulletsToRemove) {
			player.removeBullet(bullet);
		}
	}


	@Override
	public void dispose ()
	{
		batch.dispose();
		player.dispode();

		for (enemy enemy : enemies) {
			enemy.dispose();
		}

		if (music != null)
		{
			music.dispose();
		}

		music.dispose();
	}

}
