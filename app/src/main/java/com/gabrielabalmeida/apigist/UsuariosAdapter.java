package com.gabrielabalmeida.apigist;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by gabriela on 03/01/18.
 */

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.ViewHolder> {

    Context mContext;
    private List<UsuariosItem> mUsuariosItem;

    OnGistClickListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mFotoUsuario;
        TextView mNomeUsuario;
        TextView mTipo;
        TextView mLinguagem;


        OnGistClickListener mListener;

        View mView;

        public ViewHolder(View v, OnGistClickListener listener) {
            super(v);

            mView = v;
            mListener = listener;
            mFotoUsuario = v.findViewById(R.id.im_usuario);
            mNomeUsuario = v.findViewById(R.id.nm_usuario);
            mTipo = v.findViewById(R.id.tipo_gist);
            mLinguagem = v.findViewById(R.id.linguagem);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null) {
                mListener.onClicked(getAdapterPosition());
            }
        }
    }

    public UsuariosAdapter(Context mContext, List<UsuariosItem> items, OnGistClickListener listener) {
        this.mUsuariosItem = items;
        this.mContext = mContext;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_gist, parent, false);

        return new ViewHolder(layout, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        UsuariosItem item = mUsuariosItem.get(position);

        if(getItemViewType(position) == 0) {
            holder.mView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.velho));
        } else {
            holder.mView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.branco));
        }

        String ownerName = mContext.getString(R.string.anonimo);
        if(item.dono != null) {
            ownerName = item.dono.login;

            if(!TextUtils.isEmpty(item.dono.avatar)) {
                Glide.with(mContext)
                        .load(item.dono.avatar)
                        .centerCrop()
                        .bitmapTransform(new TransformarBipMap(mContext, 30, 0, TransformarBipMap.CornerType.ALL))
                        .into(holder.mFotoUsuario);
            }
        }
        holder.mNomeUsuario.setText(mContext.getString(R.string.gist_dono, ownerName, item.objetoFile.nomeArquivo));
        holder.mLinguagem.setText(item.objetoFile.linguagem);
        holder.mTipo.setText(item.objetoFile.tipo);

    }

    @Override
    public int getItemViewType(int position) {
        return (position % 2 == 0) ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return mUsuariosItem.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setListener(OnGistClickListener listener) {
        this.mListener = listener;
    }

    public interface OnGistClickListener{
        void onClicked(int position);
    }

}