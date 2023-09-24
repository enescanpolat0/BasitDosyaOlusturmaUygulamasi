package com.enescanpolat.basitdosyaolusturmauygulamasi.data.file

import com.enescanpolat.basitdosyaolusturmauygulamasi.util.Resource

interface FileWriter {


    suspend fun writeFile(byteArray: ByteArray):Resource<String>


    companion object{
        const val FILE_NAME= "FileExportApp"
    }

}