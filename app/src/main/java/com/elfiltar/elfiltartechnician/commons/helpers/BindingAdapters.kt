package com.elfiltar.elfiltartechnician.commons.helpers

import android.graphics.Paint
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.data.local.session.SessionHelper
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("setViewVisible")
    fun setViewVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("setText")
    internal fun setText(textView: TextView, value: String?) {
        var mValue = if (value == null || value.isEmpty() || value.trim { it <= ' ' }
                .isEmpty() || value === "null") "" else value
//        if (mValue.isNullOrEmpty()) {
//            textView!!.visibility = View.GONE
//            return
//        }
        textView.text = mValue
    }

    @JvmStatic
    @BindingAdapter("setHtmlText")
    internal fun setHtmlText(textView: TextView, value: String?) {
        var mValue = if (value == null || value.isEmpty() || value.trim { it <= ' ' }
                .isEmpty() || value === "null") "" else value
//        if (mValue.isNullOrEmpty() || mValue == "0.0" || mValue == "0") {
//            textView!!.visibility = View.GONE
//            return
//        }
        textView.text = mValue
        textView?.text = Html.fromHtml("$mValue")

    }

    @JvmStatic
    @BindingAdapter("midlineText")
    fun setMidlineText(view: TextView, value: String?) {
        var mValue = if (value == null || value.isEmpty() || value.trim { it <= ' ' }
                .isEmpty() || value === "null") "" else value

//        if (mValue.isNullOrEmpty() || mValue == "0.0" || mValue == "0") {
//            view!!.visibility = View.GONE
//            return
//        }

        view?.paintFlags = view?.paintFlags!! or Paint.STRIKE_THRU_TEXT_FLAG
        view.text = value
    }


    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, imageUrl: String?) {
        if (imageUrl.isNullOrEmpty()) {
            view.setImageResource(R.drawable.logo_elfiltar)
            return
        }
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(R.drawable.logo_elfiltar)
            .timeout(20000)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("bindServerDate")
    fun bindServerDate(textView: TextView, dateString: String?) {
        var dateStr = ""
        val sessionHelper = SessionHelper(textView.context)
        if (dateString != null) {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            var date: Date? =
                null //You will get date object relative to server/client timezone wherever it is parsed
            try {
                //  Sat 10 November 2018
                date = dateFormat.parse(dateString)
                val formatter: DateFormat = SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale(sessionHelper.getUserLanguageCode())
                ) //If you need time just put specific format for time like 'HH:mm:ss'
                dateStr = formatter.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        textView.text = dateStr
    }

    @JvmStatic
    @BindingAdapter("bindServerTime")
    fun bindServerTime(textView: TextView, dateString: String?) {
        var dateStr = ""
        val sessionHelper = SessionHelper(textView.context)
        if (dateString != null) {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            var date: Date? =
                null //You will get date object relative to server/client timezone wherever it is parsed
            try {
                //  Sat 10 November 2018
                date = dateFormat.parse(dateString)
                val formatter: DateFormat = SimpleDateFormat(
                    "HH:mm aa",
                    Locale(sessionHelper.getUserLanguageCode())
                ) //If you need time just put specific format for time like 'HH:mm:ss'
                dateStr = formatter.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        textView.text = dateStr
    }


    @JvmStatic
    @BindingAdapter("bindBirthdate")
    fun bindBirthdate(textView: TextView, dateString: String?) {
        var dateStr = ""
        val sessionHelper = SessionHelper(textView.context)
        if (dateString != null) {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            var date: Date? =
                null //You will get date object relative to server/client timezone wherever it is parsed
            try {
                date = dateFormat.parse(dateString)
                val formatter: DateFormat = SimpleDateFormat(
                    "dd/MM/yyyy", Locale.ENGLISH
                ) //If you need time just put specific format for time like 'HH:mm:ss'
                dateStr = formatter.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        textView.text = dateStr
    }

}