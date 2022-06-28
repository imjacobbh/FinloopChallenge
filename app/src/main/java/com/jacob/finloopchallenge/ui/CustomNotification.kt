package com.jacob.finloopchallenge.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.jacob.finloopchallenge.AppConstants.USER_ID_SELECTED
import com.jacob.finloopchallenge.AppConstants.USER_NAME
import com.jacob.finloopchallenge.R
import com.jacob.finloopchallenge.domain.model.FirebaseNotificationsBody
import com.jacob.finloopchallenge.ui.view.SecondActivity

object CustomNotification {
    private const val CHANNEL_ID = "finloop_notification"
    private const val NOTIFICATION_ID = 1

    fun createNotification(context: Context, firebaseMessage: FirebaseNotificationsBody) {
        val title = firebaseMessage.title
        val body = firebaseMessage.body
        createNotificationChannel(context)
        val ladingPendingIntent = getIntentFromNotification(firebaseMessage, context)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        builder.setSmallIcon(R.drawable.ic_heart_filled)
        builder.setContentTitle(title)
        builder.setContentText(body)
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        builder.setContentIntent(ladingPendingIntent)
        builder.setAutoCancel(true)
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
    }

    private fun getIntentFromNotification(
        firebaseMessage: FirebaseNotificationsBody,
        context: Context
    ): PendingIntent {
        val landingIntent = Intent(context, SecondActivity::class.java).apply {
            putExtra(USER_NAME, firebaseMessage.username)
            putExtra(USER_ID_SELECTED, firebaseMessage.userId.toInt())
        }
        landingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return PendingIntent.getActivity(context, 0, landingIntent, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "finloop_notification"
            val description = "finloop_notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
