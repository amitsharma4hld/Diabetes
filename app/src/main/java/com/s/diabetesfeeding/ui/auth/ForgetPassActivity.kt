package com.s.diabetesfeeding.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.Data
import com.s.diabetesfeeding.databinding.ActivityForgetPassBinding
import com.s.diabetesfeeding.util.alertDialog
import com.s.diabetesfeeding.util.hide
import com.s.diabetesfeeding.util.show
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.activity_forget_pass.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ForgetPassActivity : AppCompatActivity(),AuthListener, KodeinAware {
    override val kodein by kodein()
    private val factory : ForgotPassModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val binding : ActivityForgetPassBinding = DataBindingUtil.setContentView(this,R.layout.activity_forget_pass)
        val viewModel = ViewModelProvider(this, factory).get(ForgotPassModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this
    }

    override fun onStarted() {
        progress_bar_new.show()
    }

    override fun onVerificationFailed(title: String, message: String) {
        alertDialog(title,message)
    }
    override fun onSuccess(data: Data) {
        progress_bar_new.hide()
    }
    @SuppressLint("ResourceAsColor")
    override fun onFailure(message: String) {
        root_layout.snackbar("$message")
        progress_bar_new.hide()
    }
}