package theschulk.com.kotlinweatherdemo.Data.Room

import android.os.Handler
import android.os.HandlerThread


/**
 * Create a worker thread to access the db off of the main thread
 */
class DbWorkerThread(threadName: String) : HandlerThread(threadName) {

    private var workerHandler: Handler? = null

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        workerHandler = Handler(looper)
    }

    fun postTask(task:Runnable){
        workerHandler!!.post(task)
    }
}