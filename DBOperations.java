package com.example.luxevistaresort;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBOperations extends SQLiteOpenHelper {

    public DBOperations(@Nullable Context context) {super(context, "DB_Resort", null, 2);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tblUser(username VARCHAR(50) PRIMARY KEY, password VARCHAR(25))";
        db.execSQL(sql);
        sql = "CREATE TABLE tblRoom(roomID VARCHAR(30) PRIMARY KEY, roomType VARCHAR(50), price DOUBLE, availability VARCHAR(30), roomDetails VARCHAR(600), imageRoom BLOG)";
        db.execSQL(sql);
        sql = "CREATE TABLE tblBooking(bookingID VARCHAR(30), roomID VARCHAR(30), checkINDate DATE(10), checkOutDate DATE(10), userFullName VARCHAR(50), username VARCHAR(50), contactNo INT(15))";
        db.execSQL(sql);
        sql = "CREATE TABLE tblService(serviceID VARCHAR(30), serviceType VARCHAR(50), serviceDetails VARCHAR(150), servicePrice DOUBLE, imageService BLOG)";
        db.execSQL(sql);
        sql = "CREATE TABLE tblReservation(reservationID VARCHAR(30), serviceID VARCHAR(30), userFullName VARCHAR(50), date DATE(10), time VARCHAR(10))";
        db.execSQL(sql);

        sql = "CREATE TABLE tblRooms(roomID VARCHAR(30) PRIMARY KEY, roomType VARCHAR(50), price DOUBLE, availability VARCHAR(30), roomDetails VARCHAR(150), imageRoom BLOG)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS tblUser");
        db.execSQL("DROP TABLE IF EXISTS tblRoom");
        db.execSQL("DROP TABLE tblRoom");
        db.execSQL("DELETE FROM tblRooms WHERE roomID='SHR001'");
    }



    public void insertUser(User user){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        database.insert("tblUser", null, contentValues);

    }

    public boolean userlogin(User user){
        SQLiteDatabase database = getReadableDatabase();

        String sql = "SELECT * FROM tblUser WHERE username='"+user.getUsername()+"' AND password='"+user.getPassword()+"'";
        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.getCount() == 1){
            return true;
        }else{
            return false;
        }
    }

    public int updateUser(User user){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        return database.update("tblUser", contentValues, "username= ?", new String[]{user.getUsername()});
    }

    public int deleteUser(String user){
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("tblUser", "username= ?", new String[]{user});
    }

    public ArrayList<String> allUsernames(){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "SELECT username FROM tblUser";
        Cursor cursor = database.rawQuery(sql, null);
        ArrayList<String> usernameList = new ArrayList<>();

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                usernameList.add(cursor.getString(0));
            }
        }
        return usernameList;
    }

    public long insertRooms(Rooms rooms){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("roomID", rooms.getRoomID());
        contentValues.put("roomType", rooms.getRoomType());
        contentValues.put("price", rooms.getPrice());
        contentValues.put("availability", rooms.getAvailability());
        contentValues.put("roomDetails", rooms.getDetails());
        contentValues.put("imageRoom", rooms.getRoomImage());

        return  database.insert("tblRooms", null, contentValues);


    }

    public ArrayList<Rooms> viewAllRooms(){
       SQLiteDatabase database = getWritableDatabase();
       Cursor cursor = database.rawQuery("SELECT * FROM tblRooms", null);
       ArrayList<Rooms> rooms = new ArrayList<>();

        if (cursor.getCount()>0){
           while (cursor.moveToNext()){
                Rooms rooms1 = new Rooms();
                rooms1.setRoomID(cursor.getString(0));
                rooms1.setRoomType(cursor.getString(1));
                rooms1.setPrice(cursor.getDouble(2));
                rooms1.setAvailability(cursor.getString(3));
                rooms1.setDetails(cursor.getString(4));
                rooms1.setRoomImage(cursor.getBlob(5));

                rooms.add(rooms1);
            }
        }
        return rooms;
    }
    public int updateRooms(Rooms rooms){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("roomID", rooms.getRoomID());
        contentValues.put("roomType", rooms.getRoomType());
        contentValues.put("price", rooms.getPrice());
        contentValues.put("availability", rooms.getAvailability());
        contentValues.put("roomDetails", rooms.getDetails());
        contentValues.put("imageRoom", rooms.getRoomImage());

        return  database.update("tblRooms", contentValues, "roomID= ?", new String[]{rooms.getRoomID()});
    }
    public int deleteRooms(String rooms){
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("tblRooms", "roomID= ?", new String[]{rooms});
    }

    public Rooms searchRoom(String roomType){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "SELECT * FROM tblRooms WHERE roomType='"+roomType+"'";
        Cursor cursor = database.rawQuery(sql, null);
        Rooms rooms = new Rooms();

        if (cursor.getCount() == 1){
            cursor.moveToFirst();
            rooms.setRoomID(cursor.getString(0));
            rooms.setRoomType(cursor.getString(1));
            rooms.setPrice(cursor.getDouble(2));
            rooms.setAvailability(cursor.getString(3));
            rooms.setDetails(cursor.getString(4));
            rooms.setRoomImage(cursor.getBlob(5));
        }else{
            rooms = null;
        }
        return rooms;
    }

    public long insertServices(Services services){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("serviceID", services.getServiceID());
        contentValues.put("serviceType", services.getServiceType());
        contentValues.put("serviceDetails", services.getServiceDetails());
        contentValues.put("servicePrice", services.getServicePrice());
        contentValues.put("imageService", services.getImageService());

        return database.insert("tblService", null, contentValues);
    }

    public ArrayList<Services> viewAllServices(){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM tblService", null);
        ArrayList<Services> services = new ArrayList<>();

        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                Services services1 = new Services();
                services1.setServiceID(cursor.getString(0));
                services1.setServiceType(cursor.getString(1));
                services1.setServiceDetails(cursor.getString(2));
                services1.setServicePrice(cursor.getDouble(3));
                services1.setImageService(cursor.getBlob(4));

                services.add(services1);
            }
        }
        return services;
    }

    public int updateServices(Services services){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("serviceID", services.getServiceID());
        contentValues.put("serviceType", services.getServiceType());
        contentValues.put("serviceDetails", services.getServiceDetails());
        contentValues.put("servicePrice", services.getServicePrice());
        contentValues.put("imageService", services.getImageService());

        return  database.update("tblService", contentValues, "serviceID= ?", new String[]{services.getServiceID()});

    }

    public int deleteService(String services){
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("tblService", "serviceID= ?", new String[]{services});
    }

    public Services searchServices(String serviceType){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "SELECT * FROM tblService WHERE serviceType='"+serviceType+"'";
        Cursor cursor = database.rawQuery(sql, null);
        Services services = new Services();

        if (cursor.getCount() == 1){
            cursor.moveToFirst();
            services.setServiceID(cursor.getString(0));
            services.setServiceType(cursor.getString(1));
            services.setServiceDetails(cursor.getString(2));
            services.setServicePrice(cursor.getDouble(3));
            services.setImageService(cursor.getBlob(4));
        }else{
            services = null;
        }
        return services;
    }

    public long insertBookings(Bookings bookings){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("bookingID", bookings.getBookingID());
        contentValues.put("roomID", bookings.getRoomType());
        contentValues.put("checkINDate", bookings.getCheckInDate());
        contentValues.put("checkOutDate", bookings.getCheckOutDate());
        contentValues.put("userFullName", bookings.getFullname());
        contentValues.put("username", bookings.getEmail());
        contentValues.put("contactNo", bookings.getContNo());

        return database.insert("tblBooking", null, contentValues);
    }

    public ArrayList<Bookings> viewAllBookings(){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM tblBooking", null);
        ArrayList<Bookings> bookings = new ArrayList<>();

        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Bookings bookings1 = new Bookings();

                bookings1.setBookingID(cursor.getString(0));
                bookings1.setRoomType(cursor.getString(1));
                bookings1.setCheckInDate(cursor.getString(2));
                bookings1.setCheckOutDate(cursor.getString(3));
                bookings1.setFullname(cursor.getString(4));
                bookings1.setEmail(cursor.getString(5));
                bookings1.setContNo(cursor.getInt(6));

                bookings.add(bookings1);
            }
        }
        return bookings;
    }

    public int deleteBooking(String bookings){
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("tblBooking", "bookingID= ?", new String[]{bookings});
    }

    public long insertReservations(Reservations reservations){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("reservationID", reservations.getReservationID());
        contentValues.put("serviceID", reservations.getServiceType());
        contentValues.put("userFullName", reservations.getFullnameRes());
        contentValues.put("date", reservations.getResDate());
        contentValues.put("time", reservations.getResTime());

        return database.insert("tblReservation", null, contentValues);
    }



}
