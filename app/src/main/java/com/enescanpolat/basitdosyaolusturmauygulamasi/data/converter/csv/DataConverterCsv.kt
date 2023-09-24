package com.enescanpolat.basitdosyaolusturmauygulamasi.data.converter.csv

import com.enescanpolat.basitdosyaolusturmauygulamasi.data.converter.DataConverter
import com.enescanpolat.basitdosyaolusturmauygulamasi.data.converter.GenarateInfo
import com.enescanpolat.basitdosyaolusturmauygulamasi.domain.model.ExportModel
import com.enescanpolat.basitdosyaolusturmauygulamasi.util.Resource
import com.opencsv.CSVWriter
import com.opencsv.CSVWriterBuilder
import com.opencsv.ICSVWriter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.StringWriter
import java.io.Writer

class DataConverterCsv :DataConverter {

    private fun getCSVWriter(writer:Writer):ICSVWriter{
        return CSVWriterBuilder(writer)
            .withSeparator(SEPARATOR)
            .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
            .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
            .withLineEnd(CSVWriter.DEFAULT_LINE_END)
            .build()
    }

    override fun convertSensorData(
        exportDatalist: List<ExportModel>
    ): Flow<Resource<GenarateInfo>> = flow {

        emit(Resource.Loading(GenarateInfo()))
        val writer = StringWriter()
        val cvsWriter = getCSVWriter(writer)
        val valuesForOnePercent = (exportDatalist.size/100)+1
        var alreadyConverteredValues = 0
        cvsWriter.writeNext(HEADER_DATA)

        exportDatalist.forEach {exportModel ->

            cvsWriter.writeNext(
                arrayOf(
                    "${exportModel.sensorData}",
                    "${exportModel.time}"
                )
            )
            alreadyConverteredValues += 1
            if (alreadyConverteredValues%valuesForOnePercent == 0){

                emit(Resource.Loading(
                    GenarateInfo(
                    progressPercentage = alreadyConverteredValues/valuesForOnePercent
                )
                ))

            }

        }
        emit(Resource.Success(
            GenarateInfo(
            byteArray = String(writer.buffer).toByteArray(),
            progressPercentage = 100
           )
        ))
        cvsWriter.close()
        writer.close()

    }



    companion object{
        const val SEPARATOR = ';'
        val HEADER_DATA = arrayOf("sensor_data","time")
    }

}