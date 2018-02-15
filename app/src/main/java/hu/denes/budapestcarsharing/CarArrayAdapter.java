package hu.denes.budapestcarsharing;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CarArrayAdapter {
    private Set<CarInfo> cars;
    private static Set<CarInfoDatasetChanged> observers;

    public CarArrayAdapter() {
        this.cars = new HashSet<>();
        this.observers = new HashSet<>();
    }

    public synchronized void add(final CarInfo carInfo) {
        this.cars.add(carInfo);
    }

    public synchronized void addAll(final Collection<CarInfo> carInfo) {
        this.cars.addAll(carInfo);
    }

    public Set<CarInfo> getCars() {
        return cars;
    }

    public void clear() {
        cars.clear();
    }

    public void subscribe(final CarInfoDatasetChanged subscriber) {
        this.observers.add(subscriber);
    }

    public void notifyDatasetChanged() {
        for(final CarInfoDatasetChanged subscriber : observers) {
            subscriber.carInfoDataChanged();
        }
    }

    public interface CarInfoDatasetChanged {
        void carInfoDataChanged();
    }
}
