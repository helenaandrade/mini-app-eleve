package com.example.helena.elevesaude;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

public class CriarMetaActivity extends AppCompatActivity {

    private EditText nome, fundamentacao, data_limite;
    private Spinner tipo;
    private Button criar, cancelar;
    private Calendar myCalendar;
    private EditText num_execucoes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_meta);
        setTitle("Criar Meta");

        nome = (EditText) findViewById(R.id.nome);
        fundamentacao = (EditText) findViewById(R.id.fundamentacao);
        tipo = (Spinner) findViewById(R.id.tipo);
        num_execucoes = (EditText) findViewById(R.id.num_exec);
        data_limite = (EditText) findViewById(R.id.data_limite);
        criar = (Button) findViewById(R.id.criar);
        cancelar = (Button) findViewById(R.id.cancelar);

        setListeners();

        String[] items = new String[]{"Atividade Física", "Comportamento Alimentar", "Sono", "Estresse"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        tipo.setAdapter(adapter);
    }

    public void setListeners(){
        myCalendar = Calendar.getInstance();

        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nome.getText().toString().equals("")|| fundamentacao.getText().toString().equals("") || num_execucoes.getText().toString().equals("") || data_limite.getText().toString().equals("")) {
                    Toast.makeText(CriarMetaActivity.this, R.string.campos_obrigatorios, Toast.LENGTH_SHORT).show();
                } else if (num_execucoes.getText().toString().equals("0")) {
                    Toast.makeText(CriarMetaActivity.this, R.string.num_exec_zero, Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent();
                    i.putExtra("nome", nome.getText().toString());
                    i.putExtra("fundamentacao", fundamentacao.getText().toString());
                    i.putExtra("tipo", tipo.getSelectedItem().toString());
                    i.putExtra("num_execucoes", num_execucoes.getText().toString());
                    i.putExtra("data_limite", data_limite.getText().toString());
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        data_limite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CriarMetaActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        data_limite.setText(sdf.format(myCalendar.getTime()));
    }
}
