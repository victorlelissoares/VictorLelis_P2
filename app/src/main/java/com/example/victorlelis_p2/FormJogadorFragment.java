package com.example.victorlelis_p2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.victorlelis_p2.databinding.FragmentFormJogadorBinding;


public class FormJogadorFragment extends Fragment {

    private FragmentFormJogadorBinding binding;

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

        binding.btnfinalizarjogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  NavHostFragment.findNavController(FormJogadorFragment.this)
                        .navigate(R.id.action_FormJogadorFragment_to_ThirdFragment);*/
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}