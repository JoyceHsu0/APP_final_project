package com.example.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cart.ai.Constants.RECEIVE_ID
import com.example.cart.ai.Constants.SEND_ID
import com.example.cart.ai.Message
import com.example.cart.ai.MessagingAdapter
import com.example.cart.ai.Time
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException

class AIActivity : AppCompatActivity() {

    private lateinit var adapter: MessagingAdapter
    var messagesList = mutableListOf<Message>()
    private val demolist = listOf(
        "嗨",
        "你有什麼推薦的壽司嗎？","好的，3份",
        "最近有什麼新品嗎？", "不用",
        "介紹一下鮪魚壽司。", "好的", "2份",
        "我要一份甜蝦。",
        "就這樣了",
        "好啊",
        "是",
        "我要查看訂單")
    private var demo_mode=false
    private var demo_conter=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aiactivity)

        recyclerView()

        clickEvents()

        customBotMessage("嗨! 我是您的AI助手，可以提供您：\n\n" +
                "1. 推薦商品\n" +
                "2. 推薦新品\n" +
                "3. 介紹商品\n" +
                "4. 加入購物車\n" +
                "5. 查看訂單\n\n" +
                "的功能，請問有什麼我可以協助您的嗎？")


    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        findViewById<RecyclerView>(R.id.rv_messages).adapter = adapter
        findViewById<RecyclerView>(R.id.rv_messages).layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun sendMessage() {
        val message = findViewById<EditText>(R.id.et_message).text.toString()
        val timeStamp = Time.timeStamp()



        if (message.isNotEmpty()) {
            //Adds it to our local list
            if(message=="demo"){
                demo_mode=true
                demo_conter=1
                findViewById<EditText>(R.id.et_message).setText(demolist[0])
            }
            else{
                messagesList.add(Message(message, SEND_ID, timeStamp))
                findViewById<EditText>(R.id.et_message).setText("")

                adapter.insertMessage(Message(message, SEND_ID, timeStamp))
                findViewById<RecyclerView>(R.id.rv_messages).scrollToPosition(adapter.itemCount - 1)

                botResponse(message)
            }

        }
       /* else if(demo_mode){
            if(demo_conter == demolist.size){
                demo_mode = false
                demo_conter =0
            }
            else{
                findViewById<EditText>(R.id.et_message).setText(demolist[demo_conter])
                demo_conter += 1
            }

        }*/
    }
    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            //Fake response delay
            delay(1000)

            withContext(Dispatchers.Main) {
                val client = OkHttpClient()
                val urlBuilder = "https://s0854006.lionfree.net/nlu/nlu.php/${message}".toHttpUrlOrNull()
                    ?.newBuilder()
                val url = urlBuilder?.build().toString()
                val request = Request.Builder()
                    .url(url)
                    .build()


                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }
                    override fun onResponse(call: Call, response_2: Response) {
                        if (response_2.isSuccessful) {
                            val responseBody = response_2.body?.string()
                            println("-----${responseBody}")
                            var response="1"
                            response = responseBody.toString()


                            runOnUiThread {
                                //Adds it to our local list
                                messagesList.add(Message(response, RECEIVE_ID, timeStamp))

                                //Inserts our message into the adapter
                                adapter.insertMessage(
                                    Message(
                                        response.toString(),
                                        RECEIVE_ID,
                                        timeStamp
                                    )
                                )

                                //Scrolls us to the position of the latest message
                                findViewById<RecyclerView>(R.id.rv_messages).scrollToPosition(
                                    adapter.itemCount - 1
                                )
                            }

                            if(demo_mode){
                                Thread.sleep(1500)
                                runOnUiThread {

                                    if(demo_conter == demolist.size){
                                        demo_mode = false
                                        demo_conter =0
                                    }
                                    else{
                                        findViewById<EditText>(R.id.et_message).setText(demolist[demo_conter])
                                        demo_conter += 1
                                    }

                                }
                            }

                        } else {
                            println("Request failed")

                        }
                    }
                })


            }
        }
    }

    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                findViewById<RecyclerView>(R.id.rv_messages).scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun clickEvents() {

        //Send a message
        findViewById<Button>(R.id.btn_send).setOnClickListener {
            sendMessage()
        }

        //Scroll back to correct position when user clicks on text view
        findViewById<EditText>(R.id.et_message).setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    findViewById<RecyclerView>(R.id.rv_messages).scrollToPosition(adapter.itemCount - 1)
                }
            }
        }
    }

    private fun customBotMessage(message: String) {

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))


                findViewById<RecyclerView>(R.id.rv_messages).scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}