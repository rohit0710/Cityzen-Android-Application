package com.example.user.seachengine;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class SearchEntryActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText etSearch;
    Button btnSearch;
    TextView myAddress;
    String address;
    GPSTracker gps;
    double latitude, longitude;
    private TextView searchContentTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_entry);

        etSearch = (EditText) findViewById(R.id.etSearc);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        myAddress = (TextView) findViewById(R.id.myaddress);
        searchContentTV = (TextView) findViewById(R.id.searchContentTV);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gps = new GPSTracker(SearchEntryActivity.this);
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    Toast.makeText(getApplicationContext(), "Your Location is Lat=" + latitude + "   Long=" + longitude, Toast.LENGTH_LONG).show();
                    //myAddress = (TextView) findViewById(R.id.myaddress);
                    myAddress.setText(getCompleteAddressString(latitude, longitude));
                } else {
                   myAddress.setText("enable GPS");
                    showSettingsAlert();
                }
                address = String.valueOf(myAddress.getText());
                Log.i("address", address);

                search(etSearch.getText().toString());
            }
        });
    }
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);


        alertDialog.setTitle("GPS Not Enabled");

        alertDialog.setMessage("Do you wants to turn On GPS");


        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });


        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        alertDialog.show();
    }
    private void search(String str) {
        Log.e("editText", str);
        try {
            AsyncHttpClient mHttpClient = new AsyncHttpClient();
            mHttpClient.get("http://192.168.40.219:9999/FirstServlet/myfirstservlet", new
                    AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (responseBody != null) {
                                String s = new String(responseBody);
                                Log.e("success", s);

                                searchContentTV.setText(Html.fromHtml(s));

                                Toast.makeText(getApplicationContext(), s + "", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                              Throwable error) {
                            String failure = new String(responseBody);
                            Log.e("failure", failure);
                        }

                    });
          
                  //  });

        } catch (Exception e) {
            Log.d("catch", "Block");
            e.printStackTrace();
        }


 /*   RequestQueue myRequestQueue = Volley.newRequestQueue(this);
    String url = "localhost:9999/?str=" + str;
    StringRequest MyStringRequest = new StringRequest(DownloadManager.Request.Method.GET, url, new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        Log.d(TAG, "onResponse() called with: response = [" + response + "]");
        //This code is executed if the server responds, whether or not the response contains data.
        //The String 'response' contains the server's response.
      }
    }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
      @Override
      public void onErrorResponse(VolleyError error) {
        //This code is executed if there is an error.
      }
    }) {
      protected Map<String, String> getParams() {
        Map<String, String> MyData = new HashMap<String, String>();
        MyData.put("Field", "Value"); //Add the data you'd like to send to the server.
        return MyData;
      }
    };

    myRequestQueue.add(MyStringRequest);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current address", "" + strReturnedAddress.toString());
            } else {
                Log.w("My Current Address", "No Address returned!");
            }
        } catch (Exception e) {


            e.printStackTrace();
            Log.w("My Current address", "Canont get Address!");
        }
        return strAdd;
    }

}
