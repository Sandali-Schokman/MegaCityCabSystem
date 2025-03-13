package dto;


public class EarningsDTO {
    private int driverId;
    private double totalEarnings;
    private double driverEarnings;
    private double companyShare;
    private int completedRides;

    public EarningsDTO(int driverId, double totalEarnings, double driverEarnings, double companyShare, int completedRides) {
        this.driverId = driverId;
        this.totalEarnings = totalEarnings;
        this.driverEarnings = driverEarnings;
        this.companyShare = companyShare;
        this.completedRides = completedRides;
            }

    public int getDriverId() { return driverId; }
    public double getTotalEarnings() { return totalEarnings; }
    public double getDriverEarnings() { return driverEarnings; }
    public double getCompanyShare() { return companyShare; }
    public int getCompletedRides() { return completedRides; }

}
