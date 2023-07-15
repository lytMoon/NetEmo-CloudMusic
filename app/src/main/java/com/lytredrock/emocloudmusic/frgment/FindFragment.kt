package com.lytredrock.emocloudmusic.frgment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lytredrock.emocloudmusic.R

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/15 19:50
 */
class FindFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_find, container, false)

    }

    public override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }


}