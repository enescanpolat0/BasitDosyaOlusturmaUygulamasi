package com.enescanpolat.basitdosyaolusturmauygulamasi.presentation

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.enescanpolat.basitdosyaolusturmauygulamasi.R

import java.io.File


@Composable
fun ExportScreen (
    exportViewModel: ExportViewModel = hiltViewModel()
) {


    val fileExportState = exportViewModel.fileExportState
    val context = LocalContext.current
    
    
    LaunchedEffect(key1 = fileExportState){
        if (fileExportState.isSharedDataClick){
            val uri = FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName+".provider",
                File(fileExportState.sharedDataUri!!)
            )

            val intent = Intent(Intent.ACTION_SEND)
            intent.type="text/csv"
            intent.putExtra(Intent.EXTRA_SUBJECT,"My Export Data")
            intent.putExtra(Intent.EXTRA_STREAM,uri)

            val chooser = Intent.createChooser(intent,"Share With")
            ContextCompat.startActivity(
                context,chooser,null
            )
            exportViewModel.onShareDataOpen()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ){

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "Collected data amount: ${exportViewModel.collectedDataAmount}",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            
            Button(onClick = {
                exportViewModel.generateExportFile()
            },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red,
                    contentColor = Color.White,
                    disabledBackgroundColor = Color.Red
                ),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(10.dp),
                shape = CircleShape,
                enabled = !fileExportState.isSharedDataReady

            ) {

                Text(
                    text ="Genarate File",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

            }
            AnimatedVisibility(visible = fileExportState.isSharedDataReady) {
                IconButton(onClick = {
                    exportViewModel.onShareDataClick()
                 }
                ) {

                    Icon(
                        painterResource(R.drawable.export),
                        contentDescription = "export",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(120.dp)
                    )


                }
            }


        }


    }

    if (fileExportState.isGenaratingLoading){
        Dialog(onDismissRequest = {

        }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                CircularProgressIndicator(
                    color= Color.White
                )
                
                Text(
                    text = "Generating File(${fileExportState.genaratingProgress}%)...",
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Medium
                )

            }


        }
    }

}