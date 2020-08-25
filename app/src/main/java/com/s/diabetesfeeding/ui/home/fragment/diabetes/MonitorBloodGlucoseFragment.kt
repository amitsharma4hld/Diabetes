package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.*
import com.s.diabetesfeeding.ui.adapter.MonitorBloodGlucoseMainAdapter
import com.s.diabetesfeeding.ui.home.MonitorBloodGlucoseViewModel
import com.s.diabetesfeeding.ui.home.MonitorBloodGlucoseViewModelFactory
import com.s.diabetesfeeding.ui.home.fragment.HomeScreenFragmentDirections
import com.s.diabetesfeeding.util.Coroutines
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_symptoms.*
import kotlinx.android.synthetic.main.monitor_blood_glucose_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class MonitorBloodGlucoseFragment : Fragment(), KodeinAware {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel : MonitorBloodGlucoseViewModel
    override val kodein by kodein()
    private val factory: MonitorBloodGlucoseViewModelFactory by instance()
    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())

    var CategoryWithItems: List<CategoryWithItems> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

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
        tv_today_date_monitor_glucose.text=currentDate
        viewModel = ViewModelProvider(this,factory).get(MonitorBloodGlucoseViewModel::class.java)
        //buildUI()
        Coroutines.io {
            context?.let {
                CategoryWithItems =  AppDatabase(it).getMonitorBloodGlucoseCatDao().getItemsAndCategory()
            }
        }
        Coroutines.main {
            context?.let {
                showMonitorBloodGlucoseMain(CategoryWithItems)
            }
        }

        mcv_done.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }
    private fun showMonitorBloodGlucoseMain(category: List<CategoryWithItems>) {
        recyclerViewMonitorBloodGlucoses.layoutManager = LinearLayoutManager(activity)
        recyclerViewMonitorBloodGlucoses.adapter = MonitorBloodGlucoseMainAdapter(requireActivity(),category)
    }
    private fun buildUI() = Coroutines.main {
        viewModel.monitorbloodGlucose.await().observe(viewLifecycleOwner, Observer {
           // initRecycleView(it.toMonitorBloodGlucoseItem())
            // context?.longToast(it.toString() + " NULL")
        })
    }

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
    companion object {
        @JvmStatic
        fun newInstance(title: String, bgColorId: Int) =
            MonitorBloodGlucoseFragment()
                .apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putInt(ARG_BG_COLOR, bgColorId)
                }
            }

        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_TITLE = "arg_title"
        private const val ARG_BG_COLOR = "arg_bg_color"
    }
}