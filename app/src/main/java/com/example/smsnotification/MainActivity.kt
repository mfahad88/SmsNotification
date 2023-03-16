package com.example.smsnotification

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.cursoradapter.widget.SimpleCursorAdapter
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : AppCompatActivity() {
    lateinit var listView:ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView=findViewById<ListView>(R.id.listView)
        if(checkPermission()) {
            readSms()
        }

    }

    fun checkPermission(): Boolean {
        var isGranted=false
        Dexter.withActivity(this@MainActivity)
            .withPermission(android.Manifest.permission.READ_SMS)
            .withListener(object: PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    isGranted=true
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    isGranted=false
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                   p1!!.continuePermissionRequest()
                }

            }).onSameThread().check()
        return isGranted
    }

    fun readSms(){
        // Create Inbox box URI
        // Create Inbox box URI
        val inboxURI: Uri = Uri.parse("content://sms/inbox")

// List required columns

// List required columns
        val reqCols = arrayOf("_id", "address", "body")

// Get Content Resolver object, which will deal with Content Provider

// Get Content Resolver object, which will deal with Content Provider
        val cr: ContentResolver = contentResolver

// Fetch Inbox SMS Message from Built-in Content Provider

// Fetch Inbox SMS Message from Built-in Content Provider
        val c: Cursor? = cr.query(inboxURI, reqCols, "address like '%zub%'", null, null)

// Attached Cursor with adapter and display in listview

// Attached Cursor with adapter and display in listview
        var adapter = SimpleCursorAdapter(
            this, android.R.layout.two_line_list_item, c, arrayOf<String>("body", "address"), intArrayOf(
                android.R.id.text1, android.R.id.text2
            )
        )
        listView.setAdapter(adapter)
    }
}