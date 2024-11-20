import java.time.LocalDate;

public class Timesheet {
    private LocalDate date;
    private double hoursWorked;
    private double hourlyRate;
    private double totalPay;

    public Timesheet(LocalDate date, double hoursWorked, double hourlyRate) {
        this.date = date;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        this.totalPay = hoursWorked * hourlyRate;
    }

    // Accessors
    public LocalDate getDate() {
        return date;
    }
    public double getHoursWorked() {
        return hoursWorked;
    }
    public double getHourlyRate() {
        return hourlyRate;
    }
    public double getTotalPay() {
        return totalPay;
    }

    @Override
    public String toString() {
        return "Date: " + date + "\nHours Worked: " + hoursWorked + "\nHourly Rate: " + hourlyRate + "\nTotal Pay: " + totalPay;
    }
}
