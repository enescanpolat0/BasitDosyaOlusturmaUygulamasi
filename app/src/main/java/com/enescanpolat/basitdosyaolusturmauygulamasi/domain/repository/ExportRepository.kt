package com.enescanpolat.basitdosyaolusturmauygulamasi.domain.repository

import com.enescanpolat.basitdosyaolusturmauygulamasi.domain.model.ExportModel
import com.enescanpolat.basitdosyaolusturmauygulamasi.domain.model.PathInfo
import com.enescanpolat.basitdosyaolusturmauygulamasi.util.Resource
import kotlinx.coroutines.flow.Flow

interface ExportRepository {

    fun exportData(
        exportList:List<ExportModel>
    ): Flow<Resource<PathInfo>>


}