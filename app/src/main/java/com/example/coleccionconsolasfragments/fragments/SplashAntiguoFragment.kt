package com.example.coleccionconsolasfragments.fragments

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coleccionconsolasfragments.R
import com.example.coleccionconsolasfragments.databinding.FragmentSplashAntiguoBinding

class SplashAntiguoFragment : Fragment() {

    private lateinit var binding: FragmentSplashAntiguoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashAntiguoBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            findNavController().navigate(R.id.action_splashAntiguoFragment_to_loginFragment)
        }else {
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashAntiguoFragment_to_loginFragment)
            }, 3000)
        }
    }

    companion object {

        fun newInstance() =
            SplashAntiguoFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}