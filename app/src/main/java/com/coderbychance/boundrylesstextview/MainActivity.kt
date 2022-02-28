package com.coderbychance.boundrylesstextview

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {


    companion object {
        private const val TAG = "MainActivity"
    }

    lateinit var congestedTextView: TextView
    lateinit var ll_layout: LinearLayout
    val bounds = Rect()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        congestedTextView = findViewById(R.id.conjustedTextView)
        ll_layout = findViewById(R.id.ll_layout)
        /*val bounds = Rect()
        congestedTextView.paint.getTextBounds(
            congestedTextView.text.toString(),
            0,
            congestedTextView.text.length,
            bounds
        )

        createBox(congestedTextView,bounds)


        Log.d(
            TAG, "onCreate: height: ${bounds.height().toFloat().toDp(this)}, width: ${
                bounds.width().toFloat().toDp(
                    this
                )
            }, left -> ${bounds.left}, right -> ${bounds.right}, top -> ${bounds.top}, bottom -> ${bounds.bottom}"
        )


        congestedTextView.getLocalVisibleRect(bounds);
        val location = congestedTextView.getLocationOnScreen()
        val absX = location.x
        val absY = location.y

        Log.d("SIZE: WIDTH          :", (bounds.width()).toString())
        Log.d("SIZE: HEIGHT       :", (bounds.height()).toString())
        Log.d("SIZE: left         :", (bounds.left).toString())
        Log.d("SIZE: right        :", (bounds.right).toString())
        Log.d("SIZE: top          :", (bounds.top).toString())
        Log.d("SIZE: bottom       :", (bounds.bottom).toString())
        Log.d("SIZE: x            :", absX.toString())
        Log.d("SIZE: y            :", absY.toString())
*/


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
//        {
            drawTextBounds(congestedTextView)

            createBox(congestedTextView, bounds)
//        }
    }

    private fun getWidth(): Int {
        val bounds = Rect()
        congestedTextView.paint.getTextBounds(
            congestedTextView.text.toString(),
            0,
            congestedTextView.text.length,
            bounds
        )
        return bounds.width()
    }

//    @RequiresApi(Build.VERSION_CODES.Q)
    private fun drawTextBounds(textView: TextView) {

        textView.measure(0, 0)
        val s = textView.text.toString()

        // bounds will store the rectangle that will circumscribe the text.

        // bounds will store the rectangle that will circumscribe the text.
        val textPaint: Paint = textView.paint

        // Get the bounds for the text. Top and bottom are measured from the baseline. Left
        // and right are measured from 0.

        // Get the bounds for the text. Top and bottom are measured from the baseline. Left
        // and right are measured from 0.
        textPaint.getTextBounds(s, 0, s.length, bounds)
        val baseline: Int = textView.baseline
        val top = baseline + bounds.top
        bounds.top = top
        bounds.bottom = baseline + bounds.bottom
        val startPadding: Int = textView.paddingStart
        val left = bounds.left + startPadding
        bounds.left = left
        Log.d(TAG, "drawTextBounds: ${bounds.top} | ${bounds.left}")

        // textPaint.getTextBounds() has already computed a value for the width of the text,
        // however, Paint#measureText() gives a more accurate value.
        bounds.right = textPaint.measureText(s, 0, s.length).toInt() + startPadding

        // At this point, (x, y) of the text within the TextView is (bounds.left, bounds.top)
        // Draw the bounding rectangle.

        // At this point, (x, y) of the text within the TextView is (bounds.left, bounds.top)
        // Draw the bounding rectangle.
    }

    private fun createBox(textView: TextView, bounds: Rect) {

        textView.measure(0, 0)
        val bitmap = Bitmap.createBitmap(
            textView.measuredWidth,
            textView.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        val rectPaint = Paint()
        rectPaint.color = Color.RED
        rectPaint.style = Paint.Style.STROKE
        rectPaint.strokeWidth = 1f


//        canvas.drawRect(bounds, rectPaint)


        val params: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )


        val outContainer: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

        outContainer.width = bounds.width()
        outContainer.height = bounds.height()

        ll_layout.layoutParams = outContainer

        params.setMargins(-bounds.left, -bounds.top - 1, 0, 0)
        congestedTextView.layoutParams = params

        textView.foreground = BitmapDrawable(resources, bitmap)
    }
}

private fun Float.toDp(context: Context): Float {
    return this / context.resources.displayMetrics.density
}

fun TextView.charLocation(offset: Int): Point? {
    layout ?: return null // Layout may be null right after change to the text view

    val lineOfText = layout.getLineForOffset(offset)
    val xCoordinate = layout.getPrimaryHorizontal(offset).toInt()
    val yCoordinate = layout.getLineTop(lineOfText)
    return Point(xCoordinate, yCoordinate)
}

fun View.getLocationOnScreen(): Point {
    val location = IntArray(2)
    this.getLocationOnScreen(location)
    return Point(location[0], location[1])
}


