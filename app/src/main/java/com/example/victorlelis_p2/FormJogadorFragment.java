package com.example.victorlelis_p2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.victorlelis_p2.databinding.FragmentFormJogadorBinding;

import java.util.ArrayList;


public class FormJogadorFragment extends Fragment {
    private FragmentFormJogadorBinding binding;
    DBHelper helper;
    ArrayList<Time> listTime;
    Time tJogador;

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

        binding.btnfinalizarjogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FormJogadorFragment.this)
                        .navigate(R.id.action_FormJogadorFragment_to_ThirdFragment);
            }
        });

    }

    public void loadDataSpinner(){
        helper = new DBHelper(getContext());
        listTime = helper.listaTimes();

        ArrayAdapter<Time> dataAdapter = new ArrayAdapter<Time>(getContext(), android.R.layout.simple_spinner_item,
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