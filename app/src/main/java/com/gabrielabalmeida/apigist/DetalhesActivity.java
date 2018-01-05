package com.gabrielabalmeida.apigist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * Created by gabriela on 03/01/18.
 */

public class DetalhesActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ScrollView mScrollView;
    private LinearLayout mLinearLayout;

    public static final String BUNDLE_GIST = "com.gabrielabalmeida.apigist.DetalhesActivity";

    float mPegarImagem;

    UsuariosItem mGist;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        mToolbar = findViewById(R.id.toolbar);
        mScrollView = findViewById(R.id.scroll_atualizar);
        WebView mWebView = findViewById(R.id.web_view_atualizar);
        TextView mTextView = findViewById(R.id.dono);
        ImageView mImageView = findViewById(R.id.imagem);
        mLinearLayout = findViewById(R.id.cabeca);
        configureToolbar();

        Bundle extras = getIntent().getExtras();
        if(extras == null) finish();

        assert extras != null;
        mGist = (UsuariosItem) extras.getSerializable(BUNDLE_GIST);

        if(mGist == null) finish();

        String ownerName = getString(R.string.anonimo);
        if(mGist.dono != null) {
            ownerName = mGist.dono.login;

            if(!TextUtils.isEmpty(mGist.dono.avatar)) {
                Glide.with(this)
                        .load(mGist.dono.avatar)
                        .centerCrop()
                        .bitmapTransform(new TransformarBipMap(this, 30, 0, TransformarBipMap.CornerType.ALL))
                        .into(mImageView);
            }
        }
        mTextView.setText(getString(R.string.gist_dono, ownerName, mGist.objetoFile.nomeArquivo));

        mScrollView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mScrollView.getViewTreeObserver().removeOnPreDrawListener(this);
                mPegarImagem = mLinearLayout.getY();
                mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        int scrollY = mScrollView.getScrollY() - mToolbar.getHeight();
                        mLinearLayout.setY((-scrollY + mPegarImagem) * 0.5f);
                    }
                });

                return false;
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mGist.objetoFile.url);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setScrollContainer(false);
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
