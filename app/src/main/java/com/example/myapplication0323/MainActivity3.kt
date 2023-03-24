package com.example.myapplication0323

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.myapplication0323.databinding.ActivityMain3Binding
import java.util.Locale.Builder

class MainActivity3 : AppCompatActivity() {
    /* 변수 선언 */
    lateinit var binding: ActivityMain3Binding
    lateinit var manyger: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNotificate.setOnClickListener {
            /* 1. 객체 참조 변수 생성
             * notificationManager 객체 참조 변수
             * notificationCompat.Builder 객체 참조 변수
            */
            manyger = getSystemService(NOTIFICATION_SERVICE) as NotificationManager // 위에 선언 똑같음
            val builder: Notification.Builder
            /*  2. Oreo 버전 이상 인지를 확인 하는 조건문
             * !주의! : NotificationChannel 참조 변수를 만든다(API 26버전 이상부터 체널을 만들어줘야 함)
             * 서포트 라이브러리에 추가가 되지 않아서 사용이 불가능
            */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                /* 3.
                 * 26 버전 이상 채널 객체 참조 변수
                 * val channel = NotificationChannel(채널 아이디, 채널 이름, 채널 중요도)
                */
                val channelD: String = "huiseon-channel"
                val channelName = "Have a good day today!"
                val channel = NotificationChannel(
                    channelD,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                ) //무조건 알림이 옴
                /* 채널에 대한 정보를 등록(시스템 설정에서 사용자에게 표시 되는 설명을 지정) */
                channel.description = "My KDJ Channel Description"
                channel.setShowBadge(true)
                val notificationUri: Uri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributesBuilder = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                channel.setSound(notificationUri, audioAttributesBuilder)
                /* !주의! : 한번 설정해 놓으면 이후에 앱에서 바꿀수 없고, 유저만이 키거나 끌수 있다.*/
                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(100, 200, 100, 200) // 0.1~0.2 초 간격으로

                /* 4. 채널을 NotificationManager 등록 */
                manyger.createNotificationChannel(channel)
                /* 5. channelD를 이용해서 Builder 생성 */
                builder = Notification.Builder(this, channelD) //채널 o
            } else {
                /* 5-1. channelD를 이용하지 않고 Builder 생성 */
                builder = Notification.Builder(this) //채널 x
            }
            /* 6. builder 알림창이 어떤 방법으로 구현할지를 보여주는 것*/
            builder.setSmallIcon(android.R.drawable.ic_notification_overlay)
            builder.setWhen(System.currentTimeMillis())
            builder.setContentTitle("My First Notification")
            builder.setContentText("My First Notification content")
            builder.setAutoCancel(false)    // 자동으로 지워지는 것 막기
            builder.setOngoing(true)        // 스와이프를 하여 지우는 기능
            builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.id.imageView1))

            /* 7-1. 알림이 발생 후 터치시 내가 지정한 액티비티로 화면으로 전환하는 pendingIndent 기능을 부여하기*/
            /* 7-2. 알림이 발생 후 터치시 내가 지정한 broadcasting 화면으로 정보를 알려준다 */

            /*var intent = Intent(this, DetailActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_IMMUTABLE)
            builder.setContentIntent(pendingIntent)*/

            /*8. 알림에 액션 등록하기*/
            val actionIntent = Intent(this, OneReceiver::class.java)
            val actionPendingIntent =
                PendingIntent.getBroadcast(this, 20, actionIntent, PendingIntent.FLAG_IMMUTABLE)

            /*      builder.addAction(
                      NotificationCompat.Action.Builder(
                          android.R.drawable.stat_notify_more,
                          "Action",
                          actionPendingIntent
                      ).build())*/

            /* 9. 알림에 */
            /* 10. manyger 알림 발생 */
            manyger.notify(11, builder.build())
        }
        binding.btnNotificateCancel.setOnClickListener {
            manyger.cancel(11)
        }
    }
}