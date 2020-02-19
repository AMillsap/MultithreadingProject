package com.example.multithreadingproject

import android.os.AsyncTask
import org.greenrobot.eventbus.EventBus

class AsyncTaskPrimeNum : AsyncTask<ParamNumbers, String, String>()
{

    override fun doInBackground(vararg params: ParamNumbers) : String
    {
        var low = params[0].lowNum
        var high = params[0].highNum
        var primes = mutableListOf<String>()

        while(low <= high)
        {
            if(checkIfPrimeNumber(low))
            {
                primes.add(low.toString())
                low++
            }
            else
            {
                low++
            }
        }
        return primes.toString()
    }

    fun checkIfPrimeNumber(num: Int): Boolean {
        var flag = true
        for (i in 2..num / 2) {
            if (num % i == 0) {
                flag = false
                break
            }
        }
        return flag
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        EventBus.getDefault().post(AsyncTaskEvent(result?: "No Result"))

    }


}

class ParamNumbers internal constructor(internal var lowNum :Int, internal var highNum :Int)

interface AsyncTaskCallback
{
    fun onResult(result: String?)
}