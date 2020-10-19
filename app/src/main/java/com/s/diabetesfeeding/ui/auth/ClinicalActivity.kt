package com.s.diabetesfeeding.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.Data
import com.s.diabetesfeeding.data.db.entities.auth.PatientListModelFactory
import com.s.diabetesfeeding.databinding.ActivityClinicalBinding
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.ClinicianListner
import com.s.diabetesfeeding.ui.adapter.ClinicianAdapter
import com.s.diabetesfeeding.ui.home.HomeActivity
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.hide
import com.s.diabetesfeeding.util.show
import kotlinx.android.synthetic.main.activity_clinical.*
import kotlinx.android.synthetic.main.activity_clinical.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance



class ClinicalActivity : AppCompatActivity(), KodeinAware, PatientsDataListner, ClinicianListner {
    override val kodein by kodein()
    private lateinit var viewModel:PatientsListModel
    private val factory : PatientListModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val binding : ActivityClinicalBinding = DataBindingUtil.setContentView(this,R.layout.activity_clinical)
        viewModel = ViewModelProvider(this, factory).get(PatientsListModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this
        viewModel.getLoggedInUser().observe(this, Observer { data ->
            if (data != null){
                when (data.role) {
                    "patient" -> {
                        if (prefs.getSavedIsPatientSelected()){
                            prefs.saveIsPatientSelected(false)
                            Intent(this, HomeActivity::class.java).also {
                                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(it)
                            }
                        }
                    }
                }
            }
        })
        if (!prefs.getSavedDoctorId().isNullOrEmpty()){
            viewModel.getMyPatientsList()
        }
        mcv_dashboard.setOnClickListener {
            val itn = Intent(this@ClinicalActivity, DashboardActivity::class.java)
            startActivity(itn)
        }
    }

    override fun onStarted() {
        progress_bar.show()
    }
 
    override fun onSuccess(data: List<com.s.diabetesfeeding.data.db.entities.auth.Data>) {
        progress_bar.hide()
        if (data.isNotEmpty()){
            rv_clinical_patient_list.layoutManager = LinearLayoutManager(this)
            rv_clinical_patient_list.adapter = ClinicianAdapter(this, data,this)
        } else{
            rv_clinical_patient_list.visibility = View.GONE
            tv_no_data_found.visibility = View.VISIBLE
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        rv_clinical_patient_list.visibility = View.GONE
        tv_no_data_found.visibility = View.VISIBLE
    }

    override fun onPatientSelect(data: Data) {
        Coroutines.main {
            prefs.saveIsPatientSelected(true)
            viewModel.savePatientData(data)
        }

    }


}