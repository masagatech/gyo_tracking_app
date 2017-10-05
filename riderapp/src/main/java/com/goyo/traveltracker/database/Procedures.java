package com.goyo.traveltracker.database;

/**
 * Created by mTech on 03-Mar-2017.
 */
public final class Procedures {

    // make the constructor private.
    private Procedures(){}

    public static class tbl_driver_info{

        public static final String CREATE = "CREATE TABLE  IF NOT EXISTS "
                + Tables.tbl_driver_info.name +
                "("
                +  Tables.tbl_driver_info.autoid + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  Tables.tbl_driver_info.mibuid  + " VARCHAR, "
                +  Tables.tbl_driver_info.sarthinm  + " VARCHAR ,"
                +  Tables.tbl_driver_info.mob1  + " VARCHAR ,"
                +  Tables.tbl_driver_info.mob2  + " VARCHAR ,"
                +  Tables.tbl_driver_info.adharno  + " VARCHAR ,"
                +  Tables.tbl_driver_info.ownrship  + " VARCHAR ,"
                +  Tables.tbl_driver_info.vehno  + " VARCHAR ,"
                +  Tables.tbl_driver_info.yrsold  + " INTEGER ,"
                +  Tables.tbl_driver_info.btchno + " VARCHAR ,"
                +  Tables.tbl_driver_info.howmny + " INTEGER ,"
                +  Tables.tbl_driver_info.driving  + " VARCHAR ,"
                +  Tables.tbl_driver_info.vehtype + " VARCHAR,"
                +  Tables.tbl_driver_info.alruseing  + " VARCHAR ,"
                +  Tables.tbl_driver_info.goyointr  + " VARCHAR ,"
                +  Tables.tbl_driver_info.doyohv  + " VARCHAR ,"
                +  Tables.tbl_driver_info.prefloc  + " VARCHAR ,"
                +  Tables.tbl_driver_info.createon  + " VARCHAR ,"
                +  Tables.tbl_driver_info.createdby  + " VARCHAR,"
                +  Tables.tbl_driver_info.sentToserver  + " VARCHAR ,"
                +  Tables.tbl_driver_info.remarks  + " VARCHAR ,"
                +  Tables.tbl_driver_info.lat + " VARCHAR,"
                +  Tables.tbl_driver_info.lon+ " VARCHAR,"
                +  Tables.tbl_driver_info.profpic + " VARCHAR,"
                +  Tables.tbl_driver_info.profpic_upload + " VARCHAR,"
                +  Tables.tbl_driver_info.servrid + " VARCHAR,"

                +  Tables.tbl_driver_info.doc1 + " VARCHAR,"
                +  Tables.tbl_driver_info.doc1_upload+ " VARCHAR,"
                +  Tables.tbl_driver_info.doc2 + " VARCHAR,"
                +  Tables.tbl_driver_info.doc2_upload+ " VARCHAR,"
                +  Tables.tbl_driver_info.doc3+ " VARCHAR,"
                +  Tables.tbl_driver_info.doc3_upload+ " VARCHAR,"
                +  Tables.tbl_driver_info.doc4+ " VARCHAR,"
                +  Tables.tbl_driver_info.doc4_upload+ " VARCHAR"
                +")";

        public static final String DROP =  "DROP TABLE IF EXISTS " + Tables.tbl_driver_info.name ;

        public static final String INSERT = "INSERT INTO " + Tables.tbl_driver_info.name
                + " ("
                + Tables.tbl_driver_info.mibuid +  "," + Tables.tbl_driver_info.sarthinm + "," +  Tables.tbl_driver_info.mob1+ ","
                +  Tables.tbl_driver_info.mob2 + "," +  Tables.tbl_driver_info.adharno +","+  Tables.tbl_driver_info.ownrship +","
                +  Tables.tbl_driver_info.vehno +","+  Tables.tbl_driver_info.yrsold +"," +  Tables.tbl_driver_info.btchno +","
                +  Tables.tbl_driver_info.howmny +","+  Tables.tbl_driver_info.driving +"," +  Tables.tbl_driver_info.alruseing +","
                +  Tables.tbl_driver_info.goyointr +","+  Tables.tbl_driver_info.doyohv +"," +  Tables.tbl_driver_info.prefloc +","
                +  Tables.tbl_driver_info.createon +","+  Tables.tbl_driver_info.createdby+","+  Tables.tbl_driver_info.sentToserver
                +","+  Tables.tbl_driver_info.vehtype+","+  Tables.tbl_driver_info.remarks+","+  Tables.tbl_driver_info.lat
                +","+  Tables.tbl_driver_info.lon+","+  Tables.tbl_driver_info.profpic+","+Tables.tbl_driver_info.profpic_upload+","+  Tables.tbl_driver_info.doc1
                +","+  Tables.tbl_driver_info.doc1_upload+","+  Tables.tbl_driver_info.doc2+","+  Tables.tbl_driver_info.doc2_upload
                +","+  Tables.tbl_driver_info.doc3+","+  Tables.tbl_driver_info.doc3_upload+","+  Tables.tbl_driver_info.doc4
                +","+  Tables.tbl_driver_info.doc4_upload
                + ")"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";



    }

    public static class tblnotification{

        public static final String CREATE = "CREATE TABLE  IF NOT EXISTS "
                + Tables.tblnotification.name +
                "("
                +  Tables.tblnotification.autoid + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  Tables.tblnotification.createon  + " VARCHAR, "
                +  Tables.tblnotification.data  + " VARCHAR, "
                +  Tables.tblnotification.exp  + " INTEGER "
                +")";


        public static final String INSERT = "INSERT INTO " + Tables.tblnotification.name
                + " ("
                + Tables.tbl_driver_info.createon +  "," + Tables.tblnotification.data
                +  "," + Tables.tblnotification.exp
                + ")"
                + " VALUES (?,?,?)";

        public static final String UPGRADE_2 = "ALTER TABLE " + Tables.tblnotification.name + " ADD COLUMN " +  Tables.tblnotification.exp +" INTEGER";

    }



    public static class tblofflinetask{

        public static final String CREATE = "CREATE TABLE  IF NOT EXISTS "
                + Tables.tblofflinetask.name +
                "("
                +  Tables.tblofflinetask.Task_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  Tables.tblofflinetask.Task_Title  + " VARCHAR, "
                +  Tables.tblofflinetask.Task_Body  + " VARCHAR, "
                +  Tables.tblofflinetask.Task_Lat  + " VARCHAR, "
                +  Tables.tblofflinetask.Task_Lon  + " VARCHAR, "
                +  Tables.tblofflinetask.Task_Tags + " VARCHAR, "
                +  Tables.tblofflinetask.Task_Creat_On  + " VARCHAR, "
                +  Tables.tblofflinetask.Is_Server_Send  + " VARCHAR, "
                +  Tables.tblofflinetask.Task_Time  + " VARCHAR, "
                +  Tables.tblofflinetask.Task_Images_Paths + " VARCHAR, "
                +  Tables.tblofflinetask.EXP_ID + " VARCHAR, "
                +  Tables.tblofflinetask.EXP_Type+ " VARCHAR, "
                +  Tables.tblofflinetask.EXP_Value + " VARCHAR, "
                +  Tables.tblofflinetask.EXP_Disc + " VARCHAR, "
                +  Tables.tblofflinetask.IN_TIME + " VARCHAR, "
                +  Tables.tblofflinetask.OUT_TIME + " VARCHAR, "
                +  Tables.tblofflinetask.STOP_ID + " VARCHAR "
                +")";


        public static final String INSERT = "INSERT INTO " + Tables.tblnotification.name
                + " ("
                + Tables.tbl_driver_info.createon +  "," + Tables.tblnotification.data
                +  "," + Tables.tblnotification.exp
                + ")"
                + " VALUES (?,?,?)";

        public static final String UPGRADE_2 = "ALTER TABLE " + Tables.tblnotification.name + " ADD COLUMN " +  Tables.tblnotification.exp +" INTEGER";

    }


    public static class tbltag{

        public static final String CREATE = "CREATE TABLE  IF NOT EXISTS "
                + Tables.tbltags.name +
                "("
                +  Tables.tbltags.Tag_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  Tables.tbltags.Tag_Title  + " VARCHAR, "
                +  Tables.tbltags.Tag_remark_1  + " VARCHAR, "
                +  Tables.tbltags.Tag_remark_2  + " VARCHAR, "
                +  Tables.tbltags.Tag_remark_3  + " VARCHAR, "
                +  Tables.tbltags.Tag_Empl_Id  + " VARCHAR, "
                +  Tables.tbltags.Tag_Creat_On  + " VARCHAR, "
                +  Tables.tbltags.Is_Server_Send  + " VARCHAR "
                +")";


        public static final String INSERT = "INSERT INTO " + Tables.tblnotification.name
                + " ("
                + Tables.tbl_driver_info.createon +  "," + Tables.tblnotification.data
                +  "," + Tables.tblnotification.exp
                + ")"
                + " VALUES (?,?,?)";

        public static final String UPGRADE_2 = "ALTER TABLE " + Tables.tblnotification.name + " ADD COLUMN " +  Tables.tblnotification.exp +" INTEGER";

    }


    public static class tbltask{

        public static final String CREATE = "CREATE TABLE  IF NOT EXISTS "
                + Tables.tbltasks.name +
                "("
                +  Tables.tbltasks.Task_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  Tables.tbltasks.Tks_id  + " VARCHAR, "
                +  Tables.tbltasks.Task_Nature  + " VARCHAR, "
                +  Tables.tbltasks.Task_Value  + " VARCHAR, "
                +  Tables.tbltasks.Task_Remark  + " VARCHAR, "
                +  Tables.tbltasks.Task_Tags + " VARCHAR, "
                +  Tables.tbltasks.Task_Status + " VARCHAR, "
                +  Tables.tbltasks.Task_Creat_On  + " VARCHAR, "
                +  Tables.tbltasks.Is_Server_Send  + " VARCHAR, "
                +  Tables.tbltasks.EXP_ID + " VARCHAR, "
                +  Tables.tbltasks.EXP_Type + " VARCHAR, "
                +  Tables.tbltasks.EXP_Value + " VARCHAR, "
                +  Tables.tbltasks.EXP_Disc + " VARCHAR, "
                +  Tables.tbltasks.LAT + " VARCHAR, "
                +  Tables.tbltasks.LON + " VARCHAR, "
                +  Tables.tbltasks.TIME + " VARCHAR "
                +")";


        public static final String INSERT = "INSERT INTO " + Tables.tblnotification.name
                + " ("
                + Tables.tbl_driver_info.createon +  "," + Tables.tblnotification.data
                +  "," + Tables.tblnotification.exp
                + ")"
                + " VALUES (?,?,?)";

        public static final String UPGRADE_2 = "ALTER TABLE " + Tables.tblnotification.name + " ADD COLUMN " +  Tables.tblnotification.exp +" INTEGER";

    }


    public static class tblexpense{

        public static final String CREATE = "CREATE TABLE  IF NOT EXISTS "
                + Tables.tblexpense.name +
                "("
                +  Tables.tblexpense.Expense_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  Tables.tblexpense.Exp_ID  + " VARCHAR, "
                +  Tables.tblexpense.Expense_Name  + " VARCHAR, "
                +  Tables.tblexpense.Expense_Disc  + " VARCHAR, "
                +  Tables.tblexpense.Expense_Value + " VARCHAR, "
                +  Tables.tblexpense.Expense_Code  + " VARCHAR, "
                +  Tables.tblexpense.Expense_Is_Active  + " VARCHAR, "
                +  Tables.tblexpense.Expense_Server  + " VARCHAR ,"
                +  Tables.tblexpense.Approval_Amount  + " VARCHAR "
                +")";


        public static final String INSERT = "INSERT INTO " + Tables.tblnotification.name
                + " ("
                + Tables.tbl_driver_info.createon +  "," + Tables.tblnotification.data
                +  "," + Tables.tblnotification.exp
                + ")"
                + " VALUES (?,?,?)";

        public static final String UPGRADE_2 = "ALTER TABLE " + Tables.tblnotification.name + " ADD COLUMN " +  Tables.tblnotification.exp +" INTEGER";

    }

    public static class tblleave{

        public static final String CREATE = "CREATE TABLE  IF NOT EXISTS "
                + Tables.tblleave.name +
                "("
                +  Tables.tblleave.Leave_Id+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  Tables.tblleave.Leave_From  + " VARCHAR, "
                +  Tables.tblleave.Leave_To  + " VARCHAR, "
                +  Tables.tblleave.Leave_Type  + " VARCHAR, "
                +  Tables.tblleave.Leave_Details  + " VARCHAR, "
                +  Tables.tblleave.Leave_Server  + " VARCHAR, "
                +  Tables.tblleave.Leave_Status  + " VARCHAR, "
                +  Tables.tblleave.Leave_Created_By + " VARCHAR "
                +")";


        public static final String INSERT = "INSERT INTO " + Tables.tblnotification.name
                + " ("
                + Tables.tbl_driver_info.createon +  "," + Tables.tblnotification.data
                +  "," + Tables.tblnotification.exp
                + ")"
                + " VALUES (?,?,?)";

        public static final String UPGRADE_2 = "ALTER TABLE " + Tables.tblnotification.name + " ADD COLUMN " +  Tables.tblnotification.exp +" INTEGER";

    }


    public static class tblexpense_all{

        public static final String CREATE = "CREATE TABLE  IF NOT EXISTS "
                + Tables.tblexpense_all.name +
                "("
                +  Tables.tblexpense_all.Expense_Id+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  Tables.tblexpense_all.Expense_Type  + " VARCHAR, "
                +  Tables.tblexpense_all.Expense_Value  + " VARCHAR, "
                +  Tables.tblexpense_all.Expense_Tags  + " VARCHAR, "
                +  Tables.tblexpense_all.Expense_Disc  + " VARCHAR, "
                +  Tables.tblexpense_all.Exp_Created_By  + " VARCHAR, "
                +  Tables.tblexpense_all.Expense_Server + " VARCHAR "
                +")";


        public static final String INSERT = "INSERT INTO " + Tables.tblnotification.name
                + " ("
                + Tables.tbl_driver_info.createon +  "," + Tables.tblnotification.data
                +  "," + Tables.tblnotification.exp
                + ")"
                + " VALUES (?,?,?)";

        public static final String UPGRADE_2 = "ALTER TABLE " + Tables.tblnotification.name + " ADD COLUMN " +  Tables.tblnotification.exp +" INTEGER";

    }


}
