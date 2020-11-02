package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.*
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.adapter.MonitorBloodGlucoseMainAdapter
import com.s.diabetesfeeding.ui.home.MonitorBloodGlucoseViewModel
import com.s.diabetesfeeding.ui.home.MonitorBloodGlucoseViewModelFactory
import com.s.diabetesfeeding.ui.home.fragment.HomeScreenFragmentDirections
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.getDateFromOffsetDateTime
import com.s.diabetesfeeding.util.shortToast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_symptoms.*
import kotlinx.android.synthetic.main.monitor_blood_glucose_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.threeten.bp.OffsetDateTime


class MonitorBloodGlucoseFragment : Fragment(), KodeinAware, MonitorGlucoseResponseListner {

    private lateinit var viewModel : MonitorBloodGlucoseViewModel
    override val kodein by kodein()
    private val factory: MonitorBloodGlucoseViewModelFactory by instance()
    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())

    var CategoryWithItems: List<CategoryWithItems> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.monitor_blood_glucose_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (prefs.getSavedFormattedDate().isNullOrEmpty()){
            tv_today_date_monitor_glucose.text=currentDate
        }else{
            tv_today_date_monitor_glucose.text = prefs.getSavedFormattedDate()

        }
        viewModel = ViewModelProvider(this,factory).get(MonitorBloodGlucoseViewModel::class.java)
        viewModel.monitorGlucoseResponseListner = this
        viewLifecycleOwner.lifecycleScope.launch {
            CategoryWithItems =  AppDatabase(requireActivity().applicationContext).getMonitorBloodGlucoseCatDao().getItemsAndCategory()
            showMonitorBloodGlucoseMain(CategoryWithItems)
        }

        mcv_done.setOnClickListener {
            if (!prefs.getSavedIsPreviousDate()) {
                if (CategoryWithItems.isNotEmpty()) {
                    val wakeupData = CategoryWithItems[0].bloodGlucoseCategoryItems
                    val breakFastData = CategoryWithItems[1].bloodGlucoseCategoryItems
                    val lunchData = CategoryWithItems[2].bloodGlucoseCategoryItems
                    val dinnerData = CategoryWithItems[3].bloodGlucoseCategoryItems
                    val bedTimeData = CategoryWithItems[4].bloodGlucoseCategoryItems

                    viewModel.saveBloodGlucoseDataToServer(
                        prefs.getSavedUserId()!!,
                        "blood-glucose",
                        wakeupData[0].value,
                        wakeupData[0].time,
                        wakeupData[0].score.toString(),
                        breakFastData[0].value,
                        breakFastData[0].time,
                        breakFastData[0].score.toString(),
                        breakFastData[1].value,
                        breakFastData[1].time,
                        breakFastData[1].score.toString(),
                        breakFastData[2].value,
                        breakFastData[2].time,
                        breakFastData[2].score.toString(),
                        lunchData[0].value,
                        lunchData[0].time,
                        lunchData[0].score.toString(),
                        lunchData[1].value,
                        lunchData[1].time,
                        lunchData[1].score.toString(),
                        lunchData[2].value,
                        lunchData[2].time,
                        lunchData[2].score.toString(),
                        dinnerData[0].value,
                        dinnerData[0].time,
                        dinnerData[0].score.toString(),
                        dinnerData[1].value,
                        dinnerData[1].time,
                        dinnerData[1].score.toString(),
                        dinnerData[2].value,
                        dinnerData[2].time,
                        dinnerData[2].score.toString(),
                        bedTimeData[0].value,
                        bedTimeData[0].time,
                        bedTimeData[0].score.toString()
                    )
                }
            }

        }

    }
    private fun showMonitorBloodGlucoseMain(category: List<CategoryWithItems>) {
        recyclerViewMonitorBloodGlucoses.layoutManager = LinearLayoutManager(activity)
        recyclerViewMonitorBloodGlucoses.adapter = MonitorBloodGlucoseMainAdapter(requireActivity(),category)
    }
    /*private fun buildUI() = Coroutines.main {
        viewModel.monitorbloodGlucose.await().observe(viewLifecycleOwner, Observer {
           // initRecycleView(it.toMonitorBloodGlucoseItem())
            // context?.longToast(it.toString() + " NULL")
        })
    }*/

    private fun initRecycleView(MonitorBloodGlucoseItem: List<MonitorBloodGlucoseChildItem>) {
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(MonitorBloodGlucoseItem.asReversed())
        }
        recyclerViewMonitorBloodGlucose.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun List<MonitorbloodGlucose>.toMonitorBloodGlucoseItem() : List<MonitorBloodGlucoseChildItem>{
        return this.map {
            MonitorBloodGlucoseChildItem(
                it
            )
        }
    }

    override fun onStarted() {
        requireActivity().shortToast("Syncing to server")
    }

    override fun onSuccess(message: String) {
        requireActivity().shortToast(message)
        requireActivity().onBackPressed()
    }

    override fun onFailure(message: String) {
        requireActivity().shortToast(message)
        requireActivity().onBackPressed()
    }
}