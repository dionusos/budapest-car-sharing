package hu.denes.budapestcarsharing;

import com.google.android.gms.maps.model.LatLng;

public class CarInfo {
    String company;
    String plate;
    LatLng position;
    int charge;


    public CarInfo(String company, String plate, LatLng position, int charge) {
        this.company = company;
        this.plate = plate;
        this.position = position;
        this.charge = charge;
    }

    public void update(String company, String plate, LatLng position, int charge) {
        this.company = company;
        this.plate = plate;
        this.position = position;
        this.charge = charge;
    }
}
