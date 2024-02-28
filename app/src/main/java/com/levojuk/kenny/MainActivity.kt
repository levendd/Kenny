package com.levojuk.kenny

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.levojuk.kenny.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var runnable = Runnable{}
    var handler = Handler(Looper.getMainLooper())
    var score = 0
    var time= 0
    var check =10

    var imageArray = ArrayList<ImageView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        imageArray.add(binding.imageView)
        imageArray.add(binding.imageView1)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)

        var game = intent.getBooleanExtra("restart",false)
        println("gelen veri : $game")
        if (!game){
            val start : AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
            start
                .setMessage("Are u Ready")
                .setPositiveButton("I'm ready"){ dialogInterface: DialogInterface, i: Int ->
                    hideImages()
                    object : CountDownTimer(15000,1000){
                        override fun onTick(p0: Long) {
                            binding.textTime.text = "Time : ${p0/1000}"
                            time = p0.toInt()
                        }

                        override fun onFinish() {
                            Toast.makeText(this@MainActivity,"Score: $score",Toast.LENGTH_LONG).show()
                            handler.removeCallbacks(runnable )
                            val builder : AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                            builder
                                .setMessage("Your Score : $score")
                                .setTitle("GAME OVER")
                                .setPositiveButton("Restart",object : DialogInterface.OnClickListener{
                                    override fun onClick(dialog: DialogInterface?, which: Int) {
                                        val intentFromMain = Intent(this@MainActivity,MainActivity::class.java)

                                        finish()
                                        intentFromMain.putExtra("restart",true)
                                        startActivity(intentFromMain)
                                    }
                                })
                                .setNegativeButton("EXIT",object : DialogInterface.OnClickListener{
                                    override fun onClick(dialog: DialogInterface?, which: Int) {
                                        finish()
                                    }
                                })
                            builder.show()
                        }
                    }.start()}
                .setNegativeButton("No I'm chicken"){ dialogInterface: DialogInterface, i: Int -> finish()}
            start.show()

        }
        else if (game) {
            hideImages()
            object : CountDownTimer(15000,1000){
                override fun onTick(p0: Long) {
                    binding.textTime.text = "Time : ${p0/1000}"
                    time = p0.toInt()
                }

                override fun onFinish() {
                    Toast.makeText(this@MainActivity,"Score: $score",Toast.LENGTH_LONG).show()
                    handler.removeCallbacks(runnable )
                    val builder : AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                    builder
                        .setMessage("Your Score : $score")
                        .setTitle("GAME OVER")
                        .setPositiveButton("Restart",object : DialogInterface.OnClickListener{
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                val intentFromMain = Intent(this@MainActivity,MainActivity::class.java)

                                finish()
                                intentFromMain.putExtra("restart",true)
                                startActivity(intentFromMain)
                            }
                        })
                        .setNegativeButton("EXIT",object : DialogInterface.OnClickListener{
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                finish()
                            }
                        })
                    builder.show()
                }
            }.start()
        }



    }

    fun upScore(view: View){
       if (time >1000 ){
           score ++
           binding.textScore.text= "Score $score"
           println("TİME : $time")
       }
    }
    fun hideImages(){
        runnable = object :Runnable {
            override fun run() {
                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }
                val random = Random()

                var randomIndex = random.nextInt(9)

                    if (randomIndex == check){
                        if(check==8){randomIndex --}
                        else if (check==0){randomIndex++}
                        else (randomIndex++)

                    }
                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable,500)
                check = randomIndex
            }
        }
        handler.post(runnable)

    }
}

