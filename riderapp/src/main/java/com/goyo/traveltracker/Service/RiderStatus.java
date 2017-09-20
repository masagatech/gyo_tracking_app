package com.goyo.traveltracker.Service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.SocketClient.SC_IOApplication;
import com.goyo.traveltracker.common.CheckAppForground;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.forms.newOrder;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.initials.splash_screen;
import com.goyo.traveltracker.model.model_loginusr;
import com.goyo.traveltracker.model.model_notification;
import com.goyo.traveltracker.utils.SHP;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static android.app.Notification.VISIBILITY_PUBLIC;
import static com.goyo.traveltracker.Service.ActivitiesIntentService.detectedActivities;
import static com.goyo.traveltracker.forms.dashboard.TripId;
import static com.goyo.traveltracker.gloabls.Global.urls.livebeats;


/**
 * Created by fajar on 31-May-17.
 */

public class RiderStatus extends Service implements com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    //socket
    private Socket mSocket;
    private boolean isSocConnected;


    private LocationRequest mLocationRequest;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private GoogleApiClient mGoogleApiClient;

    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 3000; // 4 seconds
    private static final long FASTEST_TIME_BW_UPDATES = 3000; // 4 seconds

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1000; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 10000; // in Milliseconds

//    private static final Integer NOTIFICATION_CHECKR_TIMER = 8;//seconds
    private static  Integer LOCATION_SENDER_TIMER = 9;//seconds

    public static LocationManager locationManager;
    public static Handler handler = new Handler();
    public Context context = RiderStatus.this;
    public static String Rider_Lat = "0.0", Rider_Long = "0.0";
    private static String riderid = "0", hsid = "0";
    private Location location;
    Handler handler1;
    Runnable notify;
    String ordid, olnm, stops, amt;
    Integer exptm = 3;
    private Location mCurrentLoc;
    private Location mPreviousLoc;
    private Location currentBestLocation;
    float bearing = 0f,accuracy=0f;
    double altitude=0;
   public static NotificationCompat.Builder mBuilder;
    public static NotificationManager notificationManager;
    private NotificationManager notificationManager2;
    private String ActivityRecg= "";
    private int ActivityPerc=0;


//    private ActivityDetectionBroadcastReceiver mBroadcastReceiver;

    Integer VersionCode;
//    Integer NotifyTimerResseter = NOTIFICATION_CHECKR_TIMER;
    Integer LocationTimerResseter = LOCATION_SENDER_TIMER;

    SQLBase sql;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        hsid = SHP.get(getBaseContext(), SHP.ids.hsid, "0").toString();
        riderid = SHP.get(getBaseContext(), SHP.ids.uid, "0").toString();
        sql = new SQLBase(this);
        SocketClient();

//        mBroadcastReceiver = new ActivityDetectionBroadcastReceiver();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(ActivityRecognition.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            createLocationRequest();
        }

        mPreviousLoc = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mPreviousLoc != null) {
            Rider_Lat = String.valueOf(mPreviousLoc.getLatitude());
            Rider_Long = String.valueOf(mPreviousLoc.getLongitude());

//            LatLng position = new LatLng(Double.parseDouble(Rider_Lat), Double.parseDouble(Rider_Long));
//            MapLoc.add(position);
        }


        handler1 = new Handler();
        notify = new Runnable() {
            @Override
            public void run() {
                String activityString = "";
                if(detectedActivities!=null) {
                    int maxAt = 0;
                    int size = detectedActivities.size();
                    if (size > 0) {
                        int max = detectedActivities.get(0).getConfidence();
                        int pos = 0;

                        for(int i=1; i<detectedActivities.size(); i++) {
                            if (max < detectedActivities.get(i).getConfidence()) {
                                pos = i;
                                max = detectedActivities.get(i).getConfidence();
                            }
                        }
//                        for(int i=0;i<=detectedActivities.size()-1;i++) {
//                            maxAt = detectedActivities.get(i).getConfidence() > detectedActivities.get(maxAt).getConfidence() ? i : maxAt;
//                        }
//                        if (detectedActivities.get(pos).getConfidence() > 50) {
                            ActivityRecg=getDetectedActivity(detectedActivities.get(pos).getType());
                            ActivityPerc=detectedActivities.get(pos).getConfidence();
                        if(getDetectedActivity(detectedActivities.get(pos).getType()).equals("Still")||getDetectedActivity(detectedActivities.get(pos).getType()).equals("Tilting")){
                            activityString="Your Idle";
                        }else if(getDetectedActivity(detectedActivities.get(pos).getType()).equals("On foot")){
                            activityString="Your On Foot";
                        }else if(getDetectedActivity(detectedActivities.get(pos).getType()).equals("Running")){
                            activityString="Your Running";
                        }else if(getDetectedActivity(detectedActivities.get(pos).getType()).equals("Walking")){
                            activityString="Your Walking";
                        }else if(getDetectedActivity(detectedActivities.get(pos).getType()).equals("In a vehicle")){
                            activityString="Your In a vehicle";
                        }else if(getDetectedActivity(detectedActivities.get(pos).getType()).equals("On a bicycle")){
                            activityString="Your On a bicycle";
                        }
//                            activityString = getDetectedActivity(detectedActivities.get(pos).getType()) + " : " + detectedActivities.get(pos).getConfidence() + " %";
//                        }
                    }
                }

//                Notification
                try {
                    showNotification(activityString);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //if phone is in "still" send data every 30 sec
                if((ActivityRecg.equals("Still")&&ActivityPerc==100)||(ActivityRecg.equals("Tilting")&&ActivityPerc==100)){
                    LOCATION_SENDER_TIMER=20;
                }else {
                    LOCATION_SENDER_TIMER=9;
                }
                //checkin timer to call
                //sending data to server in frequency
                if (LocationTimerResseter >= LOCATION_SENDER_TIMER) {

                    //send geo data only if its accurate
//                    if(!(accuracy<=30.0&&((ActivityRecg.equals("Still")&&ActivityPerc==100)||(ActivityRecg.equals("Tilting")&&ActivityPerc==100))))
//                    Toast.makeText(context, accuracy+"", Toast.LENGTH_SHORT).show();
                    if(accuracy<=20&&!(Rider_Lat.equals("0.0"))&&!(Rider_Long.equals("0.0"))){
                        sendingLocationToServer();
                    }
                    LocationTimerResseter = 0;
                }
//                NotifyTimerResseter += 1;
                LocationTimerResseter += 1;
                handler1.postDelayed(this, 1000);
            }

        };

        handler1.postDelayed(notify, 2000);





        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return START_STICKY;
        }

//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES, MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, RiderStatus.this);
//        showCurrentLocation();

        return START_STICKY;

    }




    public void showNotification(String Activity) throws ExecutionException, InterruptedException {
        PendingIntent pi = null;
        boolean foregroud = new CheckAppForground().execute(this).get();
        if(!foregroud) {
                pi = PendingIntent.getActivity(this, 0, new Intent(this, splash_screen.class), 0);

        }else {
            Intent intent = new Intent(context, RiderStatus.class);
            pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Online!")
                .setSmallIcon(R.drawable.tracker_ic)
                .setContentTitle("Time-In With Travel Tracker!")
                .setContentText(Activity)
                .setContentIntent(pi)
                .setAutoCancel(false)
                .setOngoing(true)
                .build();

        notificationManager2 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager2.notify(2, notification);
        startForeground(2, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
//        stopLocationUpdates();
//        if (location != null) {
//            locationManager.removeUpdates(this);
//        }
        mSocket.close();
        if(notificationManager2!=null) {
            stopForeground(true);
            notificationManager2.cancel(2);
        }
        handler1.removeCallbacks(notify);
        notify = null;
        handler1 = null;
        // sql.close();

    }

    private void sendingLocationToServer() {

        try {
            VersionCode = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        int speed;
        if(mCurrentLoc!=null) {
            speed = (int) ((mCurrentLoc.getSpeed() * 3600) / 1000);
        }else {
            speed=0;
        }

        JsonObject json = new JsonObject();
        json.addProperty("loc", "["+ Rider_Lat+","+Rider_Long+"]");
        json.addProperty("enttid", Global.loginusr.getEnttid()+"");
        json.addProperty("tripid", TripId + "");
        json.addProperty("flag", "start");
        json.addProperty("drvid",  riderid + "");
        json.addProperty("uid", riderid + "");
        json.addProperty("bearing", bearing + "");
        json.addProperty("btr", getBatteryLevel() + "");
        json.addProperty("appvr",VersionCode + "");
        json.addProperty("speed",speed);
        json.addProperty("alt",altitude);
        json.addProperty("accr",accuracy);
        json.addProperty("act",ActivityRecg);
        json.addProperty("actpr",ActivityPerc);

        Ion.with(this)
                .load(livebeats.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            if (result != null) Log.v("result", result.toString());

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });
    }

    private void Notify() {
        Ion.with(this)
                .load("GET", Global.urls.getNotify.value)
                .addQuery("uid", riderid)
                .addQuery("flag", "neworder")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());
                            {
                                JsonObject d = result.get("data").getAsJsonObject();
                                if (d.get("state").getAsBoolean()) {
                                    JsonObject data = d.get("data").getAsJsonObject().get("extra").getAsJsonObject();
                                    //sql.NOTIFICATION_DELETEAll();
                                    Gson gson = new Gson();
                                    Type listType = new TypeToken<model_notification>() {
                                    }.getType();
                                    model_notification m = gson.fromJson(data, listType);

                                    sql.NOTIFICATION_INSERT(data.toString(),m.expmin);
                                    processData(m);
                                }
                            }

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });
    }

    public float getBatteryLevel() {
        Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // Error checking that probably isn't needed but added just in case.
        if (level == -1 || scale == -1) {
            return 50.0f;
        }
        return ((float) level / (float) scale) * 100.0f;
    }

//    protected void showCurrentLocation() {
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        if (location == null) {
//            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        }
//        if (location != null) {
//            Rider_Lat = String.valueOf(location.getLatitude());
//            Rider_Long = String.valueOf(location.getLongitude());
//
////            LatLng position = new LatLng(Double.parseDouble(Rider_Lat), Double.parseDouble(Rider_Long));
////            MapLoc.add(position);
//        }
//
//    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {

            if(isBetterLocation(location, mPreviousLoc)) {
                currentBestLocation = location;
                mCurrentLoc=location;

                Rider_Lat = String.valueOf(currentBestLocation.getLatitude());
                Rider_Long = String.valueOf(currentBestLocation.getLongitude());


            }else {
                mCurrentLoc=location;
            }

            altitude =location.getAltitude();

            accuracy =location.getAccuracy();




            if (mPreviousLoc != null) {
                bearing = mPreviousLoc.bearingTo(mCurrentLoc);
            }

//            LatLng position = new LatLng(Double.parseDouble(Rider_Lat), Double.parseDouble(Rider_Long));
//            MapLoc.add(position);

        }

    }

//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//        //Toast.makeText(this, "Provider status changed",Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//        //Toast.makeText(this, "GPS turned on",Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//        //Toast.makeText(this,"Please Turn GPS ON to Tracking work",Toast.LENGTH_LONG).show();
//    }
//
//    //pub sub socket client


    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    private void SocketClient() {
        SC_IOApplication app = new SC_IOApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("ordmsg", onNewMessage);
        mSocket.connect();
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
                    if (!isSocConnected) {
                        //if(null!=mUsername)
                        /*Toast.makeText(getApplicationContext(),
                                "Connected", Toast.LENGTH_LONG).show();*/
                        isSocConnected = true;
                    }
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

                    //Log.i(TAG, "diconnected");
                    isSocConnected = false;
                    /*Toast.makeText(getApplicationContext(),
                            "Disconnect", Toast.LENGTH_LONG).show();*/
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

                    //Log.e(TAG, "Error connecting");
                    /*Toast.makeText(getApplicationContext(),
                            "Unable to connect server!", Toast.LENGTH_LONG).show();*/
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
                    /*JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;*/
                    try {
                        JSONObject data = ((JSONObject) args[0]);
                        if (data.get("evt").equals("regreq")) {
                            mSocket.emit("regnotify", riderid);
                        } else if (data.get("evt").equals("registered")) {

                        } else if (data.get("evt").equals("data")) {

                            Notify();
                        }

                       /* username = data.getString("username");
                        message = data.getString("message");*/
                        Toast.makeText(getApplicationContext(),
                                args[0].toString(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        //Log.e(TAG, e.getMessage());
                        return;
                    }
        }
    };


    //Notification Showing here
    private void processData(model_notification m) {
        int flag = 0;

        //Map<String,String> Data= (Map<String, String>) _msg.getData();
        Intent dialogIntent = new Intent(RiderStatus.this, newOrder.class);
        if (Global.loginusr == null) {
            Global.loginusr = new model_loginusr();
            Global.loginusr.setDriverid(Long.parseLong(riderid));
        }
        //dialogIntent.putExtra(riderid,)
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        dialogIntent.putExtra("FromDashboard", flag);
        startActivity(dialogIntent);


        ordid = m.ordid;
        olnm = m.olnm;
        stops = m.stops;//obj.get("stops").getAsString();
        amt = m.amt;
        exptm = m.expmin;

        try {
            boolean foregroud = new CheckAppForground().execute(this).get();
//            if(!foregroud)
            {
                PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, splash_screen.class), 0);
                mBuilder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.tracker_ic)
                                .setContentTitle("New Task")
                                .setDefaults(Notification.DEFAULT_ALL) // must requires VIBRATE permission
                                .setPriority(NotificationCompat.PRIORITY_HIGH) //must give priority to High, Max which will considered as heads-up notification
                                .setVisibility(BIND_IMPORTANT)
                                .setContentText(olnm)
                                .setContentIntent(pi)
                                .setVisibility(VISIBILITY_PUBLIC )
                                .setOngoing(false);

                // CountTimer.start();
//
                notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification note = mBuilder.build();
                note.flags = Notification.FLAG_INSISTENT;
//to post your notification to the notification bar with a id. If a notification with same id already exists, it will get replaced with updated information.
                notificationManager.notify(0, note);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }



    public String getDetectedActivity(int detectedActivityType) {
        Resources resources = this.getResources();
        switch(detectedActivityType) {
            case DetectedActivity.IN_VEHICLE:
                return resources.getString(R.string.in_vehicle);
            case DetectedActivity.ON_BICYCLE:
                return resources.getString(R.string.on_bicycle);
            case DetectedActivity.ON_FOOT:
                return resources.getString(R.string.on_foot);
            case DetectedActivity.RUNNING:
                return resources.getString(R.string.running);
            case DetectedActivity.WALKING:
                return resources.getString(R.string.walking);
            case DetectedActivity.STILL:
                return resources.getString(R.string.still);
            case DetectedActivity.TILTING:
                return resources.getString(R.string.tilting);
            case DetectedActivity.UNKNOWN:
                return resources.getString(R.string.still);
            default:
                return resources.getString(R.string.unidentifiable_activity, detectedActivityType);
        }
    }


//    public class ActivityDetectionBroadcastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            ArrayList<DetectedActivity> detectedActivities = intent.getParcelableArrayListExtra(Constants.STRING_EXTRA);
//            int size=detectedActivities.size();
//            String activityString = "";
////            for(DetectedActivity activity: detectedActivities){
//            if(size>0){
//                activityString = getDetectedActivity(detectedActivities.get(size-1).getType()) + " : " + detectedActivities.get(size-1).getConfidence();
//                try {
//                    showNotification(activityString);
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
////            }
//            }
//
////            mDetectedActivityTextView.setText(activityString);
//        }
//    }


    protected void createLocationRequest() {
        mGoogleApiClient.connect();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(MIN_TIME_BW_UPDATES);
        mLocationRequest.setFastestInterval(FASTEST_TIME_BW_UPDATES);
        mLocationRequest.setSmallestDisplacement(MIN_DISTANCE_CHANGE_FOR_UPDATES);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private PendingIntent getActivityDetectionPendingIntent() {
        Intent intent = new Intent(this, ActivitiesIntentService.class);

        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();

        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mGoogleApiClient, 15000, getActivityDetectionPendingIntent());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
