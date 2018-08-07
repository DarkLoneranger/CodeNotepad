

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;


/**
 * 会话中展示图片
 */
public class BubbleImageView extends AppCompatImageView {

    private static final int LOCATION_LEFT = 0;
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 1;
    private ColorFilter cf;
    private int mAngle = dp2px(10);
    private int mArrowTop = dp2px(40);
    private int mArrowWidth = dp2px(20);
    private int mArrowHeight = dp2px(20);
    private int mArrowOffset = 0;
    private int mArrowLocation = LOCATION_LEFT;

    private Rect mDrawableRect;
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private Paint mBitmapPaint;
    private Matrix mShaderMatrix;
    private int mBitmapWidth;
    private int mBitmapHeight;

    public BubbleImageView(Context context) {
        super(context);
        initView(null);
    }

    public BubbleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(attrs);
    }

    public BubbleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BubbleImageView);
            //弧形所在圆形的半径
            mAngle = (int) a.getDimension(R.styleable.BubbleImageView_bubble_angle, mAngle);
            mArrowHeight = (int) a.getDimension(R.styleable.BubbleImageView_bubble_arrowHeight, mArrowHeight);
            mArrowOffset = (int) a.getDimension(R.styleable.BubbleImageView_bubble_arrowOffset, mArrowOffset);
            mArrowTop = (int) a.getDimension(R.styleable.BubbleImageView_bubble_arrowTop, mArrowTop);
            mArrowWidth = (int) a.getDimension(R.styleable.BubbleImageView_bubble_arrowWidth, mAngle);
            mArrowLocation = a.getInt(R.styleable.BubbleImageView_bubble_arrowLocation, mArrowLocation);
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        RectF rect = new RectF(getPaddingLeft(), getPaddingTop(), getRight() - getLeft() - getPaddingRight(), getBottom() - getTop() - getPaddingBottom());

        Path path = new Path();

        if (mArrowLocation == LOCATION_LEFT) {
            leftPath(rect, path);
        } else {
            rightPath(rect, path);
        }

        canvas.drawPath(path, mBitmapPaint);
    }

    public void rightPath(RectF rect, Path path) {
        path.moveTo(mAngle, rect.top);
        path.lineTo(rect.width(), rect.top);
        //Rect的参数是int型，RectF的参数是float型，RectF比Rect的精确度更高
        //从改矩形RectF内切椭圆的270度开始，画90度，水平向右为0度，sweepangle：顺时针为正，逆时针为负
        //右上圆弧
        path.arcTo(new RectF(rect.right - mAngle * 2 - mArrowWidth, rect.top, rect.right - mArrowWidth, mAngle * 2 + rect.top), 270, 90);
        //箭头上部顶点
        path.lineTo(rect.right - mArrowWidth, mArrowTop);
        //箭头右侧顶点
        path.lineTo(rect.right, mArrowTop - mArrowOffset);
        //箭头下部顶点
        path.lineTo(rect.right - mArrowWidth, mArrowTop + mArrowHeight);
        path.lineTo(rect.right - mArrowWidth, rect.height() - mAngle);
        //右下圆弧
        path.arcTo(new RectF(rect.right - mAngle * 2 - mArrowWidth, rect.bottom - mAngle * 2, rect.right - mArrowWidth, rect.bottom), 0, 90);
        path.lineTo(rect.left, rect.bottom);
        //左下圆弧
        path.arcTo(new RectF(rect.left, rect.bottom - mAngle * 2, mAngle * 2 + rect.left, rect.bottom), 90, 90);
        path.lineTo(rect.left, rect.top);
        path.arcTo(new RectF(rect.left, rect.top, mAngle * 2 + rect.left, mAngle * 2 + rect.top), 180, 90);
        path.close();
    }

    public void leftPath(RectF rect, Path path) {
        path.moveTo(mAngle + mArrowWidth, rect.top);
        path.lineTo(rect.width(), rect.top);
        path.arcTo(new RectF(rect.right - mAngle * 2, rect.top, rect.right, mAngle * 2 + rect.top), 270, 90);
        path.lineTo(rect.right, rect.top);
        path.arcTo(new RectF(rect.right - mAngle * 2, rect.bottom - mAngle * 2, rect.right, rect.bottom), 0, 90);
        path.lineTo(rect.left + mArrowWidth, rect.bottom);
        path.arcTo(new RectF(rect.left + mArrowWidth, rect.bottom - mAngle * 2, mAngle * 2 + rect.left + mArrowWidth, rect.bottom), 90, 90);
        path.lineTo(rect.left + mArrowWidth, mArrowTop + mArrowHeight);
        path.lineTo(rect.left, mArrowTop - mArrowOffset);
        path.lineTo(rect.left + mArrowWidth, mArrowTop);
        path.lineTo(rect.left + mArrowWidth, rect.top);
        path.arcTo(new RectF(rect.left + mArrowWidth, rect.top, mAngle * 2 + rect.left + mArrowWidth, mAngle * 2 + rect.top), 180, 90);
        path.close();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
        setup();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
        setup();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    private void setup() {
        if (mBitmap == null) {
            return;
        }

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mBitmapPaint = new Paint();
        if (cf != null) {
            mBitmapPaint.setColorFilter(cf);
        }
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);
        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();
        updateShaderMatrix();
        invalidate();
    }

    public void setColorFilter(ColorFilter cf) {
        this.cf = cf;
    }

    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;

        mShaderMatrix = new Matrix();
        mShaderMatrix.set(null);

        mDrawableRect = new Rect(0, 0, getRight() - getLeft(), getBottom() - getTop());
        //如果图片框与图片的高度比 比 它们的宽度比大
        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            //（如果图片展示框为正方形，要展示的图片的宽如果大于高的话）
            //将图片的高度缩放到与框一般高
            scale = mDrawableRect.height() / (float) mBitmapHeight;
            //宽度方向上的偏移量，居中显示
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
        } else {
            scale = mDrawableRect.width() / (float) mBitmapWidth;
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
        }

        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));

        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

}
