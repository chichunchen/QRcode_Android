package edu.nctu.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Maydaycha on 8/25/14.
 */
public class ApiRequestClient {

    private static final String BASE_URL = "http://localhost:3000/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    // request function
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        //	      client.get(getAbsoluteUrl(url), params, responseHandler);
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.post(getAbsoluteUrl(url), params, responseHandler);
        client.post(url, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
