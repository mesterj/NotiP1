package kite.com.notip1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_notification_text_activity.*

class Notification_text_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_text_activity)
        textView3.setText(intent.extras.getString("NOTIF_STRING"))
    }
}
