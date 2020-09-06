package com.s.diabetesfeeding.ui.home.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.*
import com.s.diabetesfeeding.data.db.entities.obgynentities.Observation
import com.s.diabetesfeeding.databinding.FragmentHomeScreenBinding
import com.s.diabetesfeeding.ui.MainActivity
import com.s.diabetesfeeding.ui.auth.AuthViewModel
import com.s.diabetesfeeding.ui.auth.AuthViewModelFactory
import com.s.diabetesfeeding.ui.home.HomeViewModel
import com.s.diabetesfeeding.ui.home.HomeViewModelFactory
import com.s.diabetesfeeding.util.Coroutines
import kotlinx.android.synthetic.main.fragment_home_screen.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_TITLE = "arg_title"
private const val ARG_BG_COLOR = "arg_bg_color"


class HomeScreenFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private lateinit var viewModel: HomeViewModel
    private lateinit var authViewModel: AuthViewModel
    private var param1: String? = null
    private var param2: String? = null
    private val factory:HomeViewModelFactory by instance()
    private val authfactory : AuthViewModelFactory by instance()
    var isOptionSelected = true
    val currentDate: String = java.text.SimpleDateFormat(
        "MMM dd, yyyy",
        java.util.Locale.getDefault()
    ).format(
        java.util.Date()
    )
    val allCategory: MutableList<MonitorBloodGlucoseCategory> = ArrayList()
    val categoryItemList: MutableList<BloodGlucoseCategoryItem> = ArrayList()

    val symptoms = listOf(
        SymptomsData(1, "Shaky", false),
        SymptomsData(2, "Fast heartbeat", false),
        SymptomsData(3, "Sweating", false),
        SymptomsData(4, "Dizzy", false),
        SymptomsData(5, "Anxious", false),
        SymptomsData(6, "Hungry", false),
        SymptomsData(7, "Blurry Vision", false),
        SymptomsData(8, "Weakness or Fatigue", false),
        SymptomsData(9, "Headche", false),
        SymptomsData(10, "Irritable", false),
        SymptomsData(11, "Other", false)
    )
    val observationList = listOf(
        Observation(0,"Vaginal Bleeding",false),
        Observation(0,"Leakage of fluid",false),
        Observation(0,"Fetal movement",false),
        Observation(0,"Contraction",false),
        Observation(0,"Nausea and/or Vomiting",false),
        Observation(0,"Other",false)
    )

    val homeMenusList = listOf(
        HomeMenus(0, "Diabetes"),
        HomeMenus(0, "OBGYN"),
        HomeMenus(0, "Breastfeeding")
    )
    val scoreTypeList = listOf(
        ScoreType(0,"Visit"),
        ScoreType(0,"Record"),
        ScoreType(0,"Observe")
    )
    val subMenuList = listOf(
        HomeSubMenus(0, "Blood Glucose", 1,2),
        HomeSubMenus(0, "Insulin", 1,2),
        HomeSubMenus(0, "Weight", 1,2),
        HomeSubMenus(0, "Symptoms", 1,3),
        HomeSubMenus(0, "Progress", 1,3),
        HomeSubMenus(0, "Observations of Myself", 2,3),
        HomeSubMenus(0, "Counseling", 2,2),
        HomeSubMenus(0, "Prenatal Visit", 2,1),
        HomeSubMenus(0, "Progress", 2,3),
        HomeSubMenus(0, "BreastFeeding", 3,2),
        HomeSubMenus(0, "Diaper Change", 3,2),
        HomeSubMenus(0, "Daily Observation", 3,3),
        HomeSubMenus(0, "Baby Weight", 3,2)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
       /* val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
            if (isOptionSelected){
                ll_options.visibility = View.VISIBLE
                diabetes_options.visibility = View.GONE
                isOptionSelected = false
            }
        } */
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentHomeScreenBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home_screen,
            container,
            false
        )
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        authViewModel = ViewModelProvider(this, authfactory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_today_date.text = currentDate
        savedataAllCategory()
        saveAllCategoryItems()

        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                if (AppDatabase(it).getMonitorBloodGlucoseCatDao().getAllCategory().isNullOrEmpty()){
                    AppDatabase(it).getMonitorBloodGlucoseCatDao().saveAllMonitorbloodGlucoseCat(allCategory)
                }
            }
            context?.let {
                if ( AppDatabase(it).getHomeMenusDao().getAllScoreType().isNullOrEmpty()) {
                    AppDatabase(it).getHomeMenusDao().saveAllScoreType(scoreTypeList)
                    Log.d("AppDatabase : ",
                        "ScoreTyp Saved ${AppDatabase(it).getHomeMenusDao().getAllScoreType().size} added"
                    )
                }
            }
            context?.let {
                if(AppDatabase(it).getMonitorBloodGlucoseCatDao().getAllCategoryItems().isNullOrEmpty()){
                    AppDatabase(it).getMonitorBloodGlucoseCatDao().saveAllBloodGlucoseCategoryItem(categoryItemList)
                }
            }
            context?.let {
                if (AppDatabase(it).getSymptomsDao().getSymptom().isNullOrEmpty()){
                    AppDatabase(it).getSymptomsDao().saveAllSymptoms(symptoms)
                }
            }
            context?.let {
                if (AppDatabase(it).getHomeMenusDao().getAllMenus().isNullOrEmpty()){
                    AppDatabase(it).getHomeMenusDao().saveAllMenus(homeMenusList)
                }
            }
            context?.let {
                if (AppDatabase(it).getHomeMenusDao().getAllSubMenu().isNullOrEmpty()){
                    AppDatabase(it).getHomeMenusDao().saveAllSubMenus(subMenuList)
                }
            }

            context?.let {
              if ( AppDatabase(it).getObgynDao().getAllObservation().isNullOrEmpty()) {
                    AppDatabase(it).getObgynDao().saveAllObservation(observationList)
                  Log.d("AppDatabase : ",
                      "observationList Saved ${AppDatabase(it).getObgynDao().getAllObservation().size} added"
                  )
              }
            }

         /*   context?.let {
                val current_date = OffsetDateTime.now()
                    for (i in 1..13) {
                    AppDatabase(it).getHomeMenusDao().saveScores(
                        ScoreTable(0, 0, i, 2, current_date)
                    )}
                    Log.d(
                        "AppDatabase : ", "Scores Saved ${AppDatabase(it).getHomeMenusDao().getAllScores().size} added"
                    )
            }*/
        }

        Diabetes.setOnClickListener {
             viewModel.user.value?.let { it1 ->
                 val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToDiabetesFragment()
                     .setDoctorname(it1.doctorName.toString())
                     .setName(it1.display_name.toString())
                     .setTime(it1.appointment.toString())
                     .setWeekofpreg(it1.weekOfPregnancy!!)
                 Navigation.findNavController(it).navigate(action)
             }
        }

        OBGYN.setOnClickListener {
            viewModel.user.value?.let { it1 ->
                val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToObgynFragment(
                    it1.display_name!!,
                    it1.weekOfPregnancy!!,
                    it1.doctorName!!,
                    it1.appointment!!
                )
                Navigation.findNavController(it).navigate(action)
            }
        }
        breastfeeding.setOnClickListener {
            viewModel.user.value?.let { it1 ->
                val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToBreastfeedingFragment()
                    .setDoctorname(it1.doctorName.toString())
                    .setName(it1.display_name.toString())
                    .setTime(it1.appointment.toString())
                    .setWeekofpreg(it1.weekOfPregnancy!!)
                Navigation.findNavController(it).navigate(action)
            }
        }

        tv_logout.setOnClickListener {
            authViewModel.getLoggedInUser().observe(viewLifecycleOwner, Observer { data ->
                if (data != null) {
                    logoutUser(data)
                }
            })
        }
    }

    private fun saveAllCategoryItems() {
        categoryItemList.add(
            BloodGlucoseCategoryItem(
                0,
                1,
                "wake_up_fasting",
                "65-95",
                "08:30 AM",
                "Wake Up Fasting",
                "65",
                0
            )
        )

        categoryItemList.add(
            BloodGlucoseCategoryItem(
                0,
                2,
                "before_breakfast",
                "65-95",
                "08:30 AM",
                "Before Breakfast",
                "65",
                0
            )
        )
        categoryItemList.add(
            BloodGlucoseCategoryItem(
                0,
                2,
                "1_hr_after_breakfast",
                "65-140",
                "09:30 AM",
                "1 Hour After Breakfast",
                "68",
                0
            )
        )
        categoryItemList.add(
            BloodGlucoseCategoryItem(
                0,
                2,
                "2_hr_after_breakfast",
                "65-120",
                "10:30 AM",
                "2 Hour After Breakfast",
                "",
                0
            )
        )

        categoryItemList.add(
            BloodGlucoseCategoryItem(
                0,
                3,
                "before_lunch",
                "65-95",
                "12:30 PM",
                "Before Lunch",
                "",
                0
            )
        )
        categoryItemList.add(
            BloodGlucoseCategoryItem(
                0,
                3,
                "1_hr_after_lunch",
                "65-140",
                "01:30 PM",
                "1 Hour After Lunch",
                "",
                0
            )
        )
        categoryItemList.add(
            BloodGlucoseCategoryItem(
                0,
                3,
                "2_hr_after_lunch",
                "65-120",
                "02:30 PM",
                "2 Hour After Lunch",
                "",
                0
            )
        )

        categoryItemList.add(
            BloodGlucoseCategoryItem(
                0,
                4,
                "before_dinner",
                "65-95",
                "06:30 PM",
                "Before Diner",
                "",
                0
            )
        )
        categoryItemList.add(
            BloodGlucoseCategoryItem(
                0,
                4,
                "1_hr_after_dinner",
                "65-140",
                "07:30 PM",
                "1 Hour After Diner",
                "",
                0
            )
        )
        categoryItemList.add(
            BloodGlucoseCategoryItem(
                0,
                4,
                "2_hr_after_dinner",
                "65-120",
                "08:30 PM",
                "2 Hour After Diner",
                "",
                0
            )
        )

        categoryItemList.add(
            BloodGlucoseCategoryItem(
                0,
                5,
                "bedtime",
                "65-95",
                "10:30 PM",
                "Bedtime",
                "65",
                0
            )
        )
    }

    private fun savedataAllCategory() {
        allCategory.add(MonitorBloodGlucoseCategory(1, "Wake Up"))
        allCategory.add(MonitorBloodGlucoseCategory(2, "Breakfast"))
        allCategory.add(MonitorBloodGlucoseCategory(3, "Lunch"))
        allCategory.add(MonitorBloodGlucoseCategory(4, "Diner"))
        allCategory.add(MonitorBloodGlucoseCategory(5, "Bedtime"))
    }

    private fun logoutUser(data: Data) {
        context?.let {
            AlertDialog.Builder(it).apply {
                setTitle("Confirmation")
                setMessage("Are you sure want to logout. This might delete unsaved progress.")
                setPositiveButton("Yes") { _, _ ->
                    Coroutines.io{
                       // AppDatabase(context).getUserDao().delete(data)
                        AppDatabase(it).clearAllTables()
                        activity?.let{
                            val intent = Intent(it, MainActivity::class.java).also {
                                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                            it.startActivity(intent)
                        }
                    }
                }
                setNegativeButton("No") { _, _ ->
                }
            }.create().show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, bgColorId: Int) =
            HomeScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putInt(ARG_BG_COLOR, bgColorId)
                }
            }
    }

}