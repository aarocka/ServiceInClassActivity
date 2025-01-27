package edu.temple.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button

class MainActivity : AppCompatActivity() {
    //Declare ServiceConnection
    private var binder: TimerService.TimerBinder? = null

    private val myService = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            //Cast IBinder to TimerBinder
            binder = service as TimerService.TimerBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            //Do nothing
            binder = null
        }
    }

    private val service = TimerService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TimerService.bindService(intent, this, Context.BIND_AUTO_CREATE)

        bindService(Intent(this, TimerService::class.java), myService, Context.BIND_AUTO_CREATE)

        findViewById<Button>(R.id.startButton).setOnClickListener {
            //start timer service

            binder?.start(10);

        }

        findViewById<Button>(R.id.pauseButton).setOnClickListener {
            binder?.pause();
        }
        
        findViewById<Button>(R.id.stopButton).setOnClickListener {
            binder?.stop();
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(myService)
    }
}