package com.example.coleccionconsolasfragments.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.coleccionconsolasfragments.R
import com.example.coleccionconsolasfragments.databinding.FragmentContactoBinding
import com.google.android.material.snackbar.Snackbar

class ContactoFragment : Fragment() {

    private lateinit var binding: FragmentContactoBinding
    private val CALL_PHONE_PERMISSION_REQUEST = 123
    private val LOCATION_PERMISSION_REQUEST = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.tfTelf.setStartIconOnClickListener {

            if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED){
                makePhoneCall()
            } else {

                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.CALL_PHONE)) {

                    Snackbar.make(binding.root, R.string.permisoRechazado, Snackbar.LENGTH_LONG).show()

                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.CALL_PHONE),
                        CALL_PHONE_PERMISSION_REQUEST
                    )
                }
            }
        }

        binding.tfEmail.setStartIconOnClickListener {
            sendEmail("nintendo@gmail.com", "Email prueba", "Es una prueba de email")

        }

        binding.tfLocal.setStartIconOnClickListener {

            if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
                openLocation(40.51871310608562, -3.650686180841603)

            } else {

                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    Snackbar.make(binding.root, R.string.permisoRechazado, Snackbar.LENGTH_LONG).show()

                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                        LOCATION_PERMISSION_REQUEST
                    )
                }
            }


        }

        binding.tfWhatsApp.setStartIconOnClickListener {
            openWhatsApp("664765764")
        }

        Glide
            .with(binding.root)
            .load(R.drawable.map)
            .centerCrop()
            .into(binding.ivMapa)

    }

    private fun makePhoneCall() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:664765764"))

        try {
            startActivity(intent)
        }catch (e: ActivityNotFoundException){
            Snackbar.make(binding.root, R.string.faltaAplicación, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun sendEmail(destEmail: String, asunto: String, mensaje: String ) {

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(destEmail)) // Destinatarios
            putExtra(Intent.EXTRA_SUBJECT, asunto) // Asunto
            putExtra(Intent.EXTRA_TEXT, mensaje)
        }
        try {
            startActivity(intent)
        }catch (e: ActivityNotFoundException) {
            Snackbar.make(binding.root, R.string.faltaAplicación, Snackbar.LENGTH_LONG).show()
        }

    }

    private fun openLocation(latitud: Double, longitud: Double) {
        val uri = Uri.parse("geo:$latitud,$longitud?z=15")

        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Snackbar.make(binding.root, R.string.faltaAplicación, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun openWhatsApp(phoneNumber: String) {

        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber")

        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Snackbar.make(binding.root, R.string.faltaAplicación, Snackbar.LENGTH_LONG).show()        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CALL_PHONE_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall()
            }else{ Snackbar.make(binding.root, R.string.permisoDenegado, Snackbar.LENGTH_LONG).show()

            }
        }
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openLocation(40.51871310608562, -3.650686180841603)
            }else{ Snackbar.make(binding.root, R.string.permisoDenegado, Snackbar.LENGTH_LONG).show()

            }
        }

    }

    companion object {

        fun newInstance() =
            ContactoFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}