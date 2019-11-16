package com.matheus.githubhelper.services

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent

class BootReceiver: BroadcastReceiver() {
    override fun onReceive(ctx: Context?, p1: Intent?) {
        if(ctx != null) {
            val service =  ctx.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val component = ComponentName(ctx, SyncJobService::class.java)
            val jobInfo = JobInfo.Builder(1, component)
                .setPeriodic(900000)
                .build()

            service.schedule(jobInfo)
        }
    }

}