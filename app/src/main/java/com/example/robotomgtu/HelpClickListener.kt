package com.example.robotomgtu

import android.view.View
import android.widget.Button
import android.widget.Toast

class HelpClickListener : View.OnClickListener {
    var button : Button? = null
    var button1 : Button?  = null
    override fun onClick(v: View?) {
        if(button!!.isPressed() && button1!!.isPressed()){
            Toast.makeText(v?.context, "Hello world", Toast.LENGTH_LONG).show()
        }

    }
}