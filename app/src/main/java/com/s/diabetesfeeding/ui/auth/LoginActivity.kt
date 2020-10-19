package com.s.diabetesfeeding.ui.auth

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.card.MaterialCardView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.Data
import com.s.diabetesfeeding.databinding.ActivityLoginBinding
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.home.HomeActivity
import com.s.diabetesfeeding.util.alertDialog
import com.s.diabetesfeeding.util.hide
import com.s.diabetesfeeding.util.show
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()
    var studyGroup:String = ""
    private lateinit var viewModel:AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val binding : ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { data ->
            if (data != null){
                when (data.role) {
                    "patient" -> {
                        Intent(this, HomeActivity::class.java).also {
                            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(it)
                        }
                    }
                    "administrator" -> {
                        prefs.saveAdminId(data.ID.toString())
                        Intent(this, AdminActivity::class.java).also {
                            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(it)
                        }
                    }
                    else -> {
                        prefs.saveDoctorId(data.ID.toString())
                        Intent(this, ClinicalActivity::class.java).also {
                            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(it)
                        }
                    }
                }

            }
        })
        tv_clinical.setOnClickListener {
            val itn = Intent(this@LoginActivity, ClinicalActivity::class.java)
            startActivity(itn)
        }
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
    }
    @SuppressLint("ResourceAsColor")
    override fun onFailure(message: String) {
        root_layout.snackbar("$message")
        progress_bar.hide()
    }

    private fun showSelectRoleDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custome_select_role_dialog)

        val endocrinologist = dialog.findViewById(R.id.tv_endocrinologist) as TextView
        val obstetricsGynecology = dialog.findViewById(R.id.tv_obstetrics_gynecology) as TextView
        val neo = dialog.findViewById(R.id.tv_neo) as TextView
        val lactationSpecialist = dialog.findViewById(R.id.tv_lactation_specialist) as TextView
        val socialWorker = dialog.findViewById(R.id.tv_social_worker) as TextView
        val clinicalAssistant = dialog.findViewById(R.id.tv_clinical_assistant) as TextView
        val dayCareProvider = dialog.findViewById(R.id.tv_day_care_provider) as TextView
        val patient = dialog.findViewById(R.id.tv_patient) as TextView
        val administrator = dialog.findViewById(R.id.tv_administrator) as TextView

        val mBtn = dialog.findViewById(R.id.tv_close_button) as TextView

        endocrinologist.setOnClickListener {
            viewModel.role = endocrinologist.text.toString()
            et_role.setText(viewModel.role)
            dialog.dismiss()
        }
        obstetricsGynecology.setOnClickListener {
            viewModel.role = obstetricsGynecology.text.toString()
            et_role.setText(viewModel.role)
            dialog.dismiss()
        }
        neo.setOnClickListener {
            viewModel.role = neo.text.toString()
            et_role.setText(viewModel.role)
            dialog.dismiss()
        }
        lactationSpecialist.setOnClickListener {
            viewModel.role = lactationSpecialist.text.toString()
            et_role.setText(viewModel.role)
                dialog.dismiss()
        }
        socialWorker.setOnClickListener {
            viewModel.role = socialWorker.text.toString()
            et_role.setText(viewModel.role)
                dialog.dismiss()
        }
        clinicalAssistant.setOnClickListener {
            viewModel.role = clinicalAssistant.text.toString()
            et_role.setText(viewModel.role)
                dialog.dismiss()
        }
        dayCareProvider.setOnClickListener {
            viewModel.role = dayCareProvider.text.toString()
            et_role.setText(viewModel.role)
                dialog.dismiss()
        }
        patient.setOnClickListener {
            viewModel.role = patient.text.toString()
            et_role.setText(viewModel.role)
                dialog.dismiss()
        }
        administrator.setOnClickListener {
            viewModel.role = administrator.text.toString()
            et_role.setText(viewModel.role)
                dialog.dismiss()
        }

        mBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
    private fun chooseGroupDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custome_study_group_dialog)
        val tvGroupOne = dialog.findViewById(R.id.tv_g_one) as TextView
        val tvGroupTwo = dialog.findViewById(R.id.tv_g_two) as TextView
        val closeButton = dialog.findViewById(R.id.tv_close_button) as TextView

        tvGroupOne.setOnClickListener {
            viewModel.group = "group-1"
            et_select_group.setText(viewModel.group)
            dialog.dismiss()
        }
        tvGroupTwo.setOnClickListener {
            viewModel.group = "group-2"
            et_select_group.setText(viewModel.group)
            dialog.dismiss()
        }
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}