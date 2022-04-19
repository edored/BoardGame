package com.example.logic;

public class BasicInformation {
    private String name;
    private String genre;
    private String age;
    private String minNumberOfPlayers;
    private String maxNumberOfPlayers;
    private String duration;

    private final String databaseName = "boardgames.db";
    private final String databaseTableName = "boardgames";

    public BasicInformation() {
    }

        public BasicInformation(String name, String age, String minNumberOfPlayers, String maxNumberOfPlayers, String duration, String genre) {
        this.name = name;
        this.age = age;
        this.minNumberOfPlayers = minNumberOfPlayers;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.duration = duration;
        this.genre = genre;
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

    public String getAge    () {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMinNumberOfPlayers() {
        return minNumberOfPlayers;
    }

    public void setMinNumberOfPlayers(String minNumberOfPlayers) {
        this.minNumberOfPlayers = minNumberOfPlayers;
    }

    public String getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }

    public void setMaxNumberOfPlayers(String maxNumberOfPlayers) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseTableName() {
        return databaseTableName;
    }
}
