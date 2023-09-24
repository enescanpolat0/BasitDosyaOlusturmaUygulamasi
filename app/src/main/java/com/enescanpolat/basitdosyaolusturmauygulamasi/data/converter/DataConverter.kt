package com.enescanpolat.basitdosyaolusturmauygulamasi.data.converter

import com.enescanpolat.basitdosyaolusturmauygulamasi.domain.model.ExportModel
import com.enescanpolat.basitdosyaolusturmauygulamasi.util.Resource
import kotlinx.coroutines.flow.Flow

interface DataConverter {


    fun convertSensorData(
        exportDatalist:List<ExportModel>
    ): Flow<Resource<GenarateInfo>>


}