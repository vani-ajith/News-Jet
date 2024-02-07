package com.example.newsarticleapp.controller

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.newsarticleapp.R
import com.example.newsarticleapp.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "news_notification_channel"
const val channelName = "com.example.newsarticleapp"
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // Checks if the message contains data payload
        message.data.isNotEmpty().let{
            val data = message.data
        }
        // Checks if the message contains notification payload
        message.notification?.let {
            val title = it.title
            val message = it.body
            if (title != null) {
                if (message != null) {
                    showNotification(title,message)
                }
            }
        }
    }


    fun getRemoteView(title : String,message : String) : RemoteViews{
        val remoteView = RemoteViews("com.example.newsarticleapp",R.layout.layout_notification)

        remoteView.setTextViewText(R.id.app_title,title)
        remoteView.setTextViewText(R.id.app_message,message)
        remoteView.setImageViewResource(R.id.app_logo,R.drawable.ic_news)

        return remoteView
    }

    private fun showNotification(title: String, message: String) {

        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        var builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_news)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title,message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0,builder.build())

    }


}