package com.fin1te.hackoverflow.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.fin1te.hackoverflow.MainActivity
import com.fin1te.hackoverflow.R
import com.fin1te.hackoverflow.databinding.FragmentTicketBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.Random
import kotlin.math.abs

class TicketFragment : Fragment() {

    private var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentTicketBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTextGradient()

        // fetch data from bundle and set it to the views
        val bundle = arguments
        if (bundle != null) {
            binding.userName.text = bundle.getString("name")
            binding.category.text = bundle.getString("category")
            binding.teamName.text = "Team: ${bundle.getString("teamName")}"
        }

        if (bundle?.getString("avatarURL").isNullOrEmpty()) {
            val drawableArray = arrayOf(
                R.drawable.z_ph_1, R.drawable.z_ph_2, R.drawable.z_ph_3,
                R.drawable.z_ph_4, R.drawable.z_ph_5, R.drawable.z_ph_6,
                R.drawable.z_ph_7, R.drawable.z_ph_8, R.drawable.z_ph_9, R.drawable.z_ph_10
            )
            binding.profileImage.setImageResource(drawableArray[Random().nextInt(10)])
        } else {
            Glide.with(requireContext()).load(bundle?.getString("avatarURL"))
                .into(binding.profileImage)
        }

        val hash = bundle?.getString("email").hashCode()
        val userId = abs(hash % 1000000000).toString().padStart(10, '0')
        generateQRCode(userId, binding.qrCode)

        binding.userId.text = bundle?.getString("userId")
        if (bundle?.getString("category")?.lowercase() == "online" || bundle?.getString("category")
                ?.lowercase() == "offline"
        ) {
            binding.userType.text = "Participant"
        } else {
            binding.userType.text = "Organizing Team"
            binding.category.text = "Core Team"
        }

        binding.userId.text = "ID: $userId"

//        initializeOnClickListeners()


        binding.shareAll.setOnClickListener {
            val cardView = binding.ticketCard
            val bitmap = Bitmap.createBitmap(cardView.width, cardView.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            cardView.draw(canvas)

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

            val file = File(Environment.getExternalStorageDirectory().toString() + File.separator + "${binding.userName.text} HackOverflow.png")
            if (file.exists()) {
                file.delete()
                Log.d("TicketFragment", "file deleted")
            }

            val path: String = MediaStore.Images.Media.insertImage(requireContext().contentResolver, bitmap, "${binding.userName.text} HackOverflow", null)
            val uri: Uri = Uri.parse(path)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(intent, "Share"))
        }

        binding.shareWhatsapp.setOnClickListener {
            val cardView = binding.ticketCard
            val bitmap = Bitmap.createBitmap(cardView.width, cardView.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            cardView.draw(canvas)

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

            val file = File(Environment.getExternalStorageDirectory().toString() + File.separator + "${binding.userName.text} HackOverflow.png")
            if (file.exists()) {
                file.delete()
                Log.d("TicketFragment", "file deleted")
            }

            val path: String = MediaStore.Images.Media.insertImage(requireContext().contentResolver, bitmap, "${binding.userName.text} HackOverflow", null)
            val uri: Uri = Uri.parse(path)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.putExtra(Intent.EXTRA_TEXT, "Hey, I'm participating in HackOverflow 1.0, a 3-day long national level hackathon at PHCET, Navi Mumbai!")
            intent.setPackage("com.whatsapp")
            val shareIntent = Intent.createChooser(intent, "Share")
            val componentName = shareIntent.resolveActivity(requireContext().packageManager)

            if (componentName != null) {
                startActivity(Intent.createChooser(intent, "Share"))
            } else {
                Toast.makeText(requireContext(), "WhatsApp is not installed!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.shareTwitter.setOnClickListener {
            val cardView = binding.ticketCard
            val bitmap = Bitmap.createBitmap(cardView.width, cardView.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            cardView.draw(canvas)

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

            val file = File(Environment.getExternalStorageDirectory().toString() + File.separator + "${binding.userName.text} HackOverflow.png")
            if (file.exists()) {
                file.delete()
                Log.d("TicketFragment", "file deleted")
            }

            val path: String = MediaStore.Images.Media.insertImage(requireContext().contentResolver, bitmap, "${binding.userName.text} HackOverflow", null)
            val uri: Uri = Uri.parse(path)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.putExtra(Intent.EXTRA_TEXT, "Hey, I'm participating in HackOverflow 1.0, a 3-day long national level hackathon at PHCET, Navi Mumbai!")
            intent.setPackage("com.twitter.android")
            val shareIntent = Intent.createChooser(intent, "Share")
            val componentName = shareIntent.resolveActivity(requireContext().packageManager)

            if (componentName != null) {
                startActivity(Intent.createChooser(intent, "Share"))
            } else {
                Toast.makeText(requireContext(), "Twitter is not installed!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.shareInstagram.setOnClickListener {
            val cardView = binding.ticketCard
            val bitmap = Bitmap.createBitmap(cardView.width, cardView.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            cardView.draw(canvas)

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

            val file = File(Environment.getExternalStorageDirectory().toString() + File.separator + "${binding.userName.text} HackOverflow.png")
            if (file.exists()) {
                file.delete()
                Log.d("TicketFragment", "file deleted")
            }

            val path: String = MediaStore.Images.Media.insertImage(requireContext().contentResolver, bitmap, "${binding.userName.text} HackOverflow", null)
            val uri: Uri = Uri.parse(path)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.setPackage("com.instagram.android")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            val shareIntent = Intent.createChooser(intent, "Share")
            val componentName = shareIntent.resolveActivity(requireContext().packageManager)

            if (componentName != null) {
                startActivity(Intent.createChooser(intent, "Share"))
            } else {
                Toast.makeText(requireContext(), "Instagram is not installed!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.shareLinkedin.setOnClickListener {
            val cardView = binding.ticketCard
            val bitmap = Bitmap.createBitmap(cardView.width, cardView.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            cardView.draw(canvas)

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

            val file = File(Environment.getExternalStorageDirectory().toString() + File.separator + "${binding.userName.text} HackOverflow.png")
            if (file.exists()) {
                file.delete()
                Log.d("TicketFragment", "file deleted")
            }


            val path: String = MediaStore.Images.Media.insertImage(requireContext().contentResolver, bitmap, "${binding.userName.text} HackOverflow", null)
            val uri: Uri = Uri.parse(path)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.putExtra(Intent.EXTRA_TEXT, "Hey, I'm participating in HackOverflow 1.0, a 3-day long national level hackathon at PHCET, Navi Mumbai!")
            intent.setPackage("com.linkedin.android")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            val shareIntent = Intent.createChooser(intent, "Share")
            val componentName = shareIntent.resolveActivity(requireContext().packageManager)

            if (componentName != null) {
                startActivity(Intent.createChooser(intent, "Share"))
            } else {
                Toast.makeText(requireContext(), "LinkedIn is not installed!", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun setTextGradient() {
        textGradient(binding.hackoverflowTitle, "#9580FF", "#FF80BF")
        textGradient(binding.userName, "#FFFF80", "#FF80BF")
        textGradient(binding.userType, "#8AFF80", "#81B3FF")
        // Yellow Orange
        // textGradient(binding.hackoverflowTitle, "#FFFF80", "#FF80BF")
        // Pink Purple
        // textGradient(binding.hackoverflowTitle, "#9580FF", "#FF80BF")
        // Purple Cyan
        // textGradient(binding.hackoverflowTitle, "#80FFEA", "#9580FF")
    }


    private fun textGradient(textView: TextView, color1: String, color2: String) {
        val paint = textView.paint
        val height = paint.fontSpacing
        val textShader = LinearGradient(
            0f, 0f, 0f, height,
            intArrayOf(
                Color.parseColor(color1),
                Color.parseColor(color2)
            ), null, Shader.TileMode.CLAMP
        )
        textView.paint.shader = textShader
    }


    private fun generateQRCode(number: String, imageView: ImageView) {
        try {
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(
                number,
                BarcodeFormat.QR_CODE, 200, 200
            )
            val bitmap: Bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565)
            for (x in 0 until 200) {
                for (y in 0 until 200) {
                    bitmap.setPixel(
                        x,
                        y,
                        if (bitMatrix.get(x, y)) Color.WHITE else ContextCompat.getColor(
                            requireContext(),
                            R.color.drac_dark
                        )
                    )
                }
            }
            imageView.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}