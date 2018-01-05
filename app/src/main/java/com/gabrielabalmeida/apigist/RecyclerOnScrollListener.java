package com.gabrielabalmeida.apigist;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by gabriela on 02/01/18.
 */

public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener{
    public static String TAG = RecyclerOnScrollListener.class.getSimpleName();

    private boolean carregamento = true;
    private int totalPreVisualizacao = 0;

    private int pagina_atual = 1;

    private LinearLayoutManager mLinearLayoutManager;

    RecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int contaItensVisiveis = recyclerView.getChildCount();
        int totalDeItem = mLinearLayoutManager.getItemCount();
        int primeiroItemVisivel = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (carregamento) {
            if (totalDeItem > totalPreVisualizacao) {
                carregamento = false;
                totalPreVisualizacao = totalDeItem;
            }
        }
        int limiteVisivel = 5;
        if (!carregamento && (totalDeItem - contaItensVisiveis)
                <= (primeiroItemVisivel + limiteVisivel)) {

            pagina_atual++;

            onMaisCarga(pagina_atual);

            carregamento = true;
        }
    }

    abstract void onMaisCarga(int pagina_atual);
}