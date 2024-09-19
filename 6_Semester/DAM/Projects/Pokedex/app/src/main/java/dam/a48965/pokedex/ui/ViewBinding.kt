package dam.a48965.pokedex.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.transition.Transition
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.google.android.material.card.MaterialCardView


object ViewBinding {
    @JvmStatic
    @BindingAdapter("android:src", "isToSetBackground")
    fun setRegionImage(imageView: AppCompatImageView, regionName: String, isToSetBackground: Boolean) {
        try {
            val drawableResId = if (isToSetBackground) {
                val regionImageUri = "bg_${regionName.lowercase()}"
                imageView.context.resources.getIdentifier(regionImageUri, "drawable", imageView.context.packageName)
            } else {
                val regionImageUri = "pk_${regionName.lowercase()}"
                imageView.context.resources.getIdentifier(regionImageUri, "drawable", imageView.context.packageName)
            }

            if (drawableResId != 0) {
                imageView.setImageResource(drawableResId)
            } else {
                // Handle case where resource is not found
                Log.e("setRegionImage", "Drawable resource not found for region: $regionName")
            }
        } catch (e: Exception) {
            // Handle any other exceptions
            Log.e("setRegionImage", "Error loading drawable resource", e)
        }
    }


    @JvmStatic
    @BindingAdapter("paletteImage","paletteCard")
    fun bindLoadImagePaletteView(view: AppCompatImageView, url: String, paletteView: MaterialCardView) {
        val context = view.context
        Glide.with(view.context)
            .asBitmap()
            .load(url)
            .listener(object : RequestListener<Bitmap>
            {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {

                    Log.d("TAG", e?.message.toString())
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    p2: Target<Bitmap>?,
                    dataSource: DataSource,
                    p4: Boolean
                ): Boolean {
                    Log.d("TAG", "OnResourceReady")

                    if (resource != null) {
                        val p: Palette = Palette.from(resource).generate()

                        val rgb = p?.lightMutedSwatch?.rgb

                        val light = p?.lightVibrantSwatch?.rgb
                        val domain = p?.dominantSwatch?.rgb

                        if (domain != null) {
                            if (light != null) {

                                val gfgGradient = GradientDrawable(
                                    GradientDrawable.Orientation.TOP_BOTTOM,
                                    intArrayOf(
                                        domain,
                                        light
                                    ))

                                paletteView.background = gfgGradient
                            } else {
                                if (rgb != null) {
                                    paletteView.setBackgroundColor(rgb)
                                }
                            }
                        }
                    }
                    return false
                }
            })
            .into(view)
        paletteView.clipChildren = true
        paletteView.clipToPadding = true
    }

    @JvmStatic
    @BindingAdapter("paletteImage", "paletteLinearLayout")
    fun bindLoadImagePaletteView(linearLayout: LinearLayout, url: String, paletteView: MaterialCardView) {
        Glide.with(linearLayout.context)
            .asBitmap()
            .load(url)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("Palette", "Image load failed")
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    target: Target<Bitmap>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("Palette", "Resource ready")
                    val palette = Palette.from(resource).generate()
                    val dominantSwatch = palette.dominantSwatch
                    dominantSwatch?.let {
                        val color = it.rgb
                        paletteView.setCardBackgroundColor(color)
                        linearLayout.setBackgroundColor(color) // Set LinearLayout's background color here
                    }
                    return true // Return true to indicate that the request was handled successfully
                }
            })
            .submit()
    }



}
