package com.example.tuionf.multistatelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by tuion on 2017/6/16.
 */



public class MultiStateLayout extends RelativeLayout {

    public static final int STATUS_CONTENT      = 0x00;
    public static final int STATUS_ERROR        = 0x03;
    public static final int STATUS_NO_NETWORK   = 0x04;

    private int mCustomViewResId;
    private int mErrorViewResId;
    private int mNoNetworkViewResId;
    private int mViewStatus;

    private View mErrorView;
    private View mNoNetworkView;
    private View mContentView;
    private View mErrorRetryView;
    private View mNoNetworkRetryView;

    private static final int NULL_RESOURCE_ID   = -1;

    private OnClickListener mOnRetryClickListener;

    private LayoutInflater mInflater;

    private final ViewGroup.LayoutParams mLayoutParams = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    public MultiStateLayout(Context context) {
        this(context,null);
    }

    public MultiStateLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MultiStateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MultiStateLayout,defStyleAttr,0);
        mCustomViewResId = typedArray.getResourceId(R.styleable.MultiStateLayout_contentLayout,R.layout.custom_content);
        mErrorViewResId = typedArray.getResourceId(R.styleable.MultiStateLayout_errorLayout,R.layout.custom_error_view);
        mNoNetworkViewResId = typedArray.getResourceId(R.styleable.MultiStateLayout_noNetworkLayout,R.layout.custom_no_network_view);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mInflater = LayoutInflater.from(getContext());
    }

    /** 获取当前状态 */
    public int getViewStatus(){
        return mViewStatus;
    }

    /**
     * 设置重试点击事件
     * @param onRetryClickListener 重试点击事件
     */
    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.mOnRetryClickListener = onRetryClickListener;
    }

    /** 显示错误视图 */
    public final void showError() {
        mViewStatus = STATUS_ERROR;
        if (null == mErrorView) {
            mErrorView = mInflater.inflate(mErrorViewResId,null);
            //TODO  添加下拉刷新
            mErrorRetryView = mErrorView.findViewById(R.id.error_retry_view);
            if (null != mOnRetryClickListener && null != mErrorRetryView ) {
                mErrorRetryView.setOnClickListener(mOnRetryClickListener);
            }
            addView(mErrorView,0,mLayoutParams);
        }
        showViewByStatus(mViewStatus);
    }

    /** 显示内容视图 */
    public final void showContent() {
        mViewStatus = STATUS_CONTENT;
        if (null == mContentView) {
            if(mCustomViewResId != NULL_RESOURCE_ID){
                mContentView = mInflater.inflate(mCustomViewResId, null);
                addView(mContentView,0, mLayoutParams);
            }else{
                mContentView = findViewById(R.id.content_view);
            }
        }
        showViewByStatus(mViewStatus);
    }

    /** 显示无网络视图 */
    public final void showNoNetwork() {
        mViewStatus = STATUS_NO_NETWORK;
        if(null == mNoNetworkView){
            mNoNetworkView = mInflater.inflate(mNoNetworkViewResId, null);
            mNoNetworkRetryView = mNoNetworkView.findViewById(R.id.no_network_retry_view);
            if(null != mOnRetryClickListener && null != mNoNetworkRetryView){
                mNoNetworkRetryView.setOnClickListener(mOnRetryClickListener);
            }
            addView(mNoNetworkView,0, mLayoutParams);
        }
        showViewByStatus(mViewStatus);
    }

    private void showViewByStatus(int viewStatus) {
        if(null != mErrorView){
            mErrorView.setVisibility(viewStatus == STATUS_ERROR ? View.VISIBLE : View.GONE);
        }
        if(null != mNoNetworkView){
            mNoNetworkView.setVisibility(viewStatus == STATUS_NO_NETWORK ? View.VISIBLE : View.GONE);
        }
        if(null != mContentView){
            mContentView.setVisibility(viewStatus == STATUS_CONTENT ? View.VISIBLE : View.GONE);
        }
    }
}
