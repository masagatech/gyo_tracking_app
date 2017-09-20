package com.goyo.traveltracker.adapters;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.forms.Orientation;
import com.goyo.traveltracker.forms.PendingOrdersView;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_notification;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.Context.BIND_IMPORTANT;
import static com.goyo.traveltracker.Service.RiderStatus.mBuilder;
import static com.goyo.traveltracker.Service.RiderStatus.notificationManager;
import static com.goyo.traveltracker.gloabls.Global.urls.setStatus;

/**
 * Created by fajar on 02-Jun-17.
 */

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapterViewHolder> {

    private List<model_notification> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    static HashMap<Integer, CountDownTimer> thread = new HashMap<>();
    SQLBase sb;

    public NewOrderAdapter(List<model_notification> feedList, Orientation orientation, boolean withLinePadding, SQLBase _sb) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;
        sb = _sb;
    }

    @Override
    public int getItemViewType(int position) {
        return PendingOrdersView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public NewOrderAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;
        view = mLayoutInflater.inflate(R.layout.timeline_new_order, parent, false);
        return new NewOrderAdapterViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final NewOrderAdapterViewHolder holder,int position) {
//        if (holder.timer != null) KillTimer(holder.timer);
        final model_notification timeLineModel = mFeedList.get(position);

        holder.mStops.setText(timeLineModel.msg + "");
        holder.popup_counter.setText(timeLineModel.createdon+"");
//        holder.Cash_amount.setText(String.format("%.2f", Double.parseDouble(timeLineModel.amt)));
        holder.mOulets.setText(timeLineModel.title+"");
//        holder.mTime.setText(timeLineModel.pcktm);


        //accept/reject button hide if order is expired
//        if(!timeLineModel.isExpired) {
//            holder.Btn_Reject.setVisibility(View.VISIBLE);
//            holder.Btn_Accept.setVisibility(View.VISIBLE);
//        }


//        holder.Btn_Accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setNoti("accept");
//                int newPosition = holder.getAdapterPosition();
//
//                //calling Api
//                setStatus("accord", timeLineModel.ordid, newPosition, timeLineModel.autoid, holder,"");
//
//
//            }
//        });

//        holder.Btn_Reject.setOnClickListener(new View.OnClickListener() {
//                                                 @Override
//                                                 public void onClick(View v) {
//                                                     final EditText edittext = new EditText(mContext);
//                                                     edittext.setMaxLines(100);
//
//                                                     //showing alert dialog if press reject
//                                                    final AlertDialog alertDialog = new AlertDialog.Builder(mContext)
//                                                             .setTitle(mContext.getResources().getString(R.string.reject_reason_head))
//                                                             .setMessage(mContext.getResources().getString(R.string.reject_reason_body))
//                                                             .setView(edittext)
//                                                             .setPositiveButton(mContext.getResources().getString(R.string.yes), null)
//                                                             .setNegativeButton(R.string.alert_no_text, null)
//                                                             .setIcon(R.drawable.rider_del)
//                                                             .create();
//
//                                                     alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//
//                                                         @Override
//                                                         public void onShow(DialogInterface dialog) {
//
//                                                             Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
//                                                             button.setOnClickListener(new View.OnClickListener() {
//                                                                 @Override
//                                                                 public void onClick(View view) {
//                                                                     String feedabck = "";
//                                                                     //Getting rider feed back
//                                                                     feedabck = edittext.getText().toString();
//                                                                     //hiding button and canceling timer
//
//                                                                     int newPosition = holder.getAdapterPosition();
//                                                                     if (feedabck.equals("")) {
//                                                                         Toast.makeText(mContext, "Please enter the reason!", Toast.LENGTH_SHORT).show();
//                                                                     } else {
//                                                                         //calling api
//                                                                         setNoti("reject");
//                                                                         setStatus("rejord", timeLineModel.ordid, newPosition, timeLineModel.autoid, holder, feedabck);
//                                                                         alertDialog.dismiss();
//                                                                     }
//                                                                 }
//                                                             });
//
//                                                             Button button2 = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
//                                                             button2.setOnClickListener(new View.OnClickListener() {
//                                                                 @Override
//                                                                 public void onClick(View view) {
//                                                                     alertDialog.dismiss();
//                                                                 }
//                                                             });
//                                                         }
//                                                     });
//                                                     alertDialog.show();
//                                                 }
//                                             });
//        Log.v("rmtm", "" + timeLineModel.remaintime);
//
//
//        //timer in each order
//        holder.timer = new CountDownTimer(timeLineModel.remaintime, 1000) {
//            public void onFinish() {
//                int newPosition = holder.getAdapterPosition();
//                Log.v("autoid", "" + timeLineModel.autoid);
//                Log.v("pos", "" + newPosition);
//                sb.NOTIFICATION_DELETE(timeLineModel.autoid + "");
//                holder.popup_counter.setText(mContext.getResources().getString(R.string.exp));
//                if(mFeedList.size()>0) {
//                    mFeedList.remove(newPosition);
//                    notifyItemRemoved(newPosition);
//                    notifyItemRangeChanged(newPosition, getItemCount());
//                }
//                holder.Btn_Reject.setVisibility(View.GONE);
//                holder.Btn_Accept.setVisibility(View.GONE);
//                timeLineModel.isExpired = true;
//            }
//
//            public void onTick(long millisUntilFinished) {
//                timeLineModel.remaintime -= 1000;
//                holder.popup_counter.setText("" + String.format("%02d:%02d",
//                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
//                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
//            }
//        }.start();
    }



    //showing notification
    private void setNoti(String s){

        if(s.equals("reject")){
            s=mContext.getString(R.string.rejectorder_notifi);
        }else {
            s=mContext.getString(R.string.acceptorder_notifi);
        }
        mBuilder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.drawable.rider)
                        .setContentTitle(s)
                        .setDefaults(Notification.DEFAULT_ALL) // must requires VIBRATE permission
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVisibility(BIND_IMPORTANT)
                        .setOngoing(false);
        Notification note = mBuilder.build();
        if(notificationManager!=null) {
            notificationManager.notify(0, note);
        }
    }


    //service api

    private void setStatus(final String flag, String ordid, final int position, final int autoid,final NewOrderAdapterViewHolder holder,String feedabck) {
        if(feedabck==null){
            feedabck="";
        }
        JsonObject json = new JsonObject();
        json.addProperty("flag", flag);
        json.addProperty("ordid", ordid);
        json.addProperty("rdid", Global.loginusr.getDriverid() + "");
        json.addProperty("remark",feedabck);

        Ion.with(mContext)
                .load(setStatus.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) {
                                JsonObject data = result.get("data").getAsJsonObject();
                                if (data.get("status").getAsBoolean()) {
                                    String msg = data.get("msg").toString();
                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                    if(flag=="accord" ){
                                        holder.popup_counter.setText(mContext.getResources().getString(R.string.notification_accept)) ;
                                        holder.timer.cancel();
                                        holder.timer= null;
                                        LastOrderCheck();
                                        mFeedList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, getItemCount());
//                                        notifyItemRemoved(position);

                                    }else{
                                        holder.popup_counter.setText(mContext.getResources().getString(R.string.notification_reject)) ;
                                        LastOrderCheck();
                                        holder.timer.cancel();
                                        holder.timer= null;
                                        mFeedList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, getItemCount());
                                    }
                                } else {
                                    holder.popup_counter.setText(mContext.getResources().getString(R.string.notification_alredyassign));
                                    LastOrderCheck();
                                    mFeedList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, getItemCount());
                                    String msg = data.get("msg").toString();
                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                }
                                sb.NOTIFICATION_DELETE(autoid + "");

                            } else {
                                Toast.makeText(mContext, result.toString(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });

    }

    private void LastOrderCheck(){
        if(mFeedList.size()==1){
            ((Activity) mContext).finish();
        }
    }

    public void Kill() {
        Set<Map.Entry<Integer, CountDownTimer>> s = thread.entrySet();
        if (s != null) {
            Iterator it = s.iterator();
            while (it.hasNext()) {
                try {

                    Map.Entry pairs = (Map.Entry) it.next();
                    CountDownTimer r = (CountDownTimer) pairs.getValue();
                    r.cancel();
                    r = null;
                } catch (Exception x) {

                }
            }
            s = null;
            it = null;
            thread.clear();
        }


    }

    public void KillTimer(CountDownTimer tmr) {
        tmr.cancel();
    }

    @Override
    public int getItemCount() {
        return mFeedList.size();
    }


}
