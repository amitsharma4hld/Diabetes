package com.s.diabetesfeeding.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BreastFeedingSessionData
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.CellClickListener
import com.s.diabetesfeeding.ui.home.fragment.breastfeeding.BreastfeedingFragmentDirections
import com.s.diabetesfeeding.util.snackbar
import kotlinx.android.synthetic.main.item_breastfeeding_sessions_list.view.*
import kotlinx.android.synthetic.main.item_trimester.view.*


class BreastFeedingSessionAdapter(val sessions : List<BreastFeedingSessionData>, private val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<BreastFeedingSessionAdapter.SessionViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        return SessionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_breastfeeding_sessions_list, parent, false)
        )
    }

    override fun getItemCount()= sessions.size

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val session = sessions[position]
        if (sessions[0].breastfeedingTime.isNotEmpty()){
            if (session.breastfeedingTimerCount.isNullOrEmpty() && sessions[position-1].breastfeedingTimerCount.isNotEmpty()){
                holder.view.iv_session_time_blank.setImageResource(R.drawable.ic_session_add)
                holder.view.iv_session_time_blank.setOnClickListener {
                    if (prefs.getSavedIsPreviousDate()) {
                        it.snackbar("Previous data can not be edited")
                    }else{
                        val action = BreastfeedingFragmentDirections.actionBreastfeedingFragmentToGoalBreastfeedFragment()
                        action.breastFeedingSession = session
                        Navigation.findNavController(it).navigate(action)
                    }

                }
            }
            if (session.breastfeedingTime !=""){
                holder.view.tv_session_time_value.visibility = View.VISIBLE
                holder.view.tv_session_type.visibility = View.VISIBLE
                holder.view.iv_session_time_blank.visibility = View.GONE
                holder.view.cv_breastfeeding_blank.visibility = View.GONE
                holder.view.cv_breastfeeding.visibility = View.VISIBLE
                if(session.breastfeedingType == "Breastfeed"){
                    holder.view.tv_session_type.text = "Breast"
                }else{
                    holder.view.tv_session_type.text = "Suppl."
                }
                holder.view.tv_session_time_value.text = session.breastfeedingTime + "\n" +session.breastfeedingTimerCount
            }else{
                holder.view.tv_session_time_value.visibility = View.GONE
                holder.view.tv_session_type.visibility = View.GONE
                holder.view.iv_session_time_blank.visibility = View.VISIBLE
                holder.view.cv_breastfeeding_blank.visibility = View.VISIBLE
            }
        }else{
            if (position==0){
                holder.view.iv_session_time_blank.setImageResource(R.drawable.ic_session_add)
                holder.view.iv_session_time_blank.setOnClickListener {
                    if (prefs.getSavedIsPreviousDate()) {
                        it.snackbar("Previous data can not be edited")
                    }else{
                        val action = BreastfeedingFragmentDirections.actionBreastfeedingFragmentToGoalBreastfeedFragment()
                        action.breastFeedingSession = session
                        Navigation.findNavController(it).navigate(action)
                    }
                }
            }else{
                holder.view.tv_session_time_value.visibility = View.GONE
                holder.view.iv_session_time_blank.visibility = View.VISIBLE
                holder.view.cv_breastfeeding_blank.visibility = View.VISIBLE
            }
        }


    }

    class SessionViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}