package com.fin1te.hackoverflow.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.fin1te.hackoverflow.databinding.FragmentAboutBinding
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentAboutBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTextAndLogo()

        if (savedInstanceState != null) {
            // restore the scroll position
            binding.aboutScrollView.scrollTo(0, savedInstanceState.getInt("scrollY"))
        }

        binding.clickBrochure.setOnClickListener {
            val pdfIntent = Intent(Intent.ACTION_VIEW)
            pdfIntent.data = Uri.parse("https://phcet.tech/pdf")
            startActivity(pdfIntent)
        }

        binding.clickCollegeDetails.setOnClickListener {
            findNavController().navigate(AboutFragmentDirections.actionAboutFragmentToAboutCollegeFragment())
        }

        binding.clickCredits.setOnClickListener {
            findNavController().navigate(AboutFragmentDirections.actionAboutFragmentToAboutCreditsFragment())
        }


//        val carousel = binding.collegeCarousel
//        carousel.registerLifecycle(viewLifecycleOwner)
//        val list = mutableListOf<CarouselItem>()
//
//        val items = listOf(
//            CarouselItem(imageUrl = "https://media.discordapp.net/attachments/1064970321641341068/1071476239619068025/unknown_275696579_1220916334982644_2951958222518989358_n.jpg?width=531&height=664"),
//            CarouselItem(imageUrl = "https://media.discordapp.net/attachments/1064970321641341068/1071476239015092294/unknown_67201298_139288220641833_7635776072924296833_n.jpg?width=930&height=664"),
//            CarouselItem(imageUrl = "https://media.discordapp.net/attachments/735859351998169088/1071474706953273365/y1D80qGuZ2nfV6w24hu0H8eAD46NAKHZrBEvgMVYKNmddFpCMwIX-AV-qe6tFmrwi3wgqSgyG5NF-9iv0RFry84lrSwvKG6-zPksvVG91-DXOriw5iKrfHknHkIhF1pZsxvLCufJV7GHU6OoJTloNMeZ0cfF_3PisFboC97UQV2-pzwsSkTvJAP1PenoViHH8iYCYjYLi7pP79G35LhKeg9ytLamyHl6vltVy-OfXk1Eh8WKk6m1fnE1MyMi64_jUxClYMknEM3dJOlie18e0ZH7Enytpe1W1y_CbxDxsmmZ7SgB2asH_AWr1kwYm1YYCHpDb3-ksozp4FPkYmYQcBH7j7jrBwWOvz_k-jZTUHUhGx3GGGfcUBfYekcFZqBY09uwbD4tFwd3_0-O4_MPDSKh-N84ZGz2bHTZo508yplWnmZSnV5m3v6hg9fZElmLKyCbDVx7vvc3O7QRGNmU7AU9HdCwvMWNCAFsKYdf1JxWfiMZlE05DfVA1FOa..png?width=498&height=664"),
//            CarouselItem(imageUrl = "https://media.discordapp.net/attachments/1064970321641341068/1071474853460320277/ec05adf3-e275-42e1-bb92-102b356342a6.png?width=885&height=664"),
//            CarouselItem(imageUrl = "https://media.discordapp.net/attachments/1064970321641341068/1071476239342252063/unknown_243385689_183831480559270_656160042826974438_n.jpg"),
//            CarouselItem(imageUrl = "https://media.discordapp.net/attachments/1064970321641341068/1071476239900090479/unknown_281339516_698744538120434_4728064807204164762_n.jpg?width=531&height=664"),
//            CarouselItem(imageUrl = "https://media.discordapp.net/attachments/1064970321641341068/1071474376198865049/b2b90040-a396-419e-a7e1-e44b9f18febc.png?width=498&height=664")
//        )
//        list.addAll(items)
//
//        carousel.setData(list)
    }


    private fun setTextAndLogo() {
        textGradient(binding.hackoverflowTitle, "#80FFEA", "#9580FF")
        textGradient(binding.phcetAboutTitle, "#FFFF80", "#FF80BF")
        textGradient(binding.creditsTitle, "#8AFF80", "#81B3FF")

        val hack_logo = mapOf(1 to "logo_hof_1", 2 to "logo_hof_2", 3 to "logo_hof_3")
        val hofLogo = hack_logo[(1..3).random()] ?: "logo_hof_3"
        val hofLogoId = resources.getIdentifier(hofLogo, "drawable", requireContext().packageName)
        binding.hofLogo.setImageResource(hofLogoId)

        val college_logo = mapOf(1 to "logo_pillai", 2 to "logo_mes")
        val collegeLogo = college_logo[(1..2).random()] ?: "logo_pillai"
        val collegeLogoId =
            resources.getIdentifier(collegeLogo, "drawable", requireContext().packageName)
        binding.collegeLogo.setImageResource(collegeLogoId)

        binding.hofLogo.scaleX = 0f
        binding.hofLogo.scaleY = 0f
        binding.hofLogo.animate().scaleX(1f).scaleY(1f).setDuration(1000).setStartDelay(400).start()

        binding.collegeLogo.scaleX = 0f
        binding.collegeLogo.scaleY = 0f
        binding.collegeLogo.animate().scaleX(1f).scaleY(1f).setDuration(1000).setStartDelay(400)
            .start()
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}