package com.autoever.jamanchu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.autoever.jamanchu.R
import com.autoever.jamanchu.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class HomeFragment : Fragment() {
    val users: MutableList<User> = mutableListOf()
    private lateinit var adapter: MyAdapter // 우리가 만든 MyAdapter 전역으로 선언해주기
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        // 그리드 레이아웃 매니저 설정
        val layoutManager = GridLayoutManager(requireContext(), 2) // 열 개수 설정
        recyclerView.layoutManager = layoutManager

        // 데이터 설정
        adapter = MyAdapter(users) // 어댑터 설정
        recyclerView.adapter = adapter

        fetchUsers()

        return view
    }

    // 파이어베이스 유저 리스트 가져오기
    fun fetchUsers() {
        users.clear()
        // Firestore 인스턴스 가져오기
        val db = Firebase.firestore
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val currentUserId = currentUser!!.uid // 현재 사용자 ID 가져오기

        db.collection("users") // "users"는 저장된 사용자 데이터의 컬렉션 이름입니다.
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // 데이터 변환, 문서 ID도 포함
                    val user = document.toObject(User::class.java).copy(id = document.id)
                    // 필요한 작업 수행 (예: 로그 출력, UI 업데이트 등)
                    println("User: ${user.nickname}, Email: ${user.email}, Gender: ${user.gender}, Age: ${user.age}")

                    // 현재 사용자 제외 (ID 비교)
                    if (currentUserId != user.id) {
                        users.add(user)
                    }
                }
                adapter.notifyDataSetChanged() // 어댑터 refresh
            }
            .addOnFailureListener { exception ->
                // 오류 처리
                println("Error getting documents: $exception")
            }
    }
}


class MyAdapter(private val users: List<User>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textViewNick: TextView = itemView.findViewById(R.id.textViewNick)
        val textViewIntroduce: TextView = itemView.findViewById(R.id.textViewIntroduce)
        val buttonFriend: TextView = itemView.findViewById(R.id.textViewFriend)
        val buttonChat: TextView = itemView.findViewById(R.id.textViewChat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false) // 아이템 레이아웃 추가
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.textViewNick.text = user.nickname
        holder.textViewIntroduce.text = user.introduction
    }

    override fun getItemCount() = users.size
}