package com.example.pivot;

public class Song {

    public String id;
    public String title;
    public String artists;
    public String fileLink;
    public double songLength;
    public int drawable;

    public Song(String id, String title, String artists, String fileLink, double songLength, int drawable ){
        this.id = id;
        this.title = title;
        this.artists = artists;
        this.fileLink = fileLink;
        this.songLength = songLength;
        this.drawable = drawable;


    }
    public String getId() {return id; }
    public String getTitle() {return title;}
    public String getArtists() {return artists;}
    public String getFileLink() {return fileLink;}
    public double getSongLength() {return songLength;}
    public int getDrawable() { return drawable;}

    public void setId(String newId) { this.id = newId; }
    public void setTitle(String newTitle) { this.title = newTitle; }
    public void setArtist(String newArtist) { this.artists = newArtist; }
    public void setFileLink(String newFileLink) { this.fileLink = newFileLink; }
    public void setSongLength(double newSongLength) { this.songLength = newSongLength; }
    public void setDrawable(int newDrawable) { this.drawable = newDrawable; }


    }


