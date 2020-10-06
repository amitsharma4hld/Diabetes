package com.s.diabetesfeeding.ui.home.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.*
import com.s.diabetesfeeding.data.db.entities.obgynentities.Observation
import com.s.diabetesfeeding.data.db.entities.obgynentities.ProgressObservation
import com.s.diabetesfeeding.databinding.FragmentHomeScreenBinding
import com.s.diabetesfeeding.prefs
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
import java.io.File
import java.text.DateFormat
import java.util.*
import java.util.Collections.list
import kotlin.collections.ArrayList

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
    var day = 0
    var month: Int = 0
    var year: Int = 0
    private lateinit var calendar:Calendar
    var currentDate: String = java.text.SimpleDateFormat(
        "MMM dd, yyyy",
        java.util.Locale.getDefault()
    ).format(
        java.util.Date()
    )
    val allCategory: MutableList<MonitorBloodGlucoseCategory> = ArrayList()
    val categoryItemList: MutableList<BloodGlucoseCategoryItem> = ArrayList()
    private var progressBloodGlucose:List<ProgressBloodGlucose> = java.util.ArrayList()
    private var progressSymptoms:List<ProgressSymptoms> = java.util.ArrayList()
    private var progressObservation:List<ProgressObservation> = ArrayList()


    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
        .build()

    val symptoms = listOf(
        SymptomsData(1, "Shaky", false,""),
        SymptomsData(2, "Fast heartbeat", false,""),
        SymptomsData(3, "Sweating", false,""),
        SymptomsData(4, "Dizzy", false,""),
        SymptomsData(5, "Anxious", false,""),
        SymptomsData(6, "Hungry", false,""),
        SymptomsData(7, "Blurry Vision", false,""),
        SymptomsData(8, "Weakness or Fatigue", false,""),
        SymptomsData(9, "Headche", false,""),
        SymptomsData(10, "Irritable", false,""),
        SymptomsData(11, "Other", false,"")
    )
    val observationList = listOf(
        Observation(1,"Vaginal Bleeding",false,""),
        Observation(2,"Leakage of fluid",false,""),
        Observation(3,"Fetal movement",false,""),
        Observation(4,"Contraction",false,""),
        Observation(5,"Nausea and/or Vomiting",false,""),
        Observation(6,"Other",false,"")
    )

    val homeMenusList = listOf(
        HomeMenus(1, "Diabetes"),
        HomeMenus(2, "OBGYN"),
        HomeMenus(3, "Breastfeeding")
    )
    val scoreTypeList = listOf(
        ScoreType(1,"Visit"),
        ScoreType(2,"Record"),
        ScoreType(3,"Observe")
    )
    val subMenuList = listOf(
        HomeSubMenus(1, "Blood Glucose", 1,2),
        HomeSubMenus(2, "Insulin", 1,2),
        HomeSubMenus(3, "Weight", 1,2),
        HomeSubMenus(4, "Symptoms", 1,3),
        HomeSubMenus(5, "Progress", 1,3),
        HomeSubMenus(6, "Observations of Myself", 2,3),
        HomeSubMenus(7, "Counseling", 2,2),
        HomeSubMenus(8, "Prenatal Visit", 2,1),
        HomeSubMenus(9, "Progress", 2,3),
        HomeSubMenus(10, "BreastFeeding", 3,2),
        HomeSubMenus(11, "Supplement", 3,2),
        HomeSubMenus(12, "Diaper Change", 3,2),
        HomeSubMenus(13, "Daily Observation", 3,3),
        HomeSubMenus(14, "Baby Weight", 3,2)
    )


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
        if (!prefs.getSavedIsLoggedIn()) {
            showDialog("ccss")
        }
        prefs.saveIsLoggedIn(true)
        if (prefs.getSavedFormattedDate().isNullOrEmpty()){
            tv_today_date.text=currentDate
        }else{
            tv_today_date.text = prefs.getSavedFormattedDate()
            val savedDate = prefs.getOffsetDateTime()
            if (OffsetDateTime.parse(savedDate).dayOfMonth == OffsetDateTime.now().dayOfMonth) {
                prefs.saveIsPrevousDate(false)
            }else{
                prefs.saveIsPrevousDate(true)
            }
        }
        iv_calendar_picup_date.setOnClickListener {
            calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)

            val mDateSetListener =
                DatePickerDialog.OnDateSetListener { it, year, monthOfYear, day ->
                    val selectedDate = formatDate(year, monthOfYear, day)
                    tv_today_date.text = selectedDate

                    val odt = OffsetDateTime.now()
                    val offsetDefaultZone = odt.offset

                    val month = monthOfYear + 1
                    val dayWithZero = if (day < 10) "0$day" else day.toString()
                    val monthWithZero = if (month < 10) "0$month" else month.toString()
                    val date: String = year.toString() + "-" + monthWithZero + "-" + dayWithZero + "T00:00:00.356" + offsetDefaultZone.toString()
                    val toDate: String = year.toString() + "-" + monthWithZero + "-" + dayWithZero + "T23:59:58.356" + offsetDefaultZone.toString()

                    prefs.saveformattedDate(selectedDate)
                    prefs.saveOffsetDateTime(toDate)
                    prefs.saveFromDate(date)
                    if (day == OffsetDateTime.now().dayOfMonth) {
                        prefs.saveIsPrevousDate(false)
                    }else{
                        prefs.saveIsPrevousDate(true)
                    }
                    val fromOffsetDateTime = OffsetDateTime.parse(date)
                    val toOffsetDate = OffsetDateTime.parse(toDate)

                    Coroutines.io {
                        // BloodGlucose
                        context?.let {
                            progressBloodGlucose = AppDatabase(it).getMonitorBloodGlucoseCatDao().getProgressDataWithoutId(fromOffsetDateTime,toOffsetDate)
                        }
                        context?.let {
                            if (progressBloodGlucose.isEmpty()) {
                                    AppDatabase(it).getMonitorBloodGlucoseCatDao().saveAllBloodGlucoseCategoryItem(categoryItemList)
                            }else {
                                for (i in progressBloodGlucose.indices){
                                    AppDatabase(it).getMonitorBloodGlucoseCatDao()
                                        .updateBloodGlucoseColumn(
                                            progressBloodGlucose[i].title,
                                            progressBloodGlucose[i].time,
                                            progressBloodGlucose[i].value,
                                            progressBloodGlucose[i].isBlank)
                                }
                            }
                        }
                    }
                    Coroutines.io {
                        // Symptoms
                        context?.let {
                            progressSymptoms = AppDatabase(it).getSymptomsDao().getProgressDataWithoutId(fromOffsetDateTime,toOffsetDate)
                        }
                        context?.let {
                            if (progressSymptoms.isEmpty()) {
                                AppDatabase(it).getSymptomsDao().saveAllSymptoms(symptoms)
                            }else {
                                AppDatabase(it).getSymptomsDao().saveAllSymptoms(symptoms)
                                for (i in progressSymptoms.indices){
                                    AppDatabase(it).getSymptomsDao()
                                        .updateSymptomColumn(
                                            progressSymptoms[i].Symptom,
                                            progressSymptoms[i].comment,
                                            progressSymptoms[i].isChecked)
                                }
                            }
                        }
                    }
                    Coroutines.io {
                        // Observation
                        context?.let {
                            progressObservation = AppDatabase(it).getObgynDao().getProgressDataWithoutId(fromOffsetDateTime,toOffsetDate)
                        }
                        context?.let {
                            if (progressObservation.isEmpty()) {
                                AppDatabase(it).getObgynDao().saveAllObservation(observationList)
                            }else {
                                AppDatabase(it).getObgynDao().saveAllObservation(observationList)
                                for (i in progressObservation.indices){
                                    AppDatabase(it).getObgynDao()
                                        .updateObservationColumn(
                                            progressObservation[i].title,
                                            progressObservation[i].comment,
                                            progressObservation[i].isChecked)
                                }
                            }
                        }
                    }
                    Coroutines.io {
                        // Step Count
                        context?.let {
                                val date = AppDatabase(it).getHomeMenusDao().getStepsCountDateWise(fromOffsetDateTime,toOffsetDate)
                                val step =  date[date.lastIndex].stepsCount
                                tv_steps_count?.text = getString(R.string.step_count_display, step)
                        }
                    }

                }
            val datePickerDialog = DatePickerDialog(requireContext(),mDateSetListener, year, month,day)
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis() + 1000
            datePickerDialog.show()

        }
        // tv_steps_count.text = "# steps today"

        savedataAllCategory()
        saveAllCategoryItems()
        readData()
        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                if (AppDatabase(it).getMonitorBloodGlucoseCatDao().getAllCategory().isNullOrEmpty()){
                    AppDatabase(it).getMonitorBloodGlucoseCatDao().saveAllMonitorbloodGlucoseCat(allCategory)
                }
            }
            context?.let {
                if ( AppDatabase(it).getHomeMenusDao().getAllScoreType().isNullOrEmpty()) {
                    AppDatabase(it).getHomeMenusDao().saveAllScoreType(scoreTypeList)
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
              }
            }
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
                    prefs.cearAllSharedPref()
                }
            })
        }
    }
    private fun getGoogleAccount() = GoogleSignIn.getAccountForExtension(requireActivity(), fitnessOptions)

    private fun readData() {
        Fitness.getHistoryClient(requireActivity(), getGoogleAccount())
            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener { dataSet ->
                val total = when {
                    dataSet.isEmpty -> 0
                    else -> dataSet.dataPoints.first().getValue(Field.FIELD_STEPS).asInt()
                }

                if (prefs.getSavedIsPreviousDate()) {
                    val fromDate = OffsetDateTime.parse(prefs.getFromOffsetDateTime())
                    val toDate = OffsetDateTime.parse(prefs.getOffsetDateTime())
                    viewLifecycleOwner.lifecycleScope.launch {
                        context?.let {
                            val date = AppDatabase(it).getHomeMenusDao().getStepsCountDateWise(fromDate,toDate)
                            if (!date.isNullOrEmpty()){
                                val step =  date[date.lastIndex].stepsCount
                                tv_steps_count?.text = getString(R.string.step_count_display, step)
                            }else
                                tv_steps_count?.text= getString(R.string.step_count_display, 0)
                        }
                    }

                }else
                    tv_steps_count?.text =  getString(R.string.step_count_display, total)
                     Log.i("TAG", "Total steps: $total")
                viewLifecycleOwner.lifecycleScope.launch {
                    context?.let {
                        val currentDate = OffsetDateTime.now()
                        AppDatabase(it).getHomeMenusDao()
                            .saveSteps(
                                StepCount(0, total, "", currentDate)
                            )
                    }
                }
            }
            .addOnFailureListener { e ->

                Log.w("TAG", "There was a problem getting the step count.", e)
            }
    }

    private fun saveAllCategoryItems() {
        categoryItemList.add(
            BloodGlucoseCategoryItem(
                1,
                1,
                "wake_up_fasting",
                "65-95",
                "08:30 AM",
                "Wake Up Fasting",
                "",
                0,
                false
            )
        )

        categoryItemList.add(
            BloodGlucoseCategoryItem(
                2,
                2,
                "before_breakfast",
                "65-95",
                "08:30 AM",
                "Before Breakfast",
                "",
                0,
                false
            )
        )
        categoryItemList.add(
            BloodGlucoseCategoryItem(
                3,
                2,
                "1_hr_after_breakfast",
                "65-140",
                "09:30 AM",
                "1 Hour After Breakfast",
                "",
                0,
                false
            )
        )
        categoryItemList.add(
            BloodGlucoseCategoryItem(
                4,
                2,
                "2_hr_after_breakfast",
                "65-120",
                "10:30 AM",
                "2 Hour After Breakfast",
                "",
                0,
                false
            )
        )

        categoryItemList.add(
            BloodGlucoseCategoryItem(
                5,
                3,
                "before_lunch",
                "65-95",
                "12:30 PM",
                "Before Lunch",
                "",
                0,
                false
            )
        )
        categoryItemList.add(
            BloodGlucoseCategoryItem(
                6,
                3,
                "1_hr_after_lunch",
                "65-140",
                "01:30 PM",
                "1 Hour After Lunch",
                "",
                0,
                false
            )
        )
        categoryItemList.add(
            BloodGlucoseCategoryItem(
                7,
                3,
                "2_hr_after_lunch",
                "65-120",
                "02:30 PM",
                "2 Hour After Lunch",
                "",
                0,
                false
            )
        )

        categoryItemList.add(
            BloodGlucoseCategoryItem(
                8,
                4,
                "before_dinner",
                "65-95",
                "06:30 PM",
                "Before Diner",
                "",
                0,
                false
            )
        )
        categoryItemList.add(
            BloodGlucoseCategoryItem(
                9,
                4,
                "1_hr_after_dinner",
                "65-140",
                "07:30 PM",
                "1 Hour After Diner",
                "",
                0,
                false
            )
        )
        categoryItemList.add(
            BloodGlucoseCategoryItem(
                10,
                4,
                "2_hr_after_dinner",
                "65-120",
                "08:30 PM",
                "2 Hour After Diner",
                "",
                0,
                false
            )
        )

        categoryItemList.add(
            BloodGlucoseCategoryItem(
                11,
                5,
                "bedtime",
                "65-95",
                "10:30 PM",
                "Bedtime",
                "",
                0,
                false
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
                         AppDatabase(it).clearAllTables()
                        //deleteDatabaseFile(requireActivity(),"DiabetesDatabase.db")
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

    private fun deleteDatabaseFile(context: Context, databaseName: String) {
        val databases = File(context.applicationInfo.dataDir.toString() + "/databases")
        val db = File(databases, databaseName)
        if (db.delete()) println("Database deleted") else println("Failed to delete database")
        val journal = File(databases, "$databaseName-journal")
        if (journal.exists()) {
            if (journal.delete()) println("Database journal deleted") else println("Failed to delete database journal")
        }
    }

    private fun formatDate(year:Int, month:Int, day:Int):String{
        calendar.set(year, month, day, 0, 0, 0)
        val chosenDate = calendar.time
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM)
        return df.format(chosenDate)
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


        private fun showDialog(title: String) {
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_dialog)
            val body = dialog.findViewById(R.id.tv_quotes) as TextView
            //body.text = title
            val mBtn = dialog.findViewById(R.id.tv_close_button) as TextView
            mBtn.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
    }

}