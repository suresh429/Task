package com.vcspinfo.task.ui.t.base

import ViewModelFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.vcspinfo.task.data.network.RemoteDataSource
import com.vcspinfo.task.data.repository.BaseRepository
import net.simplifiedcoding.ui.base.BaseViewModel


abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {

   // protected lateinit var userPreferences: UserPreferences
    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected val remoteDataSource = RemoteDataSource()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // userPreferences = UserPreferences(requireContext())
        binding = getFragmentBinding(inflater, container)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

       // lifecycleScope.launch { userPreferences.authToken.first() }

        return binding.root
    }

   /* fun logout() = lifecycleScope.launch{
        val authToken = userPreferences.authToken.first()
        val authRefreshToken = userPreferences.authRefreshToken.first()
        val api = remoteDataSource.buildApi(UserApi::class.java, authToken,authRefreshToken,requireContext())
        viewModel.logout(api)
        userPreferences.clear()
        requireActivity().startNewActivity(AuthActivity::class.java)
    }*/

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getFragmentRepository(): R

}