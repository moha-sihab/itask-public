package com.sihabudin.itask.core.utils

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.sihabudin.itask.R
import com.sihabudin.itask.main.MainActivity

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TYPE_ONE_TIME = "Itask Alarm"
        const val EXTRA_MESSAGE = "message"
        private const val ID_ONETIME = 100


    }

    override fun onReceive(context: Context, intent: Intent?) {
        val message = intent?.getStringExtra(EXTRA_MESSAGE)

        val title = TYPE_ONE_TIME
        val notifId = ID_ONETIME

        if(message != null)
        {
            showAlarmNotification(context,title,message,notifId)
        }

    }

    private fun showAlarmNotification(context: Context,title: String,message : String, notifId: Int)
    {
        val channelId = "channel_itask"
        val channelName = "AlarmItask Channel"

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val resultIntent = Intent(context,MainActivity::class.java)
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(resultIntent)
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val builder = NotificationCompat.Builder(context,channelId)
            .setContentIntent(resultPendingIntent)
            .setSmallIcon(R.drawable.itask)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context,android.R.color.transparent))
            .setVibrate(longArrayOf(1000,1000,1000,1000,1000))
            .setSound(alarmSound)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000,1000,1000,1000,1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notifId,notification)
    }

    fun setAlarm(context: Context,dateTimeAlarm: Long,idAlarm: Int, message:String)
    {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE,message)

        val pendingIntent = PendingIntent.getBroadcast(context,idAlarm,intent,0)

      //  alarmManager.set(AlarmManager.RTC_WAKEUP, dateTimeAlarm,pendingIntent)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, dateTimeAlarm,pendingIntent)


    }
    fun cancelAlarm(context: Context,idAlarm: Int)
    {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        try
        {
            val pendingIntent = PendingIntent.getBroadcast(context, idAlarm, intent, 0)
            pendingIntent.cancel()

            alarmManager.cancel(pendingIntent)
        }
        catch(ex : Exception){
            Log.e("cancel Alaram Log=",ex.message.toString())
        }

    }
    fun isAlarmSet(context: Context,idAlarm: Int) : Boolean
    {
        val intent = Intent(context, AlarmReceiver::class.java)

        return PendingIntent.getBroadcast(context, idAlarm, intent, PendingIntent.FLAG_NO_CREATE) != null
    }

}