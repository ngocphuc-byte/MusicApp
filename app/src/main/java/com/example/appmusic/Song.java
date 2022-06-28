package com.example.appmusic;

public class Song {
    private String name;
    private int File;

    public Song(String name, int file) {
        this.name = name;
        File = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }
}
