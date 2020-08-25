package com.s.diabetesfeeding.ui.home.fragment.breastfeeding

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.s.diabetesfeeding.R
import kotlinx.android.synthetic.main.fragment_diaper_change.*
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class DiaperChangeFragment : Fragment() {
    val currentTime: String = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault()).format(
        java.util.Date())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diaper_change, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_time.text=currentTime

        iv_poop.setOnClickListener {
            iv_poop.setImageResource(R.drawable.ic_poop_fill)
            iv_pee.setImageResource(R.drawable.ic_pee_unfill)
        }
        iv_pee.setOnClickListener {
            iv_pee.setImageResource(R.drawable.ic_pee_fill)
            iv_poop.setImageResource(R.drawable.ic_poop_unfill)

        }

    }


}