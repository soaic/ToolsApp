package com.soaic.jetpackcomponent.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soaic.jetpackcomponent.R
import com.soaic.jetpackcomponent.databinding.StartFragmentBinding
import com.soaic.jetpackcomponent.model.User
import com.soaic.jetpackcomponent.viewmodel.UserViewModel

class StartFragment : Fragment() {

    private lateinit var dataBinding: StartFragmentBinding
    private val user = User()
    companion object {
        fun newInstance() = StartFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.start_fragment, container, false)


        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataBinding.user = user
        user.firstName.set("test")
        user.lastName.set("test")

        val userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        dataBinding.userViewModel = userViewModel
        userViewModel.firstName.set("testViewModel")
        userViewModel.lastName.set("testViewModel")

        dataBinding.changeValueBtn.setOnClickListener{
            user.firstName.set("test1")
            user.lastName.set("test2")

            userViewModel.firstName.set("testViewModel1")
            userViewModel.lastName.set("testViewModel1")
        }

    }

}
