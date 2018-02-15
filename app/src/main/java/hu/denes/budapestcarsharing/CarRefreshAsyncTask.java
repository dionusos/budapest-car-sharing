package hu.denes.budapestcarsharing;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;

import hu.denes.budapestcarsharing.cardownloader.GreengoDataDownloader;
import hu.denes.budapestcarsharing.cardownloader.LimoDataDownloader;

public class CarRefreshAsyncTask extends AsyncTask<String, Integer, String> {

    private CarArrayAdapter adapter;

    CarRefreshAsyncTask(CarArrayAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected String doInBackground(String... strings) {
        adapter.clear();
        final Collection<CarInfo> store = new ConcurrentLinkedQueue<>();
        final CyclicBarrier barrier = new CyclicBarrier(3);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    store.addAll(new GreengoDataDownloader().download());
                    barrier.await();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    store.addAll(new LimoDataDownloader().download());
                    barrier.await();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            Log.i("BPC", "Main task is waiting...");
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        adapter.addAll(store);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        adapter.notifyDatasetChanged();
        super.onPostExecute(s);
    }
}
