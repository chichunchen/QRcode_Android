package edu.nctu.QRcode;

import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.loopj.android.http.JsonHttpResponseHandler;
import edu.nctu.utils.ApiRequestClient;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Maydaycha on 9/24/14.
 */
public class ListProductActivity extends ListActivity {

    private final String TAG = "ListProductActivity";

    public JSONArray products;

    private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private SimpleAdapter adapter;

    private TextView textViewProductPrice, textViewProductName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findView();

        getProductList();
    }


    private void getProductList() {

        String url = "http://140.113.72.8:3000/products.json";

        ApiRequestClient.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                /** If the response is JSONObject instead of expected JSONArray */
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray productsList) {
                products = productsList;
//                Log.e(TAG, "products: " + products);
                for (int i = 0; i < products.length(); i++) {
                    try {
                        JSONObject product = (JSONObject) products.get(i);

                        HashMap<String, Object> item = new HashMap<String, Object>();

                        item.put("name", product.getString("name"));
                        item.put("price", product.getString("price"));

                        /** convert string to byte[] */
                        byte[] data = null;
                        if (!product.getString("image").equals("null")) {
                            data = product.getString("image").getBytes(Charset.forName("UTF-8"));
                        }
//                        byte[] data = product.getString("image").getBytes(Charset.forName("UTF-8"));
//                        Bitmap bmap = null;

                        if (data != null && data.length > 0) {
                            Log.e(TAG, "data: " + data);
//                            Bitmap bmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//                            Log.e(TAG, "bmap: " + bmap);
//                            item.put("image", bmap);
                        } else {
                            item.put("image", R.drawable.no_images);
                        }


//                        if (!product.getString("image").equals("null")) {
//                            Bitmap bitmap = getBitmapFromUrl("http://140.113.72.8:3000/application/show_image/product/3");
////                            new Thread(new NetworkThread("http://140.113.72.8:3000/application/show_image/product/3", i, list)).start();
////                            item.put("image", bitmap);
//                        } else {
//                            item.put("image", R.drawable.no_images);
//                        }


//                        Log.e(TAG, "test: " + product.getString("image"));
//                        if (!product.getString("image").equals("null")) {
//                            new DownloadImageTask((ImageView) findViewById(R.id.imageView_productImage)).execute("http://140.113.72.8:3000/application/show_image/product/3");
//                            Log.e(TAG, "image not null: " + i);
//                        }

                        list.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

//                adapter = new SimpleAdapter(getApplicationContext(), list, android.R.layout.simple_list_item_2, new String[]{"name", "price"}, new int[]{android.R.id.text1, android.R.id.text2});
                adapter = new SimpleAdapter(getApplicationContext(), list, R.layout.listview_product, new String[]{"image", "name", "price"}, new int[]{R.id.imageView_productImage, R.id.textView_productName, R.id.textView_productPrice});

                adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data, String s) {
                        if ((view instanceof ImageView) && (data instanceof Bitmap)) {
                            Log.e(TAG, "here");
                            ImageView imageView = (ImageView) view;
                            Bitmap bmap = (Bitmap) data;
                            imageView.setImageBitmap(bmap);
                            return true;
                        }
                        return false;
                    }
                });

                setListAdapter(adapter);

                getListView().setTextFilterEnabled(true);

            }
        });

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
            Log.e(TAG, "view: " + this.imageView);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
//            imageView.setImageBitmap(result);
        }
    }

    public Bitmap getBitmapFromUrl(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    private class NetworkThread implements Runnable {
        private String src;
        int index;
        ArrayList list;
        public NetworkThread(String src, int index, ArrayList list) {
            this.src = src;
            this.index = index;
            this.list = list;
        }

        @Override
        public void run() {
            Bitmap map = getBitmapFromUrl(src);
        }
    }


    private void findView() {
        textViewProductPrice = (TextView) findViewById(R.id.textView_productPrice);
        textViewProductName = (TextView) findViewById(R.id.textView_productName);
    }

}