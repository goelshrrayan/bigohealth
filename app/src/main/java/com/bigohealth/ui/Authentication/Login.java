package com.bigohealth.ui.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bigohealth.R;
import com.bigohealth.ui.homeactivity.HomeActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText phone,password;
    String phn;
    Button login_btn;
    String url="https://bigobackend.herokuapp.com/login";
    String token="";
    SharedPreferences sharedPreferences;
    static  String firebasetoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       sharedPreferences =getSharedPreferences("Token",MODE_PRIVATE);
        FirebaseApp.initializeApp(this);
      //  firebasetoken=FirebaseInstanceId.getInstance().getToken("38109354020","FCM");
       AttemptLogin attemptLogin=new AttemptLogin();
       attemptLogin.execute();

        firebasetoken=FirebaseInstanceId.getInstance().getToken();
        Log.i("token",firebasetoken+"hey");
        findViewById(R.id.Register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Login.this,Registeration.class));
            }

        });
        phone=findViewById(R.id.Phone_et);
        password=findViewById(R.id.password_et);
        login_btn=findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("token",firebasetoken);
                String pass;
                phn=phone.getText().toString();

                if(phn.length()==10)
                {phn="91"+phn;
                }
                else if(phn.length()==12)
                { StringBuilder str
                        = new StringBuilder(phn);
                    str.setCharAt(0,'9');
                    str.setCharAt(1,'1');
                    phn=str.toString();}
                pass=password.getText().toString();

                final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                final JSONObject jsonobj=new JSONObject();
                try {


                    jsonobj.put("data","Muzaffarpur");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonRequest<JSONArray> stringRequest=  new JsonRequest<JSONArray>(Request.Method.POST,
                        url,
                        jsonobj.toString(), new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response", "res-rec is" + response);
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);


                            } catch (Exception e) {
                                Log.i("Error", e + "");
                            }

                        }
                    }
                }
                        , new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Login request", "Error: " + error.getMessage());
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Server Error!!Please try later", Toast.LENGTH_SHORT).show();
                        Log.d("Volley Error:", "Volley Error:" + error.getMessage());

                    }
                })

                {

                    @Override
                    public byte[] getBody() {
                        HashMap<String, String> params2 = new HashMap<String, String>();
                        Log.i("My Params",phn+" "+firebasetoken+pass);
                        params2.put("phone",phn);
                        params2.put("firebaseToken",firebasetoken );
                        params2.put("password",pass);
                        params2.put("role",1+"");

                        return new JSONObject(params2).toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {


                        Map<String, String> params = new HashMap<String, String>();
                        Log.i("My Params",phn+" "+firebasetoken+pass);
                        // params.put("uniquesessiontokenid", "39676161-b890-4d10-8c96-7aa3d9724119");
                        params.put("phone",phn);
                        params.put("firebaseToken",firebasetoken );
                        params.put("password",pass);
                        params.put("role",1+"");
//                                       params.put("online_max_quantity",max_online_quantity.toString());
//                                        params.put("city",city);
//                                        params.put("language_id","English");
                        return params;
                    }

                    @Override
                    protected com.android.volley.Response<JSONArray> parseNetworkResponse(NetworkResponse networkResponse) {
                        try {
                            String jsonString = new String(networkResponse.data,
                                    HttpHeaderParser
                                            .parseCharset(networkResponse.headers));
                            Log.i("response of network",jsonString+"");
                            Log.i("Response of new net","adfdsfdsf");
                            JSONObject JsonObject=new JSONObject(jsonString);

                            Log.i("response part 2",JsonObject.toString()+"hey"+JsonObject.toString().length());



                           token=JsonObject.getString("token");
                            Log.i("token is",token+"");

                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("token",token);
                            editor.commit();
                            editor.apply();
                            Log.i("Token val" , token+" IS");


                            return com.android.volley.Response.success(new JSONArray(jsonString),
                                    HttpHeaderParser
                                            .parseCacheHeaders(networkResponse));
                        } catch (UnsupportedEncodingException e) {
                            Login.this.runOnUiThread(new Runnable() {
                                public void run() {

                                progressDialog.dismiss();
                                }
                            });
                            return com.android.volley.Response.error(new ParseError(e));
                        } catch (JSONException je) {
                            Login.this.runOnUiThread(new Runnable() {
                                public void run() {

                                progressDialog.dismiss();
                                }
                            });
                            return  com.android.volley.Response.error(new ParseError(je));
                        }
                        finally {
                            progressDialog.dismiss();
                            if(!token.equals("null"))
                            {Log.i("Inside","Login");
                                progressDialog.dismiss();
                                startActivity(new Intent(Login.this, HomeActivity.class));
                                Login.this.finish();}
                            else {
                                Log.i("Inside", "Login Error");
                                progressDialog.dismiss();
                                Login.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(Login.this, "Error Logging in!!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }


                        //  return null;
                    }
                };

                RequestQueue requestQueue1 = Volley.newRequestQueue(Login.this);
                requestQueue1.add(stringRequest);

            }
        });
    }
    private static class AttemptLogin extends AsyncTask<Void, String, String>{

        // member variables of the task class


        @Override
        protected String doInBackground(Void... args) {
            try {
                firebasetoken=FirebaseInstanceId.getInstance().getToken("38109354020","FCM");
                Log.i("Firebase Token",firebasetoken);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return firebasetoken;}
    }

}
