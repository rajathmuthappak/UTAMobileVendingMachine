package com.example.utamobilevendingsystem.domain;

public enum Location {


    COOPER_AND_UTA_BLVD(1, "Cooper & UTA Blvd", 2),
    W_NEDDERMAN_AND_GREEK_ROW(2,"W Nedderman & Greek Row", 1),
    S_DAVIS_AND_W_MITCHELL(3,"S Davis & W Mitchell", 2),
    COOPER_AND_W_MITCHELL(4,"Cooper & W Mitchell", 3),
    S_OAK_AND_UTA_BLVD(5, "S Oak & UTA Blvd",2),
    SPANIOLO_AND_W_1ST(6, "Spaniolo & W 1st", 4),
    SPANIOLO_AND_W_MITCHELL(7, "Spaniolo & W Mitchell", 2),
    S_CENTER_AND_W_MITCHELL(8, "S Center & W Mitchell", 1);


    private int id;
    private String locationName;
    private int duration;

    Location(int id, String locationName, int duration) {
        this.id = id;
        this.locationName = locationName;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", locationName='" + locationName + '\'' +
                ", duration=" + duration +
                '}';
    }
}
