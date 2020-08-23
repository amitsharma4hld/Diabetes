package com.s.diabetesfeeding.ui.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.*
import com.s.diabetesfeeding.ui.adapter.MonitorBloodGlucoseMainAdapter
import com.s.diabetesfeeding.ui.home.MonitorBloodGlucoseViewModel
import com.s.diabetesfeeding.ui.home.MonitorBloodGlucoseViewModelFactory
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
    val allCategory: MutableList<AllCategory> = ArrayList()
    val categoryItemList1: MutableList<CategoryItem> = ArrayList()
    val categoryItemList2: MutableList<CategoryItem> = ArrayList()
    val categoryItemList3: MutableList<CategoryItem> = ArrayList()
    val categoryItemList4: MutableList<CategoryItem> = ArrayList()
    val categoryItemList5: MutableList<CategoryItem> = ArrayList()

    var CategoryWithItems: List<CategoryWithItems> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        categoryItemList1.add(CategoryItem("1","65-95","08:30 AM","Wake Up Fasting","65"))

        categoryItemList2.add(CategoryItem("1","65-95","08:30 AM","Before Breakfast","65"))
        categoryItemList2.add(CategoryItem("2","65-140","09:30 AM","1 Hour After Breakfast","68"))
        categoryItemList2.add(CategoryItem("3","65-120","10:30 AM","2 Hour After Breakfast",""))

        categoryItemList3.add(CategoryItem("1","65-95","12:30 PM","Before Lunch",""))
        categoryItemList3.add(CategoryItem("2","65-140","01:30 PM","1 Hour After Lunch",""))
        categoryItemList3.add(CategoryItem("3","65-120","02:30 PM","2 Hour After Lunch",""))

        categoryItemList4.add(CategoryItem("1","65-95","06:30 PM","Before Diner",""))
        categoryItemList4.add(CategoryItem("2","65-140","07:30 PM","1 Hour After Diner",""))
        categoryItemList4.add(CategoryItem("3","65-120","08:30 PM","2 Hour After Diner",""))

        categoryItemList5.add(CategoryItem("1","65-95","10:30 PM","Bedtime","65"))


        allCategory.add(AllCategory(1,categoryItemList1))
        allCategory.add(AllCategory(2,categoryItemList2))
        allCategory.add(AllCategory(3,categoryItemList3))
        allCategory.add(AllCategory(4,categoryItemList4))
        allCategory.add(AllCategory(5,categoryItemList5))

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
            MonitorBloodGlucoseChildItem(it)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(title: String, bgColorId: Int) =
            MonitorBloodGlucoseFragment().apply {
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