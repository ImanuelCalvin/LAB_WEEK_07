package com.example.lab_week_07

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val TAG = "MapsActivity"
        private const val ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION
    }

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    getLastLocation()
                } else {
                    showPermissionRationale {
                        requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
                    }
                }
            }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        when {
            hasLocationPermission() -> getLastLocation()
            shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION) -> {
                showPermissionRationale { requestPermissionLauncher.launch(ACCESS_FINE_LOCATION) }
            }
            else -> requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }

    private fun hasLocationPermission() =
        ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

    private fun showPermissionRationale(positiveAction: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle("Location Permission")
            .setMessage("Aplikasi ini membutuhkan akses lokasi agar dapat berjalan dengan baik.")
            .setPositiveButton("OK") { _, _ -> positiveAction() }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun getLastLocation() {
        Log.d(TAG, "getLastLocation() called — belum mengambil lokasi (Commit 1)")
    }
}
