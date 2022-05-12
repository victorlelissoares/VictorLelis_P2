package com.example.victorlelis_p2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.victorlelis_p2.databinding.FragmentSecondBinding;

import java.util.ArrayList;

public class SecondFragment extends Fragment {
    private FragmentSecondBinding binding;
    DBHelper helper;
    Time t;
    ArrayList<Time> listTime;
    ArrayAdapter<Time> arrayAdapterTime;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    //chamado quando o fragmento está prestes a ser desenhado
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        preencheListViewTime();
        //atualiza o spinner com os novos times atualizados
        binding.btnCadastrotime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FormTime);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void preencheListViewTime(){
        helper = new DBHelper(getActivity());
        listTime = helper.listaTimes();
        helper.close();

       if(listTime!=null){
            arrayAdapterTime = new ArrayAdapter<Time>(getContext(),
                    android.R.layout.simple_list_item_1, listTime);
            binding.listTimes.setAdapter(arrayAdapterTime);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}