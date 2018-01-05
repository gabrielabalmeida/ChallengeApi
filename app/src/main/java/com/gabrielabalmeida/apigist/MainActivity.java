package com.gabrielabalmeida.apigist;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import retrofit.client.Response;


import java.util.ArrayList;
import java.util.List;



    public class MainActivity extends AppCompatActivity {

        private RecyclerView mListaRecycler;
        LinearLayout semData;
        SwipeRefreshLayout swRefresh;

        List<UsuariosItem> mUsuariosLista = new ArrayList<>();
        UsuariosAdapter adapter;

        int mPagina= 0;

        public CallbackRetrof<List<Usuarios>> gistCallback = new CallbackRetrof<List<Usuarios>>() {
            @Override
            public void failure(Erro erro) {
                swRefresh.setRefreshing(false);
                try {
                    Toast.makeText(com.gabrielabalmeida.apigist.MainActivity.this, erro.getStrMessage(), Toast.LENGTH_LONG).show();
                }catch (Exception ignored){}
                showNoData(View.VISIBLE, View.GONE);
            }

            @Override
            public void success(List<Usuarios> usuarios, Response response) {
                swRefresh.setRefreshing(false);
                for (Usuarios u : usuarios) {
                    for (Arquivo a : u.objetoFile.gists) {
                        UsuariosItem usuariosItem = new UsuariosItem();
                        usuariosItem.objetoFile = a;
                        usuariosItem.dono = u.dono;
                        mUsuariosLista.add(usuariosItem);
                    }
                }
                if (adapter == null) {
                    adapter = new UsuariosAdapter(com.gabrielabalmeida.apigist.MainActivity.this, mUsuariosLista, new UsuariosAdapter.OnGistClickListener() {
                        @Override
                        public void onClicked(int position) {
                            Bundle bnd = new Bundle(1);
                            bnd.putSerializable(DetalhesActivity.BUNDLE_GIST, mUsuariosLista.get(position));

                            Intent gistIntent = new Intent(com.gabrielabalmeida.apigist.MainActivity.this, DetalhesActivity.class);
                            gistIntent.putExtras(bnd);

                            startActivity(gistIntent);
                        }
                    });
                    mListaRecycler.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }

                mPagina++;
                showNoData(View.GONE, View.VISIBLE);
            }
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Toolbar mToolbar = findViewById(R.id.toolbar);
            mToolbar.setTitle("Netshoes Challenge");
            setSupportActionBar(mToolbar);

            //Configuração do RecyclerView
            mListaRecycler = findViewById(R.id.lista);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mListaRecycler.setHasFixedSize(true);
            mListaRecycler.setLayoutManager(linearLayoutManager);
            mListaRecycler.addItemDecoration(new DivisaoListaUsuarios(this));
            mListaRecycler.addOnScrollListener(new RecyclerOnScrollListener(linearLayoutManager) {
                @Override
                void onMaisCarga(int pagina_atual) {
                    getGistList(mPagina);
                }
            });
            swRefresh = findViewById(R.id.swiperefresh);
            swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    startGistList();
                }
            });
            getGistList(mPagina);
        }

        private void startGistList() {
            mPagina = 0;
            mUsuariosLista = new ArrayList<>();
            adapter = null;
            getGistList(mPagina);
        }

        @SuppressWarnings("unused")
        public void tryAgain() {
            startGistList();
            getGistList(mPagina);
        }

        private void showNoData(int visible, int gone) {
            semData = findViewById(R.id.erro);
            semData.setVisibility(visible);
            mListaRecycler.setVisibility(gone);
        }

        public void getGistList(int page){

            Api.getGist(this, page, gistCallback);

        }
    }