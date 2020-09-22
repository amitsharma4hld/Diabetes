package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.s.diabetesfeeding.util.lazyDeferred
import org.threeten.bp.OffsetDateTime

@BindingAdapter("layout_height")
fun setLayoutHeight(view: View, height: Float) {
    view.layoutParams = view.layoutParams.apply { this.height = height.toInt() }
}

class ProgressBloodGlucoseModel (
    private var repository: ProgressBloodGlucoseRepository
): ViewModel() {
   // lateinit var progressWithCategory: List<ProgressDataWithCategory>
   //suspend fun getProgressData() = repository.getProgressData()
   var sunday:Int = 0

    val getProgressData by lazyDeferred {
        repository.getProgressData()
    }
    val getProgressDataByDate by lazyDeferred {
       // repository.getProgressDataByDate(1,"","")
    }
    fun getProgressByDate(id:Int, from: OffsetDateTime, to: OffsetDateTime){

    }
}