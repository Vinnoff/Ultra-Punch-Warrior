package balekouy.industries.punchwarrior.presentation

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.KeyEvent
import android.view.View
import android.view.ViewTreeObserver

open class BaseDialog : AppCompatDialogFragment() {

    companion object {
        private val EXTRA_POSITIVE = BaseDialog::class.java.name + "EXTRA_POSITIVE"

        private val EXTRA_NEGATIVE = BaseDialog::class.java.name + "EXTRA_NEGATIVE"

        fun newInstance(
            positive: String,
            negative: String
        ): BaseDialog {
            val dialog = BaseDialog()
            val bundle = getBaseBundle(positive, negative)
            dialog.arguments = bundle
            return dialog
        }

        fun getBaseBundle(positive: String? = null, negative: String? = null): Bundle {
            val bundle = Bundle()
            bundle.putString(EXTRA_POSITIVE, positive)
            bundle.putString(EXTRA_NEGATIVE, negative)
            return bundle
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
            }
        })
    }

    lateinit var root: View
    private var positive: String? = null
    private var negative: String? = null

    protected var positiveListener: OnPositiveClickListener? = null
    protected var negativeListener: OnNegativeClickListener? = null
    protected var dismissListener: OnDismissListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            positive = it.getString(EXTRA_POSITIVE)
            negative = it.getString(EXTRA_NEGATIVE)
        }
    }

    protected fun safeDialog(): Dialog {
        val dialog = AlertDialog.Builder(activity!!)
            .setOnKeyListener { _, keyCode, _ ->
                return@setOnKeyListener (keyCode == KeyEvent.KEYCODE_BACK)
            }
            .setView(root)
            .create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    fun setOnPositiveClickListener(listener: OnPositiveClickListener) {
        positiveListener = listener
    }

    fun setOnNegativeListener(listener: OnNegativeClickListener) {
        negativeListener = listener
    }

    fun setOnDismissListener(listener: OnDismissListener) {
        dismissListener = listener
    }

    interface OnPositiveClickListener {
        fun onPositiveClick()
    }

    interface OnNegativeClickListener {
        fun onNegativeClick()
    }

    interface OnDismissListener {
        fun onDismiss()
    }

    fun onClickValidate() {
        positiveListener?.onPositiveClick()
        dismiss()
    }

    fun onClickCancel() {
        negativeListener?.onNegativeClick()
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (dismissListener != null) {
            dismissListener!!.onDismiss()
        }
    }
}