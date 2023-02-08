package com.elfiltar.elfiltartechnician.commons.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Handler
import android.provider.Settings
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bobgenix.datetimedialogkt.UnifiedDateTimePicker
import com.elfiltar.elfiltartechnician.R
import com.elfiltar.elfiltartechnician.commons.models.BaseErrorModel
import com.elfiltar.elfiltartechnician.commons.models.ErrorResultModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import www.sanju.motiontoast.MotionToast
import java.io.IOException
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class MyUtils {


    companion object {
        // parse error body
        fun parseResponse(response: Response<Any>): BaseErrorModel? {
            var errorMessage = BaseErrorModel(0, "")
            var errorModel: ErrorResultModel
            try {
                val jsonObject = JSONObject(response.errorBody()!!.string())
                errorModel = if (jsonObject != null) {
                    var errorMessage = jsonObject.getString("message")
                    ErrorResultModel(errorMessage)
                    //                    errorModel =
                    //                        Gson().fromJson(errorObject.toString(), ErrorResultModel::class.java)
                } else {
                    ErrorResultModel("Error in api response!")
                }
                if (errorModel!!.status != null)
                    errorModel!!.status = -1
                else
                    errorModel!!.message = errorModel.message!!

                if (errorModel!!.message.isNullOrEmpty())
                    errorModel!!.message = ""
                else
                    errorModel!!.message = errorModel.message!!

                errorMessage.message = errorModel.message
                errorMessage.status = errorModel.status
            } catch (e: JSONException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return errorMessage
        }

        //Get Device Id
        fun getDeviceId(context: Context): String? {
            return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }

        //ColorTintList
        fun getColorTintList(context: Context, color: Int): ColorStateList? {
            return ColorStateList.valueOf(context.resources.getColor(color))
        }

        //setTextViewDrawable
        fun setTextViewDrawable(textView: TextView, start: Int, end: Int) {
            textView.compoundDrawables[0]
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, 0, end, 0)
        }

        fun autoScrollRecycler(recyclerView: RecyclerView?, dataListSize: Int) {
            val speedScroll = 3500
            val handler = Handler()
            val runnable: Runnable = object : Runnable {
                var count = 0
                override fun run() {
                    if (recyclerView != null) {
                        if (count == dataListSize) {
                            count = 0
                        }
                        if (count < dataListSize) {
                            recyclerView.smoothScrollToPosition(count)
                            count++
                            handler.postDelayed(this, speedScroll.toLong())
                        }
                    }
                }
            }
            handler.postDelayed(runnable, speedScroll.toLong())
        }


        fun shoMsg(context: Activity, msg: String, type: String) {
            MotionToast.createColorToast(
                context,
                context.getString(R.string.app_name),
                msg,
                type,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION, null
            )
        }

        fun executeDelay(milliseconds: Long, onExecute: () -> Unit) {
            Handler().postDelayed({
                onExecute()
            }, milliseconds)
        }


        fun setLocale(lang: String, context: Context): Context? {
            val locale = Locale(lang)
            Locale.setDefault(locale)
            val resources = context.resources
            val configuration = resources.configuration
            configuration.setLocale(locale)
            //configuration.locale = locale;
            resources.updateConfiguration(configuration, resources.displayMetrics)
            return context
        }

        fun share(context: Context, url: String) {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
                val shareMessage = "" + url
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                context.startActivity(
                    Intent.createChooser(
                        shareIntent,
                        ""
//                        context.getString(R.string.share_with)
                    )
                )
            } catch (e: Exception) {
                //e.toString();
            }
        }

        fun callPhoneNumber(context: Context, phoneNumber: String) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            context.startActivity(intent)
        }

        fun openWhatsApp(context: Context, phoneNumber: String) {
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            context.startActivity(i)
        }

        fun openUrl(context: Context, link: String) {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            context.startActivity(myIntent)
        }


        fun formatDate(date: Date): String {
            var dateStr = ""
            try {
                val formatter: DateFormat = SimpleDateFormat(
                    "yyyy-MM-dd", Locale.ENGLISH
                ) //If you need time just put specific format for time like 'HH:mm:ss'
                dateStr = formatter.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return dateStr
        }

        fun formatTime(date: Date): String {
            var dateStr = ""
            try {
                val formatter: DateFormat = SimpleDateFormat(
                    "HH:mm", Locale.ENGLISH
                ) //If you need time just put specific format for time like 'HH:mm:ss'
                dateStr = formatter.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return dateStr
        }

        // This method  converts String to RequestBody
        fun toRequestBody(value: Any): RequestBody {
            return RequestBody.create("text/plain".toMediaTypeOrNull(), value.toString())
        }

        fun toRequestBodyMap(map: HashMap<String, Any>): HashMap<String, RequestBody> {
            var requestBodyMap = HashMap<String, RequestBody>()
            map.mapKeys {
                requestBodyMap[it.key] = toRequestBody(it.value)
            }
            return requestBodyMap
        }

        fun showIosDateTimePicker(context: Context, onDateSelected: (Date) -> Unit) {
//            val dateTimeSelectedListener = object :
//                OnDateTimeSelectedListener {
//                override fun onDateTimeSelected(selectedDateTime: Calendar) {
//                    //This is the calendar reference of selected date and time.
//                    //We can format the date time as we need here.
//                    println("Selected date ${selectedDateTime.time}")
//                    onDateSelected(selectedDateTime.time)
//                }
//
//            }
//            val dateTimePickerDialog = DialogDateTimePicker(
//                context, //context
//                Calendar.getInstance(), //start date of calendar
//                12, //No. of future months to shown in calendar
//                dateTimeSelectedListener,
//                "Select date and time"
//            ) //Dialog title
//            dateTimePickerDialog.setTitleTextColor(android.R.color.black)
//
//            dateTimePickerDialog.setDividerBgColor(android.R.color.black)
//            dateTimePickerDialog.setFontSize(14)
//            dateTimePickerDialog.setCenterDividerHeight(38)
//            dateTimePickerDialog.show()

            UnifiedDateTimePicker.Builder(context)
                .titleTextColor(R.color.text_color_black)
                .backgroundColor(R.color.background_screen)
                .dateTimeTextColor(R.color.text_color_black)
                .buttonColor(R.color.primary)
                .buttonTextColor(R.color.white)
                .vibration(false)
                .addListener(object : com.bobgenix.datetimedialogkt.OnDateTimeSelectedListener {
                    override fun onDateTimeSelected(millis: Long) {
                        val calendar: Calendar = Calendar.getInstance()
                        calendar.timeInMillis = millis
                        onDateSelected(calendar.time)
                    }
                })
                .show()
//            object : OnDateTimeSelectedListener {
//                override fun onDateTimeSelected(millis: Long) {
//
//                }
//
//                override fun onPickerDismissed(millis: Long) {
//                    /* no use as of now */
//                }
//            }
        }

    }

}