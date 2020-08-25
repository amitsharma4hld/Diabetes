package com.s.diabetesfeeding.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.BreastFeedingSessionData
import com.s.diabetesfeeding.ui.CellClickListener
import kotlinx.android.synthetic.main.item_breastfeeding_sessions_list.view.*


class BreastFeedingSessionAdapter(val sessions : List<BreastFeedingSessionData>,
                                  private val cellClickListener: CellClickListener
) :
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
        if (position==2){
            holder.view.iv_session_time_blank.setImageResource(R.drawable.ic_session_add)
            holder.view.iv_session_time_blank.setOnClickListener {
                cellClickListener.onCellClickListener(it)
               /* holder.view.iv_session_time_blank.setOnClickListener(
                    Navigation.createNavigateOnClickListener(R.id.action_breastfeedingFragment_to_babyWeightFragment)
                )*/
            }
        }
        if (session.breastfeedingTime !=""){
            holder.view.tv_session_time_value.visibility = View.VISIBLE
            holder.view.iv_session_time_blank.visibility = View.GONE
            holder.view.cv_breastfeeding_blank.visibility = View.GONE
            holder.view.cv_breastfeeding.visibility = View.VISIBLE
            holder.view.tv_session_time_value.text = session.breastfeedingTime + "\n" +session.breastfeedingTimerCount
        }else{
            holder.view.tv_session_time_value.visibility = View.GONE
            holder.view.iv_session_time_blank.visibility = View.VISIBLE
            holder.view.cv_breastfeeding_blank.visibility = View.VISIBLE
        }

    }

    class SessionViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}