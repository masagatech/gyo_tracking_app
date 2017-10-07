package com.goyo.traveltracker.database;

/**
 * Created by mTech on 03-Mar-2017.
 */
public final class Tables {

        public static class tbl_driver_info{
            public static final String name = "tbl_driver_info";
            public static final String autoid = "autoid";
            public static final String mibuid = "mibuid";
            public static final String servrid = "servrid";
            public static final String sarthinm = "sarthinm";
            public static final String mob1 = "mob1";
            public static final String mob2 = "mob2";
            public static final String adharno = "adharno";
            public static final String ownrship = "ownrship";
            public static final String vehno = "vehno";
            public static final String btchno = "btchno";
            public static final String yrsold = "yrsold";
            public static final String howmny = "howmny";
            public static final String vehtype = "vehtype";
            public static final String driving = "driving";
            public static final String alruseing = "alruseing";
            public static final String goyointr = "goyointr";
            public static final String doyohv = "doyohv";
            public static final String prefloc = "prefloc";
            public static final String remarks = "remarks";
            public static final String createon = "createon";
            public static final String createdby = "createdby";
            public static final String sentToserver = "sentToserver";
            public static final String lat = "lat";
            public static final String lon = "lon";
            public static final String profpic = "profpic";
            public static final String profpic_upload = "profpic_upload";

            public static final String doc1 = "doc1";
            public static final String doc1_upload = "doc1_upload";
            public static final String doc2 = "doc2";
            public static final String doc2_upload = "doc2_upload";
            public static final String doc3 = "doc3";
            public static final String doc3_upload = "doc3_upload";
            public static final String doc4 = "doc4";
            public static final String doc4_upload = "doc4_upload";



        }

    public static class tblnotification{

        public static final String name = "tbl_notifications";
        public static final String autoid = "autoid";
        public static final String createon = "createon";
        public static final String data = "data";
        public static final String exp = "exptm";

    }

    public static class tblofflinetask{

        public static final String name = "tbl_offlinetask";

        public static final String Task_Id = "id";
        public static final String Task_Title = "task_title";
        public static final String Task_Body = "task_body";
        public static final String Task_Lat = "task_lat";
        public static final String Task_Lon = "task_lon";
        public static final String Task_Tags = "task_tags";
        public static final String Task_Creat_On = "task_creat_on";
        public static final String Is_Server_Send = "is_server_send";
        public static final String Task_Time= "task_time";
        public static final String Task_Images_Paths= "task_image_paths";
        public static final String EXP_Type= "exp_type";
        public static final String EXP_ID= "exp_id";
        public static final String EXP_Value= "exp_value";
        public static final String EXP_Disc= "exp_disc";
        public static final String IN_TIME= "IN_TIME";
        public static final String OUT_TIME= "OUT_TIME";
        public static final String STOP_ID= "STOP_ID";
        public static final String TEST= "TEST";

    }



    public static class tbltags{

        public static final String name = "tbl_tags";

        public static final String Tag_Id = "id";
        public static final String Tag_Title = "tag_title";
        public static final String Tag_remark_1 = "tag__remark_1";
        public static final String Tag_remark_2 = "tag__remark_2";
        public static final String Tag_remark_3 = "tag__remark_3";
        public static final String Tag_Empl_Id = "tag_empl_id";
        public static final String Tag_Creat_On = "tag_creat_on";
        public static final String Is_Server_Send = "is_server_send";

    }

    public static class tbltasks{

        public static final String name = "tbl_task";

        public static final String Task_Id = "id";
        public static final String Task_Nature = "task_nature";
        public static final String Task_Status = "task_status";
        public static final String Task_Value = "task_value";
        public static final String Task_Remark = "task_remark";
        public static final String Task_Tags = "task_tags";
        public static final String Task_Creat_On = "task_creat_on";
        public static final String Is_Server_Send = "is_server_send";
        public static final String Tks_id = "tks_id";
        public static final String EXP_Type= "exp_type";
        public static final String EXP_ID= "exp_id";
        public static final String EXP_Value= "exp_value";
        public static final String EXP_Disc= "exp_disc";
        public static final String LAT= "lat";
        public static final String LON= "lon";
        public static final String TIME= "time";
    }

    public static class tblActivitySumm{


        public static final String Creat_On = "creat_on";
        public static final String LAT= "lat";
        public static final String LON= "lon";
        public static final String TIME= "time";
         public static final String TITLE= "title";
        public static final String TYPE= "type";


    }

    public static class tblallexpense{


        public static final String Date = "date";
        public static final String Time= "time";
        public static final String Name= "name";
        public static final String Value= "value";
        public static final String Disc= "disc";
        public static final String Is_Server= "server";
        public static final String Tags= "tags";


    }

    public static class tblexpense{

        public static final String name = "tbl_expense";

        public static final String Expense_Id = "id";
        public static final String Expense_Name = "expense_name";
        public static final String Expense_Value = "expense_value";
        public static final String Expense_Code= "expense_code";
        public static final String Expense_Disc = "expense_disc";
        public static final String Expense_Server = "expense_server";
        public static final String Exp_ID = "exp_id";
        public static final String Expense_Is_Active = "expense_is_active";
        public static final String Approval_Amount = "approval_amount";
    }

    public static class tblleave{

        public static final String name = "tbl_leave";

        public static final String Leave_Id = "id";
        public static final String Leave_From = "leave_from";
        public static final String Leave_To= "leave_to";
        public static final String Leave_Type= "leave_type";
        public static final String Leave_Server = "leave_server";
        public static final String Leave_Details = "leave_details";
        public static final String Leave_Created_By = "leave_created_by";
        public static final String Leave_Status = "leave_status";
    }


    public static class tblexpense_all{

        public static final String name = "tblexpense_all";

        public static final String Expense_Id = "id";
        public static final String Expense_Type= "expense_type";
        public static final String Expense_Value = "expense_value";
        public static final String Expense_Tags= "expense_tags";
        public static final String Expense_Disc = "expense_disc";
        public static final String Expense_Server = "expense_server";
        public static final String Exp_Created_By = "exp_created_by";
    }


}
