package com.tamertokbaev.momari.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.tamertokbaev.momari.models.UserDB

class DBManager(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Qytap"
        private val USERS_TABLE = "users"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE IF NOT EXISTS $USERS_TABLE (\n" +
                "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\tfullName TEXT NOT NULL,\n" +
                "\temail TEXT NOT NULL UNIQUE,\n" +
                "\tbirthdate DATE DEFAULT NULL,\n" +
                "\tpassword TEXT NOT NULL,\n" +
                "\tbalance REAL DEFAULT 0,\t\n" +
                "\tlastLogin TEXT,\n" +
                "\tcreatedAt TEXT,\n" +
                "\tdeletedAt TEXT DEFAULT NULL\n" +
                ");"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val upgrade = "DROP TABLE IF EXISTS $USERS_TABLE"
        db!!.execSQL(upgrade)
        onCreate(db)
    }

    // DataBase manipulation functions:

    // TODO 29.11.2021

    // Adding new User
    fun addNewUser(userDB: UserDB):Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", userDB.id)
        contentValues.put("fullName", userDB.fullName)
        contentValues.put("email", userDB.email)
        contentValues.put("password", userDB.password)

        // Inserting row and preparing SQL query
        val success = db.insert(USERS_TABLE, null, contentValues)
        db.close()
        return success
    }

    // Authenticating User
    @SuppressLint("Recycle", "Range")
    fun checkCredentialsUser(email: String, password: String): Boolean{
        val db = this.readableDatabase
        val query = "SELECT password FROM $USERS_TABLE WHERE email='$email'"
        var cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        }catch (e: SQLException){
            db.execSQL(query)
            return false
        }
        if(cursor.moveToFirst()){
            val userRealPassword = cursor.getString(cursor.getColumnIndex("password"))
            if(userRealPassword == password) return true
        }
        return false
    }

    // Get User data
    fun getUserData(){

    }

    // Manipulating User Data
    fun manipulateUserData() {

    }
}