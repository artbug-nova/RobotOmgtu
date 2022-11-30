package com.example.robotomgtu

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.os.SystemClock
import android.text.TextPaint
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.robotomgtu.databinding.ActivityMainBinding
import java.io.OutputStream

import java.util.*


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    public var checks: HelpClickListener = HelpClickListener()
    var outputStream: OutputStream? = null;
    var i : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val findViewById1 = findViewById<Button>(R.id.button2)
        val findViewById2 = findViewById<Button>(R.id.button4)
        val findViewById3 = findViewById<Button>(R.id.button5)
        checks.button = findViewById1
        checks.button1 = findViewById2

        val findViewById = this.findViewById<EditText>(R.id.editText)
        val sendButton = this.findViewById<Button>(R.id.buttonSend)

        sendButton.setOnClickListener{
            var ss: String = findViewById.text.toString() + ","
            outputStream?.write(ss.toByteArray())
        }
        findViewById3.setOnClickListener{

            val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
            val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

            val bondedDevices = bluetoothAdapter?.bondedDevices
            if (bluetoothAdapter == null) {

                // Device doesn't support Bluetooth
            }
            var devis = bondedDevices?.stream()?.filter{ ss -> ss.name.equals("HC-05")}?.findFirst()?.orElse(null)
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            val socket : BluetoothSocket = devis!!.createRfcommSocketToServiceRecord(uuid)
            outputStream = socket.outputStream
            socket.connect()


            var dd = ""
        }
        findViewById1.clickWithDebounce {
            if(i == 0){
                if(findViewById1.isPressed && findViewById2.isPressed) {
                    Toast.makeText(this, "Hello world", Toast.LENGTH_LONG).show()
                    i += 1
                }
            }
        }
        findViewById2.clickWithDebounce {
            if(i == 0){
                if(findViewById1.isPressed && findViewById2.isPressed){
                    Toast.makeText(this, "Hello world", Toast.LENGTH_LONG).show()
                    i += 1
                }
            }
        }
        //val buttonSend = this.findViewById<Button>(R.id.buttonSend)
        //var ddd = findViewById<PlainText>(R.id.textInput)
        /*buttonSend.setOnClickListener{
            outputStream.write("".toByteArray())
        }*/
    }

    fun View.clickWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
        this.setOnTouchListener(object : View.OnTouchListener {
            private var lastClickTime: Long = 0

            override fun onTouch(v: View?, p1: MotionEvent?): Boolean {
                if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return true
                else action()

                lastClickTime = SystemClock.elapsedRealtime()
                return false;
            }
        })
    }
}