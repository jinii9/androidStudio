package com.seojin.fishbread

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.seojin.fishbread.models.Bread
import com.seojin.fishbread.models.FishBread
import com.seojin.fishbread.models.FlowerBread

class MainActivity : AppCompatActivity() {
//    var bread = Bread()
    val breadList: MutableList<Bread> = mutableListOf() // 빵 객체 리스트

    private lateinit var getResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val imageView = findViewById<ImageView>(R.id.imageView0)
        // 이미지뷰 레이아웃 ID를 배열로 저장
        val numberImageViews = arrayOf(
            R.id.imageView0, R.id.imageView1, R.id.imageView2, R.id.imageView3,
            R.id.imageView4, R.id.imageView5, R.id.imageView6, R.id.imageView7,
            R.id.imageView8
        )
        val imageViews: ArrayList<ImageView> = ArrayList() // 이미지뷰들의 리스트
        for (id in numberImageViews) {
            val imageView = findViewById<ImageView>(id) // 각 ID에 해당하는 ImageView 
            imageViews.add(imageView) // ArrayList에 추가
        }

        // 텍스트뷰 레이아웃 ID를 배열로 저장
        val numberTextViews = arrayOf(
            R.id.textView0, R.id.textView1, R.id.textView2, R.id.textView3,
            R.id.textView4, R.id.textView5, R.id.textView6, R.id.textView7,
            R.id.textView8
        )
        val textViews: ArrayList<TextView> = ArrayList() // 텍스트뷰들의 리스트
        for (id in numberTextViews) {
            val textView = findViewById<TextView>(id)
            textViews.add(textView)
        }


        getResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            // 결과값으로 Bread 객체를 만든다.
            val data: Intent? = result.data
            val shape = data?.getStringExtra("shape")
            val sauce = data?.getStringExtra("sauce")
            var bread = Bread()
            bread = if (shape == "붕어빵") FishBread() else FlowerBread()
            bread.shape = shape!!
            bread.sauce = sauce!!
            breadList.add(bread) // 빵 리스트에 추가


            // breadList를 통해 빵 진열
//            imageViews.clear()
            for ((i ,bread) in breadList.withIndex()) {
                // 이미지뷰에 만든 빵 이미지 셋팅
                when (bread.shape) {
                    "붕어빵" -> imageViews[i].setImageResource(R.drawable.fish)
                    "국화빵" -> imageViews[i].setImageResource(R.drawable.flower)
                }
                // 소스 텍스트뷰에 출력
                textViews[i].text = bread.sauce
            }

        }

        val buttonMake = findViewById<Button>(R.id.buttonMake)
        buttonMake.setOnClickListener {
            val intent = Intent(this, MoldActivity::class.java)
//            startActivity(intent)
            getResult.launch(intent)
        }

    }
}