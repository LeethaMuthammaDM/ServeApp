package com.happiestminds.servicedemo

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val receiver=MyReceiver()
    lateinit var nameEditText:EditText
    lateinit var linkEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        nameEditText=findViewById(R.id.nameE)
        linkEditText=findViewById(R.id.linkE)

        //register the receiver
        val filter=IntentFilter(Intent.ACTION_USER_PRESENT)
        registerReceiver(receiver,filter)
    }

    override fun onDestroy() {

        super.onDestroy()
        unregisterReceiver(receiver)

    }

    fun onStopClick(view: View) {
        //start service
        val serviceIntent=Intent(this,MyService::class.java)
        stopService(serviceIntent)
        Toast.makeText(this,"Service stopped  ",Toast.LENGTH_LONG).show()


    }
    fun onStartClick(view: View) {

        //stop service
        val serviceIntent=Intent(this,MyService::class.java)//instead of javaclass

        serviceIntent.putExtra("imageName","${nameEditText.text}")
        serviceIntent.putExtra("url","${linkEditText.text}")
        startService(serviceIntent)
        Toast.makeText(this,"Service Started  ",Toast.LENGTH_LONG).show()
    }
}