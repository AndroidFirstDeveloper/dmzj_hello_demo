package com.example.myapplication.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;

import java.util.List;

import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private List<MyItemBean> list;
    private Context context;
    private Transformation transformation;

    public MyRecyclerViewAdapter(List<MyItemBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_recycler_view_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    private int transform_style = 0;

    public int getTransform_style() {
        return transform_style;
    }

    public void setTransform_style(int transform_style) {
        this.transform_style = transform_style;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView1.setText(list.get(position).getPhone());
        holder.textView2.setText(list.get(position).getName());
        //使用glide加载图片，使用圆角特效时，如果加载的图片大小不一致，并且scaleType=centercrop，最终加载图片显示的圆角效果不一样
        //原因：图片大小不一致，但设置的圆角一样时，较大的图片截取的圆角占本身比例较小，当通过等比缩放显示在控件上时，导致圆角部分也进行了缩放.
        //这种结果好像跟图片的格式没有什么关系
        switch (getTransform_style()) {
            case 0:
                transformation = new RoundedCornersTransformation(8, 0, RoundedCornersTransformation.CornerType.BOTTOM);
                break;
            case 1:
                transformation = new ColorFilterTransformation(Color.argb(99, 0, 0, 0));
                break;
            case 2:
                transformation = new MultiTransformation(new GrayscaleTransformation(), new RoundedCornersTransformation(8, 0));
                break;
            case 3:
                transformation = new BlurTransformation(8, 8);
                break;
            case 4:
                transformation = new MaskTransformation(R.drawable.mask_starfish);
                break;
            case 5:
                transformation = new ToonFilterTransformation();
                break;
            case 6:
                transformation = new SepiaFilterTransformation();
                break;
            case 7:
                transformation = new ContrastFilterTransformation();
                break;
            case 8:
                transformation = new InvertFilterTransformation();
                break;
            case 9:
                transformation = new PixelationFilterTransformation();
                break;
            case 10:
                transformation = new SketchFilterTransformation();
                break;
            case 11:
                transformation = new SwirlFilterTransformation();
                break;
            case 12:
                transformation = new BrightnessFilterTransformation();
                break;
            case 13:
                transformation = new KuwaharaFilterTransformation();
                break;
            case 14:
                transformation = new VignetteFilterTransformation();
                break;
        }
        Glide.with(context)
                .load(list.get(position).getImage())
                .apply(RequestOptions.bitmapTransform(transformation))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1, textView2;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.activity_recycler_view_item_layout_tv1);
            textView2 = (TextView) itemView.findViewById(R.id.activity_recycler_view_item_layout_tv2);
            imageView = (ImageView) itemView.findViewById(R.id.activity_recycler_view_item_layout_image);

        }
    }

}
