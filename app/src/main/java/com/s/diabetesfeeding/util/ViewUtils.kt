package com.s.diabetesfeeding.util

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BreastFeedingSessionData
import com.s.diabetesfeeding.data.db.entities.obgynentities.Observation
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
 fun Context.showComentDialog(title: SymptomsData) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(true)
    dialog.setContentView(R.layout.custom_edit_dialog)
    val body = dialog.findViewById(R.id.et_comment) as EditText
    val mBtn = dialog.findViewById(R.id.tv_close_button) as TextView
    val saveBtn = dialog.findViewById(R.id.tv_done_button) as TextView

    if (title.comment.isNotEmpty()){
        body.setText(title.comment)
        body.isFocusable = false
        mBtn.visibility = View.VISIBLE
        saveBtn.visibility = View.GONE
    }else{
        body.isFocusable = true
        mBtn.visibility = View.GONE
        saveBtn.visibility = View.VISIBLE
    }
    mBtn.setOnClickListener {
        dialog.dismiss()
    }
    saveBtn.setOnClickListener {
        title.comment = body.text.toString()
        dialog.dismiss()
    }
    dialog.show()
}
fun Context.showObservationComentDialog(title: Observation) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(true)
    dialog.setContentView(R.layout.custom_edit_dialog)
    val body = dialog.findViewById(R.id.et_comment) as EditText
    val mBtn = dialog.findViewById(R.id.tv_close_button) as TextView
    val saveBtn = dialog.findViewById(R.id.tv_done_button) as TextView

    if (title.comment.isNotEmpty()){
        body.setText(title.comment)
        body.isFocusable = false
        mBtn.visibility = View.VISIBLE
        saveBtn.visibility = View.GONE
    }else{
        body.isFocusable = true
        mBtn.visibility = View.GONE
        saveBtn.visibility = View.VISIBLE
    }
    mBtn.setOnClickListener {
        dialog.dismiss()
    }
    saveBtn.setOnClickListener {
        title.comment = body.text.toString()
        dialog.dismiss()
    }
    dialog.show()
}

fun Context.showSlectRoleDialog(title: Observation) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(true)
    dialog.setContentView(R.layout.custome_select_role_dialog)
    val body = dialog.findViewById(R.id.mcv_patient) as MaterialCardView
    val mBtn = dialog.findViewById(R.id.tv_close_button) as TextView

    body.setOnClickListener {

    }
    mBtn.setOnClickListener {
        dialog.dismiss()
    }

    dialog.show()
}
fun Context.chooseBreastFeedTypeDialog(
    mcontext: FragmentActivity,
    breastFeedingSessionData: BreastFeedingSessionData
) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.custome_breastfeed_type_dialog)
    val mcvBreastfeed = dialog.findViewById(R.id.mcv_breastfeed) as MaterialCardView
    val mcvSupplemental = dialog.findViewById(R.id.mcv_supplemental) as MaterialCardView
    val closeButton = dialog.findViewById(R.id.tv_close_button) as TextView

    mcvBreastfeed.setOnClickListener {
        breastFeedingSessionData.breastfeedingType = "Breastfeed"
        dialog.dismiss()
    }
    mcvSupplemental.setOnClickListener {
        breastFeedingSessionData.breastfeedingType = "Supplemental"
        dialog.dismiss()
    }
    closeButton.setOnClickListener {
        mcontext.onBackPressed()
        dialog.dismiss()
    }
    dialog.show()
}
fun View.showSoftKeyboard(view: View) {
    if (view.requestFocus()) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}
