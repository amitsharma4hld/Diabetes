package com.s.diabetesfeeding.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.auth.Data
import com.s.diabetesfeeding.data.db.entities.auth.PatientListModelFactory
import com.s.diabetesfeeding.databinding.ActivityAdminBinding
import com.s.diabetesfeeding.databinding.ActivityClinicalBinding
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.MainActivity
import com.s.diabetesfeeding.ui.adapter.AdminAdapter
import com.s.diabetesfeeding.ui.adapter.ClinicianAdapter
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.hide
import com.s.diabetesfeeding.util.shortToast
import com.s.diabetesfeeding.util.show
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.activity_clinical.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class AdminActivity : AppCompatActivity(), KodeinAware,PatientsDataListner, OnRoleUpdateListener {
    override val kodein by kodein()
    private lateinit var viewModel:UsersListModel
    private val factory : UserListFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val binding : ActivityAdminBinding = DataBindingUtil.setContentView(this,R.layout.activity_admin)
        viewModel = ViewModelProvider(this, factory).get(UsersListModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        if (!prefs.getSaveAdminId().isNullOrEmpty()){
            viewModel.getAllUsersList()
        }
        mcv_admin.setOnClickListener {
            Coroutines.io{
                AppDatabase(this).clearAllTables()
                prefs.cearAllSharedPref()
                this?.let{
                    val intent = Intent(it, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    it.startActivity(intent)
                }
            }
        }

    }

    override fun onStarted() {
        progress_bar_admin.show()
    }

    override fun onSuccess(data: List<Data>) {
        progress_bar_admin.hide()
        if (data.isNotEmpty()){
            rv_admin_patient_list.layoutManager = LinearLayoutManager(this)
            rv_admin_patient_list.adapter = AdminAdapter(this, this,data)
        } else{
            rv_admin_patient_list.visibility = View.GONE
            tv_no_data_found_admin.visibility = View.VISIBLE
        }
    }

    override fun onFailure(message: String) {
        progress_bar_admin.hide()
        if (message == "Role updated successfully"){
            this.shortToast("Role is updated")
            if (!prefs.getSaveAdminId().isNullOrEmpty()){
                viewModel.getAllUsersList()
            }
        }else{
            rv_admin_patient_list.visibility = View.GONE
            tv_no_data_found_admin.visibility = View.VISIBLE
        }

    }

    override fun onRoleUpdateCall(id: String, role: String) {
        viewModel.updateUserRole(id,role)
    }
}