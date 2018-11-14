package com.cosplay.kotlin.hw.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.cosplay.kotlin.hw.R
import kotlinx.android.synthetic.main.activity_accelerometer.*


class AccelerometerActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private var isShake: Boolean = false
    private lateinit var handler: Handler
    private lateinit var vibrator: Vibrator
    private lateinit var soundPool: SoundPool
    private var weiChatAudio: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometer)
        handler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                when (msg?.what) {
                    1 -> {
                        transLateAnima()
                        isShake = true
                        tv_show_status.text = "摇动中"
                        handler.sendEmptyMessageDelayed(3, 1500)
                    }
                    3 -> {
                        isShake = false
                        tv_show_status.text = "不摇动"
                    }
                }
            }
        }
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        // Take all sensors in device
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        soundPool = SoundPool(1, AudioManager.STREAM_SYSTEM, 5)
        weiChatAudio = soundPool.load(this, R.raw.weichat_audio, 1)

    }

    private fun transLateAnima() {

        var topAnima: ObjectAnimator = ObjectAnimator.ofFloat(ll_top, "translationY", 0.0f, -75f)

        var topBackAnima: ObjectAnimator = ObjectAnimator.ofFloat(ll_top, "translationY", -75f, 0f)

        var bottomAnima: ObjectAnimator = ObjectAnimator.ofFloat(ll_bottom, "translationY", 0.0f, 75f)

        var bottomBackAnima: ObjectAnimator = ObjectAnimator.ofFloat(ll_bottom, "translationY", 75f, 0f)

        var topSet = AnimatorSet()
        var bottomSet = AnimatorSet()
        bottomSet.duration = 400
        bottomSet.playTogether(topBackAnima, bottomBackAnima)
        topSet.playTogether(topAnima, bottomAnima)
        topSet.duration = 700
        topSet.start()
        iv_top_line.visibility = View.VISIBLE
        iv_bottom_line.visibility = View.VISIBLE
        handler.postDelayed(Runnable {
            kotlin.run {
                bottomSet.start()
                handler.postDelayed(Runnable {
                    kotlin.run {
                        iv_top_line.visibility = View.GONE
                        iv_bottom_line.visibility = View.GONE
                    }
                }, bottomSet.duration)

            }
        }, topSet.duration + 300)


    }

    override fun onStart() {
        super.onStart()
        tv_show_status.text = "不摇动"
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onRestart() {
        super.onRestart()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {

        val type = event!!.sensor.type

        if (type == Sensor.TYPE_ACCELEROMETER) {
            //获取三个方向值
            val values = event.values
            val x = values[0]
            val y = values[1]
            val z = values[2]

            if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math
                    .abs(z) > 17) && !isShake) {
                vibrator.vibrate(500)
                soundPool.play(weiChatAudio, 1f, 1f, 0, 0, 1f)
                handler.obtainMessage(1).sendToTarget()

            }
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
