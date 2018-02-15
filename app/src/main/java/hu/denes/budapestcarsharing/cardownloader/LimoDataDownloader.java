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

public class LimoDataDownloader extends CarDataDownloader {
    public LimoDataDownloader() {
        super("https://www.mollimo.hu/data/cars.js");
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
            cars = new JSONArray(sb.toString().substring(14));
            for(int i = 0; i < cars.length(); ++i) {
                JSONObject car = cars.getJSONObject(i);
                result.add(new CarInfo("Mollimo", car.getJSONObject("vehicleDescription").getString("plate"),
                        new LatLng(car.getJSONObject("location").getJSONObject("position").getDouble("lat"),
                                car.getJSONObject("location").getJSONObject("position").getDouble("lon")),
                        car.getJSONObject("status").getInt("energyLevel")));
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
