package in.ac.iitp.hdi;

/**
 * Created by anupam on 2/4/17.
 */

public class HdiDataModel {
    private double EI;
    private double HI;
    private double II;
    private String userUID;
    private double latitude;
    private double longitude;

    public HdiDataModel() {

    }

    public HdiDataModel(double EI, double HI, double II, double latitude, double longitude, String userUID) {
        this.EI = EI;
        this.HI = HI;
        this.II = II;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userUID = userUID;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getII() {
        return II;
    }

    public void setII(double II) {
        this.II = II;
    }

    public double getHI() {
        return HI;
    }

    public void setHI(double HI) {
        this.HI = HI;
    }

    public double getEI() {
        return EI;
    }

    public void setEI(double EI) {
        this.EI = EI;
    }
}
