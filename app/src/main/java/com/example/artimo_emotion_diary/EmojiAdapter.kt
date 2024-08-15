package com.example.artimo_emotion_diary

import android.content.Context
import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException

import android.content.Intent


class EmojiAdapter(private val context: Context, private val imageList: List<String>) :
    RecyclerView.Adapter<EmojiAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_emoji, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageFileName = imageList[position]

        try {
            // 이미지 파일을 AssetManager를 사용하여 읽어옴
            val assetManager: AssetManager = context.assets
            val inputStream = assetManager.open(imageFileName)
            val drawable = Drawable.createFromStream(inputStream, null)
            holder.imageView.setImageDrawable(drawable)
            inputStream.close()

            // 이미지를 클릭할 때의 동작 설정
            holder.imageView.setOnClickListener {
//                val intent = Intent(context, FullScreenImageActivity::class.java)
//
//                // 이미지 파일 이름을 전달
//                intent.putExtra("imageFileName", imageFileName)
//
//                context.startActivity(intent)
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}






//class EmojiAdapter(private val context: Context, private val imageList: List<String>) :
//    RecyclerView.Adapter<EmojiAdapter.ImageViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        Log.d("EmojiAdapter", "onCreateViewHolder called")
//        val view = LayoutInflater.from(context).inflate(R.layout.item_emoji, parent, false)
//        return ImageViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        val imageFileName = imageList[position]
//        Log.d("EmojiAdapterBind", "Binding image: $imageFileName at position: $position")
//        try {
//            val assetManager: AssetManager = context.assets
//            val inputStream = assetManager.open("emoji/$imageFileName")
//            val drawable = Drawable.createFromStream(inputStream, null)
//            holder.imageView.setImageDrawable(drawable)
//            inputStream.close()
//            Log.d("EmojiAdapterBind", "Successfully loaded image: $imageFileName")
//        } catch (e: IOException) {
//            Log.e("EmojiAdapterBind", "Error loading image: $imageFileName", e)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        val count = imageList.size
//        Log.d("EmojiAdapter7", "Item count: $count")
//        return count
//    }
//
//    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageView)
//        init {
//            Log.d("EmojiAdapter8", "ViewHolder initialized")
//        }
//    }
//}
