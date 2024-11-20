import java.time.LocalDate;

public class Timesheet {
    private LocalDate date;
    private float hoursWorked;
    private float hourlyRate;
    private float totalPay;

    public Timesheet(LocalDate date, float hoursWorked, float hourlyRate) {
        this.date = date;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        this.totalPay = hoursWorked * hourlyRate;
    }

    // Accessors
    public LocalDate getDate() {
        return date;
    }
    public float getHoursWorked() {
        return hoursWorked;
    }
    public float getHourlyRate() {
        return hourlyRate;
    }
    public float getTotalPay() {
        return totalPay;
    }

    @Override
    public String toString() {
        return "Date: " + date + "\nHours Worked: " + hoursWorked + "\nHourly Rate: " + hourlyRate + "\nTotal Pay: " + totalPay;
    }
}
