package com.goyo.traveltracker.initials;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.crashlytics.android.Crashlytics;
import com.goyo.traveltracker.R;

import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.utils.SHP;

import io.fabric.sdk.android.Fabric;

public class splash_screen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SetColor();

        Global.prgdialog = di();
        try {
            Integer VersionCode = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionCode;
            String versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
            TextView tvversionCode = (TextView) findViewById(R.id.txtVersionNo);
            tvversionCode.setText("V : " + versionName + "  Build V :  "+VersionCode);

            if (!((Boolean) SHP.get(getApplicationContext(), SHP.ids.shortcut, false))) {
                addShortcut();
                SHP.set(getApplicationContext(), SHP.ids.shortcut, true);
            }

//            SQLBase dataaseInitiate = new SQLBase(splash_screen.this);
//            dataaseInitiate.close();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                Intent i = new Intent(splash_screen.this, sessionchecker.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);


    }


    private void addShortcut() {
        //Adding shortcut for MainActivity
        //on Home screen
        Intent shortcutIntent = new Intent(getApplicationContext(),
                splash_screen.class);

        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                        R.drawable.goyo));

        addIntent
                .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
    }

    private void SetColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private ProgressDialog di(){
        ProgressDialog d = new ProgressDialog(getApplicationContext());
        d.setCancelable(false);
        d.setMessage("Loading");
        return d;
    }
}
