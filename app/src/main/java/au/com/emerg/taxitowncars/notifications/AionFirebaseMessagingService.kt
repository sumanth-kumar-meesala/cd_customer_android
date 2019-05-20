package au.com.emerg.taxitowncars.notifications

import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import au.com.emerg.taxitowncars.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AionFirebaseMessagingService : FirebaseMessagingService() {

    val TAG = AionFirebaseMessagingService::class.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (remoteMessage == null) return

        Log.d(TAG, "Downstream message")
        Log.d(TAG, "notification: ${remoteMessage.notification}")
        Log.d(TAG, "data: ${remoteMessage.data}")
        Log.d(TAG, "from: ${remoteMessage.from}")
        Log.d(TAG, "messageType: ${remoteMessage.messageType}")

        if (remoteMessage.notification != null) {
            val notificationBuilder = NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(remoteMessage.notification?.title)
                .setContentText(remoteMessage.notification?.body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(NotificationCompat.BigTextStyle())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notificationBuilder.build())
        }

    }

    override fun onNewToken(token: String?) {
        Log.d(TAG, "Refreshed token: $token")
    }
}