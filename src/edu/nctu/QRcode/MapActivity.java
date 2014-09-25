package edu.nctu.QRcode;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.JsonHttpResponseHandler;
import edu.nctu.utils.ApiRequestClient;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Maydaycha on 9/24/14.
 */
public class MapActivity extends Activity {
    private final String TAG = "MapActivity";

//    static final LatLng NKUT = new LatLng(23.979548, 120.696745);
    private GoogleMap map;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        String address = this.getIntent().getStringExtra("address");

        if (!address.equals("")) {
            String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + address  + "&sensor=false&language=zh-tw";
            ApiRequestClient.get(url, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    /** If the response is JSONObject instead of expected JSONArray */
                    Log.e(TAG, "map result: " + response);
                    try {
                        if (response.getString("status").equals("OK")) {
                            JSONObject location = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                            double lat = Double.parseDouble(location.getString("lat"));
                            double lng = Double.parseDouble(location.getString("lng"));
                            LatLng lagLng = new LatLng(lat, lng);

                            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                            Marker marker = map.addMarker(new MarkerOptions().position(lagLng).title("地址").snippet("數位生活創意系"));
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(lagLng, 16));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                }
            });
        }






        // http://maps.googleapis.com/maps/api/geocode/json?address=彰化市吉祥街9號&sensor=false&language=zh-tw
    }

}