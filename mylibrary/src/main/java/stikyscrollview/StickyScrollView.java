package stikyscrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ScrollView;

import com.example.relicemxd.mylibrary.R;

import java.util.ArrayList;

/**
 * 滑动顶部固定ScrollView
 * 实现原理:
 * 通过getTop,Left,Bottom,Right方法算出有 tag(需要置顶view)的顶部位置,
 * 并使用invalidate(ViewGroup),onDraw(View)将view绘制出来,最终顶部滚动的效果是通过属性动画实现.
 * 使用:
 * 1.在指定要置顶的view中添加: android:tag="sticky"
 */
public class StickyScrollView extends ScrollView {

    /**
     * 黏贴tag: 需要顶部固定的tag,在布局内添加(imageview,textview.relativelayout等)
     */
    public static final String STICKY_TAG = "sticky";

    /**
     * Flag for views that should stick and have non-constant drawing. e.g. Buttons, ProgressBars etc
     */
    public static final String FLAG_NONCONSTANT = "-nonconstant";

    /**
     * Flag for views that have aren't fully opaque
     */
    public static final String FLAG_HASTRANSPARANCY = "-hastransparancy";

    /**
     * 默认阴影效果
     */
    private static final int DEFAULT_SHADOW_HEIGHT = 10; // dp;

    private ArrayList<View> stickyViews;//存储带tag="sticky"的容器
    private View currentlyStickingView;//当前黏贴在顶部的View
    private float stickyViewTopOffset;
    private int stickyViewLeftOffset;
    private boolean redirectTouchesToStickyView;
    private boolean clippingToPadding;
    private boolean clipToPaddingHasBeenSet;

    private int mShadowHeight;
    private Drawable mShadowDrawable;

    //请求重绘View树，即draw()过程，假如视图发生大小没有变化就不会调用layout()过程，
    // 并且只绘制那些“需要重绘的”视图，即(View的话，只绘制该View ；ViewGroup，
    // 则绘制整个ViewGroup)请求invalidate()方法，就绘制该视图。
    private final Runnable invalidateRunnable = new Runnable() {
        @Override
        public void run() {
            if (currentlyStickingView != null) {
                int l = getLeftForViewRelativeOnlyChild(currentlyStickingView);
                int t = getBottomForViewRelativeOnlyChild(currentlyStickingView);
                int r = getRightForViewRelativeOnlyChild(currentlyStickingView);
                int b = (int) (getScrollY() + (currentlyStickingView.getHeight() + stickyViewTopOffset));
                invalidate(l, t, r, b);
            }
            postDelayed(this, 16);
        }
    };

    public StickyScrollView(Context context) {
        this(context, null);
    }

    public StickyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.scrollViewStyle);
    }

    public StickyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup();

        //处理阴影效果 (可在布局设置)
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.StickyScrollView, defStyle, 0);

        final float density = context.getResources().getDisplayMetrics().density;
        int defaultShadowHeightInPix = (int) (DEFAULT_SHADOW_HEIGHT * density + 0.5f);

        mShadowHeight = a.getDimensionPixelSize(
                R.styleable.StickyScrollView_stuckShadowHeight,
                defaultShadowHeightInPix);

        int shadowDrawableRes = a.getResourceId(
                R.styleable.StickyScrollView_stuckShadowDrawable, -1);

        if (shadowDrawableRes != -1) {
            mShadowDrawable = context.getResources().getDrawable(
                    shadowDrawableRes);
        }

        a.recycle();
    }

    /**
     * Sets the height of the shadow drawable in pixels.
     * 设置阴影效果高度
     *
     * @param height
     */
    public void setShadowHeight(int height) {
        mShadowHeight = height;
    }

    public void setup() {
        stickyViews = new ArrayList<View>();
    }

    /**
     * 获取stiky view left坐标
     *
     * @param v
     * @return
     */
    private int getLeftForViewRelativeOnlyChild(View v) {
        int left = v.getLeft();
        while (v.getParent() != getChildAt(0)) {
            v = (View) v.getParent();
            left += v.getLeft();
        }
        return left;
    }

    /**
     * 获取stiky view top坐坐标
     *
     * @param v
     * @return
     */
    private int getTopForViewRelativeOnlyChild(View v) {
        int top = v.getTop();
        while (v.getParent() != getChildAt(0)) {
            v = (View) v.getParent();
            top += v.getTop();
        }
        return top;
    }

    /**
     * 获取stiky view right坐坐标
     *
     * @param v
     * @return
     */
    private int getRightForViewRelativeOnlyChild(View v) {
        int right = v.getRight();
        while (v.getParent() != getChildAt(0)) {
            v = (View) v.getParent();
            right += v.getRight();
        }
        return right;
    }

    /**
     * 获取stiky view bottom坐坐标
     *
     * @param v
     * @return
     */
    private int getBottomForViewRelativeOnlyChild(View v) {
        int bottom = v.getBottom();
        while (v.getParent() != getChildAt(0)) {
            v = (View) v.getParent();
            bottom += v.getBottom();
        }
        return bottom;
    }

    /**
     * 设置黏贴view位置
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!clipToPaddingHasBeenSet) {
            clippingToPadding = true;
        }
        notifyHierarchyChanged();
    }

    /**
     * void setClipToPadding(boolean clipToPadding)
     * true：滚动时child不可以绘制到padding区域，即剪裁掉
     * false：滚动时child可以绘制到padding区域
     *
     * @param clipToPadding
     */
    @Override
    public void setClipToPadding(boolean clipToPadding) {
        super.setClipToPadding(clipToPadding);
        clippingToPadding = clipToPadding;
        clipToPaddingHasBeenSet = true;
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        findStickyViews(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        findStickyViews(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        findStickyViews(child);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        findStickyViews(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        findStickyViews(child);
    }

    /**
     * 绘制需要顶部显示的view,如果是viewgroup则会走invalidate
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (currentlyStickingView != null) {
            canvas.save();
            canvas.translate(getPaddingLeft() + stickyViewLeftOffset, getScrollY() + stickyViewTopOffset + (clippingToPadding ? getPaddingTop() : 0));
            /**
             * 自己查看api
             * Intersect the current clip with the specified rectangle, which is
             * expressed in local coordinates.
             */
            canvas.clipRect(0, (clippingToPadding ? -stickyViewTopOffset : 0),
                    getWidth() - stickyViewLeftOffset,
                    currentlyStickingView.getHeight() + mShadowHeight + 1);

            //绘制 阴影效果
            if (mShadowDrawable != null) {
                int left = 0;
                int right = currentlyStickingView.getWidth();
                int top = currentlyStickingView.getHeight();
                int bottom = currentlyStickingView.getHeight() + mShadowHeight;
                /**
                 * Specify a bounding rectangle for the Drawable.
                 * This is where the drawable will draw when its draw() method is called.
                 */
                mShadowDrawable.setBounds(left, top, right, bottom);
                mShadowDrawable.draw(canvas);
            }

            canvas.clipRect(0, (clippingToPadding ? -stickyViewTopOffset : 0), getWidth(), currentlyStickingView.getHeight());
            if (getStringTagForView(currentlyStickingView).contains(FLAG_HASTRANSPARANCY)) {
                showView(currentlyStickingView);
                currentlyStickingView.draw(canvas);
                hideView(currentlyStickingView);
            } else {
                currentlyStickingView.draw(canvas);
            }
            canvas.restore();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            redirectTouchesToStickyView = true;
        }

        if (redirectTouchesToStickyView) {
            redirectTouchesToStickyView = currentlyStickingView != null;
            if (redirectTouchesToStickyView) {
                redirectTouchesToStickyView =
                        ev.getY() <= (currentlyStickingView.getHeight() + stickyViewTopOffset) &&
                                ev.getX() >= getLeftForViewRelativeOnlyChild(currentlyStickingView) &&
                                ev.getX() <= getRightForViewRelativeOnlyChild(currentlyStickingView);
            }
        } else if (currentlyStickingView == null) {
            redirectTouchesToStickyView = false;
        }
        if (redirectTouchesToStickyView) {
            ev.offsetLocation(0, -1 * ((getScrollY() + stickyViewTopOffset) - getTopForViewRelativeOnlyChild(currentlyStickingView)));
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean hasNotDoneActionDown = true;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (redirectTouchesToStickyView) {
            ev.offsetLocation(0, ((getScrollY() + stickyViewTopOffset) - getTopForViewRelativeOnlyChild(currentlyStickingView)));
        }

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            hasNotDoneActionDown = false;
        }

        if (hasNotDoneActionDown) {
            MotionEvent down = MotionEvent.obtain(ev);
            down.setAction(MotionEvent.ACTION_DOWN);
            super.onTouchEvent(down);
            hasNotDoneActionDown = false;
        }

        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            hasNotDoneActionDown = true;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 滚动顶部监听
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        doTheStickyThing();
    }

    /**
     * 核心处理滚动顶部 view
     * 将浮动view顶上去效果
     */
    private void doTheStickyThing() {
        View viewThatShouldStick = null;
        View approachingView = null;
        for (View v : stickyViews) {
            int viewTop = getTopForViewRelativeOnlyChild(v) - getScrollY() + (clippingToPadding ? 0 : getPaddingTop());
            if (viewTop <= 0) {
                if (viewThatShouldStick == null || viewTop > (getTopForViewRelativeOnlyChild(viewThatShouldStick) - getScrollY() + (clippingToPadding ? 0 : getPaddingTop()))) {
                    viewThatShouldStick = v;
                }
            } else {
                //即将靠近Top的view
                if (approachingView == null || viewTop < (getTopForViewRelativeOnlyChild(approachingView) - getScrollY() + (clippingToPadding ? 0 : getPaddingTop()))) {
                    approachingView = v;
                }
            }
        }
        if (viewThatShouldStick != null) {
            stickyViewTopOffset = approachingView == null ? 0 : Math.min(0, getTopForViewRelativeOnlyChild(approachingView) - getScrollY() + (clippingToPadding ? 0 : getPaddingTop()) - viewThatShouldStick.getHeight());
            if (viewThatShouldStick != currentlyStickingView) {
                if (currentlyStickingView != null) {
                    stopStickingCurrentlyStickingView();
                }
                // only compute the left offset when we start sticking.
                stickyViewLeftOffset = getLeftForViewRelativeOnlyChild(viewThatShouldStick);
                startStickingView(viewThatShouldStick);
            }
        } else if (currentlyStickingView != null) {
            stopStickingCurrentlyStickingView();
        }
    }

    /**
     * 开始置顶操作,并获取有透明效果的view
     *
     * @param viewThatShouldStick
     */
    private void startStickingView(View viewThatShouldStick) {
        currentlyStickingView = viewThatShouldStick;
        if (getStringTagForView(currentlyStickingView).contains(FLAG_HASTRANSPARANCY)) {
            hideView(currentlyStickingView);
        }
        //开始post置顶操作
        if (((String) currentlyStickingView.getTag()).contains(FLAG_NONCONSTANT)) {
            post(invalidateRunnable);
        }
    }

    private void stopStickingCurrentlyStickingView() {
        if (getStringTagForView(currentlyStickingView).contains(FLAG_HASTRANSPARANCY)) {
            showView(currentlyStickingView);
        }
        currentlyStickingView = null;
        removeCallbacks(invalidateRunnable);
    }

    /**
     * Notify that the sticky attribute has been added or removed from one or more views in the View hierarchy
     */
    public void notifyStickyAttributeChanged() {
        notifyHierarchyChanged();
    }

    private void notifyHierarchyChanged() {
        if (currentlyStickingView != null) {
            stopStickingCurrentlyStickingView();
        }
        stickyViews.clear();
        findStickyViews(getChildAt(0));
        doTheStickyThing();
        invalidate();
    }

    /**
     * 遍历寻找需要顶部的view
     *
     * @param v
     */
    private void findStickyViews(View v) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                String tag = getStringTagForView(vg.getChildAt(i));
                if (tag != null && tag.contains(STICKY_TAG)) {
                    stickyViews.add(vg.getChildAt(i));
                } else if (vg.getChildAt(i) instanceof ViewGroup) {
                    findStickyViews(vg.getChildAt(i));
                }
            }
        } else {
            String tag = (String) v.getTag();
            if (tag != null && tag.contains(STICKY_TAG)) {
                stickyViews.add(v);
            }
        }
    }

    private String getStringTagForView(View v) {
        Object tagObject = v.getTag();
        return String.valueOf(tagObject);
    }

    /**
     * 隐藏 顶上去透明动画效果
     *
     * @param v
     */
    private void hideView(View v) {
        if (Build.VERSION.SDK_INT >= 11) {
            v.setAlpha(0);
        } else {
            AlphaAnimation anim = new AlphaAnimation(1, 0);
            anim.setDuration(0);
            anim.setFillAfter(true);
            v.startAnimation(anim);
        }
    }

    private void showView(View v) {
        if (Build.VERSION.SDK_INT >= 11) {
            v.setAlpha(1);
        } else {
            AlphaAnimation anim = new AlphaAnimation(0, 1);
            anim.setDuration(0);
            anim.setFillAfter(true);
            v.startAnimation(anim);
        }
    }

}
