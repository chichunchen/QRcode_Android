package edu.nctu.QRcode;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;
import edu.nctu.utils.ApiRequestClient;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Maydaycha on 9/23/14.
 */
public class ListUserActivity extends ListActivity {

    private final String TAG = "ListUserActivity";

    private ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    private SimpleAdapter adapter;

    public JSONArray users;

    private static final String[] mPlaces = new String[] {
            "台北市", "新北市", "台南市", "高雄市", "苗粟縣",
            "台北市", "新北市", "台南市", "高雄市", "苗粟縣",
            "台北市", "新北市", "台南市", "高雄市", "苗粟縣",
            "台北市", "新北市", "台南市", "高雄市", "苗粟縣",
            "台北市", "新北市", "台南市", "高雄市", "苗粟縣",
            "台北市", "新北市", "789", "cde", "abc"
    };

    private static final String[] mFoods = new String[] {
            "大餅包小餅", "蚵仔煎", "東山鴨頭", "臭豆腐", "潤餅",
            "豆花", "青蛙下蛋","豬血糕", "大腸包小腸", "鹹水雞",
            "烤香腸","車輪餅","珍珠奶茶","鹹酥雞","大熱狗",
            "炸雞排","山豬肉","花生冰","剉冰","水果冰",
            "包心粉圓","排骨酥","沙茶魷魚","章魚燒","度小月",
            "aaa","abc","bbb","bcd","123"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.listview_user);

//        for (int i = 0; i < mPlaces.length; i++) {
//            HashMap<String, String> item = new HashMap<String, String>();
//            item.put("food", mFoods[i]);
//            item.put("place", mPlaces[i]);
//            list.add(item);
//        }
//
//        adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, new String[] {"food", "place"}, new int[] {android.R.id.text1, android.R.id.text2});
//
//        setListAdapter(adapter);
//
//        getListView().setTextFilterEnabled(true);
//
        getUserList();
    }

    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
//        Log.e("test", "i: " + listView.getChildAt(position));
//        Log.e("test", "123: " + new HashMap<String, String>((HashMap)getListAdapter().getItem(position)).get("name"));
        try {

            JSONObject user = (JSONObject) users.get(position);
            Log.e(TAG, "123: "+ user);
            Intent intent = new Intent();
            intent.setClass(this, UserDetailActivity.class);
            Bundle bundle = new Bundle();
//            bundle.putString("name", user.getString("name"));
//            bundle.putString("name", user.getString("name"));
            bundle.putString("user", user.toString());
            intent.putExtras(bundle);

            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getUserList() {
        String url = "http://140.113.72.8:3000/users/profiles.json";

        ApiRequestClient.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                /** If the response is JSONObject instead of expected JSONArray */
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray usersList) {
                users = usersList;
                for (int i = 0; i < users.length(); i++) {
                    JSONObject user = null;
                    try {
                        user = (JSONObject) users.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    HashMap<String, String> item = new HashMap<String, String>();

                    try {
                        item.put("name", user.getString("name"));
                        item.put("address", user.getString("address"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    list.add(item);
                }

                adapter = new SimpleAdapter(getApplicationContext(), list, android.R.layout.simple_list_item_2, new String[]{"name", "address"}, new int[]{android.R.id.text1, android.R.id.text2});

                setListAdapter(adapter);

                getListView().setTextFilterEnabled(true);

            }
        });
    }

    private static final String[] mStrings = new String[] {
            "大餅包小餅", "蚵仔煎", "東山鴨頭", "臭豆腐", "潤餅",
            "豆花", "青蛙下蛋","豬血糕", "大腸包小腸", "鹹水雞",
            "烤香腸","車輪餅","珍珠奶茶","鹹酥雞","大熱狗",
            "炸雞排","山豬肉","花生冰","剉冰","水果冰",
            "包心粉圓","排骨酥","沙茶魷魚","章魚燒","度小月",
            "aaa","abc","bbb","bcd","123"
    };


}