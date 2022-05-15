package com.example.victorlelis_p2;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.victorlelis_p2.databinding.FragmentFormTimeBinding;

public class FormTimeFragment extends Fragment {

    private FragmentFormTimeBinding binding;
    Time t;
    DBHelper helper;
    Time altTime;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFormTimeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        helper = new DBHelper(getContext());

        Bundle bundle = getArguments();

        if(bundle!=null) {
            altTime = (Time) bundle.getSerializable("time");
        }

        t = new Time();

        if(altTime!=null){
            binding.btnFinalizarTime.setText("Alterar Time");
            binding.txtNomeTime.setText(altTime.getName());
        }

        binding.btnFinalizarTime.setOnClickListener(view1 -> {
            /*Quando o botão de finalizar cadastro do time é pressionado
                o campo do nome do time é lvo, e e enviado ao banco de dados*/
            String nomeTime = binding.txtNomeTime.getText().toString();//recupera o nome digitado
            if(altTime!=null){
                altTime.setName(nomeTime);
                helper.atualizarTime(altTime);
                Toast toast = Toast.makeText(getContext(),
                        "Time "+ altTime.getName()+" alterado com sucesso!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                t.setName(nomeTime);
                helper.insereTime(t);

                /*logo em seguida, uma mensagem de cadastro realizado com sucesso, e enviado ao toast*/
                Toast toast = Toast.makeText(getContext(),
                        nomeTime + " cadastrado com sucesso!", Toast.LENGTH_SHORT);
                toast.show();
            }

            NavHostFragment.findNavController(FormTimeFragment.this)
                    .navigate(R.id.action_FormTimeFragment_to_SecondFragment);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}