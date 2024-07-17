package com.spaceshooter.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.Hashtable;
import java.util.Random;

public class SoundEngine
{
    enum GameMusic
    {
        DFM("Die For Me.mp3"),
        OYG("Once You Gone.mp3"),
        CNS("Cons.mp3"),
        bita("backinthea.mp3");

        private final String filePath;


        GameMusic(String filePath)
        {
            this.filePath = filePath;
        }


        public String getfilePathOfTrack()
        {
            return filePath;
        }
    }

    private Hashtable<GameMusic, Music> musicTable;
    private Random random;

    public SoundEngine()
    {
        musicTable = new Hashtable<>();
        random = new Random();
        loadMusic();
    }

    public void loadMusic()
    {
        for (GameMusic c: GameMusic.values())
        {
            Music music = Gdx.audio.newMusic(Gdx.files.internal(c.getfilePathOfTrack()));
            musicTable.put(c, music);
            
        }
    }
    
    public Music getRandomMusic()
    {
        int index = random.nextInt(GameMusic.values().length);
        GameMusic randomMusic = GameMusic.values()[index];
        return musicTable.get(randomMusic);
    }

    public void dispose()
    {
        for (Music music : musicTable.values())
        {
            music.dispose();
        }
    }
}
