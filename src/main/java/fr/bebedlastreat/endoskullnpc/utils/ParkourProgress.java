package fr.bebedlastreat.endoskullnpc.utils;

public class ParkourProgress {
    private Parkour parkour;
    private int stage;
    private long start;

    public ParkourProgress(Parkour parkour, int stage, long start) {
        this.parkour = parkour;
        this.stage = stage;
        this.start = start;
    }

    public Parkour getParkour() {
        return parkour;
    }

    public void setParkour(Parkour parkour) {
        this.parkour = parkour;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }
}
