package com.lytredrock.emocloudmusic.frgment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.lytredrock.emocloudmusic.R

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/17 11:37
 */
class ItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView: View = inflater.inflate(R.layout.fragment_banner, container, false)
        val back = myView.findViewById<ImageView>(R.id.iv_banner)
        val bundle = arguments
        val email = bundle!!.getString("data")
        Glide.with(requireContext()).load(email).into(back)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }
}