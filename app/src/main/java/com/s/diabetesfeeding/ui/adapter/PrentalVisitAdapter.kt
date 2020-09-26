package com.s.diabetesfeeding.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.obgynentities.PrentalVisitRecord
import com.s.diabetesfeeding.ui.CellClickListener
import com.s.diabetesfeeding.ui.home.fragment.obgyn.PrentalVisitFragmentDirections
import kotlinx.android.synthetic.main.item_prental_visit_list.view.*

class PrentalVisitAdapter(val prentalVisitRecords : List<PrentalVisitRecord>,private val cellClickListener: CellClickListener)
    :  RecyclerView.Adapter<PrentalVisitAdapter.RecordsViewHolder>() {


    class RecordsViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordsViewHolder {
        return RecordsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_prental_visit_list, parent, false)
        )
    }

    override fun getItemCount()=prentalVisitRecords.size

    override fun onBindViewHolder(holder: RecordsViewHolder, position: Int) {
        val prentalRecord = prentalVisitRecords[position]
        holder.view.tv_measurement_of.text = prentalRecord.measurementOf
        prentalRecord.unit
        holder.view.setOnClickListener {
                val action = PrentalVisitFragmentDirections.actionPrentalVisitFragmentToPrePregnancyInputFragment()
                action.prentalVisitRecord = prentalRecord
                Navigation.findNavController(it).navigate(action)
        }

    }

}