package com.s.diabetesfeeding.util

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.s.diabetesfeeding.ui.auth.LoginActivity
import java.math.RoundingMode
import java.text.DecimalFormat

fun Context.longToast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}
fun Context.shortToast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}
fun Context.roundOffDecimal(number: Double): Double? {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(number).toDouble()
}



fun setFullScreen(window: Window){
    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
}
fun ProgressBar.show(){
    visibility = View.VISIBLE
}

fun ProgressBar.hide(){
    visibility = View.GONE
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

fun Context.alertDialog(title:String,message: String){
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)
   // builder.setIcon(android.R.drawable.ic_dialog_alert)

    builder.setPositiveButton("OK"){dialogInterface, which ->
        Intent(this, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    val alertDialog: AlertDialog = builder.create()
    alertDialog.setCancelable(false)
    alertDialog.show()
}

fun View.showSoftKeyboard(view: View) {
    if (view.requestFocus()) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        // here is one more tricky issue
        // imm.showSoftInputMethod doesn't work well
        // and imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0) doesn't work well for all cases too
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}
