package com.bombadu.jobschedexample

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class ExampleJobService : JobService() {
    private var jobCancelled = false
    override fun onStartJob(jobParameters: JobParameters): Boolean {
        Log.d(TAG, "Job Started")
        doBackgroundWork(jobParameters)
        return true
    }

    private fun doBackgroundWork(jobParameters: JobParameters) {
        Thread(Runnable {
            for (i in 0..9) {
                Log.d(TAG, "run: $i")
                if (jobCancelled) {
                    return@Runnable
                }
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            Log.d(TAG, "Job Finished")
            jobFinished(jobParameters, false)
        }).start()
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        Log.d(TAG, "Job Cancelled before completion")
        jobCancelled = true
        return false
    }

    companion object {
        private const val TAG = "ExampleJobService"
    }
}