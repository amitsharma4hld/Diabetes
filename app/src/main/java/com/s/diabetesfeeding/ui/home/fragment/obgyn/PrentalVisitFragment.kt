package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.data.db.entities.obgynentities.PrentalVisitRecord
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.CellClickListener
import com.s.diabetesfeeding.ui.adapter.PrentalVisitAdapter
import com.s.diabetesfeeding.ui.auth.AuthViewModel
import com.s.diabetesfeeding.ui.auth.AuthViewModelFactory
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.SpaceGridDecoration
import com.s.diabetesfeeding.util.getDateFromOffsetDateTime
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.fragment_observation.*
import kotlinx.android.synthetic.main.fragment_prental_visit.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.threeten.bp.OffsetDateTime


class PrentalVisitFragment : Fragment(), KodeinAware,CellClickListener {
    override val kodein by kodein()
    lateinit var currentDate:OffsetDateTime
    private lateinit var authViewModel: AuthViewModel
    private val authfactory : AuthViewModelFactory by instance()
    val parentalScore:Int=20
    var prentalVisitRecordList: List<PrentalVisitRecord> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(!prefs.getOffsetDateTime().isNullOrEmpty()){
            currentDate = OffsetDateTime.parse(prefs.getOffsetDateTime())
            val value = getDateFromOffsetDateTime(currentDate)
            tv_date.text= value
        }else{
            currentDate =  OffsetDateTime.now()
            val value = getDateFromOffsetDateTime(currentDate)
            tv_date.text= value
        }
        mcv_prental_done.setOnClickListener {
            if (prefs.getSavedIsPreviousDate()) {
                it.snackbar("Previous data can not be edited")
                return@setOnClickListener
            }else
            updateScore()
            //requireActivity().onBackPressed()
        }
        authViewModel.getLoggedInUser().observe(viewLifecycleOwner, Observer { data ->
            if (data != null) {
                tv_dr_name.text = data.doctorName
            }
        })
        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                if (AppDatabase(it).getObgynDao().getAllPrentalVisitRecord().isNotEmpty()) {
                    prentalVisitRecordList = AppDatabase(it).getObgynDao().getAllPrentalVisitRecord()
                    showRecords(prentalVisitRecordList)
                }
            }
        }

    }

    private fun showRecords(prentalVisitRecord: List<PrentalVisitRecord>) {
        rv_prental_visit_record.layoutManager = GridLayoutManager(activity, 3)
        rv_prental_visit_record!!.addItemDecoration(SpaceGridDecoration(2, 8, false))
        rv_prental_visit_record.adapter =
            PrentalVisitAdapter(
                prentalVisitRecord,
                this
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authViewModel = ViewModelProvider(this, authfactory).get(AuthViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prental_visit, container, false)
    }
    fun updateScore() {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 8, parentalScore, currentDate))
                Log.d("AppDatabase : ", "Scores Saved ${AppDatabase(it).getHomeMenusDao().getAllScores().size} added")
            }
            (context as Activity).onBackPressed()
        }
    }
    override fun onCellClickListener(view: View) {
        // val action = PrentalVisitFragmentDirections.actionPrentalVisitFragmentToPrePregnancyInputFragment()
       // Navigation.findNavController(it).navigate(action)
    }

    override fun onBottomClickListener(
        day: String,
        breastFeedingCount: String,
        poopCount: String,
        peepCount: String
    ) {

    }

}