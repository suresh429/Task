package net.simplifiedcoding.ui.base

import androidx.lifecycle.ViewModel
import com.vcspinfo.task.data.repository.BaseRepository


abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel() {

   // suspend fun logout(api: UserApi) = withContext(Dispatchers.IO) { repository.logout(api) }

}