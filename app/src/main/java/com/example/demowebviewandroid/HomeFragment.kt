package com.example.demowebviewandroid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private var txtHome: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val v = inflater!!.inflate(R.layout.fragment_home, container, false)


        txtHome = v.findViewById(R.id.txt_home)
        txtHome?.setOnClickListener {
            val intent = Intent(activity, HomeActivity::class.java)
            (activity as MainActivity?)!!.startActivity(intent)
        }
        return v
    }

}