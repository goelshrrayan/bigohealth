package com.bigohealth.ui.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class VerifyOTP extends AppCompatActivity {
EditText otp;
Button send;
String phone,OTP;
Bundle bundle;
String url="https://bigobackend.herokuapp.com/verifyOTP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        otp=findViewById(R.id.verify_otp_edittext);
        send=findViewById(R.id.verify_otp_btn);
bundle=getIntent().getExtras();
if(bundle==null)
{
    startActivity(new Intent(VerifyOTP.this,Registeration.class));
    finish();
}
else
{phone=bundle.getString("phone");

}
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(VerifyOTP.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                OTP=otp.getText().toString();
                if(OTP.equals(""))
                {
                    Toast.makeText(VerifyOTP.this, "Please enter your OTP", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    final JSONObject jsonobj=new JSONObject();
                    try {

                        jsonobj.put("opr","32434");
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
                            Log.d("Volley Error:", "Volley Error:" + error.getMessage());

                        }
                    })

                    {

                        @Override
                        public byte[] getBody() {
                            HashMap<String, String> params2 = new HashMap<String, String>();
                            Log.i("My Params",phone+" "+OTP);
                            params2.put("phone",phone);
                            params2.put("OTP", OTP);
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
                            // params.put("uniquesessiontokenid", "39676161-b890-4d10-8c96-7aa3d9724119");
                            params.put("phone",phone);
                            params.put("OTP", OTP);
                            params.put("role",1+"");
//                                       params.put("online_max_quantity",max_online_quantity.toString());
//                                        params.put("city",city);
//                                        params.put("language_id","English");
                            return params;
                        }

                        @Override
                        protected com.android.volley.Response<JSONArray> parseNetworkResponse(NetworkResponse networkResponse) {
                            String token="";
                            try {
                                String jsonString = new String(networkResponse.data,
                                        HttpHeaderParser
                                                .parseCharset(networkResponse.headers));
                                Log.i("response of network",jsonString+"");
                                Log.i("Response of new net","adfdsfdsf");
                                JSONObject JsonObject=new JSONObject(jsonString);

                                Log.i("response part 2",JsonObject.toString()+"hey"+JsonObject.toString().length());



                                token=JsonObject.toString().substring(51,238);
                                Log.i("token is",token+"");
                                SharedPreferences sharedPreferences=getSharedPreferences("Token",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("token",token);
                                editor.apply();



                                return com.android.volley.Response.success(new JSONArray(jsonString),
                                        HttpHeaderParser
                                                .parseCacheHeaders(networkResponse));
                            } catch (UnsupportedEncodingException e) {
                                return com.android.volley.Response.error(new ParseError(e));
                            } catch (JSONException je) {
                                return  com.android.volley.Response.error(new ParseError(je));
                            }
                            finally {
                                if(!token.equals(""))
                                { progressDialog.dismiss();
                                    startActivity(new Intent(VerifyOTP.this,Login.class));
                                VerifyOTP.this.finish();}
                                else
                                {progressDialog.dismiss();
                                VerifyOTP.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(VerifyOTP.this, "Unable to Verify..", Toast.LENGTH_SHORT).show();
                                    }
                                });}
                            }

                            //  return null;
                        }
                    };

                    RequestQueue requestQueue1 = Volley.newRequestQueue(VerifyOTP.this);
                    requestQueue1.add(stringRequest);



                }
            }
        });
    }
}
