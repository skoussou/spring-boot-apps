package it.sia.model;

public enum Profile {
    jdg7("jdg7"), jdg6("jdg65");

    private String name;

    Profile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}