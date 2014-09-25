package edu.nctu.QRcode;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Maydaycha on 9/24/14.
 */
public class UserDetailActivity extends Activity {
    private final String TAG = "UserDetailActivity";

    private TextView textViewUserPhone, textViewUserEmail, textViewuserName, textViewUserAddress;
    private Button buttonMap, buttonCallPhone;

    private JSONObject user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        findView();

        Bundle bundle = this.getIntent().getExtras();

        try {
            user = new JSONObject(bundle.getString("user"));
            Log.e(TAG, "detail: " + user);

            textViewUserPhone.setText(user.getString("phone"));
            textViewUserEmail.setText(user.getString("email"));
            textViewuserName.setText(user.getString("name"));
            textViewUserAddress.setText(user.getString("address"));

            buttonMap.setOnClickListener(new SeeMapListener());
            buttonCallPhone.setOnClickListener(new CallPhoneListener(user.getString("phone")));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class SeeMapListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MapActivity.class);
            try {
                intent.putExtra("address", user.getString("address"));
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class CallPhoneListener implements View.OnClickListener {
        private String phoneNumber = "";

        public CallPhoneListener(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public void onClick(View view) {
            if (!phoneNumber.equals("")) {
                phoneNumber = "tel:" + phoneNumber;
                Uri uri = Uri.parse(phoneNumber);
                Intent intent = new Intent(Intent.ACTION_CALL, uri);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "No Phone number", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void findView() {
        textViewUserPhone = (TextView) findViewById(R.id.textView_userPhone);
        textViewUserEmail = (TextView) findViewById(R.id.textView_userEmail);
        textViewuserName = (TextView) findViewById(R.id.textView_userName);
        textViewUserAddress = (TextView) findViewById(R.id.textView_userAddress);
        buttonMap = (Button) findViewById(R.id.button_map);
        buttonCallPhone = (Button) findViewById(R.id.button_callPhone);
    }


}