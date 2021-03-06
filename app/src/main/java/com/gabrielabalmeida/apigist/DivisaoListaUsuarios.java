package com.gabrielabalmeida.apigist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by gabriela on 02/01/18.
 */

public class DivisaoListaUsuarios extends RecyclerView.ItemDecoration {
    private Drawable mDivisao;

    public DivisaoListaUsuarios(Context context) {
        mDivisao = context.getResources().getDrawable(R.drawable.dv_linha_usuarios);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivisao.getIntrinsicHeight();

            mDivisao.setBounds(left, top, right, bottom);
            mDivisao.draw(c);
        }
    }
}