package com.goyo.traveltracker.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.forms.EditStops;
import com.goyo.traveltracker.forms.OrderStatus;
import com.goyo.traveltracker.forms.Orientation;
import com.goyo.traveltracker.forms.PendingOrdersView;
import com.goyo.traveltracker.model.model_completed;
import com.goyo.traveltracker.model.model_task;
import com.goyo.traveltracker.utils.VectorDrawableUtils;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.goyo.traveltracker.gloabls.Global.urls.getTripStops;

/**
 * Created by fajar on 22-May-17.
 */

public class ComplatedOrderAdapter extends RecyclerView.Adapter<pending_order_viewHolder>  {

    private List<model_task> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    private ProgressDialog loader;





    public ComplatedOrderAdapter(List<model_task> feedList, Orientation orientation, boolean withLinePadding) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;

    }
    @Override
    public int getItemViewType(int position) {
        return PendingOrdersView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public pending_order_viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;

        view = mLayoutInflater.inflate(R.layout.complated_order_timeline, parent, false);

        return new pending_order_viewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final pending_order_viewHolder holder, final int position) {

//        holder.setIsRecyclable(false);
        final model_task timeLineModel = mFeedList.get(position);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            if (timeLineModel.status == OrderStatus.INACTIVE) {
                holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
            } else if (timeLineModel.status == OrderStatus.ACTIVE) {
                holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.round));
            } else {
                holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.round));
            }
        }

        final Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        ArrayList<String> TagsArray = gson.fromJson(timeLineModel.get_tags(), type);


        //setting order no. and marchant name on start
        holder.mOrder.setText(timeLineModel.get_creat_on());
        holder.mMarchant.setText(timeLineModel.get_title());
        holder.Custmer_name.setText(timeLineModel.get_body());
        holder.mTime.setText(timeLineModel.get_time());
        holder.Tags.setTags(TagsArray);
        holder.mDate.setText("InTime: "+timeLineModel.get_in_time());
        holder.Remark.setText("OutTime: "+timeLineModel.get_out_time());

        holder.Btn_Delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext, EditStops.class);
                intent.putExtra("date",timeLineModel.get_creat_on());
                intent.putExtra("time",timeLineModel.get_time());
                intent.putExtra("title",timeLineModel.get_title());
                intent.putExtra("body",timeLineModel.get_body());
                intent.putExtra("InTime",timeLineModel.get_in_time());
                intent.putExtra("OutTime",timeLineModel.get_out_time());
                intent.putExtra("unique_id",timeLineModel.get_stop_id());
                intent.putExtra("is_server",timeLineModel.get_is_server_send());
                intent.putExtra("Exp_id",timeLineModel.get_exp_id());
                intent.putExtra("Exp_disc",timeLineModel.get_exp_disc());
                intent.putExtra("Exp_type",timeLineModel.get_exp_type());
                intent.putExtra("Exp_value",timeLineModel.get_exp_value());
                intent.putExtra("Tags",timeLineModel.get_tags());
                intent.putExtra("image paths",timeLineModel.get_image_paths());
                intent.putExtra("lat",timeLineModel.get_lat());
                intent.putExtra("lon",timeLineModel.get_lon());
                mContext.startActivity(intent);
            }
        });






        //showing border and text if order is delivered or retruned
        switch (timeLineModel.get_is_server_send()) {
            case "0": {
                GradientDrawable gd = (GradientDrawable) holder.Border.getBackground();
                int width_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mContext.getResources().getDisplayMetrics());
                gd.setStroke(width_px, Color.parseColor("#ff99cc00"));

                break;
            }
            case "1": {
                GradientDrawable gd = (GradientDrawable) holder.Border.getBackground();
                int width_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mContext.getResources().getDisplayMetrics());
                gd.setStroke(width_px, Color.parseColor("#ffff4444"));
                break;
            }
//            case "2": {
//                GradientDrawable gd = (GradientDrawable) holder.Border.getBackground();
//                int width_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mContext.getResources().getDisplayMetrics());
//                gd.setStroke(width_px, Color.parseColor("#ffffbb33"));
//                break;
//            }
            default:
                GradientDrawable gd = (GradientDrawable) holder.Border.getBackground();
                int width_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, mContext.getResources().getDisplayMetrics());
                gd.setStroke(width_px, Color.parseColor("#ffffbb33"));
        }

//        List<int[]> colors = new ArrayList<int[]>();
////int[] color = {TagBackgroundColor, TabBorderColor, TagTextColor}
//        int[] color1 = {Color.parseColor("#ff33b5e5"), Color.WHITE, Color.WHITE};
//        int[] color2 = {Color.parseColor("#ffffbb33"), Color.WHITE, Color.WHITE};
//        int[] color3 = {Color.parseColor("#ff99cc00"), Color.WHITE, Color.WHITE};
//        int[] color4 = {Color.parseColor("#ffff4444"), Color.WHITE, Color.WHITE};
//        colors.add(color1);
//        colors.add(color2);
//        colors.add(color3);
//        colors.add(color4);
//        holder.Tags.setTags(TagsArray, colors);






        //click to expand and hide when press agine
        holder.ButtonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View m) {
                if(holder.ClickToHide.getVisibility() == View.VISIBLE){
                    holder.ClickToHide.setVisibility(View.GONE);
//                    holder.mDate.setVisibility(View.GONE);
//                    holder.mOrder.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                    holder.mMarchant.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    timeLineModel.isEnabled = false;
                }else {
//                    if(timeLineModel.custname==null) {
//                        loader = new ProgressDialog(mContext);
//                        loader.setCancelable(false);
//                        loader.setMessage(mContext.getString(R.string.wait_msg));
//                        loader.show();
//
//                        //getting data when user expand
//                        ComplatedOrder(timeLineModel, holder, position);
//
//                    } else {
                        timeLineModel.isEnabled = true;
//                    }
                    notifyDataSetChanged();
                    holder.ClickToHide.setVisibility(View.VISIBLE);
//                    holder.mDate.setVisibility(View.VISIBLE);
//                    holder.mOrder.setCompoundDrawablesWithIntrinsicBounds( R.drawable.order_id, 0, 0, 0);
//                    holder.mMarchant.setCompoundDrawablesWithIntrinsicBounds( R.drawable.pending_outlets, 0, 0, 0);
                }
            }
        });
//        holder.collected_cash.setEnabled(false);

        if (timeLineModel.isEnabled) {
//            holder.mTime.setText(mContext.getString(R.string.dilvered_at) +" "+ timeLineModel.deltime + "");
//            holder.collected_cash.setText("₹ " + timeLineModel.amtcollect + "");
//            holder.Custmer_name.setText(timeLineModel.custname + "");
//            holder.mDeliver_at.setText(timeLineModel.custaddr + " ");
//            holder.mDate.setText(timeLineModel.dltm + "");

            holder.ClickToHide.setVisibility(View.VISIBLE);
//            holder.mDate.setVisibility(View.VISIBLE);
//            holder.mOrder.setCompoundDrawablesWithIntrinsicBounds(R.drawable.esitmated_time, 0, 0, 0);
//            holder.mMarchant.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending_outlets, 0, 0, 0);
        } else {
            holder.ClickToHide.setVisibility(View.GONE);
//            holder.mDate.setVisibility(View.GONE);
//            holder.mOrder.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//            holder.mMarchant.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//
//            holder.mTime.setText("");
//            holder.collected_cash.setText("");
//            holder.Custmer_name.setText("");
//            holder.mDeliver_at.setText("");
//            holder.mDate.setText("");


        }
        if(timeLineModel.get_exp_id()==null){
            holder.ArrowRemark.setImageResource(R.drawable.cwac_cam2_ic_close_white);
            holder.Btn_Map.setEnabled(false);
        }else {
            holder.ArrowRemark.setImageResource(R.drawable.ic_done);
            holder.Btn_Map.setEnabled(true);
            holder.Btn_Map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View alertLayout = mLayoutInflater.inflate(R.layout.expense_data, null);

                    final TextView Expense_Type = (TextView) alertLayout.findViewById(R.id.Expense_Type);
                    final TextView Expense_Value = (TextView) alertLayout.findViewById(R.id.Expense_Value);
                    final TextView Expense_Disc = (TextView) alertLayout.findViewById(R.id.Expense_Disc);

                    Expense_Type.setText(timeLineModel.get_exp_type());
                    Expense_Value.setText(timeLineModel.get_exp_value());
                    Expense_Disc.setText(timeLineModel.get_exp_disc());


                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setTitle("Expense");
                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    // disallow cancel of AlertDialog on click of back button and outside touch
                    alert.setCancelable(false);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();

                }
            });
        }

        String zipPath = mContext.getApplicationInfo().dataDir+"/default_image.PNG";

        if(timeLineModel.get_image_paths().equals(zipPath)){
            holder.Btn_AcceptReject.setImageResource(R.drawable.cwac_cam2_ic_close_white);
            holder.Btn_Call.setEnabled(false);
        }else {
            holder.Btn_AcceptReject.setImageResource(R.drawable.ic_done);
            holder.Btn_Call.setEnabled(true);
            holder.Btn_Call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View alertLayout = mLayoutInflater.inflate(R.layout.image_data, null);

                    final ImageView ImageView1 = (ImageView) alertLayout.findViewById(R.id.image1);
                    final ImageView ImageView2 = (ImageView) alertLayout.findViewById(R.id.image2);
                    final ImageView ImageView3 = (ImageView) alertLayout.findViewById(R.id.image3);
                    final ImageView ImageView4 = (ImageView) alertLayout.findViewById(R.id.image4);

                    ArrayList<String> f = new ArrayList<String>();// list of file paths

                    //getting selected image
                    File TargetLocation = new File(mContext.getApplicationInfo().dataDir+"/new"+timeLineModel.get_title());
                    File ZipFile = new File(timeLineModel.get_image_paths());


                    if(!TargetLocation.exists()) {
                        try {
                            //unzip
                            unzip(ZipFile, TargetLocation);
                            //get images from folders
                            getFromSdcard(TargetLocation,f);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        getFromSdcard(TargetLocation,f);
                    }

                    if(f.size()==1) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(f.get(0));
                        ImageView1.setImageBitmap(myBitmap);

                        ImageView2.setVisibility(View.GONE);
                        ImageView3.setVisibility(View.GONE);
                        ImageView4.setVisibility(View.GONE);
                    }else if (f.size()==2){
                        Bitmap myBitmap = BitmapFactory.decodeFile(f.get(0));
                        ImageView1.setImageBitmap(myBitmap);

                        Bitmap myBitmap2 = BitmapFactory.decodeFile(f.get(1));
                        ImageView2.setImageBitmap(myBitmap2);

                        ImageView3.setVisibility(View.GONE);
                        ImageView4.setVisibility(View.GONE);
                    }else if (f.size()==3){
                        Bitmap myBitmap = BitmapFactory.decodeFile(f.get(0));
                        ImageView1.setImageBitmap(myBitmap);

                        Bitmap myBitmap2 = BitmapFactory.decodeFile(f.get(1));
                        ImageView2.setImageBitmap(myBitmap2);

                        Bitmap myBitmap3 = BitmapFactory.decodeFile(f.get(2));
                        ImageView3.setImageBitmap(myBitmap3);

                        ImageView4.setVisibility(View.GONE);
                    }else if (f.size()==4){
                        Bitmap myBitmap = BitmapFactory.decodeFile(f.get(0));
                        ImageView1.setImageBitmap(myBitmap);

                        Bitmap myBitmap2 = BitmapFactory.decodeFile(f.get(1));
                        ImageView2.setImageBitmap(myBitmap2);

                        Bitmap myBitmap3 = BitmapFactory.decodeFile(f.get(2));
                        ImageView3.setImageBitmap(myBitmap3);

                        Bitmap myBitmap4 = BitmapFactory.decodeFile(f.get(3));
                        ImageView4.setImageBitmap(myBitmap4);
                    }

                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setTitle("Images");
                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    // disallow cancel of AlertDialog on click of back button and outside touch
                    alert.setCancelable(false);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            });

        }


    }

    public void getFromSdcard(File file, ArrayList<String> f)
    {
        File[] listFile;
        if (file.isDirectory())
        {
            listFile = file.listFiles();


            for (int i = 0; i < listFile.length; i++)
            {

                f.add(listFile[i].getAbsolutePath());

            }
        }
    }



    //calling service api for Complated order
    private void ComplatedOrder(final model_completed timeLineModel, final pending_order_viewHolder holder,final int position){


        JsonObject json = new JsonObject();
        json.addProperty("flag", "stpid");
        json.addProperty("stpid",timeLineModel.stpid);

        Ion.with(mContext)
                .load(getTripStops.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {

//        Ion.with(mContext)
//                .load("GET", getOrders.value)
//                .addQuery("flag", "stpid")
//                  .addQuery("stpid", timeLineModel.stpid + "")
////                  .addQuery("orddid", timeLineModel.orderdetailid + "")
////                  .addQuery("rdid", Global.loginusr.getDriverid() + "")
//                .addQuery("stat","1")
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//
//                        try {
                            if (result != null) Log.v("result", result.toString());
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<model_completed>>() {
                                }.getType();
                                List<model_completed> events = (List<model_completed>) gson.fromJson(result.get("data"), listType);

                            if (events.size() > 0) {

                                JsonObject Data = result.get("data").getAsJsonArray().get(0).getAsJsonObject();
//                                if (Data.get("ordno").getAsString().equals(timeLineModel.ordno)) {
//                                    timeLineModel.custaddr = Data.get("cadr").getAsString();
//                                    timeLineModel.custname = Data.get("stpdesc").getAsString();
//                                    timeLineModel.deltime = Data.get("dtm").getAsString();
//                                    timeLineModel.dltm = Data.get("dltm").getAsString();

//                                    holder.mTime.setText(mContext.getString(R.string.dilvered_at) + timeLineModel.deltime + "");
//                                    holder.collected_cash.setText("₹ " + timeLineModel.amtcollect + "");
//                                    holder.Custmer_name.setText(timeLineModel.custname + "");
//                                    holder.mDeliver_at.setText(timeLineModel.custaddr + " ");
//                                    holder.mDate.setText(timeLineModel.dltm + "");
                                    mFeedList.get(position).isEnabled = true;
                                    notifyDataSetChanged();

//                                }
                            }


                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        loader.hide();
                    }
                });
    }


    public static void unzip(File zipFile, File targetDirectory) throws IOException {
        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            /* if time should be restored as well
            long time = ze.getTime();
            if (time > 0)
                file.setLastModified(time);
            */
            }
        } finally {
            zis.close();
        }
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }


}
