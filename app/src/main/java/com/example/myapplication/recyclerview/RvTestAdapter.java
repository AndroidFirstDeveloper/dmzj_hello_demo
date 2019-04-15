package com.example.myapplication.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.print.PrinterId;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.myapplication.R;
import com.example.myapplication.fragment.OnItemClickedListener;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class RvTestAdapter extends RecyclerView.Adapter<RvTestAdapter.RvTestHolder> {

    public static final int PRAISE_CHANGE = 1;
    private static final int IMAGE_CHANGE = 2;
    private final String XUE_XIANG_IMAGE1 = "http://mafangvvo.com/cxkindeditor/attached/image/20181122/2018112213050664664.jpg";
    private final String XUE_XIANG_IMAGE2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543927797127&di=c043e1bb9c061ace93a2f6dcb0550556&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F0ff41bd5ad6eddc4f19aac2f33dbb6fd536633e0.jpg";

    private List<RvTestModel> list;
    private LayoutInflater layoutInflater;
    private Context context;

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public RvTestAdapter(Context context, List<RvTestModel> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RvTestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.rv_test_item_layout, parent, false);
        return new RvTestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RvTestHolder holder, final int position) {
        holder.rv_test_item_layout_title_tv.setText(list.get(position).getTitle());
        holder.rv_test_item_layout_content_tv.setText(list.get(position).getContent());

        Glide.with(context)
                .asBitmap()
                .load(list.get(position).getImagePath())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        final int imageWidth = resource.getWidth();
                        final int imageHeight = resource.getHeight();
                        final int viewWidth = dip2px(context, 120);
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.rv_test_item_layout_iv.getLayoutParams();
                        layoutParams.width = viewWidth;
                        layoutParams.height = (int) (viewWidth * imageHeight * 1.0f / imageWidth);
                        holder.rv_test_item_layout_iv.setLayoutParams(layoutParams);
                        holder.rv_test_item_layout_iv.setImageBitmap(resource);
                    }
                });

        if (list.get(position).getPraise() == 0) {
            holder.rv_test_item_layout_praise_iv.setImageResource(R.drawable.vector_drawable_un_praise);
        } else if (list.get(position).getPraise() == 1) {
            holder.rv_test_item_layout_praise_iv.setImageResource(R.drawable.vector_drawable_praise);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, list.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.rv_test_item_layout_praise_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(position, PRAISE_CHANGE);
            }
        });

        holder.rv_test_item_layout_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(position, IMAGE_CHANGE);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull RvTestHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            switch ((int) payloads.get(0)) {
                case PRAISE_CHANGE:
                    if (list.get(position).getPraise() == 0) {
                        list.get(position).setPraise((byte) 1);
                        holder.rv_test_item_layout_praise_iv.setImageResource(R.drawable.vector_drawable_praise);
                    } else if (list.get(position).getPraise() == 1) {
                        list.get(position).setPraise((byte) 0);
                        holder.rv_test_item_layout_praise_iv.setImageResource(R.drawable.vector_drawable_un_praise);
                    }
                    break;
                case IMAGE_CHANGE:
                    if (list.get(position).getImageType() == 0) {
                        list.get(position).setImageType((byte) 1);
                        list.get(position).setImagePath(XUE_XIANG_IMAGE2);
                    } else {
                        list.get(position).setImageType((byte) 0);
                        list.get(position).setImagePath(XUE_XIANG_IMAGE1);
                    }
                    Glide.with(context)
                            .load(list.get(position).getImagePath())
                            .into(holder.rv_test_item_layout_iv);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class RvTestHolder extends RecyclerView.ViewHolder {
        public ImageView rv_test_item_layout_iv, rv_test_item_layout_praise_iv;
        public TextView rv_test_item_layout_title_tv, rv_test_item_layout_content_tv;

        public RvTestHolder(View itemView) {
            super(itemView);
            rv_test_item_layout_content_tv = itemView.findViewById(R.id.rv_test_item_layout_content_tv);
            rv_test_item_layout_title_tv = itemView.findViewById(R.id.rv_test_item_layout_title_tv);
            rv_test_item_layout_iv = itemView.findViewById(R.id.rv_test_item_layout_iv);
            rv_test_item_layout_praise_iv = itemView.findViewById(R.id.rv_test_item_layout_praise_iv);
        }
    }

    public int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
