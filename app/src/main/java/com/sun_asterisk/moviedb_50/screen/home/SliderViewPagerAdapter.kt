package com.sun_asterisk.moviedb_50.screen.home

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.source.remote.GetImageAsyncTask
import com.sun_asterisk.moviedb_50.utils.Constant
import com.sun_asterisk.moviedb_50.utils.OnClickListener
import com.sun_asterisk.moviedb_50.utils.OnFetchImageListener
import kotlinx.android.synthetic.main.item_slide.view.*

class SliderViewPagerAdapter(
    private val context: Context,
    private val list: List<Movie>
) : PagerAdapter() {
    private lateinit var slideItemClickListener: OnClickListener<Movie>

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater =
            container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val slideLayout = inflater.inflate(R.layout.item_slide, null)
        slideLayout.youtubeImageView.setOnClickListener {
            slideItemClickListener.click(
                list[position]
            )
        }
        getImageSlide(slideLayout.slideImageView, this.list[position])
        slideLayout.titleSlideTextView.text = list[position].movieTitle
        container.addView(slideLayout)
        return slideLayout
    }

    private fun getImageSlide(
        img: ImageView,
        movie: Movie?
    ) {
        GetImageAsyncTask(object : OnFetchImageListener {

            override fun onImageError(e: Exception?) {
                e?.printStackTrace()
            }

            override fun onImageLoaded(bitmap: Bitmap?) {
                bitmap?.let { img.setImageBitmap(bitmap) }
            }
        }).execute(Constant.BASE_URL_IMAGE + movie?.movieBackdropPath)
    }

    override fun getCount(): Int = list.size

    override fun isViewFromObject(view: View, o: Any): Boolean = view === o

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        obj: Any
    ) {
        container.removeView(obj as View)
    }
}
