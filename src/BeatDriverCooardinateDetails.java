import java.sql.Timestamp;

/**
 * @author Akash Srivastava
 * created on 2021-09-24 20:30
 */
public class BeatDriverCooardinateDetails {

    private int driverId;
    private double latitude;
    private double longitude;
    private BeatDriverTimeStamp beatDriverTimeStamp;

    public BeatDriverTimeStamp getBeatDriverTimeStamp() {
        return beatDriverTimeStamp;
    }

    public void setBeatDriverTimeStamp(BeatDriverTimeStamp beatDriverTimeStamp) {
        this.beatDriverTimeStamp = beatDriverTimeStamp;
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

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }


}
