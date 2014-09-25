package edu.nctu.QRcode;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import edu.nctu.utils.ApiRequestClient;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Maydaycha on 8/25/14.
 */
public class AuthenticationActivity extends Activity {

    private final String TAG = "AuthenticationActivity";

    protected static boolean isLogin = false;

    private EditText editTextAccount, editTextPassword;
    private Button buttonLogin;
    private String account = "";
    private String password = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        findView();

        buttonLogin.setOnClickListener(new LoginListener());
//        buttonLogin.setOnClickListener(new TestListener());

    }

    private void findView() {
        editTextAccount = (EditText) findViewById(R.id.editText_account);
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        buttonLogin = (Button) findViewById(R.id.button_login);
    }

    private class TestListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://www.google.com", new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                    // called before request is started
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    // called when response HTTP status is "200 OK"
                    Log.e(TAG, "status: " + statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                }

                @Override
                public void onRetry(int retryNo) {
                    // called when request is retried
                }
            });
        }
    }


    private class LoginListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            setResult(Activity.RESULT_OK);
            isLogin = true;
//            finish();

            String url = "http://140.113.72.8:3000/users/sign_in";

            RequestParams params = new RequestParams();
            params.put("user[email]", "maydaychaaaa@gmail.com");
            params.put("user[password]", "a047226657");

            ApiRequestClient.post(url, params, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                    Log.e(TAG, "staus: " + statusCode);
//                    Log.e(TAG, "bute: " + new String(bytes));
                    for (Header h: headers) {
                        Log.e(TAG, h.getName() + " : " + h.getValue());
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.e(TAG, "fail");

                }
            });
        }


    }

    private class GetProductListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String url = "http://140.113.72.8:3000/products/1.json";
            ApiRequestClient.post(url, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    /** If the response is JSONObject instead of expected JSONArray */
                    Log.e(TAG, "status: " + statusCode);
                    try {
                        if (response.get("name") != null) {
                            isLogin = true;
                        }
                        Log.e(TAG, "" + response);
                    } catch (Exception e) {
                        Log.e(TAG, "except: " + e);
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                }
            });

        }
    }


}