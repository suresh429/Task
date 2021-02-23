
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vcspinfo.task.data.repository.BaseRepository
import com.vcspinfo.task.data.repository.UserRepository
import com.vcspinfo.task.ui.t.base.WheatherViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
          //  modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(WheatherViewModel::class.java) -> WheatherViewModel(repository as UserRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

}