package com.example.myapplication0323

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myapplication0323.databinding.ActivityMain2Binding
import com.example.myapplication0323.databinding.ActivityMainBinding
import com.example.myapplication0323.databinding.RegisterLayoutBinding
import com.example.myapplication0323.databinding.ToastLayoutBinding

//View.OnClickListener, DatePickerDialog.OnDateSetListener  각각 사용하는 Listener가 다름
class MainActivity2 : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(this.layoutInflater)  //최고의 inflate 가져옴
        setContentView(binding.root)
        binding.btnToast.setOnClickListener(this)
        binding.btnData.setOnClickListener(this)
        binding.btnTime.setOnClickListener(this)
        binding.btnDialog.setOnClickListener(this)
        binding.btnItemDialog.setOnClickListener(this)
        binding.btnMultiDialog.setOnClickListener(this)
        binding.btnSingItemDialog.setOnClickListener(this)
        binding.btnCustomDialog.setOnClickListener(this)
    }

    //여기에 들어가는 this =MainActivity2를 가리킴. applcation을 가져오라는 이야기가 아님.
    @RequiresApi(Build.VERSION_CODES.R) //30 버전 가능
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_toast -> {
                val toast = Toast.makeText(applicationContext, "토스트 메세지", Toast.LENGTH_SHORT)
                toast.duration = Toast.LENGTH_LONG
                toast.setGravity(Gravity.TOP, 0, 300)
                /*R.layout.toast_layout 실행하면 메모리에 올라감*/
                /*toastLayout 에는 이미 root 가 있음*/
                var toastLayoutBinding: ToastLayoutBinding
                toastLayoutBinding = ToastLayoutBinding.inflate(layoutInflater)
                toast.view = toastLayoutBinding.root
                toast.show()
            }   //end of toast
            R.id.btn_Data -> {
                DatePickerDialog(this, this, 2023, 3 - 1, 24).show()
            }    // end of Data
            R.id.btn_Time -> {
                TimePickerDialog(this, this, 3, 45, true).show()
            }    // end of btn_Time
            R.id.btn_Dialog -> {
                val eventHandler = object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        /* which==-1 가 고정값이라 바꾸려먼*/
                        Toast.makeText(
                            this@MainActivity2, "${
                                if (which == -1) {
                                    "ok"
                                } else {
                                    "no"
                                }
                            } 클릭하셨습니다.", Toast.LENGTH_SHORT
                        ).show()
                    }
                } // end of eventHandler
                val dialog = AlertDialog.Builder(this).run {
                    setTitle("알림창")
                    setIcon(R.drawable.computer_24)
                    setMessage("알림창 정보를 보여드립니다.")
                    setPositiveButton("YES", eventHandler)// end of setPositiveButton
                    setNegativeButton("NO", eventHandler)
                    show()
                }
            }  //end of Dialog
            R.id.btn_ItemDialog -> {
                val eventHandler = object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        /* which==-1 가 고정값이라 바꾸려먼*/
                        Toast.makeText(
                            this@MainActivity2, "${
                                if (which == -1) {
                                    "ok"
                                } else {
                                    "no"
                                }
                            } 클릭하셨습니다.", Toast.LENGTH_SHORT
                        ).show()
                    }
                } // end of eventHandler
                val items = arrayOf<String>("홍길동", "구길동", "고길동", "박길동")
                AlertDialog.Builder(this).run {
                    setTitle("알림창")
                    setIcon(R.drawable.computer_24)
                    setItems(items, object : DialogInterface.OnClickListener {
                        //which 선택시 내용 바뀜
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            binding.btnItemDialog.text = items[which]
                        }
                    })
                    setNegativeButton("NO", eventHandler)
                    show()
                }
            } //end of ItemDialog
            R.id.btn_MultiDialog -> {
                val items = arrayOf<String>("홍길동", "구길동", "고길동", "박길동")
                AlertDialog.Builder(this).run {
                    setTitle("알림창")
                    setIcon(R.drawable.computer_24)
                    /* 체크를 해도, 해지할때도 이벤트가 발생된다.*/
                    setMultiChoiceItems(
                        items,
                        booleanArrayOf(true, false, false, false),
                        object : DialogInterface.OnMultiChoiceClickListener {
                            override fun onClick(
                                dialog: DialogInterface?,
                                which: Int,
                                isChecked: Boolean
                            ) {
                                if (isChecked == true) {
                                    binding.btnMultiDialog.text = items[which]
                                }
                            }
                        })
                    setPositiveButton("YES", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            Toast.makeText(applicationContext, "선택했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    })
                    setNegativeButton("cancel", null)
                    show()
                }

            } //end of MultiDialog
            R.id.btn_SingItemDialog -> {
                val items = arrayOf<String>("홍길동", "구길동", "고길동", "박길동")
                AlertDialog.Builder(this).run {
                    setTitle("알림창")
                    setIcon(R.drawable.computer_24)
                    setSingleChoiceItems(items, 0, object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            binding.btnSingItemDialog.text = items[which]
                        }
                    })
                    setNegativeButton("Close", null)
                    setCancelable(false)    // 위에 버튼을 누르지 않으면 꺼지지 않음
                    show()
                }.setCanceledOnTouchOutside(true)
            } //end of btnSingItemDialog
            R.id.btn_CustomDialog -> {
                val userBinding: RegisterLayoutBinding //layoutInflater 의 정보를 가짐
                val dialogBuilder = AlertDialog.Builder(this)
                var userDialog: AlertDialog
                /*사용자 화면 인플렉션 하기*/

                userBinding = RegisterLayoutBinding.inflate(layoutInflater)
                /*사용자 다이얼로그 제목,뷰 설정 보이기*/
                dialogBuilder.setTitle("사용자 정보 입력하기  창")
                dialogBuilder.setIcon(R.drawable.computer_24)
                dialogBuilder.setView(userBinding.root)
                /* dialogBuilder.create() dialogBuilder 정보를 dismiss() 새로 추가해서 userDialog 넘겨줌*/
                userDialog = dialogBuilder.create()
                userDialog .show()
                /*이벤트 처리하기*/
                userBinding.btnCancel.setOnClickListener {
                    Toast.makeText(applicationContext, "취소", Toast.LENGTH_SHORT).show()
                    /*취소 및 확인 버튼 (dialogBuilder가 나오지 않아서 어쩔 수 없이 // userDialog = dialogBuilder.create()를 하게됨 )*/
                    userDialog.dismiss()
                }
                userBinding.btnRegister.setOnClickListener {
                    binding.tvMessage.text = userBinding.edName.text.toString()
                    userDialog.dismiss()
                }
            }
        }
    }//end of onClick

    /* override */
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Toast.makeText(
            applicationContext, "${year}-${month + 1}-${dayOfMonth}", Toast.LENGTH_SHORT
        ).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Toast.makeText(applicationContext, "${hourOfDay}시 ${minute}분,", Toast.LENGTH_SHORT).show()
    }
}

/*R.id.btn_Time -> {
    TimePickerDialog(this, { view: TimePicker?, hourOfDay: Int, minute: Int ->
        Toast.makeText(applicationContext, "${hourOfDay}시, ${minute}분,", Toast.LENGTH_SHORT).show()
    },13,45,true).show()
}*/

/*R.id.btn_Time -> {
    TimePickerDialog(this, object:TimePickerDialog.OnTimeSetListener
    {
        override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
            Toast.makeText(
                applicationContext, "${hourOfDay}시, ${minute}분,", Toast.LENGTH_SHORT).show()
        }
    },13,45,true).show()
}*/
/* 2번 방법으로
R.id.btn_Data -> {
    DatePickerDialog(
        this, { datePicker: DatePicker?, year: Int, month: Int, day: Int ->
            Toast.makeText(
                applicationContext,
                "${year}, ${month + 1}, ${day}",
                Toast.LENGTH_SHORT
            ).show()
        }, 2023, 3 - 1, 24
    ).show()
}*/

/* 보류 해둔 코드
                var toastLayoutBinding: ToastLayoutBinding
                toastLayoutBinding = ToastLayoutBinding.inflate(layoutInflater)
                toast.view = toastLayoutBinding.root
                toast.show()*/

/*  2번째 방법으로 만들기
                val toast = Toast.makeText(applicationContext, "토스트 메세지", Toast.LENGTH_SHORT)
                toast.duration = Toast.LENGTH_LONG
                toast.setGravity(Gravity.TOP, 0, 300)
                /*R.layout.toast_layout 실행하면 메모리에 올라감*/
                /*toastLayout 에는 이미 root 가 있음*/
                var toastLayoutBinding: ToastLayoutBinding
                toastLayoutBinding = ToastLayoutBinding.inflate(layoutInflater)
                toast.view = toastLayoutBinding.root
                toast.show()
*/
/* 1번째 방법으로 만들기
      val toastLayout = LayoutInflater.from(applicationContext).inflate(R.layout.toast_layout, null)
       val textView= toastLayout.findViewById<TextView>(R.id.textView)
       textView.setOnClickListener { binding.btnToast.text ="event show!" }
       toast.view = toastLayout.rootView*/

/*
toast.setText("정신일도 하사불성")
toast.addCallback(object : Toast.Callback() {
    override fun onToastShown() {
        super.onToastShown()
        binding.btnToast.text="toast show!"
    }
    override fun onToastHidden() {
        super.onToastHidden()
        binding.btnToast.text="toast not show!"
    }
})
toast.view*/
