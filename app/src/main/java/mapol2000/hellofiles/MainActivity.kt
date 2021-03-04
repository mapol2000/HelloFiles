package mapol2000.hellofiles

import android.content.Context
import android.nfc.Tag
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var fname = "sayHello.txt"
    var TAG = "MainActivity" // 현재 실행중인 액티비티 이름 선언 (로그용)
    var fpath = Environment.getExternalStorageDirectory().getAbsolutePath() // 외부 저장소 절대 경로 (/storage/emulated/0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 외부저장소에 뭔가를 기록하기 위해 접근권한을 추가함
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), Context.MODE_PRIVATE)

    }

    // 내부저장소 파일 읽기
    fun internalFileRead(v: View) {
        var ifs: FileInputStream = openFileInput(fname)
        // 앱 전용모드로 지정한 경로의 파일을 읽기모드로 엶

        var buffer = ByteArray(30)
        // 파일로부터 30byte 정도 읽어오기 위해 버퍼 설정

        ifs.read(buffer)
        ifs.close()
        // 지정한 크기만큼 문자를 읽어오고 닫음

        var sayHello = buffer.toString(Charsets.UTF_8)
        // 읽어온 문자에 대해 UTF-8인코딩변환 실시

        Toast.makeText(applicationContext, "내부저장소에서 읽은 파일내용: ${sayHello}", Toast.LENGTH_SHORT).show()

        Log.d(TAG, "내부저장소에서 읽은 파일내용: ${sayHello}")

    }

    // 내부저장소 파일 쓰기
    fun internalFileWrite(v: View) {
        var ofs: FileOutputStream = openFileOutput(fname, Context.MODE_PRIVATE)
        // 앱 전용모드로 지정한 경로의 파일을 쓰기모드로 엶

        var sayHello = "Hello, World!"
        // 파일에 쓸 내용을 정의

        ofs.write(sayHello.toByteArray())
        ofs.close()
        // 파일에 내용을 바이트형태로 기록하고 닫음

        Toast.makeText(applicationContext, "내부저장소에 파일쓰기 완료!", Toast.LENGTH_SHORT).show()

        Log.d(TAG, "내부저장소 파일쓰기 완료!")
    }

    // 외부저장소 파일 읽기
    fun externalFileRead(v: View) {
        var file = File(fpath, fname)
        var fis = FileInputStream(file)

        var buffer = ByteArray(fis.available())
        fis.read(buffer)

        var sayHello = buffer.toString(Charsets.UTF_8)

        Toast.makeText(applicationContext, "외부저장소에서 읽은 내용 ${sayHello}", Toast.LENGTH_SHORT).show()
    }

    // 외부저장소 파일 쓰기
    fun externalFileWrite(v: View) {
        var file = File(fpath, fname)
        var fos = FileOutputStream(file)
        var sayHello = "Hello, World! Again!"

        fos.write(sayHello.toByteArray())
        fos.close()

        Toast.makeText(applicationContext, "외부저장소 파일쓰기 완료", Toast.LENGTH_SHORT).show()
    }

    // 외부저장소 파일 정보
    @RequiresApi(Build.VERSION_CODES.Q)
    fun externalStorageInfo(v: View) {
        var result = "읽기 쓰기 둘다 안됨"

        var state1 = Environment.getExternalStorageState() // 외부 저장소 읽기 쓰기 상태
        var state2 = Environment.isExternalStorageLegacy() // 외부저장소 다루는 방법 지원 여부
        // android <= 10 : Legacy Storage 방식, true
        // android >= 11 : Scoped Storage 방식, false

        if (state1.equals(Environment.MEDIA_MOUNTED)) { // 외부저장소 마운트 여부
            if (state1.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
                result = "읽기는 되지만 쓰기는 안됨"
            } else {
                result = "읽기 쓰기 모두 가능"
            }
            Toast.makeText(applicationContext, "${state1} ${state2} ${result}", Toast.LENGTH_SHORT).show()
        }

    }

}