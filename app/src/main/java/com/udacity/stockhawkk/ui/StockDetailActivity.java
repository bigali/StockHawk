package com.udacity.stockhawkk.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);
        ButterKnife.bind(this);






        Intent i= getIntent();
        if( i!=null && i.hasExtra("symbol")){
            String symbol = i.getStringExtra("symbol");
            Timber.d("Symbole: "+ symbol);
            Glide.with(this).load("https://chart.finance.yahoo.com/z?s="+symbol+"&t=8m&q=l&l=on&z=s&p=m100,m400").into(ivGraph);
            tvSymbol.setText(symbol);

        }
    }

}
