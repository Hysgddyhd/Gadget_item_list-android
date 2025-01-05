package com.example.gadgetlist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gadgetlist.ui.GadgetApp
import com.example.gadgetlist.ui.home.HomeScreen
import com.example.gadgetlist.ui.navigation.InventoryApp
import com.example.gadgetlist.ui.navigation.InventoryNavHost
import com.example.gadgetlist.ui.theme.GadgetListTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            GadgetListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Log.d("lifecycle", "create in main Activity before call home screen")

                //    val navController: NavHostController = rememberNavController()
                    InventoryApp()
                    Log.d("lifecycle", "create in main Activity after" +
                            " call home screen")


                }
            }
        }
    }
    override fun onPause() {
        super.onPause()
        Log.d("lifecycle", "pause")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "resume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("lifecycle","stop")
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifecycle","start")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("lifecycle","restart")
    }
}

