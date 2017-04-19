package com.zakati.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.TextUtils;

import com.ta.models.events.AddressFetchEvent;
import com.ta.utils.AppConstants;
import com.ta.utils.Lg;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by rahil on 4/4/16.
 */
public class FetchAddressIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public static final String TAG = "FetchAddressIntentService";

    public FetchAddressIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String errorMessage = "";

        // Get the location passed to this service through an extra.
        Location location = intent.getParcelableExtra(
                AppConstants.LOCATION_DATA_EXTRA);

        AddressFetchEvent event = new AddressFetchEvent();

        List<Address> addresses = null;
        Address address = null;
        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            //errorMessage = getString(R.string.service_not_available);
            errorMessage = "service_not_available";
            Lg.e(TAG, errorMessage, ioException);
            event.setAddressFetched(false);
            event.setMsg(errorMessage);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            //errorMessage = getString(R.string.invalid_lat_long_used);
            errorMessage = "invalid_lat_long_used";
            Lg.e(TAG, errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " +
                    location.getLongitude(), illegalArgumentException);

            event.setAddressFetched(false);
            event.setMsg(errorMessage);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "service_not_available";
                Lg.e(TAG, errorMessage);
            }
            event.setAddressFetched(false);
            event.setMsg(errorMessage);
            /*deliverResultToReceiver(AppConstants.FAILURE_RESULT, errorMessage);*/
        } else {
            address = addresses.get(0);
            List<String> addressFragments = new ArrayList<String>();


            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Lg.i(TAG, "address_found");
            event.setAddressFetched(true);
            event.setMsg("address_found");
            /*deliverResultToReceiver(AppConstants.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"),
                            addressFragments));*/
            event.setFullAddress(TextUtils.join(",",
                    addressFragments));
        }
        event.setAddress(address);
        EventBus.getDefault().post(event);
    }

    private void deliverResultToReceiver(String message) {
        /*Bundle bundle = new Bundle();
        bundle.putString(AppConstants.RESULT_DATA_KEY, message);
        mReceiver.send(resultCode, bundle);*/
        //EventBus.getDefault().post(new AddressFetchEvent());
        Lg.e(TAG, message);
    }
}
