package com.godwin.network.godwin.worker


import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by WiSilica on 3/4/2019 4:56 PM.
 *
 * @author : Godwin Joseph Kurinjikattu
 * @since : 2017
 */
object ThreadPoolProvider {
    val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
    /*
     * thread pool executor for background tasks
     */
    private var mForBackgroundTasks: ThreadPoolExecutor
    /*
     * thread pool executor for main thread tasks
     */

    init{
        //restricts initialization

        // setting the thread factory

        // setting the thread pool executor for mForBackgroundTasks;
        mForBackgroundTasks = ThreadPoolExecutor(
            NUMBER_OF_CORES * 5,
            NUMBER_OF_CORES * 5,
            60L,
            TimeUnit.SECONDS,
            LinkedBlockingQueue(),
            Executors.defaultThreadFactory()        )
        // setting the thread pool executor for mMainThreadExecutor;
    }


    /**
     * Execute back ground task.
     *
     * @param runnable the runnable
     */
    fun executeBackGroundTask(runnable: Runnable) {
        mForBackgroundTasks.execute(runnable)
    }
}