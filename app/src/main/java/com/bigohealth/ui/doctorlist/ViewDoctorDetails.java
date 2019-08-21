package com.bigohealth.ui.doctorlist;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bigohealth.R;
import com.bigohealth.model.Doctor;
import com.bigohealth.ui.Authentication.Login;
import com.bigohealth.ui.homeactivity.HomeActivity;
import com.bigohealth.viewmodels.DoctorListViewModel;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewDoctorDetails extends Fragment implements DatePickerDialog.OnDateSetListener {

    DoctorListViewModel viewModel;
    private Doctor doctor;
    private TextView fullName, qualification, specialisation, verified, experience, address_1, address_2, city, time_slot_1, time_slot_2, feedback, doc_fee;
    ImageView image;
    private Context context;
    ConstraintLayout bookappointmentbtn;
     Dialog dialog1;
    JSONObject JsonObject2;
    int length;
    EditText name,phone,age,address,referral;
    private String url,url2;
    String data;
    String max_quantity;
    String slot;
    String formattedslot;
    String jsonStr;
    String max_online_quantity;
    ArrayList<String> mList;
    String updatedlist = "";
    int characterPos;
    ListView lv;
    String currentDate;
    int positiontrue = -1;
    int myVal = 0;
    List<String> list;
    String token;
    HashMap<String, String> parameter;
    ListView listView;
    ListAdapter listAdapter;

    Button book_cnfrm;

    public ViewDoctorDetails(Doctor doctor) {
        this.doctor = doctor;
    }

    public ViewDoctorDetails() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();

        characterPos = -1;
        parameter = new HashMap<>();
        SharedPreferences sp=context.getSharedPreferences("Token",Context.MODE_PRIVATE);
       token=sp.getString("token","");
        url = "https://bigobackend.herokuapp.com/slots";
        url2="https://bigobackend.herokuapp.com/request";
        mList = new ArrayList<String>();
Log.i("token",token+"");
        View view = inflater.inflate(R.layout.fragment_view_doctor_details, container, false);



        fullName = view.findViewById(R.id.doc_fullname);

        qualification = view.findViewById(R.id.doc_quali_vd);
        specialisation = view.findViewById(R.id.doc_speci_vd);
        verified = view.findViewById(R.id.verified_tv);
        experience = view.findViewById(R.id.doc_exp_tv);
        address_1 = view.findViewById(R.id.address_line_1);
        address_2 = view.findViewById(R.id.address_line_2);
        city = view.findViewById(R.id.address_line_3);
        time_slot_1 = view.findViewById(R.id.time_slot_1);
        time_slot_2 = view.findViewById(R.id.time_slot_2);
        feedback = view.findViewById(R.id.wait_time_tv);
        bookappointmentbtn = view.findViewById(R.id.constraintLayout);
        doc_fee = view.findViewById(R.id.doc_fee1);
        image = view.findViewById(R.id.view_doc_image);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        fullName.setText(doctor.getDocFirstname() + " " + doctor.getDocLastname());
        qualification.setText(doctor.getQualification());
        specialisation.setText(doctor.getSpecialisation());
        experience.setText(doctor.getExperience() + " years");
        address_1.setText(doctor.getAddressLine1());
        address_2.setText(doctor.getAddressLine2());
        city.setText(doctor.getCity());
        time_slot_1.setText(doctor.getGeneralSlot());
        time_slot_2.setText("");
        Picasso.get().load(doctor.getDocImgUrl()).into(image);
        doc_fee.setText("Rs." + doctor.getDocFee());
        feedback.setOnClickListener(v -> Toast.makeText(getActivity(), "Coming soon!", Toast.LENGTH_SHORT).show());

        bookappointmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              showDatePicker();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }



    private void checkMySlot() {

        final JSONObject jsonobj=new JSONObject();
        try {

            jsonobj.put("opr","32434");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest<JSONArray> stringRequest=  new JsonRequest<JSONArray>(Request.Method.POST,
                url,
                jsonobj.toString(), new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("Here  are we", response + "");
length=response.length();
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JsonObject2=response.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data

                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    }
                }

            }
        })


        {

            @Override
            public byte[] getBody() {

                HashMap<String, String> params2 = new HashMap<String, String>();
               Log.i("My Params",currentDate+" "+doctor.getDocId()+" "+token);
                params2.put("date","0"+currentDate);
                params2.put("doc_id",doctor.getDocId());
               // params2.put("token",token);
//                params2.put("role",1+"");

                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("authorization",token);
                return params;
            }




            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<String, String>();
                // params.put("uniquesessiontokenid", "39676161-b890-4d10-8c96-7aa3d9724119");

//                                       params.put("online_max_quantity",max_online_quantity.toString());
//                                        params.put("city",city);
                Log.i("doc_id",doctor.getDocId()+" "+currentDate);
                                        params.put("date","0"+currentDate);
                                        params.put("doc_id",doctor.getDocId());
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

                    try {
                            JSONObject jsonObject = new JSONObject(jsonString);
                            data = jsonObject.getString("data");
                            max_quantity = jsonObject.getInt("max_quantity")+"";
                            max_online_quantity = jsonObject.getInt("online_max_quantity")+"";
                            int x, y, k;
                            x = y = k = 0;
                            mList.clear();
                            for (int j = 0; j < data.length(); j++) {
                                if (j == 0)
                                    mList.add("Morning Slots:-");
                                if (j == 24)
                                    mList.add("Afternoon Slots:-");
                                if (j == 35)
                                    mList.add("Evening Slots:-");
                                if (data.charAt(j) == 'A') {
                                    if (j % 2 == 0) {
                                        x = ((j + 1) * 30);
                                        y = x / 60;
                                        k = x % 60;
                                        String val = "";
                                        if (y < 10)
                                            val = "0" + y;
                                        else
                                            val = "" + y;
                                        float z = (x + 30.0f) / 60.0f;


                                        if ((k - 30) == 0)
                                            mList.add("Slot" + (j + 1) + "- " + val + ":" + (k - 30) + "0" + "--" + val + ":" + k);
                                        else {
                                            mList.add("Slot" + (j + 1) + "- " + val + ":" + (k - 30) + "--" + val + ":" + k);
                                        }
                                    } else {
                                        String s = "";
                                        if (y < 10) {
                                            s = "0" + y + ":" + k;
                                        } else {
                                            s = y + ":" + k;
                                        }
                                        x = (j + 1) * 30;
                                        y = x / 60;
                                        if (y < 10)
                                            mList.add("Slot" + (j + 1) + "- " + s + "--" + "0" + y + ":" + "00");
                                        else {
                                            if (j == 47)
                                                mList.add("Slot" + (j + 1) + "- " + s + "--" + "00" + ":" + "00");
                                            else
                                                mList.add("Slot" + (j + 1) + "- " + s + "--" + y + ":" + "00");
                                        }
                                    }
                                } else if (data.charAt(j) == '0') {
                                } else {
                                    if (j % 2 == 0) {
                                        x = ((j + 1) * 30);
                                        y = x / 60;
                                        k = x % 60;
                                        String val = "";
                                        if (y < 10)
                                            val = "0" + y;
                                        else
                                            val = "" + y;
                                        float z = (x + 30.0f) / 60.0f;
                                        if ((k - 30) == 0)
                                            mList.add("Slot" + (j + 1) + "- " + val + ":" + (k - 30) + "0" + "--" + val + ":" + k);
                                        else
                                            mList.add("Slot" + (j + 1) + "- " + val + ":" + (k - 30) + "--" + val + ":" + k);
                                    } else {
                                        String s = "";
                                        if (y < 10) {
                                            s = "0" + y + ":" + k;
                                        } else {
                                            s = y + ":" + k;
                                        }
                                        x = (j + 1) * 30;
                                        y = x / 60;
                                        if (y < 10)
                                            mList.add("Slot" + (j + 1) + "- " + s + "--" + "0" + y + ":" + "00");
                                        else {
                                            if (j == 47)
                                                mList.add("Slot" + (j + 1) + "- " + s + "--" + "00" + ":" + "00");
                                            else
                                                mList.add("Slot" + (j + 1) + "- " + s + "--" + y + ":" + "00");
                                        }

                                    }
                                }


                            }

                    }catch(JSONException e){
                            e.printStackTrace();
                        }
                        Log.i("data", data);
                        Log.i("max_quantity", max_quantity);
                        Log.i("max_quantity", max_online_quantity);
                    Log.i("values:", mList.get(2) + "hey there");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1
                                , mList);



                    Activity activity = (Activity) context;
                    activity.runOnUiThread(new Runnable() {
                        public void run() {

                            final Dialog dialog = new Dialog(context);
                            Rect displayRectangle = new Rect();

                            Window window = activity.getWindow();
                            window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                            LayoutInflater inflater2 = (LayoutInflater) (context).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View layout = inflater2.inflate(R.layout.booking_slot, null);


                            dialog.setContentView(layout);
                            Log.i("Reached", " Dialog");
                            dialog.show();
                            characterPos = -1;
                            positiontrue = -1;
                            book_cnfrm = layout.findViewById(R.id.confirm_bookin_btn);
                            lv = layout.findViewById(R.id.list_view_slot);
                            lv.setAdapter(adapter);

                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Log.i("position", position + "");
                                  slot=  mList.get(position);
                                    characterPos = position;
                                    positiontrue = position;
                                }
                            });



                            book_cnfrm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                     formattedslot=slot.substring(slot.indexOf("-")+4,slot.indexOf("-")+7);
                                     String fslot=slot.substring(slot.indexOf("-")+2,slot.indexOf("-")+4);
                                    int slotsfinal=Integer.parseInt(fslot);
                                    if(slotsfinal>12)
                                    {
                                        slotsfinal=slotsfinal-12;
                                        if(slotsfinal<10)
                                            fslot="0"+slotsfinal;
                                        else
                                            fslot=""+slotsfinal;

                                        formattedslot=fslot+formattedslot+" PM";
                                    }
                                    else
                                    {formattedslot=formattedslot+" AM";}
                                    characterPos = positiontrue;
                                    if (characterPos == -1) {
                                        Toast.makeText(context, "Select a slot first.", Toast.LENGTH_SHORT).show();

                                    } else {
                                        if (characterPos == 0 || characterPos == 25 || characterPos == 37) {

                                            Toast.makeText(context, "Select a valid slot first.", Toast.LENGTH_SHORT).show();
                                        } else if (characterPos > 0 && characterPos < 25) {
                                            characterPos -= 1;
                                            if (data.charAt(characterPos) != 'A')
                                                myVal = Integer.parseInt(data.charAt(characterPos) + "");
                                            else
                                                myVal = 1;

                                        } else if (characterPos > 24 && characterPos < 37) {
                                            characterPos -= 2;
                                            if (data.charAt(characterPos) != 'A')
                                                myVal = Integer.parseInt(data.charAt(characterPos) + "");
                                            else
                                                myVal = 1;
                                            characterPos -= 2;
                                        } else if (characterPos > 37) {
                                            characterPos -= 3;
                                            if (data.charAt(characterPos) != 'A')
                                                myVal = Integer.parseInt(data.charAt(characterPos) + "");
                                            else
                                                myVal = 1;

                                        }
                                        Log.i("L̥pos", characterPos + "");
                                    }

                                    int max = Integer.parseInt(max_online_quantity);
                                    Log.i("MyVAl", myVal + "");
                                    if (myVal < max) {

                                        dialog.dismiss();
                                        Log.i("slot",formattedslot);

                                       dialog1= new Dialog(context);
                                        // Include dialog.xml file
                                        dialog1.setContentView(R.layout.form);
                                        // Set dialog title
                                        dialog1.setTitle("Add Info");
                                        Button finalbook=dialog1.findViewById(R.id.finalbook_btn);
                                        name=dialog1.findViewById(R.id.ed_name);
                                        phone=dialog1.findViewById(R.id.ed_phone);
                                        age=dialog1.findViewById(R.id.ed_age);
                                        address=dialog1.findViewById(R.id.ed_address);
                                        referral=dialog1.findViewById(R.id.ed_referral);
                                        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
                                        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);

                                        dialog1.getWindow().setLayout(width,height);
                                        dialog1.show();

                                        finalbook.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(name.getText().toString().equals("")||phone.getText().toString().equals("")
                                                ||age.getText().toString().equals("")||address.getText().toString().equals(""))
                                                {
                                                    Toast.makeText(context, "Please complete details", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                    {

                                                        StringBuilder string = new StringBuilder(data);
                                                        string.setCharAt(characterPos, (myVal + "").charAt(0));
                                                        updatedlist = string.toString();
                                                        parameter.put("data", "AAAAAAAAAAA");
                                                        parameter.put("max_quantity", "2");
                                                        parameter.put("online_max_quantity", "2");
                                                        Log.i("new String", updatedlist);
                                                        JSONObject jsonobj = new JSONObject();
                                                        try {
//                                    jsonobj.put("data",updatedlist);
//                                    jsonobj.put("max_quantity",max_quantity+"");
//                                    jsonobj.put("online_max_quantity",max_online_quantity.toString());
                                                            jsonobj.put("data", "32434");
                                                            jsonobj.put("city", "Muzaffarpur");
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        JsonRequest<JSONArray> stringRequest2=  new JsonRequest<JSONArray>(Request.Method.POST,
                                                                url2,
                                                                jsonobj.toString(), new com.android.volley.Response.Listener<JSONArray>() {
                                                            @Override
                                                            public void onResponse(JSONArray response) {
                                                                Log.d("response", "res-rec is" + response);
                                                                for (int i = 0; i < response.length(); i++) {
                                                                    try {



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
                                                                DateFormat df = new SimpleDateFormat("dd MMM yyyy, HH:mm");
                                                                final String dates = df.format(Calendar.getInstance().getTime());
                                                                Log.i("slot",formattedslot);
                                                                params2.put("doc_id",doctor.getDocId());
                                                                params2.put("date",currentDate );
                                                                params2.put("slot",formattedslot);
                                                                params2.put("offline_appoint",0+"");
                                                                params2.put("name",name.getText().toString());
                                                                params2.put("age",age.getText().toString());
                                                                params2.put("phone",phone.getText().toString());
                                                                params2.put("doc_name",doctor.getDocFirstname());
                                                                params2.put("referral",referral.getText().toString());
                                                                params2.put("specs",doctor.getSpecialisation());
                                                                params2.put("address",address.getText().toString());
                                                                params2.put("hospitalId",doctor.getHospitalId());
                                                                params2.put("status","0");
                                                                params2.put("booking_placed_time",dates);

                                                                return new JSONObject(params2).toString().getBytes();
                                                            }

                                                            @Override
                                                            public Map<String, String> getHeaders() throws AuthFailureError {
                                                                HashMap<String, String> params = new HashMap<String, String>();
                                                                params.put("authorization",token);
                                                                return params;
                                                            }

                                                            @Override
                                                            protected Map<String, String> getParams() throws AuthFailureError {


                                                                Map<String, String> params = new HashMap<String, String>();
                                                                // params.put("uniquesessiontokenid", "39676161-b890-4d10-8c96-7aa3d9724119");
                                                                Log.i("params",token);
                                                                DateFormat df = new SimpleDateFormat("dd MMM yyyy, HH:mm");
                                                                final String dates = df.format(Calendar.getInstance().getTime());
                                                                params.put("doc_id",doctor.getDocId());
                                                                params.put("date",currentDate );
                                                                params.put("slot",formattedslot);
                                                                params.put("offline_appoint",0+"");
                                                                params.put("name",name.getText().toString());
                                                                params.put("age",age.getText().toString());
                                                                params.put("phone",phone.getText().toString());
                                                                params.put("doc_name",doctor.getDocFirstname());
                                                                params.put("referral",referral.getText().toString());
                                                                params.put("specs",doctor.getSpecialisation());
                                                                params.put("address",address.getText().toString());
                                                                params.put("hospitalId",doctor.getHospitalId());
                                                                params.put("status","0");
                                                                params.put("booking_placed_time",dates);
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

                                                                    jsonStr=jsonString;






                                                                    return com.android.volley.Response.success(new JSONArray(jsonString),
                                                                            HttpHeaderParser
                                                                                    .parseCacheHeaders(networkResponse));
                                                                } catch (UnsupportedEncodingException e) {

                                                                    return com.android.volley.Response.error(new ParseError(e));
                                                                } catch (JSONException je) {

                                                                    return  com.android.volley.Response.error(new ParseError(je));
                                                                }
                                                                finally {
                                                                    dialog1.dismiss();
                                                                    if(jsonStr.contains("success"))
                                                                    { activity.runOnUiThread(new Runnable() {
                                                                        public void run() {
                                                                            Toast.makeText(context, "Your request has been places", Toast.LENGTH_SHORT).show();
                                                                            dialog.dismiss();

                                                                        }
                                                                    });}
                                                                    else
                                                                    { activity.runOnUiThread(new Runnable() {
                                                                        public void run() {
                                                                            Toast.makeText(context, "error placing request!!please try later..", Toast.LENGTH_SHORT).show();
                                                                            dialog.dismiss();

                                                                        }
                                                                    });}
                                                                }



                                                                //  return null;
                                                            }
                                                        };

                                                        RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                                                        requestQueue1.add(stringRequest2);

                                                    }
                                            }
                                        });



                                    }
                                }


                            });
                        }
                    });






                    return com.android.volley.Response.success(new JSONArray(jsonString),
                            HttpHeaderParser
                                    .parseCacheHeaders(networkResponse));
                } catch (UnsupportedEncodingException e) {

                    return com.android.volley.Response.error(new ParseError(e));
                } catch (JSONException je) {

                    return  com.android.volley.Response.error(new ParseError(je));
                }



                //  return null;
            }
        }; ;
        RequestQueue requestQueue1 = Volley.newRequestQueue(context);
        requestQueue1.add(stringRequest);

    }

    private void showDatePicker() {
        DatePickerFragment datepicker = new DatePickerFragment();
        Log.i("Show fragment"," Date Picker "+getFragmentManager());
        datepicker.setTargetFragment(ViewDoctorDetails.this, 0);
        datepicker.show(getFragmentManager(),"Pic A date");

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.set(Calendar.MONTH, month);
        month=month+1;
         currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        Log.i("current date",currentDate+"yo");
        currentDate=month+"/"+dayOfMonth+"/"+year;
        Log.i("current date",currentDate+"yo");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Toast.makeText(getContext(), currentDate+"hey", Toast.LENGTH_SHORT).show();
        }
        checkMySlot();

    }


}




