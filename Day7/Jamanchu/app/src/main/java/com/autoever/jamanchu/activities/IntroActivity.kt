package com.autoever.jamanchu.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.autoever.jamanchu.R

class IntroActivity : AppCompatActivity() {
    // 이미지 슬라이드 관련 멤버 변수
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ImageAdapter
    private val images = listOf(
        R.drawable.idol2,
        R.drawable.idol3,
        R.drawable.idol4,
        R.drawable.idol5,

    )
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    private val runnable = object : Runnable {
        override fun run() {
            currentPage = (currentPage + 1) % images.size // 다음 페이지로 이동
            viewPager.setCurrentItem(currentPage, true) // 페이지 전환
            handler.postDelayed(this, 3000) // 3초 후에 다시 실행
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // 이미지 슬라이드 뷰페이저
        viewPager = findViewById(R.id.viewPager)
        adapter = ImageAdapter(images)
        viewPager.adapter = adapter

        // 3초 후에 자동 페이징 시작
        handler.postDelayed({
            handler.post(runnable) // 딜레이 후 runnable 실행
        }, 3000) // 3000ms (3초) 딜레이

        // 회원가입 버튼
        val buttonSignup = findViewById<TextView>(R.id.buttonSignUp)
        buttonSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // 로그인 버튼
        // 회원가입 버튼
        val buttonLogin = findViewById<TextView>(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}


class ImageAdapter(private val images: List<Int>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_intro_image, parent, false)
        return ImageViewHolder(view)
    }

    // 실제 동작
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position]) // 이미지 설정
    }

    override fun getItemCount(): Int = images.size

}