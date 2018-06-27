package hu.denes.budapestcarsharing;

import com.google.android.gms.maps.model.LatLng;

public class CarInfo {
    String company;
    String plate;
    String type;
    LatLng position;
    int charge;


    public CarInfo(String company, String plate, LatLng position, int charge, String type) {
        this.company = company;
        this.plate = plate;
        this.type = type;
        this.position = position;
        this.charge = charge;
    }

    public CarInfo(String company, String plate, LatLng position, int charge) {
        this(company, plate, position, charge, "");
    }
}
