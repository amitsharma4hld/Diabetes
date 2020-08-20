package com.s.diabetesfeeding.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.s.diabetesfeeding.R
import kotlinx.android.synthetic.main.fragment_diaper_change.*
import kotlinx.android.synthetic.main.layoutdemo.view.*
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DiaperChangeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diaper_change, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
       // tv_help.text = ""

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiaperChangeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    //String encode function
    fun encodeEmoji(message: String): String {
        try {
            return URLEncoder.encode(
                message,
                "UTF-8"
            )
        } catch (e: UnsupportedEncodingException) {
            return message
        }

    }


    //String decode function
    fun decodeEmoji(message: String): String {
        val myString: String? = null
        try {
            return URLDecoder.decode(
                message, "UTF-8"
            )
        } catch (e: UnsupportedEncodingException) {
            return message
        }

    }
}