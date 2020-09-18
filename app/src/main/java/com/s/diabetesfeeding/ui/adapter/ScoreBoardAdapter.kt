package com.s.diabetesfeeding.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.ScoreBoardFinalData
import com.s.diabetesfeeding.util.getDateFromOffsetDateTime
import kotlinx.android.synthetic.main.fragment_breastfeeding.view.*
import kotlinx.android.synthetic.main.item_scoreboard.view.*

class ScoreBoardAdapter(context:Context,val scores: ArrayList<ScoreBoardFinalData>): RecyclerView.Adapter<ScoreBoardAdapter.ScoreViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        return ScoreViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_scoreboard, parent, false)
        )
    }

    override fun getItemCount()= scores.size

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        // Total Score Menu Titles
        val totalScore: Int = score.scoreDataList.map { it.subMenuScore }.sum()
        val totalVisitScore = score.scoreDataList.filter { it.score_type_id == 1 }.sumBy { it.subMenuScore }
        val totalRecordScore = score.scoreDataList.filter { it.score_type_id == 2 }.sumBy { it.subMenuScore }
        val totalObserveScore = score.scoreDataList.filter { it.score_type_id == 3 }.sumBy { it.subMenuScore }
        // Diabetes Score Section
        val totalBloodGlucoseScore = score.scoreDataList.filter { it.sub_menuId == 1 && it.score_type_id == 2 }.sumBy { it.subMenuScore }
        val totalInsulinScore = score.scoreDataList.filter { it.sub_menuId == 2 && it.score_type_id == 2 }.sumBy { it.subMenuScore }
        val totalWeightScore = score.scoreDataList.filter { it.sub_menuId == 3 && it.score_type_id == 2 }.sumBy { it.subMenuScore }
        val totalSymptomsScore = score.scoreDataList.filter { it.sub_menuId == 4 && it.score_type_id == 3 }.sumBy { it.subMenuScore }
        val totalProgressScore = score.scoreDataList.filter { it.sub_menuId == 5 && it.score_type_id == 3 }.sumBy { it.subMenuScore }
        // Obgyn Score Section
        val totalObservationOfMyselfScore = score.scoreDataList.filter { it.sub_menuId == 6 && it.score_type_id == 3 }.sumBy { it.subMenuScore }
        val totalCounselingScore = score.scoreDataList.filter { it.sub_menuId == 7 && it.score_type_id == 2 }.sumBy { it.subMenuScore }
        val totalPrenatalScore = score.scoreDataList.filter { it.sub_menuId == 8 && it.score_type_id == 1 }.sumBy { it.subMenuScore }
        val totalObgynProgressScore = score.scoreDataList.filter { it.sub_menuId == 9 && it.score_type_id == 3 }.sumBy { it.subMenuScore }
        // BreastFeeding Score Section
        val totalBreastScore = score.scoreDataList.filter { it.sub_menuId == 10 && it.score_type_id == 2 }.sumBy { it.subMenuScore }
        val totalSupplimentScore = score.scoreDataList.filter { it.sub_menuId == 11 && it.score_type_id == 2 }.sumBy { it.subMenuScore }
        val totalDiaperChangeScore = score.scoreDataList.filter { it.sub_menuId == 12 && it.score_type_id == 2 }.sumBy { it.subMenuScore }
        val totalDailyObservationScore = score.scoreDataList.filter { it.sub_menuId == 13 && it.score_type_id == 3 }.sumBy { it.subMenuScore }
        val totalBabyWeightScore = score.scoreDataList.filter { it.sub_menuId == 14 && it.score_type_id == 2 }.sumBy { it.subMenuScore }


        // Total Score Menu Titles
        if (score.scoreDataList.isNotEmpty()){
            holder.view.tv_date.text = holder.view.context.getDateFromOffsetDateTime(score.scoreDataList[0].date_time)
        }
        holder.view.tv_total_score_value.text = totalScore.toString()
        holder.view.tv_visit_total_value.text = totalVisitScore.toString()
        holder.view.tv_record_total_vale.text = totalRecordScore.toString()
        holder.view.tv_observe_total_value.text = totalObserveScore.toString()
        // Diabetes Score Section
        holder.view.tv_blood_glucose_record_score.text = totalBloodGlucoseScore.toString()
        holder.view.tv_insulin_record_score.text = totalInsulinScore.toString()
        holder.view.tv_weight_record_score.text = totalWeightScore.toString()
        holder.view.tv_symptoms_observe_score.text = totalSymptomsScore.toString()
        holder.view.tv_progress_observe_score.text = totalProgressScore.toString()
        // Obgyn Score Section
        holder.view.tv_observation_myslef_observe_score.text = totalObservationOfMyselfScore.toString()
        holder.view.tv_counseling_record_score.text = totalCounselingScore.toString()
        holder.view.tv_prenatal_visit_score.text = totalPrenatalScore.toString()
        holder.view.tv_obgyn_progress_observe_score.text = totalObgynProgressScore.toString()
        // BreastFeeding Score Section
        holder.view.tv_breast_record_score.text = totalBreastScore.toString()
        holder.view.tv_supplement_record_score.text = totalSupplimentScore.toString()
        holder.view.tv_diaper_change_record_score.text = totalDiaperChangeScore.toString()
        holder.view.tv_daily_observations_observe_score.text = totalDailyObservationScore.toString()
        holder.view.tv_baby_wight_record_score.text = totalBabyWeightScore.toString()



        val isExpandable : Boolean = score.expandable
        holder.view.ll_scores_holder.visibility = if (isExpandable) View.VISIBLE else View.GONE
        holder.view.iv_down.setImageResource( if (isExpandable) R.drawable.ic_baseline_keyboard_arrow_up_24 else R.drawable.ic_baseline_keyboard_arrow_down_24)

        holder.view.ll_collapsing_arrow.setOnClickListener {
            val scoresData = scores[position]
            scoresData.expandable = !scoresData.expandable
            notifyDataSetChanged()
        }
    }

    class ScoreViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}