package com.example.conexaoapi;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class CarregaMake extends AsyncTaskLoader<String> {
    private String mQueryString;
    CarregaMake(Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public String loadInBackground() { return NetworkUtils.buscaInfosMakes(mQueryString);}
    }


