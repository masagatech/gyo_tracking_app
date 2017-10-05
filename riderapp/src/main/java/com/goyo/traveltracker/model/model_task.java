package com.goyo.traveltracker.model;

import com.goyo.traveltracker.forms.OrderStatus;

/**
 * Created by mis on 14-Jul-17.
 */

public class model_task {
    int _id;
    String _is_server_send;
    String _title;
    String _body;
    String _lat;
    String _lon;
    String _tags;
    String _creat_on;
    String _time;
    String _image_paths;
    String _exp_type;
    String _exp_id;
    String _exp_value;
    String _exp_disc;
    String _in_time;
    String _out_time;
    String _stop_id;

    public String get_stop_id() {
        return _stop_id;
    }

    public void set_stop_id(String _stop_id) {
        this._stop_id = _stop_id;
    }

    public String get_exp_type() {
        return _exp_type;
    }

    public String get_in_time() {
        return _in_time;
    }

    public void set_in_time(String _in_time) {
        this._in_time = _in_time;
    }

    public String get_out_time() {
        return _out_time;
    }

    public void set_out_time(String _out_time) {
        this._out_time = _out_time;
    }

    public void set_exp_type(String _exp_type) {
        this._exp_type = _exp_type;
    }

    public String get_exp_id() {
        return _exp_id;
    }

    public void set_exp_id(String _exp_id) {
        this._exp_id = _exp_id;
    }

    public String get_exp_value() {
        return _exp_value;
    }

    public void set_exp_value(String _exp_value) {
        this._exp_value = _exp_value;
    }

    public String get_exp_disc() {
        return _exp_disc;
    }

    public void set_exp_disc(String _exp_disc) {
        this._exp_disc = _exp_disc;
    }

    public String get_image_paths() {
        return _image_paths;
    }

    public void set_image_paths(String _image_paths) {
        this._image_paths = _image_paths;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public String get_tags() {
        return _tags;
    }

    public void set_tags(String _tags) {
        this._tags = _tags;
    }

    public String get_is_server_send() {
        return _is_server_send;
    }

    public void set_is_server_send(String _is_server_send) {
        this._is_server_send = _is_server_send;
    }

    public String get_creat_on() {
        return _creat_on;
    }

    public void set_creat_on(String _creat_on) {
        this._creat_on = _creat_on;
    }



    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_body() {
        return _body;
    }

    public void set_body(String _body) {
        this._body = _body;
    }

    public String get_lat() {
        return _lat;
    }

    public void set_lat(String _lat) {
        this._lat = _lat;
    }

    public String get_lon() {
        return _lon;
    }

    public void set_lon(String _lon) {
        this._lon = _lon;
    }


    // constructor
    public model_task(String _title, String _body, String _lat,String _lon,String tags,String _creat_on,String _is_server_send,String _time_, String _image_paths_,String _exp_id,String _exp_type,String _exp_value,String _exp_disc,String in_time,String out_time,String stop_id){
        this._title = _title;
        this._body = _body;
        this._lat = _lat;
        this._lon = _lon;
        this._tags = tags;
        this._creat_on = _creat_on;
        this._is_server_send = _is_server_send;
        this._time = _time_;
        this._image_paths = _image_paths_;
        this._exp_id = _exp_id;
        this._exp_type = _exp_type;
        this._exp_value = _exp_value;
        this._exp_disc = _exp_disc;
        this._in_time = in_time;
        this._out_time = out_time;
        this._stop_id = stop_id;
    }

    public OrderStatus status = OrderStatus.ACTIVE;
    public boolean isEnabled = false;
}
