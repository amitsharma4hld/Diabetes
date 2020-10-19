package com.s.diabetesfeeding.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.Data
import com.s.diabetesfeeding.data.db.entities.HomeSubMenus
import com.s.diabetesfeeding.ui.ClinicianListner
import com.s.diabetesfeeding.util.shortToast
import kotlinx.android.synthetic.main.clinical_patient_list_item.view.*
import java.lang.System.load


class ClinicianAdapter (private val context: Context,
                        private val patientsDataList : List<com.s.diabetesfeeding.data.db.entities.auth.Data>,
                        private val clinicianListner: ClinicianListner) : RecyclerView.Adapter<ClinicianAdapter.PatientsDataListViewHolder>(){

    class PatientsDataListViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientsDataListViewHolder {
        return PatientsDataListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.clinical_patient_list_item, parent, false)
        )
    }

    override fun getItemCount()= patientsDataList.size

    override fun onBindViewHolder(holder: PatientsDataListViewHolder, position: Int) {
        val data = patientsDataList[position]
        holder.view.tv_name.text = data.display_name
        holder.view.tv_weeks.text = data.weekOfPregnancy.toString() + " Week"

        val url: String = data.profileImage

        Glide.with(context)
            .load(url)
            .centerCrop()
            .transform(CircleCrop())
            .error(R.drawable.ic_baseline_account_circle_24)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .into(holder.view.iv_image)

        holder.view.tv_view_button.setOnClickListener {
            val dataPatient =  Data(data.ID.toInt() ,data.user_login,data.user_email,data.display_name,data.type,
                data.profileImage,"","",data.user_status,data.weekOfPregnancy,data.doctorName,
                data.appointment,data.key,data.user_registered,data.user_activation_key,data.role,data.deviceType)
            clinicianListner.onPatientSelect(dataPatient)
        }

    }

}
