import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/a0s03pv/Downloads/paths.csv"));
        HashMap<Integer, List<BeatDriverCooardinateDetails>> map;
        while (true) {
            map = new HashMap<>();
            String line = bufferedReader.readLine();
            if(line == null || line.equals("")){
                break;
            }
            String[] parsed = line.split(",");
            BeatDriverCooardinateDetails location = mapInputToBeatObject(parsed);
            if(map.get(location.getDriverId()) != null){
                map.get(location.getDriverId()).add(location);
            }else {
                List<BeatDriverCooardinateDetails> list = new ArrayList<>();
                list.add(location);
                map.put(location.getDriverId(), list);
            }
        }

        for(Map.Entry<Integer,List<BeatDriverCooardinateDetails>> entry : map.entrySet()){
            List<BeatDriverCooardinateDetails> locationList = entry.getValue();
            BeatDriverCooardinateDetails previous = null;

            for(BeatDriverCooardinateDetails details : locationList){
                BeatDriverCooardinateDetails current = details;
                double price = 0;
                if(previous == null){
                    previous = details;
                }else{
                    boolean toBeRemoved = compareAndRemove(previous, current);
                    if(!toBeRemoved){
                        previous = details;
                    }else {
                        price = price + calculatePrice(previous, current);
                        previous = current;

                    }
                }
                System.out.println(price);
            }
        }
    }

    private static double calculatePrice(BeatDriverCooardinateDetails previous, BeatDriverCooardinateDetails current) {
        BeatSegment beatSegment = calculateSegment(previous, current);
        double price;
        if(beatSegment.getSpeed() >10) {
            if (previous.getBeatDriverTimeStamp().isMorningShift()) {
                price = beatSegment.getDeltaDistance() * 1.30;
            } else {
                price = beatSegment.getDeltaDistance() * 0.74;
            }
        }else {
            price = 11.90 * beatSegment.getDeltaTime();
        }

        return price;

    }

    private static boolean compareAndRemove(BeatDriverCooardinateDetails previous, BeatDriverCooardinateDetails current) {
            BeatSegment segment = calculateSegment(previous, current);
            if(segment.getSpeed() > 100){
                return false;
            }
            return true;
    }

    private static BeatSegment calculateSegment(BeatDriverCooardinateDetails previous, BeatDriverCooardinateDetails current) {
        double distance = getDeltaDistance(previous, current);
        long time = current.getBeatDriverTimeStamp().getTimestamp().getTime() -
                previous.getBeatDriverTimeStamp().getTimestamp().getTime();


        double hours = TimeUnit.HOURS.convert(time,TimeUnit.HOURS);
        double speed = distance/hours;

        BeatSegment beatSegment = new BeatSegment();
        beatSegment.setDeltaTime(hours);
        beatSegment.setSpeed(speed);
        beatSegment.setDeltaDistance(distance);
        return beatSegment;

    }

    private static double getDeltaDistance(BeatDriverCooardinateDetails previous, BeatDriverCooardinateDetails current) {
        return haversine(previous.getLatitude(), previous.getLongitude(), current.getLatitude(), current.getLatitude());
    }

    static double haversine(double lat1, double lon1,
                            double lat2, double lon2)
    {
        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }

    private static BeatDriverCooardinateDetails mapInputToBeatObject(String[] parsed) {
        BeatDriverCooardinateDetails details = new BeatDriverCooardinateDetails();
        details.setDriverId(Integer.parseInt(parsed[0]));
        details.setLatitude(Double.parseDouble(parsed[1]));
        details.setLongitude(Double.parseDouble(parsed[2]));

        BeatDriverTimeStamp beatDriverTimeStamp = new BeatDriverTimeStamp();
        Timestamp timestamp = new Timestamp(Long.parseLong(parsed[3]));
        beatDriverTimeStamp.setTimestamp(timestamp);
        int hour =  timestamp.toLocalDateTime().getHour();
        if(hour >= 0 && hour <=5) {
            beatDriverTimeStamp.setMorningShift(true);
        }
        return details;
    }


}
