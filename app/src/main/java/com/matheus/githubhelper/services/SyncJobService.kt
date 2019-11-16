package com.matheus.githubhelper.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log

class SyncJobService: JobService() {
    override fun onStopJob(p0: JobParameters?): Boolean {
        return true
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.i("JOBSERVICE", "JobService executado")
        startService(Intent(this, SyncService::class.java))
        return true
    }
}