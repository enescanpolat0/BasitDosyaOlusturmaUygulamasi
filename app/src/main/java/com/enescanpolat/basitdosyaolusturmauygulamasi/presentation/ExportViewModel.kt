package com.enescanpolat.basitdosyaolusturmauygulamasi.presentation

import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enescanpolat.basitdosyaolusturmauygulamasi.domain.model.ExportModel
import com.enescanpolat.basitdosyaolusturmauygulamasi.domain.repository.ExportRepository
import com.enescanpolat.basitdosyaolusturmauygulamasi.presentation.state.FileExportState
import com.enescanpolat.basitdosyaolusturmauygulamasi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ExportViewModel @Inject constructor(
    private val exportRepository: ExportRepository,
):ViewModel() {


    private val exportList = mutableListOf<ExportModel>()

    var collectedDataAmount by mutableStateOf(0)
        private set

    var fileExportState by mutableStateOf(FileExportState())
        private set


    private var collectingJob: Job? =null

    init {
        collectingJob = viewModelScope.launch {
            while (true){
                delay(2)
                collectedDataAmount += 120
                exportList.addAll(
                    listOf(
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),
                        ExportModel(Random.nextFloat()*10000,System.currentTimeMillis()),


                    )
                )
            }
        }

    }



    fun generateExportFile(){

        collectingJob?.cancel()

        fileExportState = fileExportState.copy(isGenaratingLoading = true)
        exportRepository.exportData(
            exportList.toList()
        ).onEach {pathInfo->
            when(pathInfo){

                is Resource.Success->{

                    fileExportState = fileExportState.copy(
                        isSharedDataReady = true,
                        isGenaratingLoading = false,
                        sharedDataUri = pathInfo.data.path,
                        genaratingProgress = 100
                    )

                }

                is Resource.Error->{

                    fileExportState = fileExportState.copy(
                        failedGenarating = true,
                        isGenaratingLoading = false,

                        )


                }

                is Resource.Loading->{

                    pathInfo.data?.let {
                        fileExportState = fileExportState.copy(
                            genaratingProgress = pathInfo.data.proggressPrecentage
                        )
                    }

                }


            }
        }.launchIn(viewModelScope)
    }


    fun onShareDataClick(){
        fileExportState = fileExportState.copy(isSharedDataClick = true)
    }

    fun onShareDataOpen(){
        fileExportState = fileExportState.copy(isSharedDataClick = false)
    }



}