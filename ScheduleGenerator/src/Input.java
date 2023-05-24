import java.time.LocalDate;

public class Input {
    private LocalDate date;
    private int busyHours;

    public Input(int busyHours, LocalDate date) {
        this.date = date;
        this.busyHours = busyHours;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getBusyHours() {
        return busyHours;
    }

    public void setBusyHours(int busyHours) {
        this.busyHours = busyHours;
    }

    @Override
    public String toString() {
        return "Input{" +
                "date=" + date +
                ", busyHours=" + busyHours +
                '}';
    }
}
