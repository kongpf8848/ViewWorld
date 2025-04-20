package com.github.kongpf8848.viewworld.activity

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.databinding.ActivityAvatarBinding
import com.github.kongpf8848.viewworld.views.CheckColorView


class SelectAvatarActivity : BaseActivity<ActivityAvatarBinding>() {

    val colors = listOf(
        "#6666CC",
        "#66CCFF",
        "#66CCCC",
        "#CCCCFF",
        "#0066CC",
        "#66CC00",
        "#CC0033",
        "#FF33CC",
        "#FFCC00",
        "#CC99CC"
    )
    val avatars = listOf(
        R.mipmap.icon_cartoon_avatar_1, R.mipmap.icon_cartoon_avatar_2,
        R.mipmap.icon_cartoon_avatar_3, R.mipmap.icon_cartoon_avatar_4,
        R.mipmap.icon_cartoon_avatar_5, R.mipmap.icon_cartoon_avatar_6,
        R.mipmap.icon_cartoon_avatar_7, R.mipmap.icon_cartoon_avatar_8,
        R.mipmap.icon_cartoon_avatar_9, R.mipmap.icon_cartoon_avatar_10,
        R.mipmap.icon_cartoon_avatar_11, R.mipmap.icon_cartoon_avatar_12
    )
    var colorIndex = -1
    var imageIndex = -1

    override fun getLayoutId(): Int {
        return R.layout.activity_avatar
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.rvColor.layoutManager =
            GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false)
        binding.rvColor.adapter = ColorAdapter(object : OnClickListener {
            override fun onItemClick(position: Int) {
                if (colorIndex != position) {
                    colorIndex = position
                    binding.rvColor.adapter?.notifyDataSetChanged()
                    binding.chvAvatar.setData("p", Color.parseColor(colors[colorIndex]))
                    if (imageIndex != -1) {
                        imageIndex = -1
                        binding.rvImage.adapter?.notifyDataSetChanged()
                    }
                }
            }
        })

        binding.rvImage.layoutManager =
            GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false)
        binding.rvImage.adapter = ImageAdapter(object : OnClickListener {
            override fun onItemClick(position: Int) {
                if (imageIndex != position) {
                    imageIndex = position
                    binding.rvImage.adapter?.notifyDataSetChanged()
                    binding.chvAvatar.setImage(avatars[imageIndex])
                    if (colorIndex != -1) {
                        colorIndex = -1
                        binding.rvColor.adapter?.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    inner class ColorAdapter(private val listener: OnClickListener) :
        RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
            return ColorViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
            holder.itemView.apply {
                val vColor = findViewById<CheckColorView>(R.id.vColor)
                val ivColorCheck = findViewById<ImageView>(R.id.ivColorCheck)
                vColor.setColor(colors[position])
                if (colorIndex == position) {
                    ivColorCheck.visibility = View.VISIBLE
                } else {
                    ivColorCheck.visibility = View.GONE
                }

                setOnClickListener {
                    listener.onItemClick(position)
                }
            }
        }

        override fun getItemCount(): Int {
            return colors.size
        }

        inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    }

    inner class ImageAdapter(private val listener: OnClickListener) :
        RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            return ImageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            holder.itemView.apply {
                val ivImage = findViewById<ImageView>(R.id.ivImage)
                val ivCheck = findViewById<ImageView>(R.id.ivCheck)
                Glide.with(this).load(avatars[position]).centerCrop().into(ivImage)
                if (imageIndex == position) {
                    ivCheck.visibility = View.VISIBLE
                } else {
                    ivCheck.visibility = View.GONE
                }

                setOnClickListener {
                    listener.onItemClick(position)
                }
            }
        }

        override fun getItemCount(): Int {
            return avatars.size
        }

        inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    }
}