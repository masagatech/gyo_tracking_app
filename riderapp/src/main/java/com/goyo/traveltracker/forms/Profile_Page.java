package com.goyo.traveltracker.forms;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.JsonObject;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.adapters.ProfileAdapter;
import com.goyo.traveltracker.gloabls.Global;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class Profile_Page extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView planets_list;
    RecyclerView.LayoutManager layout_manager;
    ProfileAdapter adapter;
    CollapsingToolbarLayout collapsing_container;
    FloatingActionButton Fab;
    String data[]=new String[7];
    ImageView imgToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__page);

        toolbar = (Toolbar) findViewById(R.id.technique_four_toolbar);
        planets_list = (RecyclerView) findViewById(R.id.days_list_4);
        collapsing_container = (CollapsingToolbarLayout) findViewById(R.id.collapsing_container);
        imgToolbar = (ImageView)findViewById(R.id.imgToolbar);

        layout_manager = new LinearLayoutManager(this);
        planets_list.setLayoutManager(layout_manager);

        GetData();

        setSupportActionBar(toolbar);
        collapsing_container.setTitle(Global.loginusr.getFullname()+"");

        Fab=(FloatingActionButton)findViewById(R.id.fab);

        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void GetData(){
        JsonObject json = new JsonObject();

        json.addProperty("empid",Global.loginusr.getDriverid()+"");
        json.addProperty("flag", "details");
        json.addProperty("enttid", Global.loginusr.getEnttid()+"");
        Ion.with(this)
                .load(Global.urls.getEmployeeDetails.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {

                            data[0]="Employee ID: "+result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("empid").getAsString();
                            data[1]=result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("mobileno1").getAsString();
                            data[3]=result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("enttname").getAsString();
                            data[2]=result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("email1").getAsString();
                            data[4]=result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("address").getAsString();
                            data[5]=result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("area").getAsString()+","+result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("city").getAsString();
                            data[6]=result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("state").getAsString()+","+result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("country").getAsString();
                            Names(data);
                            Ion.with(Profile_Page.this)
                                    .load(Global.URL + "/images/" + result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("empphoto").getAsString())
                                    .withBitmap()
                                    .placeholder(R.drawable.user)
                                    .error(R.drawable.user)
                                    .intoImageView(imgToolbar);
                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });

    }
    private void Names(String data[]){
        adapter = new ProfileAdapter(data);
        planets_list.setAdapter(adapter);
    }

    }

