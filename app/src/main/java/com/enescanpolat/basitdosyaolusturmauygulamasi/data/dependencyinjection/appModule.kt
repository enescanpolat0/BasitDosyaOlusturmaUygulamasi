package com.enescanpolat.basitdosyaolusturmauygulamasi.data.dependencyinjection

import android.content.Context
import com.enescanpolat.basitdosyaolusturmauygulamasi.data.converter.DataConverter
import com.enescanpolat.basitdosyaolusturmauygulamasi.data.converter.csv.DataConverterCsv
import com.enescanpolat.basitdosyaolusturmauygulamasi.data.file.AndroidInternalStorageFileWriter
import com.enescanpolat.basitdosyaolusturmauygulamasi.data.file.FileWriter
import com.enescanpolat.basitdosyaolusturmauygulamasi.data.repository.ExportRepositoryImpl
import com.enescanpolat.basitdosyaolusturmauygulamasi.domain.repository.ExportRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object appModule {


    @Singleton
    @Provides
    fun injectFileWriter(@ApplicationContext context:Context):FileWriter{
        return AndroidInternalStorageFileWriter(context)
    }


    @Singleton
    @Provides
    fun injectDataConverter():DataConverter{
        return DataConverterCsv()
    }

    @Singleton
    @Provides
    fun injectExportRepository(fileWriter: FileWriter,dataConverter: DataConverter): ExportRepository {
        return ExportRepositoryImpl(fileWriter, dataConverter)
    }




}