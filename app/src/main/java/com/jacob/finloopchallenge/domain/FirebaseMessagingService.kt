package com.jacob.finloopchallenge.domain

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jacob.finloopchallenge.domain.model.FirebaseNotificationsBody
import com.jacob.finloopchallenge.ui.CustomNotification
import kotlin.reflect.KClass

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("FirebaseMessaging", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FirebaseMessaging", "From: ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            val bodyFirebase = mapToObject(remoteMessage.data, FirebaseNotificationsBody::class)
            CustomNotification.createNotification(this, bodyFirebase)
        } else {
            Log.d("FirebaseMessaging", "body empty")
        }
    }

    fun <T : Any> mapToObject(map: Map<String, Any>, clazz: KClass<T>): T {
        //Get default constructor
        val constructor = clazz.constructors.first()

        //Map constructor parameters to map values
        val args = constructor
            .parameters.associateWith { map[it.name] }

        //return object from constructor call
        return constructor.callBy(args)
    }
}