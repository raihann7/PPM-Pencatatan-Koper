package id.ac.unpas.agenda.ui.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ac.unpas.agenda.models.Todo
import id.ac.unpas.agenda.persistences.TodoDao
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val todoDao: TodoDao) : ViewModel() {

    private val _isDone: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDone: LiveData<Boolean> = _isDone

    val todos : LiveData<List<Todo>> = todoDao.loadAll()

    suspend fun upsert(id: String,
                       title: String,
                       description: String,
                       dueDate: String) {
        todoDao.upsert(Todo(id, title, description, dueDate))
        _isDone.postValue(true)
    }

    suspend fun delete(id: String) {
        todoDao.delete(id)
    }
}