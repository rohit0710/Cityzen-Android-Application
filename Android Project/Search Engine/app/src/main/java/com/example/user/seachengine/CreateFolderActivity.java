package com.example.user.seachengine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateFolderActivity extends AppCompatActivity {

    public Button btnCreateFolder,btnFinished;
    public TextView textviewFolderName,textviewFileName,textviewFileContent;
    public EditText editTextFolderName,editTextFileName,editTextFileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_folder);

        btnCreateFolder= (Button)findViewById(R.id.btnCreateFolder);
        editTextFolderName=(EditText)findViewById(R.id.editTextFolderName);
        editTextFileName=(EditText)findViewById(R.id.editTextFileName);
        textviewFolderName=(TextView) findViewById(R.id.textviewFolderName);
        textviewFileName=(TextView) findViewById(R.id.textviewFileName);
        editTextFileContent=(EditText)findViewById(R.id.editTextFileContent);
        textviewFileContent=(TextView) findViewById(R.id.textViewFileContent);
        btnFinished= (Button)findViewById(R.id.btnFinished);
        editTextFolderName.setVisibility(View.INVISIBLE);
        editTextFileName.setVisibility(View.INVISIBLE);
        textviewFolderName.setVisibility(View.INVISIBLE);
        textviewFileName.setVisibility(View.INVISIBLE);
        editTextFileContent.setVisibility(View.INVISIBLE);
        textviewFileContent.setVisibility(View.INVISIBLE);
        btnFinished.setVisibility(View.INVISIBLE);

        btnCreateFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextFolderName.setVisibility(View.VISIBLE);
                editTextFileName.setVisibility(View.VISIBLE);
                textviewFolderName.setVisibility(View.VISIBLE);
                textviewFileName.setVisibility(View.VISIBLE);
                editTextFileContent.setVisibility(View.VISIBLE);
                textviewFileContent.setVisibility(View.VISIBLE);
                btnFinished.setVisibility(View.VISIBLE);

            }
        });

        btnFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folderName=editTextFolderName.getText().toString().trim();
                String fileName=editTextFileName.getText().toString().trim();
                String fileContent=editTextFileContent.getText().toString().trim();
                makefolder(folderName,fileName,fileContent);


            }
        });
    }
    void makefolder(final String folderName, String fileName, String fileContent)
    {
        String posting_url ="http://192.168.43.76/android_login_api/Server_Folders.php";
        // its your url path ok
        StringRequest postRequest = new StringRequest(Request.Method.POST, posting_url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Login"," Response: " + response.toString());


                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean error = jsonResponse.getBoolean("error");
                            if(!error)
                            Log.i("Success","Printing");
                            else
                            Log.i("Unsuccessful","Printing");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        String  value1= null;
                        String  value2= null;
                    }
                }
        ) {
            // here is params will add to your url using post method
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("myDirectory",folderName);
                //params.put("2ndParamName","valueoF2ndParam");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }
}
