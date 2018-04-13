package com.changjiashuai.cornerview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.LinearLayout

class CornerView : LinearLayout {

    enum class Corner {
        LEFT_TOP,
        LEFT_BOTTOM,
        RIGHT_TOP,
        RIGHT_BOTTOM,
        NONE
    }

    // 触摸点（卷起点）
    private var mCurl = PointF()
    // 页角点（四个角）
    private var mCorner = PointF()
    // 触摸与页角的中点
    private var mMiddle = PointF()

    // 与X轴的交点
    private var mCrossX = PointF()
    // 与Y轴的交点
    private var mCrossY = PointF()

    // 表示页脚
    private var mFooter = Corner.NONE

    // 全部掀起的Path
    private var mAllPath = Path()
    // 掀起背面的Path
    private var mPartPath = Path()

    private var mPaint = Paint()

    private var mBorderWidth = dp2px(4f)
    private var mBorderColor = resources.getColor(R.color.mandy)

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var mTriangleColor = resources.getColor(R.color.mandy)
    private var mTriangleSideA = dp2px(20f)
    private var mTriangleSideB = dp2px(20f)

    var borderWidth: Float
        get() = mBorderWidth
        set(value) {
            mBorderWidth = value
            mPaint.strokeWidth = mBorderWidth
        }

    var borderColor: Int
        get() = mBorderColor
        set(value) {
            mBorderColor = value
            mPaint.color = mBorderColor
        }

    var triangleColor: Int
        get() = mTriangleColor
        set(value) {
            mTriangleColor = value
        }

    var triangleSideA: Float
        get() = mTriangleSideA
        set(value) {
            mTriangleSideA = value
        }

    var triangleSideB: Float
        get() = mTriangleSideB
        set(value) {
            mTriangleSideB = value
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

        attrs?.let {
            val attributes =
                context.obtainStyledAttributes(attrs, R.styleable.CornerView, defStyle, 0)

            mBorderColor = attributes.getColor(R.styleable.CornerView_borderColor, mBorderColor)
            mBorderWidth = attributes.getDimension(R.styleable.CornerView_borderWidth, mBorderWidth)

            mTriangleColor =
                    attributes.getColor(R.styleable.CornerView_triangleColor, mTriangleColor)
            mTriangleSideA = attributes.getDimension(
                R.styleable.CornerView_triangleSideA,
                mTriangleSideA
            )
            mTriangleSideA = attributes.getDimension(
                R.styleable.CornerView_triangleSideB,
                mTriangleSideB
            )
            attributes.recycle()
        }

        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mPaint.color = mBorderColor
        mPaint.strokeWidth = mBorderWidth
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)

        drawBorderLine(canvas)

        mCurl.x = mWidth - mTriangleSideA
        mCurl.y = mHeight - mTriangleSideB

        calCorner()
        calPoints()

        if (mFooter == Corner.NONE) {
            return
        }
        transPath()
        canvas.clipPath(mAllPath)
        canvas.drawColor(Color.WHITE)
        canvas.clipPath(mPartPath, Region.Op.INTERSECT)
        canvas.drawColor(mTriangleColor)
    }

    private fun drawBorderLine(canvas: Canvas) {
        val x = 0f

        canvas.drawLine(x, 0f, x + mWidth, 0f, mPaint)//top
        canvas.drawLine(x, mHeight.toFloat(), x + mWidth, mHeight.toFloat(), mPaint)//bottom
        canvas.drawLine(x, 0f, 0f, mHeight.toFloat(), mPaint) //left
        canvas.drawLine(x + mWidth, 0f, x + mWidth, mHeight.toFloat(), mPaint)//right
    }

    /**
     * 转换路径
     */
    private fun transPath() {
        mAllPath.reset()
        mAllPath.moveTo(mCurl.x, mCurl.y)
        mAllPath.lineTo(mCrossX.x, mCrossX.y)
        mAllPath.lineTo(mCorner.x, mCorner.y)
        mAllPath.lineTo(mCrossY.x, mCrossY.y)
        mAllPath.close()
        mPartPath.reset()
        mPartPath.moveTo(mCurl.x, mCurl.y)
        mPartPath.lineTo(mCrossX.x, mCrossX.y)
        mPartPath.lineTo(mCrossY.x, mCrossY.y)
        mPartPath.close()
    }

    /**
     * 计算页角
     */
    private fun calCorner() {
        if (mCurl.x < width / 2 && mCurl.y < height / 2) {
            mFooter = Corner.LEFT_TOP
            mCorner.x = 0f
            mCorner.y = 0f
        } else if (mCurl.x < width / 2 && mCurl.y >= height / 2) {
            mFooter = Corner.LEFT_BOTTOM
            mCorner.x = 0f
            mCorner.y = height.toFloat()
        } else if (mCurl.x >= width / 2 && mCurl.y < height / 2) {
            mFooter = Corner.RIGHT_TOP
            mCorner.x = width.toFloat()
            mCorner.y = 0f
        } else if (mCurl.x >= width / 2 && mCurl.y >= height / 2) {
            mFooter = Corner.RIGHT_BOTTOM
            mCorner.x = width.toFloat()
            mCorner.y = height.toFloat()
        } else {
            mFooter = Corner.NONE
        }
    }

    /**
     * 计算点
     */
    private fun calPoints() {
        mMiddle.x = (mCurl.x + mCorner.x) / 2
        mMiddle.y = (mCurl.y + mCorner.y) / 2
        mCrossX.x = mMiddle.x - (mCorner.y - mMiddle.y) * (mCorner.y - mMiddle.y) /
                (mCorner.x - mMiddle.x)
        mCrossX.y = mCorner.y
        mCrossY.x = mCorner.x
        mCrossY.y = mMiddle.y - (mCorner.x - mMiddle.x) * (mCorner.x - mMiddle.x) /
                (mCorner.y - mMiddle.y)
    }
}