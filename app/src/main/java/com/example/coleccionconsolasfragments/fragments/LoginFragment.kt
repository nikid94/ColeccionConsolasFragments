package com.example.coleccionconsolasfragments.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coleccionconsolasfragments.R
import com.example.coleccionconsolasfragments.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var usuarioVacio = true
        var contraseAVacia = true

        binding.tfUsuario.editText?.addTextChangedListener { text ->
            if (text.isNullOrEmpty()) {
                binding.tfUsuario.error = "Este campo no puede estar vacío"
                usuarioVacio = true
            } else {
                binding.tfUsuario.error = null
                usuarioVacio = false
            }
        }

        binding.tfContraseA.editText?.addTextChangedListener { text ->
            if (text.isNullOrEmpty()) {
                binding.tfContraseA.error = "Este campo no puede estar vacío"
                contraseAVacia = true
            } else {
                binding.tfContraseA.error = null
                contraseAVacia = false
            }
        }

        binding.btnLoguear.setOnClickListener {
            if(usuarioVacio){
                Snackbar.make(it, this.getString(R.string.usuVacio), Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.cerrar){}
                    .setAnchorView(binding.tfUsuario)
                    .show()
            }else if(contraseAVacia){
                Snackbar.make(it, this.getString(R.string.contraVacia), Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.cerrar){}
                    .setAnchorView(binding.tfContraseA)
                    .show()
            }else {
                Snackbar.make(it, this.getString(R.string.loginCorrecto), Snackbar.LENGTH_LONG)
                    .show()
                findNavController().navigate(R.id.action_loginFragment_to_contactoFragment)
            }
        }

        binding.btnRegistrar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_formularioFragment)
        }

        fun btnPulsado(button: Button){
            button.setOnClickListener {
                Snackbar.make(it, this.getString(R.string.botonPulsado) +" "+button.text ,
                    Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        btnPulsado(binding.btnContraseA)
        btnPulsado(binding.btnFacebook)
        btnPulsado(binding.btnGoogle)


    }


    companion object {

        fun newInstance(): LoginFragment {
            return LoginFragment()
        }

    }
}