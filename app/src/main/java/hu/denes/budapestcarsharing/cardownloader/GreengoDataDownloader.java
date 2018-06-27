package hu.denes.budapestcarsharing.cardownloader;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import hu.denes.budapestcarsharing.CarInfo;

public class GreengoDataDownloader extends CarDataDownloader {

    public static final String GREEN_GO = "GreenGo";

    public GreengoDataDownloader() {
        super("https://www.greengo.hu/divcontent.php?rnd=4235235&funct=callAPI&APIname=getVehicleList&params[P_ICON_SIZE]=48");
    }

    @Override
    public List<CarInfo> download() throws IOException {
        List<CarInfo> result = new ArrayList<>();
        URL greengoURL = new URL(url);
        URLConnection urlConnection = greengoURL.openConnection();
        InputStream in = urlConnection.getInputStream();
        JSONArray cars;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(in))){
            StringBuilder sb = new StringBuilder(br.readLine());
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            cars = new JSONArray(sb.toString());
            for(int i = 0; i < cars.length(); ++i) {
                try {
                    JSONObject car = cars.getJSONObject(i);
                    result.add(new CarInfo(GREEN_GO, car.getString("plate_number"),
                            new LatLng(car.getDouble("gps_lat"), car.getDouble("gps_long")),
                            car.getInt("battery_level")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        in.close();

        return result;
    }
}
