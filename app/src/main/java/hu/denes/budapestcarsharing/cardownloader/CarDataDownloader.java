package hu.denes.budapestcarsharing.cardownloader;

import java.io.IOException;
import java.util.List;

import hu.denes.budapestcarsharing.CarInfo;

public abstract class CarDataDownloader {
    protected final String url;

    protected CarDataDownloader(final String url) {
        this.url = url;
    }

    public abstract List<CarInfo> download() throws IOException;

}
