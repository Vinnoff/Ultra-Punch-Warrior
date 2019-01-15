package balekouy.industries.punchwarrior.presentation

import android.content.Context
import android.support.v4.content.ContextCompat

class UPWUtils {
    companion object {
        fun getDrawableFromIdentifier(context: Context, resourceName: String) =
            ContextCompat.getDrawable(
                context,
                context.resources.getIdentifier(resourceName, "drawable", context.packageName)
            )

        fun getRessourceIdFromIdentifier(context: Context, resourceName: String) =
            context.resources.getIdentifier(resourceName, "drawable", context.packageName)
    }
}