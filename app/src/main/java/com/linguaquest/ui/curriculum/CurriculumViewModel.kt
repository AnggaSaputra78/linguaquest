package com.linguaquest.ui.curriculum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.linguaquest.data.local.entity.LessonProgressEntity
import com.linguaquest.data.repository.CurriculumRepository
import com.linguaquest.data.repository.UnitWithLessonsUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CurriculumUiState(
    val isLoading: Boolean = true,
    val units: List<UnitWithLessonsUi> = emptyList(),
    val progressByLesson: Map<String, LessonProgressEntity> = emptyMap(),
    val errorMessage: String? = null
)

class CurriculumViewModel(
    private val repository: CurriculumRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CurriculumUiState())
    val uiState: StateFlow<CurriculumUiState> = _uiState

    init {
        viewModelScope.launch {
            try {
                val curriculum = repository.getCurriculum()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        units = curriculum
                    )
                }
            } catch (throwable: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Gagal memuat kurikulum: ${throwable.message}"
                    )
                }
            }
        }

        viewModelScope.launch {
            repository.observeLessonProgressMap()
                .catch { throwable ->
                    _uiState.update {
                        it.copy(
                            errorMessage = "Gagal memuat progres: ${throwable.message}"
                        )
                    }
                }
                .collect { progressMap ->
                    _uiState.update {
                        it.copy(progressByLesson = progressMap)
                    }
                }
        }
    }

    companion object {
        fun factory(repository: CurriculumRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(CurriculumViewModel::class.java)) {
                        return CurriculumViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}