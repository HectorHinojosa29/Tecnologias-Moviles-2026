package com.hector.bitacora

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotification : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("FCM_TOKEN", token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FCM_MESSAGE", "Mensaje recibido")

        if (message.notification != null) {
            Log.d(
                "FCM_MESSAGE",
                "Titulo: ${message.notification!!.title}"
            )

            Log.d(
                "FCM_MESSAGE",
                "Cuerpo: ${message.notification!!.body}"
            )
        }
    }
}