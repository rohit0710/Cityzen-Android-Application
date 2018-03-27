package com.example.user.androidapp2;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.location.Address;
        import android.location.Geocoder;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;


        import java.util.List;
        import java.util.Locale;

public class GPSLocate extends AppCompatActivity {
    String type;
    String chooseLocation;
    String comments;
    String address;
    int flaga = 1;
    int flag = 1;
   /*    Button btnshow;
    TextView myAddress =(TextView)findViewById(R.id.myaddress);
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnshow = (Button)findViewById(R.id.showlocation);
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps=new GPSTracker(MainActivity.this);
                if(gps.canGetLocation()) {
                    double latitude=gps.getLatitude();
                    double longitude=gps.getLongitude();


                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                        if(addresses != null) {
                            Address returnedAddress = addresses.get(0);
                            /*String address = addresses.get(0).getAddressLine(0);
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();*/
  /*                          StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                            StringBuilder strAddress = new StringBuilder("Address:\n");
                            for(int j=0; j<returnedAddress.getMaxAddressLineIndex(); j++) {
                                strReturnedAddress.append(returnedAddress.getAddressLine(j)).append("\n");
                            }
                            myAddress.setText(strReturnedAddress.toString());
                            //strAddress.append(address).append("\n").append(city).append(state).append("\n").append(country).append("\n").append(postalCode).append("\n");
                            //myAddress.setText(strAddress.toString());
                        }
                        else{
                            myAddress.setText("No Address returned!");
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        myAddress.setText("Cannot get Address!");
                    }
                }
                else
                {
                    gps.showSettingsAlert();
                }

            }
        });

    }
*/

    String complaint = "Type of Complaint";
    String loc = "Choose Location";
    Button btnshow;
    GPSTracker gps;
    double latitude;
    double longitude;
    TextView myAddress;
    int c = 0;
    int votes = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpslocate);
        getSupportActionBar().setTitle("Enter Complaint Details");

        gps = new GPSTracker(GPSLocate.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Toast.makeText(getApplicationContext(), "Your Location is Lat=" + latitude + "   Long=" + longitude, Toast.LENGTH_LONG).show();
            myAddress = (TextView) findViewById(R.id.myaddress);
            myAddress.setText(getCompleteAddressString(latitude, longitude));
        } else {

        }
        address = String.valueOf(myAddress.getText());
        Log.i("address", address);
    }

/*
        gps = new GPSTracker(GPSLocate.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Toast.makeText(getApplicationContext(), "Your Location is Lat=" + latitude + "   Long=" + longitude, Toast.LENGTH_LONG).show();
            myAddress = (TextView) findViewById(R.id.myaddress);
            myAddress.setText(getCompleteAddressString(latitude, longitude));
        }
        else {
            gps.showSettingsAlert();

        }
        address= String.valueOf(myAddress.getText());
        Log.i("address",address);
*/


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

