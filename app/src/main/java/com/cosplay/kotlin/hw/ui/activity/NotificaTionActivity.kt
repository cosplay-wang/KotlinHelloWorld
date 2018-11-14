package com.cosplay.kotlin.hw.ui.activity

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.widget.RemoteViews
import com.cosplay.kotlin.hw.MainActivity
import com.cosplay.kotlin.hw.R
import kotlinx.android.synthetic.main.activity_notifica_tion.*

open class NotificaTionActivity : AppCompatActivity() {
    private lateinit var notifyNanager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifica_tion)
        initNotify()
        onClick()

    }

    private fun initNotify() {
        notifyNanager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        pendingIntent = PendingIntent.getActivity(this, 1, Intent(this, MainActivity::class.java), 0)
    }

    private fun onClick() {
        tv_normal.setOnClickListener {
            normalNofi()
        }
        tv_max_progress.setOnClickListener {
            maxProgressNofi()
        }
        tv_no_progress.setOnClickListener {
            noProgressNofi()
        }
        tv_head.setOnClickListener {
            headNofi()
        }
        tv_customer_1.setOnClickListener {
            customerNofi()
        }
        tv_task_1.setOnClickListener {
            stackNofi()
        }
        tv_parameter_1.setOnClickListener {
            parameterNofi()
        }
        tv_cancel_1.setOnClickListener {
            notifyNanager.cancel(11)
        }
        tv_cancelall_1.setOnClickListener {
            notifyNanager.cancelAll()
        }
        tv_big_1.setOnClickListener {
            bigNofi()
        }
    }

    private fun normalNofi() {
        var builder = NotificationCompat.Builder(this, "normal")
                .setContentIntent(pendingIntent)
                .setContentTitle("正常的")
                .setContentText("内容区域")
                .setSmallIcon(android.R.drawable.ic_dialog_dialer)
        notifyNanager.notify(11, builder.build())
    }
    private fun bigNofi() {
       var  pendingIntent2 = PendingIntent.getActivity(this, 1, Intent(this, NotificaTionActivity::class.java), 0)
        var builder = NotificationCompat.Builder(this, "normal")
                .setContentTitle("大型的")
                .setContentText("内容区域")
                .setStyle(NotificationCompat.BigTextStyle().bigText("我的大字"))
                .setSmallIcon(android.R.drawable.ic_dialog_dialer)
                .addAction(android.R.drawable.ic_menu_call,"选项一",pendingIntent)
                .addAction(android.R.drawable.ic_menu_upload_you_tube,"选项二",pendingIntent2)
        notifyNanager.notify(11, builder.build())
    }

    private fun maxProgressNofi() {
        var progress: Int = 0
        var builder = NotificationCompat.Builder(this, "normal")
                .setContentIntent(pendingIntent)
                .setContentTitle("进度的")
                .setContentText("内容区域")
                .setSmallIcon(android.R.drawable.ic_dialog_dialer)
                .setProgress(100, progress, false)
        notifyNanager.notify(11, builder.build())
        Thread(Runnable {
            kotlin.run {
                while (progress < 100) {
                    progress = progress + 5
                    Thread.sleep(500)
                    builder.setProgress(100, progress, false)
                    notifyNanager.notify(11, builder.build())
                }
                builder.setContentText("Download complete")//下载完成
                        .setProgress(0, 0, false)    //移除进度条
                notifyNanager.notify(11, builder.build())

            }
        }).start()
    }

    private fun noProgressNofi() {
        var builder = NotificationCompat.Builder(this, "normal")
                .setContentIntent(pendingIntent)
                .setContentTitle("进度的")
                .setContentText("内容区域")
                .setSmallIcon(android.R.drawable.ic_dialog_dialer)
                .setProgress(0, 0, true)
        notifyNanager.notify(11, builder.build())
        Thread(Runnable {
            kotlin.run {
                Thread.sleep(5000)
                builder.setContentText("Download complete")//下载完成
                        .setProgress(0, 0, false)    //移除进度条
                notifyNanager.notify(11, builder.build())

            }
        }).start()
    }

    private fun headNofi() {
        var builder = NotificationCompat.Builder(this, "normal")
                .setContentTitle("正常的")
                .setContentText("内容区域")
                .setSmallIcon(android.R.drawable.ic_dialog_dialer)
                .setFullScreenIntent(pendingIntent, true)
        Thread(Runnable {
            kotlin.run {
                Thread.sleep(5000)
                notifyNanager.notify(11, builder.build())
            }
        }).start()
    }
    private fun stackNofi() {
        var stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(MainActivity::class.java)
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        stackBuilder.addNextIntent(Intent(this,MainActivity::class.java))
        var builder = NotificationCompat.Builder(this, "normal")
                .setContentIntent(resultPendingIntent)
                .setContentTitle("正常的")
                .setContentText("内容区域")
                .setSmallIcon(android.R.drawable.ic_dialog_dialer)
        notifyNanager.notify(11, builder.build())



    }
    private fun parameterNofi() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP //传参必须的
        intent.putExtra("notificationID", "123")
        intent.putExtra("name", "kitty")
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        var builder = NotificationCompat.Builder(this, "normal")
                .setContentIntent(pendingIntent)
                .setContentTitle("正常的")
                .setContentText("内容区域")
                .setSmallIcon(android.R.drawable.ic_dialog_dialer)
        notifyNanager.notify(11, builder.build())
    }
    private fun customerNofi() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP //传参必须的
        intent.putExtra("notificationID", "123")
        intent.putExtra("name", "kitty")
        var contenttView : RemoteViews = RemoteViews(packageName,R.layout.customer_nofi_layout)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        var builder = NotificationCompat.Builder(this, "normal")
                .setContentIntent(pendingIntent)
                .setContentTitle("正常的")
                .setContentText("内容区域")
                .setContent(contenttView)
                .setCustomContentView(contenttView)
                .setCustomBigContentView(contenttView)
                .setCustomContentView(contenttView)
                .setSmallIcon(android.R.drawable.ic_dialog_dialer)
        notifyNanager.notify(11, builder.build())
    }

}
