package com.example.flikster

import android.R.attr
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import android.R.attr.radius
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val TAG = "MovieAdapter"
class MovieAdapter(private val context: Context, private val movies: List<Movie>, val orientation: Int)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val ivBackdrop = itemView.findViewById<ImageView>(R.id.ivBackdrop)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(context)
                    .load(movie.posterImageUrl)
                    .centerCrop()
                    .transform(RoundedCornersTransformation(30, 10))
                    .into(ivPoster)
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Glide.with(context).load(movie.backdropImageUrl).into(ivBackdrop)
            }
        }

        override fun onClick(v: View?) {
            val movie = movies[adapterPosition]

            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movie)
            val titlePair = Pair.create(tvTitle as View, "movieTitle")
            val overviewPair = Pair.create(tvOverview as View, "movieOverview")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity,
                titlePair,
                overviewPair
            )
            context.startActivity(intent, options.toBundle())
        }
    }
}
