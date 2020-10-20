package com.s.diabetesfeeding.ui.adapter

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.BloodGlucoseCategoryItem
import com.s.diabetesfeeding.data.db.entities.CategoryWithItems
import com.s.diabetesfeeding.data.db.entities.ProgressBloodGlucose
import com.s.diabetesfeeding.data.db.entities.ScoreTable
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.item_monitor_blood_glucose_child.view.*
import org.threeten.bp.OffsetDateTime


class CategoryItemAdapter(
    private val context: Context,
    private val categoryItem: List<BloodGlucoseCategoryItem>,
    private val allCategory: List<CategoryWithItems>,
    private val positionParent: Int
): RecyclerView.Adapter<CategoryItemAdapter.CategoryItemViewHolder>() {
    var tempPosition: Int = 0
    class CategoryItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
    private val currentTime: String = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault()).format(
        java.util.Date())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_monitor_blood_glucose_child, parent, false)
        )
    }

    override fun getItemCount() = categoryItem.size

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val categoryItems = categoryItem[position]
        if (position>=1){
            holder.view.rl_done.visibility = View.GONE
            holder.view.rl_or_done.visibility = View.VISIBLE
        }
        if (position == categoryItem.size-1){
            holder.view.rl_or_done.visibility = View.GONE
        }
        holder.view.et_value.clearFocus()
        holder.view.et_value.isFocusableInTouchMode = true
        holder.view.rl_done.isEnabled = true
        if (categoryItems.value.isEmpty()){
            holder.view.et_value.inputType = InputType.TYPE_CLASS_NUMBER
            holder.view.et_value.setText("")
            holder.view.rl_done.isEnabled = true
            holder.view.mc_done.isEnabled = true
            holder.view.mcv_input.isEnabled = true
        }else{
            holder.view.et_value.inputType = InputType.TYPE_NULL
            holder.view.et_value.setText(categoryItems.value)
            holder.view.tv_time.text = categoryItems.time
            holder.view.tv_time.visibility = View.VISIBLE
            holder.view.rl_done.isEnabled = false
            holder.view.mcv_input.isEnabled = false
            holder.view.mc_done.isEnabled = false
        }
        if (position==1){
            if(categoryItem[1].value.isNotEmpty() || categoryItem[2].value.isNotEmpty()){
                holder.view.et_value.inputType = InputType.TYPE_NULL
                 if (categoryItem[1].value.isNotEmpty()){
                     holder.view.et_value.setText(categoryItems.value)
                     holder.view.tv_time.text = categoryItems.time
                     holder.view.tv_time.visibility = View.VISIBLE
                     holder.view.rl_second_done.isEnabled = false
                     holder.view.mcv_second_done.isEnabled = false
                     holder.view.mcv_input.isEnabled = false
                 }
                else{
                    holder.view.et_value.setText(" ")
                    holder.view.et_value.inputType = InputType.TYPE_NULL
                     //holder.view.tv_time.text = categoryItems.time
                     holder.view.tv_time.visibility = View.GONE
                     holder.view.rl_second_done.isEnabled = false
                     holder.view.mcv_second_done.isEnabled = false
                     holder.view.mcv_input.isEnabled = false

                }
            }
        }
        if (position==2){
            if(categoryItem[1].value.isNotEmpty() || categoryItem[2].value.isNotEmpty()){
                holder.view.et_value.inputType = InputType.TYPE_NULL
                if (categoryItem[2].value.isNotEmpty()) {
                    holder.view.et_value.setText(categoryItems.value)
                    holder.view.tv_time.text = categoryItems.time
                    holder.view.tv_time.visibility = View.VISIBLE
                    holder.view.rl_second_done.isEnabled = false
                    holder.view.mcv_second_done.isEnabled = false
                    holder.view.mcv_input.isEnabled = false
                }
               else {
                    holder.view.et_value.setText(" ")
                    holder.view.et_value.inputType = InputType.TYPE_NULL
                    holder.view.tv_time.visibility = View.GONE
                    holder.view.mcv_second_done.isEnabled = false
                    holder.view.mcv_input.isEnabled = false
                    holder.view.rl_second_done.isEnabled = false
               }
            }
        }
        holder.view.tv_title.text = categoryItems.title
        //holder.view.tv_time.text = categoryItems.time
        holder.view.tv_range_value.text = categoryItems.range

        var isBlank: Boolean

        holder.view.rl_done.setOnClickListener {
            isBlank = categoryItems.isBlank
            if (!isBlank){
                holder.view.snackbar("Field cannot be blank")
            }else{
                categoryItems.value = holder.view.et_value.text.toString()
                categoryItems.score = 1
                categoryItems.time = currentTime
                updateData(categoryItems)
                updateProgressData(categoryItems)
                updateScore(categoryItems)
                holder.view.snackbar("Saved")
            }
        }
        holder.view.rl_second_done.setOnClickListener {
            isBlank = categoryItems.isBlank
            if (position>=1){
                if (!categoryItem[1].isBlank && !categoryItem[2].isBlank){
                    holder.view.snackbar("Field cannot be blank")
                }else{
                    if (tempPosition == 2){
                        val categoryItems = categoryItem[2]
                        categoryItems.score = 1
                        categoryItems.time = currentTime
                        updateData(categoryItems)
                        updateProgressData(categoryItems)
                        updateScore(categoryItems)
                        holder.view.snackbar("Saved")
                    }else{
                        // categoryItems.value = holder.view.et_value.text.toString()
                        categoryItems.score = 1
                        categoryItems.time = currentTime
                        updateData(categoryItems)
                        updateProgressData(categoryItems)
                        updateScore(categoryItems)
                        holder.view.snackbar("Saved")
                    }
                }
            }
        }

        holder.view.et_value.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun afterTextChanged(editable: Editable) {
                categoryItems.isBlank = !editable.isBlank()
                categoryItems.value = editable.toString()
                tempPosition = position
                    if (position==1 && categoryItem[2].isBlank){
                        editable.clear()
                    }
                    if (position == 2 && categoryItem[1].isBlank){
                        editable.clear()
                }
            }
        })

        if (prefs.getSavedIsPreviousDate() && categoryItems.value.isBlank()) {
            holder.view.et_value.setText(" ")
            holder.view.et_value.inputType = InputType.TYPE_NULL
            holder.view.rl_second_done.isEnabled = false
            holder.view.rl_done.isEnabled = false

            holder.view.mc_done.isEnabled = false
            holder.view.mcv_second_done.isEnabled = false
            holder.view.mcv_input.isEnabled = false
        }

        if (!prefs.getSavedIsPreviousDate()){
            if (positionParent == 1){
                val parentList = allCategory[0].bloodGlucoseCategoryItems
                if (parentList.isNotEmpty()){

                    if (position==0 && parentList[0].value.isBlank()){
                        holder.view.et_value.setText(" ")
                        holder.view.mcv_input.isEnabled = false
                        holder.view.mc_done.isEnabled = false
                        holder.view.et_value.inputType = InputType.TYPE_NULL
                    }else{
                        if (position > 0){
                            if (allCategory[1].bloodGlucoseCategoryItems[0].value.isBlank()){
                                holder.view.et_value.setText(" ")
                                holder.view.mcv_input.isEnabled = false
                                holder.view.mcv_second_done.isEnabled = false
                                holder.view.et_value.inputType = InputType.TYPE_NULL
                            }
                        }

                    }

                }
            }
            if (positionParent == 2){
                val parentList = allCategory[1].bloodGlucoseCategoryItems
                if (parentList.isNotEmpty()){

                    if (position==0 && (parentList[1].value.isBlank() && parentList[2].value.isBlank())){
                        holder.view.et_value.setText(" ")
                        holder.view.mcv_input.isEnabled = false
                        holder.view.mc_done.isEnabled = false
                        holder.view.et_value.inputType = InputType.TYPE_NULL
                    }
                    else{
                        if (position > 0){
                            if (allCategory[2].bloodGlucoseCategoryItems[0].value.isBlank()){
                                holder.view.et_value.setText(" ")
                                holder.view.mcv_input.isEnabled = false
                                holder.view.mcv_second_done.isEnabled = false
                                holder.view.et_value.inputType = InputType.TYPE_NULL
                            }
                        }
                    }
                }
            }

            if (positionParent == 3){
                val parentList = allCategory[2].bloodGlucoseCategoryItems
                if (parentList.isNotEmpty()){

                    if (position==0 && (parentList[1].value.isBlank() && parentList[2].value.isBlank())){
                        holder.view.et_value.setText(" ")
                        holder.view.mcv_input.isEnabled = false
                        holder.view.mc_done.isEnabled = false
                        holder.view.et_value.inputType = InputType.TYPE_NULL
                    }
                    else{
                        if (position > 0){
                            if (allCategory[3].bloodGlucoseCategoryItems[0].value.isBlank()){
                                holder.view.et_value.setText(" ")
                                holder.view.mcv_input.isEnabled = false
                                holder.view.mcv_second_done.isEnabled = false
                                holder.view.et_value.inputType = InputType.TYPE_NULL
                            }
                        }
                    }
                }
            }
            if (positionParent == 4) {
                val parentList = allCategory[3].bloodGlucoseCategoryItems
                if (parentList.isNotEmpty()) {
                    if (position == 0 && (parentList[1].value.isBlank() && parentList[2].value.isBlank())) {
                        holder.view.et_value.setText(" ")
                        holder.view.mcv_input.isEnabled = false
                        holder.view.mc_done.isEnabled = false
                        holder.view.et_value.inputType = InputType.TYPE_NULL
                    }
                }
            }
        }
        if (prefs.getSavedDoctorId()?.isNotBlank()!!){
            holder.view.mcv_input.isEnabled = false
            holder.view.mc_done.isEnabled = false
            holder.view.mcv_second_done.isEnabled = false
            holder.view.et_value.inputType = InputType.TYPE_NULL
        }

    }

    // if things are enabled and disabled we will grayish the filed

    private fun updateData(categoryItems: BloodGlucoseCategoryItem) {
        Coroutines.io {
            context.let {
                AppDatabase(it).getMonitorBloodGlucoseCatDao().updateBloodGlucoseCategoryItem(categoryItems)
            }
        }
        // notifyDataSetChanged()
    }
    private fun updateProgressData(categoryItems: BloodGlucoseCategoryItem) {
        Coroutines.io {
            context.let {
                val currentDate = OffsetDateTime.now()
                val progressData =  ProgressBloodGlucose(0,categoryItems.itemsCatId,categoryItems.range,categoryItems.time,categoryItems.title,
                    categoryItems.value,currentDate, currentDate.dayOfWeek.name,categoryItems.isBlank)
                AppDatabase(it).getMonitorBloodGlucoseCatDao().saveProgressBloodGlucoseData(progressData)
                Log.d("APPDATABASE : ","progressData value is ${progressData.dateTime}")
            }
        }
    }
    fun updateScore(categoryItems: BloodGlucoseCategoryItem) {
        Coroutines.io {
            context?.let {
                val currentDate = OffsetDateTime.now()
                AppDatabase(it).getHomeMenusDao().saveScores(ScoreTable(0, 0, 1, categoryItems.score, currentDate))
            }
            (context as Activity).onBackPressed()
        }
    }

}