package mapol2000.hellofiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

lateinit var sjList: TextView

var fname = "sungjuk.dat"

class SungJuk2Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sung_juk2)

        sjList = findViewById(R.id.sjList)

        listSungJuk()

    }

    private fun listSungJuk() {
        var sb = StringBuffer()
        var ifs: FileInputStream = openFileInput(fname)
        var br = BufferedReader(InputStreamReader(ifs))

        while (br.ready()) {
            sb.append("${br.readLine()}").append("\n")
        }

        br.close()
        ifs.close()

        Log.d("SungJuk2Activity", sb.toString())

        sjList.text = sb.toString()

    }


}