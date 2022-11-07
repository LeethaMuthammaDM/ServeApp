package com.happiestminds.servicedemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        Log.d("MyRecieiver","User is now active")
        val serviceIntent=Intent(context,MyService::class.java)
        context.startService(serviceIntent)
    }
}