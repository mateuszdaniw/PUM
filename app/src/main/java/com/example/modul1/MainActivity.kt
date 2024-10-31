package com.example.modul1

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var licznik: TextView
    private lateinit var pytanieText: TextView
    private lateinit var pasekStanu: ProgressBar
    private lateinit var odpowiedziRadio: RadioGroup
    private lateinit var przycisk1: Button
    private lateinit var listaPytan: List<Pytanie>
    private var indexPytanie: Int = 0
    private var punkty: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        licznik = findViewById(R.id.licznik)
        pytanieText = findViewById(R.id.pytanie)
        pasekStanu = findViewById(R.id.pasek)
        odpowiedziRadio = findViewById(R.id.odpowiedzi)
        przycisk1 = findViewById(R.id.przycisk1)

        // Lista pytan https://facet.onet.pl/warto-wiedziec/36-pytan-z-milionerow-sprobuj-odpowiedziec-poprawnie-chociaz-na-polowe/x7y5wem

        listaPytan = listOf(
            Pytanie("XVII-wieczna seria konfliktów między Ligą Katolicką a Unią Protestancką trwała:", listOf("10 lat", "30 lat", "50 lat", "100 lat"), 1),
            Pytanie("Kto jest autorem 'Pana Tadeusza'?", listOf("Adam Mickiewicz", "Juliusz Słowacki", "Henryk Sienkiewicz", "Stanisław Lem"),1),
            Pytanie("Kandydat na wysokie stanowisko często nie musi mieć odpowiednich kwalifikacji, o ile ma mocne", listOf("łydki", "kolana", "plecy", "ręce"), 2),
            Pytanie("Jaka jest stolica Australii?", listOf("Sydney", "Canberra", "Melbourne", "Perth"), 1),
            Pytanie("Która z tych witamin rozpuszcza się w tłuszczach?", listOf("Witamina C", "Witamina D", "Witamina B12", "Witamina B6"), 1)
        )

        pasekStanu.max = listaPytan.size

        ustawPytanie()

        przycisk1.setOnClickListener {
            val wybor = odpowiedziRadio.checkedRadioButtonId
            if (wybor != -1) {
                val wyborRadio = findViewById<RadioButton>(wybor)
                val wyborIndeks = odpowiedziRadio.indexOfChild(wyborRadio)
                if (wyborIndeks == listaPytan[indexPytanie].indexPoprawnejOdpowiedzi) {
                    punkty++
                }
            }

            if (indexPytanie < listaPytan.size - 1) {
                indexPytanie++
                ustawPytanie()
            } else {
                przycisk1.visibility = Button.GONE
                pytanieText.text = "Zdobyte punkty: $punkty"
            }
        }
    }

    private fun ustawPytanie() {
        val obecnePytanie = listaPytan[indexPytanie]
        licznik.text = "Pytanie ${indexPytanie + 1}/${listaPytan.size}"
        pytanieText.text = obecnePytanie.tresc
        odpowiedziRadio.clearCheck()

        val odpowiedzi = obecnePytanie.odpowiedziLista
        for (i in 0 until odpowiedziRadio.childCount) {
            val radioButton = odpowiedziRadio.getChildAt(i) as RadioButton
            radioButton.text = odpowiedzi[i]
        }

        pasekStanu.progress = indexPytanie + 1
    }

    data class Pytanie(
        val tresc: String,
        val odpowiedziLista: List<String>,
        val indexPoprawnejOdpowiedzi: Int
    )

}
