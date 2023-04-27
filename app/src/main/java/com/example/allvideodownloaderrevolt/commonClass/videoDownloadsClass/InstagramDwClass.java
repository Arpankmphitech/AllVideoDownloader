package com.example.allvideodownloaderrevolt.commonClass.videoDownloadsClass;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.allvideodownloaderrevolt.R;
import com.example.allvideodownloaderrevolt.commonClass.Utils;
import com.example.allvideodownloaderrevolt.interfacesClass.InstaGetData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;


public class InstagramDwClass {

    private Context context;
    private InstaGetData getData;

    public InstagramDwClass(Context context, InstaGetData getData) {
        this.context = context;
        this.getData = getData;
    }

    public void data(String stringData) {

        ArrayList<String> arrayList = new ArrayList<>();

        if (stringData.matches("https://www.instagram.com/(.*)")) {
            String[] data = stringData.split(Pattern.quote("?"));
            String string = data[0];
            if (isNetworkAvailable()) {
                if (InstaMethodDwClass.Companion.isDownload()) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("Accept", "application/json");
                    client.addHeader("Content-Type", "application/json;charset=UTF-8");
                    client.addHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
                    client.addHeader("x-requested-with", "XMLHttpRequest");
                    client.get(string + "?__a=1&__d=dis", null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String res = new String(responseBody);
                            Log.d("18/01", "onSuccess--->" + res);

                            try {
                                JSONObject jsonObject = new JSONObject(res);

                                String link = null;

                                JSONObject objectGraphql = jsonObject.getJSONObject("graphql");
                                JSONObject objectMedia = objectGraphql.getJSONObject("shortcode_media");
                                boolean isVideo = objectMedia.getBoolean("is_video");
                                if (isVideo) {
                                    link = objectMedia.getString("video_url");
                                } else {
                                    link = objectMedia.getString("display_url");
                                }

                                arrayList.add(link);

                                try {
                                    JSONObject objectSidecar = objectMedia.getJSONObject("edge_sidecar_to_children");
                                    JSONArray jsonArray = objectSidecar.getJSONArray("edges");

                                    arrayList.clear();

                                    String edgeSidecar = null;

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);
                                        JSONObject node = object.getJSONObject("node");
                                        boolean is_video_group = node.getBoolean("is_video");
                                        if (is_video_group) {
                                            edgeSidecar = node.getString("video_url");
                                        } else {
                                            edgeSidecar = node.getString("display_url");
                                        }
                                        arrayList.add(edgeSidecar);

                                    }

                                } catch (Exception e) {
                                    Log.e("error_show", e.toString());
                                }
                                if (!arrayList.isEmpty())
                                    try {
                                        Utils.INSTANCE.newDownload(arrayList.get(0), context);
                                    } catch (Exception e) {
                                        Log.d("18/01", "" + e.getMessage());
                                    }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                getData.getData(arrayList, context.getResources().getString(R.string.network_alert), false);
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.d("18/01", "onFaliure--->" + error.getMessage());
                            getData.getData(arrayList, context.getResources().getString(R.string.valid_url), false);
                        }
                    });
                } else {
                    getData.getData(arrayList, context.getResources().getString(R.string.download_completed), false);
                }
            } else {
                getData.getData(arrayList, context.getResources().getString(R.string.network_alert), false);
            }
        } else {
            getData.getData(arrayList, context.getResources().getString(R.string.network_alert), false);
        }
    }

    //network check
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
