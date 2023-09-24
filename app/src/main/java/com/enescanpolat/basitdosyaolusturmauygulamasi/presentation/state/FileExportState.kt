package com.enescanpolat.basitdosyaolusturmauygulamasi.presentation.state

data class FileExportState (
    val isGenaratingLoading:Boolean = false,
    val isSharedDataClick:Boolean =false,
    val isSharedDataReady:Boolean =false,
    val sharedDataUri:String?=null,
    val failedGenarating:Boolean = false,
    val genaratingProgress:Int = 0
)