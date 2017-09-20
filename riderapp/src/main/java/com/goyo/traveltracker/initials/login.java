package com.goyo.traveltracker.initials;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.common.Checker;
import com.goyo.traveltracker.forms.dashboard;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_loginusr;
import com.goyo.traveltracker.utils.SHP;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ADD_VOICEMAIL;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.PROCESS_OUTGOING_CALLS;
import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.USE_SIP;
import static android.Manifest.permission.WRITE_CALL_LOG;

public class login extends AppCompatActivity implements View.OnClickListener {
    /* form variable */
    EditText edtUserName, edtPassword;
    Button btnLogin;
    ProgressDialog loader;
    public static String EnttId = "0";
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private  boolean Permmission=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setTitle(getResources().getString(R.string.login_title));
//        ActionBar actionBar = getActionBar();
//        actionBar.hide();
        if(checkAndRequestPermissions()){
            Permmission=false;
        }
//        Permmission = checkAndRequestPermissions();
        initAllControls();
    }

    private  boolean checkAndRequestPermissions() {
        int Phone = ContextCompat.checkSelfPermission(this, PROCESS_OUTGOING_CALLS);
        int phone_state = ContextCompat.checkSelfPermission(this,  android.Manifest.permission.READ_PHONE_STATE);
        int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int loc = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (Phone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(PROCESS_OUTGOING_CALLS);
            listPermissionsNeeded.add(READ_PHONE_STATE);
                    listPermissionsNeeded.add(CALL_PHONE);
                            listPermissionsNeeded.add(READ_CALL_LOG);
                                    listPermissionsNeeded.add(WRITE_CALL_LOG);
                                            listPermissionsNeeded.add(ADD_VOICEMAIL);
                                                    listPermissionsNeeded.add(USE_SIP);

        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (phone_state != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    /*fill all controls*/
    private void initAllControls() {
        /*Edit text box*/
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        /*Button*/
        btnLogin = (Button) findViewById(R.id.btnLogin);

        /*Progress bar*/
        loader = new ProgressDialog(login.this);
        loader.setMessage("Login.. Please wait.");

        setClickListner();
    }

    /*Set all click lisners*/
    private void setClickListner() {
        btnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin: {
                if (!validate()) {
                    return;
                }
//                Intent intent=new Intent(login.this, dashboard.class);
//                startActivity(intent);
                String token = "";
                try {
                    token = FirebaseInstanceId.getInstance().getToken();
                } catch (Exception ex) {

                }
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

//                boolean Permmission = checkAndRequestPermissions();
//                if (Permmission) {
//                    Toast.makeText(this, "Please Accept permission", Toast.LENGTH_SHORT).show();
////                    Permmission = checkAndRequestPermissions();
//                } else {
                    JsonObject json = new JsonObject();
                    json.addProperty("email", edtUserName.getText().toString());
                    json.addProperty("pwd", edtPassword.getText().toString());
                    json.addProperty("token", token);
                    json.addProperty("type", "emp");
                    json.addProperty("otherdetails", "{}");
                    json.addProperty("src", "m");
                    json.addProperty("imei", telephonyManager.getDeviceId());
                    Global.showProgress(loader);
                    Ion.with(this)
                            .load(Global.urls.getlogin.value)
                            .setJsonObjectBody(json)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    // do stuff with the result or error
                                    try {
                                        if (result != null) {
                                            Log.v("result", result.toString());
                                        }else {
                                            Toast.makeText(login.this, "No Internet", Toast.LENGTH_SHORT).show();
                                        }
//                                    JsonObject Data=  result.get("data").getAsJsonObject();
//                                    EnttId=Data.get("enttid").toString();
                                        // JSONObject jsnobject = new JSONObject(jsond);
                                        Gson gson = new Gson();
                                        Type listType = new TypeToken<List<model_loginusr>>() {
                                        }.getType();
                                        List<model_loginusr> login = (List<model_loginusr>) gson.fromJson(result.get("data"), listType);
                                        if (login.size() > 0) {
                                            Global.loginusr = login.get(0);
                                            if (Global.loginusr.getStatus() == 1) {
                                                SHP.set(login.this, SHP.ids.uid, Global.loginusr.getDriverid() + "");
                                                SHP.set(login.this, SHP.ids.hsid, Global.loginusr.getHsid() + "");
                                                String g = Global.loginusr.getSessiondetails().toString();
                                                if (!g.equals("null")) {
                                                    String s = ((LinkedTreeMap) Global.loginusr.getSessiondetails()).get("sessionid").toString();
                                                    Global.loginusr.setSessiondetails(s.replace(".0", ""));
                                                    SHP.set(login.this, SHP.ids.sessionid, Global.loginusr.getSessiondetails().toString());
                                                }

                                                //Toast.makeText(login.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(login.this, dashboard.class);
                                                startActivity(i);
                                                login.this.finish();
                                            } else {
                                                Toast.makeText(login.this, "Login Failed! " + Global.loginusr.getErrmsg(), Toast.LENGTH_SHORT).show();
                                                edtPassword.setText("");
                                            }
                                        } else {
                                            Toast.makeText(login.this, "Oops there is some issue! please login later!", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception ea) {
//                                        Toast.makeText(login.this, "Error: " + ea.getMessage(), Toast.LENGTH_LONG).show();
                                        ea.printStackTrace();
                                    }
                                    Global.hideProgress(loader);

                                }
                            });

//                }
            }
            break;
            default:
                break;
        }

    }


    private boolean validate() {
        if (edtUserName.getText().toString().trim().equals("")) {
            edtUserName.setError("Required!");
            return false;
        } else if (edtPassword.getText().toString().trim().equals("")) {
            edtPassword.setError("Required!");
            return false;
        }
        edtUserName.setError(null);
        edtPassword.setError(null);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        new Checker(this).pass(new Checker.Pass() {
            @Override
            public void pass() {

            }

        }).check(Checker.Resource.NETWORK);
    }
}


