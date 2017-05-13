package com.udacity.stockhawkk.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.udacity.stockhawkk.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class StockDetailActivity extends AppCompatActivity {
    @BindView(R.id.symbol)
    TextView tvSymbol;
    @BindView(R.id.graph)
    ImageView ivGraph;
    @BindView(R.id.toolbar_detail)
    Toolbar mToolbar;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);
        ButterKnife.bind(this);






        Intent i= getIntent();
        if( i!=null && i.hasExtra("symbol")){
            String symbol = i.getStringExtra("symbol");
            Timber.d("Symbole: "+ symbol);
            Glide.with(this).load("https://chart.finance.yahoo.com/z?s="+symbol+"&t=8m&q=l&l=on&z=s&p=m100,m400")
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(ivGraph);
            tvSymbol.setText(symbol);

        }
    }

}
