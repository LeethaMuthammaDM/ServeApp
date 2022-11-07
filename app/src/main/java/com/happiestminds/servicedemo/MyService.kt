package com.happiestminds.servicedemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlin.concurrent.thread

class MyService : Service() {
    private var handler=Handler()//This handler is actually created on main thread
    //and service is also running on Mainthread
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {

        //create Thread- If one task to be executed
        super.onCreate()

        Log.d("MyService"," onCreate called")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService","onStartCommand called")
        //create thread here if multiple time execution

        val imageName=intent?.getStringExtra("imageName")
        val imgUrl=intent?.getStringExtra("url")
        thread {// task is running on this thread
            // task execution(long running task)
            Thread.sleep(10000)

            Log.d("MyService","Image: $imageName downloaded from $imgUrl")
            //we can launch activity from service
            //but never do it, because it is disturb
            handler.post{
                Toast.makeText(this,"Task completed  ", Toast.LENGTH_LONG).show()
            }
            //this toast will not be shown because toast runs on ui thread
            //runonuithread is not available in service
            //hence we use handler
            sendNotification("Image: $imageName downloaded from $imgUrl")
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d("MyService","onDestroy called")
        super.onDestroy()

    }
    fun sendNotification(descr:String){
        //get notification manager
        val nManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        //create Notification
        val notificationC=NotificationChannel("test","Download",NotificationManager.IMPORTANCE_DEFAULT)
         nManager.createNotificationChannel(notificationC)

        val builder= Notification.Builder(this,"test")

        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle("Task Completed")
        builder.setContentText(descr)
        val i=Intent(this,MainActivity::class.java)
        val pi=PendingIntent.getActivity(this,1,i,PendingIntent.FLAG_IMMUTABLE)
        builder.setContentIntent(pi)

        val myNotification=builder.build()
        //display Notification

        nManager.notify((Math.random()*10).toInt(),myNotification)
        //notify(id:should be different for getting multiple notification)

    }
}