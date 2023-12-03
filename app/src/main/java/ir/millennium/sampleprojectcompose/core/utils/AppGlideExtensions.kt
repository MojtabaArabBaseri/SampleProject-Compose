package ir.millennium.sampleprojectcompose.core.utils

import android.content.Context
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

object AppGlideExtensions {

    fun default(): RequestOptions {
        return RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }

    fun mediumRadius(context: Context): RequestOptions {
        return RequestOptions()
            .transform(
                CenterCrop(),
                RoundedCorners(
                    context.resources.getDimensionPixelSize(androidx.cardview.R.dimen.cardview_default_radius)
                )
            )
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }

    fun circle(): RequestOptions {
        return RequestOptions()
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }
}
