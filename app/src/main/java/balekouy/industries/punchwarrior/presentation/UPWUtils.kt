package balekouy.industries.punchwarrior.presentation

import android.content.Context
import android.support.v4.content.ContextCompat
import java.util.*

class UPWUtils {
    companion object {
        fun getDrawableFromIdentifier(context: Context, resourceName: String) =
            ContextCompat.getDrawable(
                context,
                context.resources.getIdentifier(resourceName, "drawable", context.packageName)
            )

        fun getRessourceIdFromIdentifier(context: Context, resourceName: String, folder: String = "drawable") =
            context.resources.getIdentifier(resourceName, folder, context.packageName)

        fun getRandomIn(min: Int = 0, max: Int) = Random().nextInt(max - min) + min
    }
}