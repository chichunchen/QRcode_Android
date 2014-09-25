package edu.nctu.QRcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity {

    public static final int REQUEST_CODE_Authenticaiton = 1;

    private Button buttonUserList, buttonProductList;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findView();

//        if (AuthenticationActivity.isLogin) {
        if (true) {
//            Intent intent = new Intent();
//            intent.setClass(this, ListUserActivity.class);
//            startActivity(intent);
            buttonUserList.setOnClickListener(new UserListListener());
            buttonProductList.setOnClickListener(new ProductListListener());
        } else {
            Intent intent = new Intent();
            intent.setClass(this , AuthenticationActivity.class);
            startActivityForResult(intent, REQUEST_CODE_Authenticaiton);
        }
    }

    private void findView() {
        buttonUserList = (Button) findViewById(R.id.button_userList);
        buttonProductList = (Button) findViewById(R.id.button_productList);
    }

    private class UserListListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ListUserActivity.class);
            startActivity(intent);
        }
    }

    private class ProductListListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ListProductActivity.class);
            startActivity(intent);
        }
    }
}
