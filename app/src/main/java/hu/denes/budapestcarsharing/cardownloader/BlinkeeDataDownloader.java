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

public class BlinkeeDataDownloader extends CarDataDownloader {

    public static final String BLINKEE = "Blinkee";

    public BlinkeeDataDownloader() {
        super("https://blinkee.city/wp-json/api/v1/regions/11/vehicles");
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
            cars = new JSONObject(sb.toString()).getJSONArray("data");
            for(int i = 0; i < cars.length(); ++i) {
                try {
                    JSONObject car = cars.getJSONObject(i);
                    result.add(new CarInfo(BLINKEE, car.getString("plate"),
                            new LatLng(car.getJSONObject("position").getDouble("lat"),
                                    car.getJSONObject("position").getDouble("lng")),
                            car.getInt("range")));
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
