package Model;

public class Task implements Comparable<Task> {
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    private int tArrival;
    private int tProcessing;
    private int tFinish;

    public Task(int id, int tProcessing, int tArrival) {
        this.id = id;
        this.tArrival = tArrival;
        this.tProcessing = tProcessing;
    }

    public int gettArrival() {
        return tArrival;
    }

    public void settArrival(int tArrival) {
        this.tArrival = tArrival;
    }

    public int gettProcessing() {
        return tProcessing;
    }

    public void settProcessing(int tProcessing) {
        this.tProcessing = tProcessing;
    }

    public int gettFinish() {
        return tFinish;
    }

    public void settFinish(int tFinish) {
        this.tFinish = tFinish;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "(" + id + ", " + tArrival + ", " + tProcessing + ")";
    }

    @Override
    public int compareTo(Task o) {
        return Integer.compare(this.tArrival, o.tArrival);
    }
}
