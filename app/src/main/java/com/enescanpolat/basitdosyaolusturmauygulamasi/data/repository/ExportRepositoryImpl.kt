package com.enescanpolat.basitdosyaolusturmauygulamasi.data.repository

import com.enescanpolat.basitdosyaolusturmauygulamasi.data.converter.DataConverter
import com.enescanpolat.basitdosyaolusturmauygulamasi.data.file.FileWriter
import com.enescanpolat.basitdosyaolusturmauygulamasi.domain.model.ExportModel
import com.enescanpolat.basitdosyaolusturmauygulamasi.domain.model.PathInfo
import com.enescanpolat.basitdosyaolusturmauygulamasi.domain.repository.ExportRepository
import com.enescanpolat.basitdosyaolusturmauygulamasi.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExportRepositoryImpl @Inject constructor(
    private val fileWriter: FileWriter,
    private val dataConverter: DataConverter
):ExportRepository {


    override fun exportData(
        exportList: List<ExportModel>
    ): Flow<Resource<PathInfo>> =
        dataConverter.convertSensorData(exportList).map {genarateInfo->

            when(genarateInfo){

                is Resource.Success->{

                    genarateInfo.data.byteArray?.let {
                        when(val result = fileWriter.writeFile(it)){
                            is Resource.Success->{
                                return@map Resource.Success(
                                    PathInfo(
                                        path = result.data,
                                        proggressPrecentage = 100
                                    )
                                )
                            }

                            is Resource.Loading->{
                                return@map Resource.Error(errorMessage = result.message?:"unkown error")
                            }

                            is Resource.Error->{
                                return@map Resource.Error(errorMessage = result.errorMessage)
                            }
                        }
                    }?: return@map Resource.Error(errorMessage =  "Unkonwn error occured")

                }

                is Resource.Error->{
                    return@map Resource.Error(errorMessage = genarateInfo.errorMessage)
                }

                is Resource.Loading->{
                    return@map Resource.Loading(
                        PathInfo(
                            proggressPrecentage = genarateInfo.data?.progressPercentage?:0
                        )
                    )
                }


            }

        }.flowOn(Dispatchers.IO)
}