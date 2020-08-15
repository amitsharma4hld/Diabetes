package com.s.diabetesfeeding.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.Data
import com.s.diabetesfeeding.data.network.ApiInterface
import com.s.diabetesfeeding.data.network.NetworkConnectionInterceptor
import com.s.diabetesfeeding.data.repositories.UserRepository
import com.s.diabetesfeeding.databinding.ActivityLoginBinding
import com.s.diabetesfeeding.ui.ForgetPassActivity
import com.s.diabetesfeeding.ui.home.HomeActivity
import com.s.diabetesfeeding.util.*
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(window)
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

     /*   tv_signup.setOnClickListener {
            mc_login.visibility = View.GONE
            mc_username.visibility = View.VISIBLE
            mc_confirm_password.visibility = View.VISIBLE
            tv_forgetpass.visibility = View.GONE
            tv_term_condition_line.visibility = View.VISIBLE
            mc_carepro.visibility = View.GONE
            mc_signup.visibility = View.VISIBLE

        }

        tv_login.setOnClickListener {
            mc_login.visibility = View.VISIBLE
            mc_username.visibility = View.INVISIBLE
            mc_confirm_password.visibility = View.GONE
            tv_forgetpass.visibility = View.VISIBLE
            tv_term_condition_line.visibility = View.INVISIBLE
            mc_carepro.visibility = View.VISIBLE
            mc_signup.visibility = View.GONE
        }*/

        tv_forgetpass.setOnClickListener {
            val itn = Intent(this@LoginActivity, ForgetPassActivity::class.java)
            startActivity(itn)
        }

    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(data: Data) {
        progress_bar.hide()
      /*  root_layout.snackbar("${data.display_name} is Logged in")
        val itn = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(itn)
        finish()*/
    }
    @SuppressLint("ResourceAsColor")
    override fun onFailure(message: String) {
        root_layout.snackbar("$message")
        mc_username.strokeColor =  Color.parseColor("#000000")
        mc_username.strokeWidth = 10
        progress_bar.hide()
    }
}