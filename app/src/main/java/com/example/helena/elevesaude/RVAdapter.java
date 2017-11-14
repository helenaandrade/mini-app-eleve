package com.example.helena.elevesaude;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MariaHelena on 11/11/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MetaViewHolder> {

    private MainActivity.OnItemTouchListener onItemTouchListener;
    List<Meta> metas;

    RVAdapter(List<Meta> metas, MainActivity.OnItemTouchListener onItemTouchListener){
        this.metas = metas;
        this.onItemTouchListener = onItemTouchListener;
    }

    public class MetaViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView tituloMetaFront;
        TextView fundamentacaoMeta;
        TextView tipoMeta;
        TextView numExecucoes;
        TextView dataLimite;
        ImageView fotoTipoMeta;
        TextView porcentagemMetaConcluida;
        RelativeLayout topCard, backCard, bottomCard;
        RelativeLayout marcarFeito;

        MetaViewHolder(final View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            tituloMetaFront = itemView.findViewById(R.id.titulo_meta);
            dataLimite = itemView.findViewById(R.id.data_limite_meta);
            tipoMeta = itemView.findViewById(R.id.tipo_meta);
            numExecucoes = itemView.findViewById(R.id.num_exec_meta);
            fotoTipoMeta = itemView.findViewById(R.id.foto_tipo_meta);
            fundamentacaoMeta = itemView.findViewById(R.id.fundamentacao_meta);
            porcentagemMetaConcluida = itemView.findViewById(R.id.porcentagem_meta_concluida);
            topCard = itemView.findViewById(R.id.top_card);
            backCard = itemView.findViewById(R.id.back_card);
            marcarFeito = itemView.findViewById(R.id.marcar_feito);
            bottomCard = itemView.findViewById(R.id.bottom_card);

            marcarFeito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemTouchListener.onConcluidoClick(v, getPosition());
                }
            });

            topCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemTouchListener.onInfoClick(v, getPosition());
                }
            });

            backCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemTouchListener.onVoltarClick(v, getPosition());
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MetaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        MetaViewHolder mvh = new MetaViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MetaViewHolder metaViewHolder, int i) {
        String concluida = porcentagemConcluida(metas.get(i).numExecucoesConcluidas, metas.get(i).numExecucoes);
        metaViewHolder.tituloMetaFront.setText(metas.get(i).titulo);
        metaViewHolder.fundamentacaoMeta.setText(metas.get(i).fundamentacao);
        metaViewHolder.numExecucoes.setText(metas.get(i).numExecucoes + " EXECUÇÕES");
        metaViewHolder.tipoMeta.setText(metas.get(i).tipo.toUpperCase());
        metaViewHolder.dataLimite.setText(metas.get(i).dataLimite.substring(0,5));
        metaViewHolder.topCard.setBackgroundColor(Color.parseColor(conversorTipoCor(metas.get(i).tipo)));
        metaViewHolder.backCard.setBackgroundColor(Color.parseColor(conversorTipoCor(metas.get(i).tipo)));
        metaViewHolder.bottomCard.setBackgroundColor(Color.parseColor(conversorTipoCor(metas.get(i).tipo)));

        if(concluida.equals("100.0")) {
            metaViewHolder.fotoTipoMeta.setImageResource(R.drawable.concluida);
        } else {
            metaViewHolder.fotoTipoMeta.setImageResource(conversorTipoFoto(metas.get(i).tipo));
        }
        metaViewHolder.porcentagemMetaConcluida.setText(porcentagemConcluida(metas.get(i).numExecucoesConcluidas, metas.get(i).numExecucoes)+"%");
    }

    @Override
    public int getItemCount() {
        return metas.size();
    }

    public int conversorTipoFoto(String tipo){
        switch (tipo) {
            case "Sono":
                return R.drawable.sono;
            case "Estresse":
                return R.drawable.estresse;
            case "Atividade Física":
                return R.drawable.gym;
            case "Comportamento Alimentar":
                return R.drawable.food;
            default:
                return R.drawable.sono;
        }
    }

    public String conversorTipoCor(String tipo){
        switch (tipo) {
            case "Sono":
                return "#27465A";
            case "Estresse":
                return "#7A1533";
            case "Atividade Física":
                return "#323440";
            case "Comportamento Alimentar":
                return "#DF3E00";
            default:
                return "#27465A";
        }
    }

    public String porcentagemConcluida(int concluidas, int total) {

            float result = ((float)concluidas/(float)total)*100;
            return Double.toString(Math.floor(result));

    }

}
