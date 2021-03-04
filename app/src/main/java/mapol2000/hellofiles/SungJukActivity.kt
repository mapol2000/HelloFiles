package mapol2000.hellofiles

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.io.FileOutputStream

class SungJukActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var kor: EditText
    lateinit var eng: EditText
    lateinit var mat: EditText

    var tot = 0
    var avg = 0.0
    var grd = "가"

    var fname = "sungjuk.dat"
    var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sung_juk)

        name = findViewById(R.id.Name)
        kor = findViewById(R.id.Kor)
        eng = findViewById(R.id.Eng)
        mat = findViewById(R.id.Mat)

    }


    // sungjuk.dat에 다음내용 저장
    // 이름/국어/영어/수학/총점/평균/학점
    // 입력한 데이터들이 누적되려면 MODE_APPEND 사용
    fun saveSungJuk(v:View) {

        computeSungJuk()
        writeSungJuk()


    }

    // 입력한 내용을 파일에 기록steve
    private fun writeSungJuk() {
        var ofs: FileOutputStream = openFileOutput(fname, Context.MODE_PRIVATE or  Context.MODE_APPEND)
        var sj = "${name.text}/${kor.text}/${eng.text}/${mat.text}/${tot}/${avg}/${grd}\n"

        ofs.write(sj.toByteArray())
        ofs.close()

        Toast.makeText(applicationContext, "성적데이터 추가완료!", Toast.LENGTH_SHORT).show()
    }

    // 총점 평균 학점 계산하는 함수
    private fun computeSungJuk() {
        tot = kor.text.trim().toString().toInt() + eng.text.trim().toString().toInt() + mat.text.trim().toString().toInt()
        avg = tot / 3.0
        grd = when ((avg / 10).toInt()) {
            9,10 -> "수"
            8 -> "우"
            7 -> "미"
            6 -> "양"
            else -> "가"
        }

        Log.d("SungJukActivity", "${tot}, ${avg}, ${grd}")
    }

    // 입력창에 입력했던 내용을 모두 지움
    fun resetSungJuk(v:View) {

        name.setText("")
        kor.setText("")
        eng.setText("")
        mat.setText("")

        Toast.makeText(applicationContext, "내부저장소에 성적 데이터 초기화 완료!", Toast.LENGTH_SHORT).show()
    }

}