package com.example.coleccionconsolasfragments.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.coleccionconsolasfragments.MainActivity
import com.example.coleccionconsolasfragments.R
import com.example.coleccionconsolasfragments.databinding.FragmentFormularioBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest


class FormularioFragment : Fragment() {

    private lateinit var binding: FragmentFormularioBinding
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormularioBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnCamara.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                abrirCamara()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        android.Manifest.permission.CAMERA
                    )
                ) {
                    Snackbar.make(binding.root, R.string.permisoRechazado, Snackbar.LENGTH_LONG).show()
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.CAMERA),
                        REQUEST_IMAGE_CAPTURE
                    )
                }
            }
        }

        binding.btnGaleria.setOnClickListener {
            abrirGaleria()
        }

        Glide
            .with(binding.root)
            .load(R.drawable.perfilusuario)
            .centerCrop()
            .into(binding.ivFoto)

        var usuarioVacio = true
        var contraseAVacia = true

        binding.tfUsuarioRegistro.editText?.addTextChangedListener { text ->
            if (text.isNullOrEmpty()) {
                binding.tfUsuarioRegistro.error = "Este campo no puede estar vacío"
                usuarioVacio = true
            } else {
                binding.tfUsuarioRegistro.error = null
                usuarioVacio = false
            }
        }

        binding.tfContraseARegistro.editText?.addTextChangedListener { text ->
            if (text.isNullOrEmpty()) {
                binding.tfContraseARegistro.error = "Este campo no puede estar vacío"
                contraseAVacia = true
            } else {
                binding.tfContraseARegistro.error = null
                contraseAVacia = false
            }
        }

        binding.etNacimientoRegistro.setOnClickListener(){
            datePickerDialog(binding.etNacimientoRegistro)
        }

        binding.btnRegistrar.setOnClickListener {
            if(usuarioVacio) {
                Snackbar.make(it, this.getString(R.string.usuVacio), Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.cerrar) {}
                    .setAnchorView(binding.tfUsuarioRegistro)
                    .show()
            }else if (!emailValido(binding.etUsuarioRegistro.text.toString())){
                Snackbar.make(it, this.getString(R.string.emailValido), Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.cerrar) {}
                    .setAnchorView(binding.tfUsuarioRegistro)
                    .show()
            }else if(contraseAVacia){
                Snackbar.make(it, this.getString(R.string.contraVacia), Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.cerrar){}
                    .setAnchorView(binding.tfContraseARegistro)
                    .show()
            }else {
                Snackbar.make(it, this.getString(R.string.loginCorrecto), Snackbar.LENGTH_LONG)
                    .show()
                findNavController().navigate(R.id.action_formularioFragment_to_contactoFragment)
            }

        }

    }

    private val usarFoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            Glide
                .with(binding.root)
                .load(imageBitmap)
                .centerCrop()
                .into(binding.ivFoto)
        }
    }

    private fun abrirCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            usarFoto.launch(intent)
        }catch (e: ActivityNotFoundException){
            Snackbar.make(binding.root, R.string.faltaAplicación, Snackbar.LENGTH_LONG).show()
        }

    }


    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            Glide
                .with(binding.root)
                .load(uri)
                .centerCrop()
                .into(binding.ivFoto)
        }
    }

    private fun abrirGaleria() {
        galleryLauncher.launch("image/*")
    }

    fun emailValido(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun datePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                editText.setText(dateFormat.format(selectedDate.time))
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FormularioFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamara()
            } else {
                Snackbar.make(binding.root, R.string.permisoDenegado, Snackbar.LENGTH_LONG).show()

            }
        }
    }
}