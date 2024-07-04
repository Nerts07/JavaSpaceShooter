package com.spaceshooter.manager;

public abstract class SoundEngine
{
    enum randomMusic
    {
        DFM(1, "Die For Me.mp3" ),
        OYG(2,"Once You Gone.mp3" );

        private final int Id;
        private final String filePath;


        randomMusic(int Id, String filePath)
        {
            this.Id = Id;
            this.filePath = filePath;
        }

        public int getId()
        {
            return Id;
        }

        public String getfilePathOfTrack()
        {
            return filePath;
        }
    }
}
