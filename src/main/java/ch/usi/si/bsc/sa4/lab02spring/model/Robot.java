package ch.usi.si.bsc.sa4.lab02spring.model;

public class Robot {
    private int start_x;
    private int start_y;
//    private Orientation orientation;

    public Robot(int start_x, int start_y) {
        this.start_x = start_x;
        this.start_y = start_y;
    }

    public int getStart_x() {
        return start_x;
    }

    public int getStart_y() {
        return start_y;
    }

}
