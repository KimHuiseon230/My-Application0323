package com.example.myapplication0323

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
import androidx.core.app.RemoteInput
import com.example.myapplication0323.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    /* 변수 선언 */
    lateinit var binding: ActivityMain3Binding
    lateinit var manager: NotificationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNotificate.setOnClickListener {
            /* 1. 객체 참조 변수 생성
            * notificationManager 객체 참조 변수
            * notificationCompat.Builder 객체 참조 변수
            */
            manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val builder: NotificationCompat.Builder
            /*2.Oreo 버전 이상 인지를 확인 하는 조건문
                *!주의!: NotificationChannel 참조 변수를 만든다(API 26버전 이상부터 체널을 만들어줘야 함)
                *서포트 라이브러리에 추가가 되지 않아서 사용이 불가능
            */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                /* 3.
                 * 26 버전 이상 채널 객체 참조 변수
                 * val channel = NotificationChannel(채널 아이디, 채널 이름, 채널 중요도)
                */
                val channelID: String = "huiseon-channel"
                val channelName = "My Khs Channel"
                val channel = NotificationChannel(
                    channelID,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                )//무조건 알림이 옴

                /* 채널에 대한 정보를 등록(시스템 설정에서 사용자에게 표시 되는 설명을 지정) */
                channel.description = "My AMW Channel Description"
                channel.setShowBadge(true)

                /*알림음 오디오 설정*/
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
                channel.vibrationPattern = longArrayOf(100, 200, 100, 200)// 0.1~0.2 초 간격으로

                /* 4. 채널을 NotificationManager 등록 */
                manager.createNotificationChannel(channel)

                /* 5. channelD를 이용해서 Builder 생성 */
                builder = NotificationCompat.Builder(this, channelID)
            } else {
                /* 5-1. channelD를 이용하지 않고 Builder 생성 */
                builder = NotificationCompat.Builder(this)
            }

            /* 6. builder 알림창이 어떤 방법으로 구현할지를 보여주는 것*/
            builder.setSmallIcon(android.R.drawable.ic_notification_overlay)
            builder.setWhen(System.currentTimeMillis())
            builder.setContentTitle("My first Notification")
            builder.setContentText("my first Notificantion cantent")
            builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.img001))

            /* 7-1. 알림이 발생 후 터치시 내가 지정한 액티비티로 화면으로 전환하는 pendingIndent 기능을 부여하기*/
            /* 7-2. 알림이 발생 후 터치시 내가 지정한 broadcasting 화면으로 정보를 알려준다 */
            val intent = Intent(this, DetailActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_IMMUTABLE)
            builder.setContentIntent(pendingIntent)

            /*8. 알림에 액션 등록하기*/
                /*  val actionIntent = Intent(this, OneReceiver::class.java)
                  val actionPendingIntent =  PendingIntent.getBroadcast(this,20,actionIntent, PendingIntent.FLAG_IMMUTABLE)
                  builder.addAction(NotificationCompat.Action.Builder(
                      android.R.drawable.stat_notify_more,
                      "Action",
                      actionPendingIntent
                  ).build())*/
            /*9.
             알림창에 데이터를 입력하면 해당 되는 데이터를 브로드 캐스팅함
             9-1 알림창에서 입력할수 있는 기능을 추가
            */
            val remoteInput = RemoteInput.Builder("KDJ_NORI_REPLY").run {
                setLabel("답장을 써주세요")
                build()
            }

            val actionIntent = Intent(this, OneReceiver::class.java)
            val actionPendingIntent =
                PendingIntent.getBroadcast(this, 20, actionIntent, PendingIntent.FLAG_IMMUTABLE)
            builder.addAction(
                NotificationCompat.Action.Builder(
                    R.drawable.send_24,
                    "답장",
                    actionPendingIntent
                ).addRemoteInput(remoteInput).build()
            )

            /*builder.setAutoCancel(false)
            builder.setOngoing(true)*/

            /* 10. manager 알림발생 */
            manager.notify(11, builder.build())
        }
        binding.btnNotificateCancel.setOnClickListener {
            manager.cancel(11)
        }
    }
}