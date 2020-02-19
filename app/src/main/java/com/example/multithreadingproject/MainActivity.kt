package com.example.multithreadingproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity(), AsyncTaskCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart()
    {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop()
    {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAsyncTaskEvent(event: AsyncTaskEvent)
    {
        tvPrimeNumbers.text = event.result
    }

    fun onClick(view: View)
    {
        when(view.id)
        {
            R.id.btnReverseStringThroughRunnable -> onRunnableThread()
            R.id.btnAsyncTaskFindPrime ->
            {
                val asyncTaskPrimeNum = AsyncTaskPrimeNum()
                val firstNumber = etFirstInteger.text.toString().toInt()
                val secondNumber = etSecondInteger.text.toString().toInt()
                val paramater = ParamNumbers(firstNumber, secondNumber)
                asyncTaskPrimeNum.execute(paramater)
            }
            R.id.btnFibonacci ->onLooperExecuted("")
        }
    }

    fun onRunnableThread()
    {
        val thread = Thread(Runnable {
            var string = etRunnableInput.text.toString()
            var reversedString = string.reversed()
            tvReversedString.text = reversedString
        })
        thread.start()
    }

    override fun onResult(result: String?)
    {
        tvPrimeNumbers.text = result
    }

    fun onLooperExecuted(valuePassed: String)
    {
        val looper = Looper()
        looper.handleMessages(Handler(Looper.getMainLooper()){
                message ->
            tvFibonacciOutput.text = message.data.getString("KEY")
            true
        })

        looper.start()
        val message = Message()
        message.data.putString("KEY_MAIN", valuePassed)
        looper.myHandler.sendMessage(message)
    }


}
