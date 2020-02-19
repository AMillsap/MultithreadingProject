package com.example.multithreadingproject

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import kotlin.math.sqrt

class Looper : Thread()
{
    lateinit var mainHandeler : Handler
    lateinit var myHandler: Handler

    fun handleMessages(passedHandler: Handler)
    {
        mainHandeler = passedHandler
        myHandler = MyLooper()
    }


    inner class MyLooper : Handler(Looper.myLooper()!!)
    {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            var fibonacciList = mutableListOf<Int>()
            fibonacciList.add(0)
            fibonacciList.add(1)

            for(i in fibonacciList[1]..19)
            {
                var number = fibonacciList[i] + fibonacciList[i-1]
                fibonacciList.add(number)
            }

            val message = Message()
            message.what = msg.what
            val bundle = Bundle()
            bundle.putString("KEY", "Here is the sequence to the 20th iteration: $fibonacciList")
            message.data = bundle
            mainHandeler.sendMessage(message)

        }
    }

    override fun run()
    {
        super.run()
        Looper.prepare()
        Looper.loop()
    }

}