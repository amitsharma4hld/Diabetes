package com.s.diabetesfeeding.ui.auth

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.card.MaterialCardView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.Data
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BreastFeedingSessionData
import com.s.diabetesfeeding.databinding.ActivityLoginBinding
import com.s.diabetesfeeding.ui.home.HomeActivity
import com.s.diabetesfeeding.util.*
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()
    var studyGroup:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val binding : ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
            val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { data ->
            if (data != null){
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        tv_forgetpass.setOnClickListener {
            val itn = Intent(this@LoginActivity, ForgetPassActivity::class.java)
            startActivity(itn)
        }
        et_role.inputType = InputType.TYPE_NULL
        et_role.setOnClickListener {
            showSelectRoleDialog()
        }
        et_select_group.inputType = InputType.TYPE_NULL
        et_select_group.setOnClickListener {
            chooseGroupDialog()
        }
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onVerificationFailed(title: String, message: String) {
        alertDialog(title,message)
    }
    override fun onSuccess(data: Data) {
        progress_bar.hide()
      /* root_layout.snackbar("${data.display_name} is Logged in")
        val itn = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(itn)
        finish()*/
    }
    @SuppressLint("ResourceAsColor")
    override fun onFailure(message: String) {
        root_layout.snackbar("$message")
        //mc_username.strokeColor =  Color.parseColor("#000000")
        //mc_username.strokeWidth = 10
        progress_bar.hide()
    }

    private fun showSelectRoleDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custome_select_role_dialog)
        val body = dialog.findViewById(R.id.mcv_patient) as MaterialCardView
        val mBtn = dialog.findViewById(R.id.tv_close_button) as TextView

        body.setOnClickListener {
            et_role.setText("Patient")
            dialog.dismiss()
        }
        mBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
    fun chooseGroupDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custome_study_group_dialog)
        val mcvGroupOne = dialog.findViewById(R.id.mcv_group_one) as MaterialCardView
        val mcvGroupTwo = dialog.findViewById(R.id.mcv_group_two) as MaterialCardView
        val closeButton = dialog.findViewById(R.id.tv_close_button) as TextView

        mcvGroupOne.setOnClickListener {
            et_select_group.setText("Group One")
            dialog.dismiss()
        }
        mcvGroupTwo.setOnClickListener {
            et_select_group.setText("Group Two")
            dialog.dismiss()
        }
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}