package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onEqualClick(view: View) {

       OnEqual()
        binding.tvdata.text = binding.tvresult.text.toString().drop(1)

    }


    fun onDigitClick(view: View) {

        if (stateError){

            binding.tvdata.text = (view as Button).text
            stateError = false
            }else{
                binding.tvdata.append((view as Button).text)
        }

        lastNumeric = true
        OnEqual()

    }


    fun onAllclearClick(view: View) {

        binding.tvdata.text = ""
        binding.tvresult.text = ""
        lastDot = false
        lastNumeric = false
        stateError = false
        binding.tvresult.visibility = View.GONE

    }


    fun onOperatorClick(view: View) {

        if (!stateError && lastNumeric){

            binding.tvdata.append((view as Button).text)
            lastNumeric = false
            lastDot = false
            OnEqual()
        }


    }


    fun onBackClick(view: View) {

        binding.tvdata.text = binding.tvdata.text.toString().dropLast(1)

        try{
            val lastchar = binding.tvdata.text.toString().last()

            if (lastchar.isDigit()){

                OnEqual()
            }

        }catch (e : Exception){

            binding.tvresult.text = ""
            binding.tvresult.visibility = View.GONE
            Log.e("last char error",e.toString())
        }

    }


    fun onClearClick(view: View) {

        binding.tvdata.text = ""
        lastNumeric = false

    }


    fun OnEqual(){
        if (lastNumeric && !stateError){

            val txt = binding.tvdata.text.toString()

            expression = ExpressionBuilder(txt).build()

            try {

                val result = expression.evaluate()

                binding.tvresult.visibility = View.VISIBLE

                binding.tvresult.text = "=" +result.toString()


            }catch (ex: ArithmeticException){

                Log.e("evaluate error", ex.toString())
                binding.tvresult.text = "error"
                stateError = true
                lastNumeric = false
            }




        }


    }
}