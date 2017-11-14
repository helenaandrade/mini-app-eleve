package com.example.helena.elevesaude;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    private List<Meta> metas;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private OnItemTouchListener itemTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Minhas metas");

        metas = new ArrayList<>();

        rv=(RecyclerView)findViewById(R.id.rv);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        setListeners();
        metas.add(new Meta("COMPROMETER-SE A ESCOLHER LANCHES NATURAIS OU MINIMAMENTE PROCESSADOS EM X DIAS", "Lanches são ótimas oportunidades de incluir alimentos naturais bem leves e altamente nutritivos em nossa rotina", "Comportamento Alimentar", 10, "12/12/2010", 0));

        initializeAdapter();
    }

    @Override
    public void onActivityResult (int reqCode, int resultCode, Intent data) {
        if(reqCode == 0) {
            if(resultCode == RESULT_OK) {
                String nome = data.getStringExtra("nome");
                String fundamentacao = data.getStringExtra("fundamentacao");
                String tipo = data.getStringExtra("tipo");
                String num_execucoes = data.getStringExtra("num_execucoes");
                String data_limite = data.getStringExtra("data_limite");
                metas.add(new Meta(nome, fundamentacao, tipo, parseInt(num_execucoes), data_limite, 0));
                initializeAdapter();
            }
        }
    }

    public void setListeners(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CriarMetaActivity.class);
                startActivityForResult(intent,0);
            }
        });

        itemTouchListener = new OnItemTouchListener() {
            @Override
            public void onConcluidoClick(View view, int position) {
                if(metas.get(position).numExecucoesConcluidas == metas.get(position).numExecucoes) {
                    Toast.makeText(MainActivity.this, R.string.meta_concluida, Toast.LENGTH_SHORT).show();
                } else {
                    metas.get(position).numExecucoesConcluidas += 1;
                    initializeAdapter();
                }
            }

            @Override
            public void onInfoClick(View view, int position) {
                trocaView(view, R.id.back_card, R.id.front_card);
            }

            @Override
            public void onVoltarClick(View view, int position) {
                trocaView(view, R.id.front_card, R.id.back_card);
            }
        };
    }

    public void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(metas, itemTouchListener);
        rv.setAdapter(adapter);
    }

    public void trocaView (View view, int visivel, int invisivel) {
        rv.findContainingItemView(view).findViewById(visivel).setVisibility(View.VISIBLE);

        Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.card_flip);
        rv.findContainingItemView(view).findViewById(R.id.cv).startAnimation(slideDown);
        rv.findContainingItemView(view).findViewById(invisivel).setVisibility(View.GONE);
    }

    public interface OnItemTouchListener {
        void onConcluidoClick(View view, int position);
        void onInfoClick(View view, int position);
        void onVoltarClick(View view, int position);
    }

}
