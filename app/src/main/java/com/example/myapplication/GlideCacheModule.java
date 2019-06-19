package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

import java.lang.annotation.Annotation;

public class GlideCacheModule implements GlideModule {

    //缓存大小
    private static final int DISK_CACHE_SIZE = 500 * 1024 * 1024;

    @Override
    public void applyOptions(Context context, GlideBuilder glideBuilder) {
        glideBuilder.setDiskCache(new DiskLruCacheFactory(context.getCacheDir().getPath() + "/GlideCacheFolder", DISK_CACHE_SIZE));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
    }
}
