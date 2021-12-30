package com.sihabudin.itask.core.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.TaskWithCategoryModel
import com.sihabudin.itask.core.domain.usecase.TaskUseCase
import com.sihabudin.itask.home.fragment.HomeFragment
import com.sihabudin.itask.main.MainActivity

class AlarmWorker(private val taskUseCase: TaskUseCase, ctx: Context, params: WorkerParameters) :
    Worker(ctx, params) {

    companion object {
        var activeReminder = false
    }


    private val isActiveReminder = inputData.getBoolean(activeReminder.toString(), false)


    override fun doWork(): Result {

        if(isActiveReminder) {
            Log.e("alarm worker is active =","yes")
            val task = taskUseCase.getTaskTodayStatusOpen()
            task.toObservable().subscribe { onNext -> showNotification(applicationContext, onNext) }
        }


        return Result.success()


    }

    private fun getPendingIntent(): PendingIntent? {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {}

        return TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    private fun showNotification(context: Context, content: List<TaskWithCategoryModel>?) {
        if (content?.size!! > 0) {
            val notificationStyle = NotificationCompat.InboxStyle()
            content?.forEach { data ->
                val dueDate = data.due_date?.let {
                    DateFormater.stringDateWithHourMinute(
                        it
                    )
                }
                val title = data.title
                val taskData = "$title - $dueDate"
                notificationStyle.addLine(taskData)
            }

            val channelId = NOTIFICATION_CHANNEL_ID
            val channelName = NOTIFICATION_CHANNEL_NAME


            val notificationManagerCompat =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val builder = NotificationCompat.Builder(context, channelId)
                .setContentIntent(getPendingIntent())
                .setSmallIcon(R.drawable.itask)
                .setContentTitle(context.resources.getString(R.string.taskNotificationTitle))
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)
                .setStyle(notificationStyle)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                )

                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

                builder.setChannelId(channelId)

                notificationManagerCompat.createNotificationChannel(channel)
            }

            val notification = builder.build()

            notificationManagerCompat.notify(ID_REPEATING, notification)
        }

    }

}