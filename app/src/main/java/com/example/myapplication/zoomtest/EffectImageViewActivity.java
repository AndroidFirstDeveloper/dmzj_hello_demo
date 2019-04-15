package com.example.myapplication.zoomtest;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.CenterAlignImageSpan;
import com.example.myapplication.R;

public class EffectImageViewActivity extends AppCompatActivity {

    private TextView titleTextView, contentTextView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effect_image_view);
        findView();
    }

    public void findView() {
        titleTextView = findViewById(R.id.activity_effect_title_tv);
        contentTextView = findViewById(R.id.activity_effect_content_tv);
        imageView = findViewById(R.id.activity_effect_coll_iv);
        final String content = "漫画详情页由fragment+顶部图片组成\n" +
                "fragment包括 章节目录+收藏、内容、更多图片、相关漫文、相关新闻、同类作品\n" +
                "其中加载数据的步骤是：首先加载到了章节目录、内容、更多图片、相关漫文、相关新闻，然后在fragment里进行了新的请求加载到了同类作品";
//        final String content = "中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人中国人v中国人中国人中国人中国人中国人中国人";
//        int width = getResources().getDisplayMetrics().widthPixels - dip2px(this, 12 * 2);
//        StaticLayout staticLayout = new StaticLayout(content, titleTextView.getPaint(), width, null, titleTextView.getLineSpacingMultiplier(), titleTextView.getLineSpacingExtra(), false);
//        int lineCount = staticLayout.getLineCount();
        /*if (lineCount > 4) {
            int endIndex = staticLayout.getLineEnd(3);
            String s = content.substring(0, endIndex);
            SpannableString spannableString = new SpannableString(s);
            contentTextView.setText(Html.fromHtml(s));
            //get drawable
            Drawable drawable = getResources().getDrawable(R.mipmap.display_all);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

            //make a imagespan
            CenterAlignImageSpan imageSpan = new CenterAlignImageSpan(drawable);

//            spannableString.setSpan(new ImageSpan(this, R.mipmap.display_all), endIndex - 4, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(imageSpan, endIndex - 1, endIndex, ImageSpan.ALIGN_BASELINE);
            titleTextView.setText(spannableString);
        }*/

//        Spanned spanned = Html.fromHtml(content);
//        contentTextView.setText(spanned);

//        int lines = contentTextView.getLineCount();
//        System.out.println(lines);
        titleTextView.setText(content);
        contentTextView.setText(content);
        contentTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int lines = contentTextView.getLineCount();
                if (lines > 4) {
                    Layout layout = contentTextView.getLayout();
                    if (layout != null) {
                        int start = layout.getLineStart(3);
                        int end = layout.getLineEnd(3);
                        int ascent = layout.getLineAscent(3);
                        int descent = layout.getLineDescent(3);
                        int top = layout.getLineTop(3);
                        int bottom = layout.getLineBottom(3);

                        Drawable drawable = getResources().getDrawable(R.mipmap.display_all);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        int drawableWidth = drawable.getMinimumWidth();
                        int drawableHeight = drawable.getMinimumHeight();
//                        int cutCount = contentTextView.getPaint().breakText(content, 0, end, false, drawableWidth + dip2px(EffectImageViewActivity.this, 12), null);
                        String body = content.substring(0, end - 2);
                        contentTextView.setText(body);
                        Rect rect = new Rect();
                        layout.getLineBounds(3, rect);
                        int lineHeight = rect.bottom - rect.top;
                        int margin = lineHeight / 2 - drawableHeight / 2;
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                        params.bottomMargin = margin;
                        imageView.setLayoutParams(params);
                        imageView.setVisibility(View.VISIBLE);
                        contentTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                } else if (lines > 0) {
                    contentTextView.setMaxLines(Integer.MAX_VALUE);
                    imageView.setVisibility(View.GONE);
                    contentTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                /*contentTextView.setMaxLines(4);*/
//                if (lines > 0) {
//                    Layout layout = contentTextView.getLayout();
//                    Paint paint = contentTextView.getPaint();
//                    if (layout != null) {

                      /*  int start = layout.getLineStart(lines > 4 ? 3 : lines);
                        int end = layout.getLineEnd(lines > 4 ? 3 : lines);
                        Drawable drawable = getResources().getDrawable(R.mipmap.display_all);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        int drawableWidth = drawable.getMinimumWidth();
                        if (lines > 4) {
                            int cutCount = paint.breakText(content, start, end, false, drawableWidth + dip2px(EffectImageViewActivity.this, 12), null);
                            String body = content.substring(0, end - cutCount);
                            StringBuilder sb=new StringBuilder(body);
                            sb.append(" ");
                            sb.append(" ");*/

//                            String result = body + "<img src='https://www.google.com.hk/intl/zh-CN/images/logo_cn.png' />";
//                            String result = body + "<font color='#0000ff'>...更多</font>";
//                            contentTextView.setText(Html.fromHtml(body));
                           /* contentTextView.setText(Html.fromHtml(result, new Html.ImageGetter() {
                                @Override
                                public Drawable getDrawable(String source) {
                                    Drawable drawable = getResources().getDrawable(R.mipmap.display_all);
                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                    return drawable;
                                }
                            }, null));*/
                           /* CenterAlignImageSpan imageSpan = new CenterAlignImageSpan(drawable);
                            SpannableString spannableString = new SpannableString(sb.toString());
                            spannableString.setSpan(imageSpan,sb.length()-1,sb.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            contentTextView.setText(spannableString);
                            System.out.println(body);
                            contentTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);*/
//                    }
            }
//            }
        });

    }

    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
