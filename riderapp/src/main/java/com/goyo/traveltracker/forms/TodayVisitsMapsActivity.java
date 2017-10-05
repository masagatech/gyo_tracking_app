package com.goyo.traveltracker.forms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.adapters.Map_Trip_Adapter;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.map_model;
import com.goyo.traveltracker.model.model_trip_map;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;

public class TodayVisitsMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();

    private Button Btn_Date;
    String SelectedDate;
    ArrayList<map_model> data = new ArrayList<map_model>();
    PieView pieView,pieStops,pieTasks;
    private ArrayList<model_trip_map> lsttrip;
    private ListView lst_trip_list;
    private String TripId = "0";
    Polyline polylineFinal;
    Marker Firstmarker,SecondMarker;

    private ProgressDialog loader;

    //drag panel
    private SlidingUpPanelLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_visits_maps);

        pieView = (PieView) findViewById(R.id.pieView);
        pieStops = (PieView) findViewById(R.id.pieStops);
        pieTasks = (PieView) findViewById(R.id.pieTasks);
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        lst_trip_list = (ListView) findViewById(R.id.lst_ripcrew_list);


        //slide panel

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                //Log.i(TAG, "onPanelStateChanged " + newState);
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });




        //date
        Btn_Date=(Button) findViewById(R.id.Date);
        Btn_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date startDate=new Date();
                DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                try {
                    startDate = df.parse(SelectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                new SingleDateAndTimePickerDialog.Builder(TodayVisitsMapsActivity.this)
                        .titleTextColor(Color.WHITE)
                        .backgroundColor(Color.BLACK)
                        .mainColor(Color.parseColor("#ff33b5e5"))
                        .bottomSheet()
                        .curved()
                        .displayHours(false)
                        .displayMinutes(false)
                       .defaultDate(startDate)
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                SelectedDate = new SimpleDateFormat("dd-MMM-yyyy").format(date);
                                if(mMap != null) {
                                    mMap.clear();
                                    DrawPath(mMap);
                                }
                                }
                        }).display();
            }
        });


        lst_trip_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loader = new ProgressDialog(TodayVisitsMapsActivity.this);
                loader.setCancelable(false);
                loader.setMessage(TodayVisitsMapsActivity.this.getString(R.string.wait_msg));
                loader.show();

                if(polylineFinal!=null){
                    polylineFinal.remove();
                }
                if(Firstmarker!=null||SecondMarker!=null){
                    Firstmarker.remove();
                    SecondMarker.remove();
                }
                model_trip_map model_trip_map=lsttrip.get(position);
                TripId=model_trip_map.trpid;
                GetPathOnly(TripId);
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        DrawPath(mMap);
    }


    private void GetTodayPath(String Date){
        JsonObject json = new JsonObject();

        json.addProperty("uid", Global.loginusr.getDriverid()+"");
        json.addProperty("flag", "mob_history");
        json.addProperty("enttid", Global.loginusr.getEnttid()+"");
        json.addProperty("trpdate", Date);
        Ion.with(this)
                .load(Global.urls.gettrackboard.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            if (result != null) Log.v("result", result.toString());
                            // JSONObject jsnobject = new JSONObject(jsond);
                            Gson gson = new Gson();
                            Type listType = new TypeToken<ArrayList<model_trip_map>>() {
                            }.getType();
                            JsonElement k = result.get("data");
                            lsttrip = (ArrayList<model_trip_map>) gson.fromJson(result.get("data"), listType);
                            bindCreawData(lsttrip);

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }


                    }
                });

    }


    private void GetPathOnly(String TripId){
        JsonObject json = new JsonObject();
        json.addProperty("limit", 50000);
        json.addProperty("tripid", TripId);
        Ion.with(this)
                .load(Global.urls.getdelta.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            ArrayList<LatLng> MapLoc= new ArrayList<LatLng>();
                            if (result != null) Log.v("result", result.toString());
                            for(int i=0;i<result.get("data").getAsJsonArray().size();i++) {
                               Double lon= result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("loc").getAsJsonArray().get(0).getAsDouble();
                                Double lat=result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("loc").getAsJsonArray().get(1).getAsDouble();
                                LatLng position = new LatLng(lat,lon);
                                MapLoc.add(position);
                            }

                            DrawinMap(MapLoc);
                            loader.hide();

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }


                    }
                });

    }

    private void DrawinMap(ArrayList<LatLng> Map) {
        PolylineOptions lineOptions = null;
        //draw path
        if (Map.size() > 0) {
            lineOptions = new PolylineOptions();
            lineOptions.addAll(Map);
            lineOptions.width(7);
            lineOptions.color(Color.BLACK);



//            mMap.addPolyline(lineOptions);
            polylineFinal = mMap.addPolyline (lineOptions);

            //Starting and End markers
            Firstmarker  = mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .position(Map.get(0))
                    .title("End Point")
//                    .snippet("")
                    .flat(true));

            SecondMarker =    mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .position(Map.get(Map.size()-1))
                    .title("Start Point")
//                    .snippet("")
                    .flat(true));


            List<LatLng> latlngs = lineOptions.getPoints();
            int size = latlngs.size() - 1;
            float[] results = new float[1];
            Double sum = 0.0;

            for(int i = 0; i < size; i++){
                Location.distanceBetween(
                        latlngs.get(i).latitude,
                        latlngs.get(i).longitude,
                        latlngs.get(i+1).latitude,
                        latlngs.get(i+1).longitude,
                        results);
                sum += results[0];
            }


            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Map.get(0), 13));

            sum=sum/1000;
            FillKM(sum);
        }
    }


    private void bindCreawData(ArrayList<model_trip_map> lst) {
        if (lst.size() > 0) {
            findViewById(R.id.txtNodatas).setVisibility(View.GONE);
            lst_trip_list.setVisibility(View.VISIBLE);
            Map_Trip_Adapter map_trip_adapter = new Map_Trip_Adapter(this, lst, getResources());
            lst_trip_list.setAdapter(map_trip_adapter);
            map_trip_adapter.notifyDataSetChanged();
            //registerForAlert();
        } else {
            lst_trip_list.setVisibility(View.GONE);
            findViewById(R.id.txtNodatas).setVisibility(View.VISIBLE);
        }
    }

    private void FillKM(Double Km){


        if(Km==0.0) {
            pieView.setPercentage(1);
        } else if (Km>5.0) {
            pieView.setPercentage(10);
        } else if (Km>10.0) {
            pieView.setPercentage(40);
        }else if (Km>30.0){
            pieView.setPercentage(70);
        }else if (Km>50.0){
        pieView.setPercentage(88);
        }

        pieView.setInnerText(new DecimalFormat("##.#").format(Km)+"km");
        pieView.setPercentageBackgroundColor(Color.parseColor("#a854d4"));
        pieView.setInnerTextVisibility(View.VISIBLE);
        PieAngleAnimation animation = new PieAngleAnimation(pieView);
        animation.setDuration(900); //This is the duration of the animation in millis
        pieView.startAnimation(animation);

    }


    private void DrawPath(GoogleMap googleMap){

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        //storing latlong of tasks to draw a path
        if(SelectedDate==null) {
            SelectedDate = formattedDate;
        }

        GetTodayPath(SelectedDate);

//       String BTNDate = new SimpleDateFormat("dd-MMM").format(SelectedDate);
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM");
        String date = format.format(Date.parse(SelectedDate));

        Btn_Date.setText(date);
        SQLBase db = new SQLBase(this);
        List<HashMap<String, String>> d = db.Get_CombinedVisit(SelectedDate);
        data.clear();
        if (d.size() > 0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                data.add(new map_model(d.get(i).get("title"), d.get(i).get("time"), d.get(i).get("lat"), d.get(i).get("lon"), d.get(i).get("type")));
            }
        }


        latlngs.clear();

        View icon = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custommapicon_1, null);

        if(data.size()>0) {
            //for stops
            int j=1;
            for (int i = 0; i < data.size(); i++) {
                ImageView MapIcon=(ImageView)icon.findViewById(R.id.MapIco);
                TextView numTxt = (TextView) icon.findViewById(R.id.num_txt);

                if((data.get(i).get_Type().equals("task"))) {
                    GradientDrawable backgroundGradient = (GradientDrawable) MapIcon.getBackground();
                    backgroundGradient.setColor(Color.parseColor("#ff33b5e5"));
                }else {
                    GradientDrawable backgroundGradient = (GradientDrawable) MapIcon.getBackground();
                    backgroundGradient.setColor(Color.parseColor("#ffffbb33"));
                }


                numTxt.setText(j+"");
                latlngs.add(new LatLng(Double.parseDouble(data.get(i).get_lat()), Double.parseDouble(data.get(i).get_lon())));
                options.title(data.get(i).get_body());
                options.snippet(data.get(i).get_title());

                options.position(latlngs.get(i));
                options.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, icon)));
                googleMap.addMarker(options);
                j++;
            }
        }else {
//            Toast.makeText(this, "There is no STOPS/TASKS on selected date! Choose another date", Toast.LENGTH_SHORT).show();
        }


        int stops=0,tasks=0;
        if(data.size()>0) {
            for (int i = 0; i < data.size(); i++) {

                if ((data.get(i).get_Type().equals("task"))) {
                    tasks++;
                } else {
                    stops++;
                }
            }
        }


        //km
        pieView.setPercentage(1);
        pieView.setPercentageBackgroundColor(Color.parseColor("#a854d4"));
        pieView.setInnerText("0km");
        pieView.setInnerTextVisibility(View.VISIBLE);
        PieAngleAnimation animation4 = new PieAngleAnimation(pieView);
        animation4.setDuration(1000); //This is the duration of the animation in millis
        pieView.startAnimation(animation4);


        if(stops==0||data.size()==0)
        {
            //stops
            pieStops.setPercentage(1);
            pieStops.setInnerText("0");
            pieStops.setPercentageBackgroundColor(Color.parseColor("#ffffbb33"));
// Change the color fill of the background of the widget, by default is transparent
//            pieStops.setMainBackgroundColor(getResources().getColor(R.color.customColor5));
            pieStops.setInnerTextVisibility(View.VISIBLE);
            PieAngleAnimation animation5 = new PieAngleAnimation(pieStops);
            animation5.setDuration(1000); //This is the duration of the animation in millis
            pieStops.startAnimation(animation5);

        }else {
            //stops

            pieStops.setPercentage(stops + 10);
//        if(stops<=2) {
//            pieStops.setPercentage(10);
//        } else if (stops>3) {
//            pieStops.setPercentage(30);
//        } else if (stops>4) {
//            pieStops.setPercentage(50);
//        }else if (stops>6){
//            pieStops.setPercentage(70);
//        }else if (stops>7){
//            pieStops.setPercentage(88);
//        }
            pieStops.setInnerText(stops + "");
            pieStops.setPercentageBackgroundColor(Color.parseColor("#ffffbb33"));
// Change the color fill of the background of the widget, by default is transparent
//            pieStops.setMainBackgroundColor(getResources().getColor(R.color.customColor5));
            pieStops.setInnerTextVisibility(View.VISIBLE);
            PieAngleAnimation animation2 = new PieAngleAnimation(pieStops);
            animation2.setDuration(900); //This is the duration of the animation in millis
            pieStops.startAnimation(animation2);


        }


        if(tasks==0||data.size()==0){
            //tasks
            pieTasks.setPercentage(1);
            pieTasks.setInnerText("0");
            pieTasks.setPercentageBackgroundColor(Color.parseColor("#ff33b5e5"));
            pieTasks.setInnerTextVisibility(View.VISIBLE);
            PieAngleAnimation animation6 = new PieAngleAnimation(pieTasks);
            animation6.setDuration(1000); //This is the duration of the animation in millis
            pieTasks.startAnimation(animation6);
        }else {
//        //tasks

            pieTasks.setPercentage(tasks + 10);
//        if(tasks<=2) {
//            pieTasks.setPercentage(10);
//        } else if (tasks>3) {
//            pieTasks.setPercentage(30);
//        } else if (tasks>4) {
//            pieTasks.setPercentage(50);
//        }else if (tasks>6){
//            pieTasks.setPercentage(70);
//        }else if (tasks>7){
//            pieTasks.setPercentage(88);
//        }
            pieTasks.setInnerText(tasks + "");
            pieTasks.setPercentageBackgroundColor(Color.parseColor("#ff33b5e5"));
            pieTasks.setInnerTextVisibility(View.VISIBLE);
            PieAngleAnimation animation3 = new PieAngleAnimation(pieTasks);
            animation3.setDuration(900); //This is the duration of the animation in millis
            pieTasks.startAnimation(animation3);

        }
    }

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

}
