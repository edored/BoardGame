package com.example.logic;

public class BasicInformation {
    private String name;
    private String genre;
    private int age;
    private int minNumberOfPlayers;
    private int maxNumberOfPlayers;
    private int duration;
    private String description;
    private byte[] image;

    public BasicInformation() {
    }

        public BasicInformation(String name, int age, int minNumberOfPlayers, int maxNumberOfPlayers, int duration, String genre, String description, byte[] image) {
        this.name = name;
        this.age = age;
        this.minNumberOfPlayers = minNumberOfPlayers;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.duration = duration;
        this.genre = genre;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAge    () {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMinNumberOfPlayers() {
        return minNumberOfPlayers;
    }

    public void setMinNumberOfPlayers(int minNumberOfPlayers) {
        this.minNumberOfPlayers = minNumberOfPlayers;
    }

    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }

    public void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

}
