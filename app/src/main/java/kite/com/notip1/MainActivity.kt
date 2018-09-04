package kite.com.notip1


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity() , AnkoLogger{
    companion object {
        val NOTIF_PREF : String = "NOTIFICATION_PREF"
        val NOTIF_ID : String = "NOTIFICATION_ID"
    }

    val NOTIFICIATION_CHANNEL_ID= "NOTICHAN"
    lateinit var notificationmanager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sp = getSharedPreferences(NOTIF_PREF, Context.MODE_PRIVATE)
        var nextnotifid = sp.getInt(NOTIF_ID,0)

        notificationmanager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notifchanel = NotificationChannel(NOTIFICIATION_CHANNEL_ID,"MAIN_CHANNEL",NotificationManager.IMPORTANCE_DEFAULT)
            notifchanel.enableLights(true)
            notifchanel.enableVibration(true)
            notifchanel.lightColor = Color.GREEN
            notifchanel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationmanager.createNotificationChannel(notifchanel)

        } else {
            info("Not implemented yet..")
        }

        btnSendNoti.setOnClickListener {
            var Notification_text = etNotiText.text.toString()
            var notifintent = Intent(applicationContext,Notification_text_activity::class.java).putExtra("NOTIF_STRING",Notification_text)
            var notifPendingintent = PendingIntent.getActivity(this,0,notifintent,PendingIntent.FLAG_ONE_SHOT)
            val notibuilder=   NotificationCompat.Builder(this,NOTIFICIATION_CHANNEL_ID)
                    .setContentText(Notification_text)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(notifPendingintent)

            notificationmanager.notify(nextnotifid,notibuilder.build())
            nextnotifid++
            sp.edit().putInt(NOTIF_ID,nextnotifid).apply()
            info("Next notif id : $nextnotifid")
            info("Stored id: ${sp.getInt(NOTIF_ID,1545)}")
            finish()
        }
    }
}
