package com.example.victorlelis_p2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.victorlelis_p2.databinding.FragmentFormJogadorBinding;

import java.util.ArrayList;


public class FormJogadorFragment extends Fragment {
    private FragmentFormJogadorBinding binding;
    DBHelper helper;
    ArrayList<Time> listTime;
    Time tJogador;
    Jogador jogador;
    ArrayAdapter<Time> dataAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFormJogadorBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadDataSpinner();//carrega o spinner com os times cadastrados
        jogador = new Jogador();

        binding.btnfinalizarjogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tJogador = (Time) binding.spinnerTimes.getSelectedItem();//pega do spinner, o time selecionado

                if(tJogador != null){//caso tenha algum time cadastrado
                    jogador.setNome(binding.txtNome.getText().toString());
                    jogador.setIdTime(tJogador.getIdTime());
                    jogador.setNomeTime(tJogador.getName());
                    jogador.setCpf(binding.txtCpf.getText().toString());
                    jogador.setAnoNascimento( Integer.parseInt(binding.txtAnoNascimento.getText().toString()) );
                    helper.insereJogador(jogador);

                    Toast toast = Toast.makeText(getContext(),
                            "Jogador "+ jogador.getNome() +" cadastrado com sucesso!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{//caso não
                    Toast toast = Toast.makeText(getContext(), "Cadastre um time antes de cadastrar um jogador!", Toast.LENGTH_LONG);
                    toast.show();
                }


                NavHostFragment.findNavController(FormJogadorFragment.this)
                        .navigate(R.id.action_FormJogadorFragment_to_ThirdFragment);
            }
        });

    }

    public void loadDataSpinner(){
        helper = new DBHelper(getContext());
        listTime = helper.listaTimes();

        dataAdapter = new ArrayAdapter<Time>(getContext(), android.R.layout.simple_spinner_item,
                listTime);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinnerTimes.setAdapter(dataAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}