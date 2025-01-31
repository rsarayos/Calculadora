package rayos.alejandro.practica_calculadora_saucedarayos

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvOperacion: TextView
    private lateinit var tvResultado: TextView
    private var operacionActual = StringBuilder() 
    private var ultimoOperador: String? = null 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        val btn0 = findViewById<Button>(R.id.btn0)
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btn5 = findViewById<Button>(R.id.btn5)
        val btn6 = findViewById<Button>(R.id.btn6)
        val btn7 = findViewById<Button>(R.id.btn7)
        val btn8 = findViewById<Button>(R.id.btn8)
        val btn9 = findViewById<Button>(R.id.btn9)
        val btnSuma = findViewById<Button>(R.id.btnSumar)
        val btnResta = findViewById<Button>(R.id.btnRestar)
        val btnDivision = findViewById<Button>(R.id.btnDividir)
        val btnMultiplicar = findViewById<Button>(R.id.btnMultiplicar)
        val btnPunto = findViewById<Button>(R.id.btnPunto)
        val btnBorrar = findViewById<Button>(R.id.btnBorrar)
        val btnIgual = findViewById<Button>(R.id.btnIgual)

        tvOperacion = findViewById(R.id.tvOperacion)
        tvResultado = findViewById(R.id.tvResultado)

        val numberButtons = listOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)
        numberButtons.forEach { button ->
            button.setOnClickListener {
                agregarAOperacion(button.text.toString())
            }
        }
        
        btnSuma.setOnClickListener { manejarOperador("+") }
        btnResta.setOnClickListener { manejarOperador("-") }
        btnMultiplicar.setOnClickListener { manejarOperador("*") }
        btnDivision.setOnClickListener { manejarOperador("/") }
        
        btnPunto.setOnClickListener {
            if (!operacionActual.contains(".")) {
                agregarAOperacion(".")
            }
        }
        
        btnIgual.setOnClickListener {
            calcularResultado()
        }
        
        btnBorrar.setOnClickListener {
            limpiarCalculadora()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun agregarAOperacion(value: String) {
        operacionActual.append(value)
        tvOperacion.text = operacionActual.toString()
    }

    private fun manejarOperador(operator: String) {
        if (operacionActual.isNotEmpty()) {
            if (ultimoOperador != null) {
                calcularResultado()
            }
            ultimoOperador = operator
            agregarAOperacion(operator)
        }
    }

    private fun calcularResultado() {
        if (operacionActual.isNotEmpty() && ultimoOperador != null) {
            try {
                val numeros = operacionActual.split(ultimoOperador!!)
                if (numeros.size == 2) {
                    val num1 = numeros[0].toDouble()
                    val num2 = numeros[1].toDouble()
                    val result = when (ultimoOperador) {
                        "+" -> num1 + num2
                        "-" -> num1 - num2
                        "*" -> num1 * num2
                        "/" -> num1 / num2
                        else -> throw IllegalArgumentException("Invalid operator")
                    }
                    tvResultado.text = result.toString()
                    operacionActual.clear()
                    operacionActual.append(result)
                    ultimoOperador = null
                }
            } catch (e: Exception) {
                tvResultado.text = "Error"
            }
        }
    }

    private fun limpiarCalculadora() {
        operacionActual.clear()
        ultimoOperador = null
        tvOperacion.text = ""
        tvResultado.text = ""
    }

}