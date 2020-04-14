package com.luiz.notes.presentation.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.AnimRes
import com.luiz.notes.R
import com.luiz.notes.presentation.NotesApplication.Companion.context
import com.luiz.notes.presentation.adapter.NoResultAdapter
import java.util.*

object Utils {
    private var dialog: Dialog? = null

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    inline fun <reified T : Activity> Context.openActivity(
        options: Bundle? = null,
        finishWhenOpen: Boolean = false,
        @AnimRes enterAnim: Int = R.anim.activity_slide_pop_vertical_open_in,
        @AnimRes exitAnim: Int = R.anim.activity_slide_pop_vertical_open_out,
        noinline f: Intent.() -> Unit = {}
    ) {

        val intent = Intent(this, T::class.java)
        intent.f()
        startActivity(intent, options)
        if (finishWhenOpen) (this as Activity).finish()
        (this as Activity).overridePendingTransition(enterAnim, exitAnim)
    }

    @SuppressLint("ObsoleteSdkInt")
    fun loadingDialog(ctx: Context): Dialog {
        val loading = Dialog(ctx)
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loading.setContentView(R.layout.dialog_loading)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull<Window>(loading.window)
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        loading.setCanceledOnTouchOutside(false)
        loading.setCancelable(false)
        return loading
    }

    fun onStartLoading(context: Context) {
        dialog = loadingDialog(context)
        dialog!!.show()
    }

    fun onStopLoading() {
        if (dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    fun noResultAdapter(context: Context, message: String, image: Int): NoResultAdapter {
        return NoResultAdapter(
            context,
            message,
            R.color.colorGreyAdapter,
            image,
            0
        )
    }

    fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}