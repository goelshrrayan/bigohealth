package com.bigohealth.ui.Authentication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

/**
 * A login screen that offers login via email/password.
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class Registeration extends Activity  {

    String email;
    private AutoCompleteTextView mNumberView;
    private EditText mPasswordView,mconfirmPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    String url="https://bigobackend.herokuapp.com/sendOTP";
    static String firebasetoken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        // Set up the login form.
        mNumberView = (AutoCompleteTextView) findViewById(R.id.number);
        mconfirmPasswordView = findViewById(R.id.cnfrm_password);




        FirebaseApp.initializeApp(this);

        AttemptLogin attemptLogin=new AttemptLogin();
        attemptLogin.execute();
        firebasetoken=FirebaseInstanceId.getInstance().getToken();
        Log.i("Firebase Token",firebasetoken+"  IS");

        findViewById(R.id.Login).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Registeration.this, Login.class));
            }

        });

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }




    /**
     * Callback received when a permissions request has been completed.



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        final ProgressDialog progressDialog = new ProgressDialog(Registeration.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        // Reset errors.
        mNumberView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
       email= mNumberView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if(password.length()<8||password.length()>100)
        {
            Toast.makeText(this, "Password must be between 8 to 100 characters", Toast.LENGTH_SHORT).show();
        }

        else if (!password.equals(mconfirmPasswordView.getText().toString()))
        {
            Toast.makeText(this, "passwords does'nt match", Toast.LENGTH_SHORT).show();
        }
        // Check for a valid email address.


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            final JSONObject jsonobj=new JSONObject();
            try {

                jsonobj.put("data","32434");
                jsonobj.put("city","Muzaffarpur");
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
                    Log.i("My Params",password+mconfirmPasswordView.getText().toString()+" "+email);
                    String finalEmail=email;
                    if(finalEmail.length()==10)
                    {finalEmail="91"+finalEmail;
                    }
                    else if(email.length()==12)
                    { StringBuilder str
                            = new StringBuilder(finalEmail);
                        str.setCharAt(0,'9');
                        str.setCharAt(1,'1');
                        email=str.toString();}
                    params2.put("phone", finalEmail);
                    params2.put("password", password);
                    params2.put("confirmpassword",mconfirmPasswordView.getText().toString());
                    params2.put("role",1+"");
                    params2.put("firebaseToken",firebasetoken);

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
                                      params.put("password",password);
                                       params.put("confirmpassword",mconfirmPasswordView.getText().toString());
//                                       params.put("online_max_quantity",max_online_quantity.toString());
//                                        params.put("city",city);
//                                        params.put("language_id","English");
                    return params;
                }

                @Override
                protected com.android.volley.Response<JSONArray> parseNetworkResponse(NetworkResponse networkResponse) {
                    JSONObject JsonObject= null;
                    try {
                        String jsonString = new String(networkResponse.data,
                                HttpHeaderParser
                                        .parseCharset(networkResponse.headers));
                        Log.i("response of network",jsonString+"");
                        Log.i("Response of new net","adfdsfdsf");
                    JsonObject=new JSONObject(jsonString);

                        Log.i("response part 2", JsonObject.toString()+"hey");






                        return com.android.volley.Response.success(new JSONArray(jsonString),
                                HttpHeaderParser
                                        .parseCacheHeaders(networkResponse));
                    } catch (UnsupportedEncodingException e) {
                        return com.android.volley.Response.error(new ParseError(e));
                    } catch (JSONException je) {
                        return  com.android.volley.Response.error(new ParseError(je));
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(Registeration.this, "Can't register", Toast.LENGTH_SHORT).show();

                    return com.android.volley.Response.error(new ParseError(e));}
                    finally {
                        if (JsonObject.toString().indexOf("success")>0)
                        {  Intent intent=new Intent(Registeration.this, VerifyOTP.class);
                            if(email.length()==10)
                            {email="91"+email;
                            }
                            else if(email.length()==12)
                            { StringBuilder str
                                    = new StringBuilder(email);
                                str.setCharAt(0,'9');
                                str.setCharAt(1,'1');
                                email=str.toString();}
                            intent.putExtra("phone",email);
                            progressDialog.dismiss();
                                startActivity(intent);
                            Registeration.this.finish();}
                            else {
                            Registeration.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    progressDialog.dismiss();
                                    Toast.makeText(Registeration.this, "Can't Register!!", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }

                    //  return null;
                }

            };

            RequestQueue requestQueue1 = Volley.newRequestQueue(Registeration.this);
            requestQueue1.add(stringRequest);



        }
    }

    private static class AttemptLogin extends AsyncTask<Void, String, String> {

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

