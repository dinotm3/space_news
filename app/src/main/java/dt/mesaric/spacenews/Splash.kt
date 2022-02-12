package dt.mesaric.spacenews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import dt.mesaric.spacenews.common.*
import dt.mesaric.spacenews.common.Constants.ERROR_INTERNET
import dt.mesaric.spacenews.data.remote.NewsService
import dt.mesaric.spacenews.databinding.ActivitySplashBinding

private const val DELAY = 3000L
class Splash : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupSplashScreen()
        redirect()
    }

    private fun setupSplashScreen() {
        binding.tvSplash.applyAnimation(R.anim.blink)
        binding.ivSplash.applyAnimation(R.anim.blink)
        binding.ivSplash.applyAnimation(R.anim.fade_in)
    }

    private fun redirect(){
        if (getBooleanPreference(DATA_IMPORTED)){
            Handler(Looper.getMainLooper()).postDelayed({startActivity<MainActivity>()}, DELAY)
        } else {
            if (isOnline()) {
                Intent(this, NewsService::class.java).apply{
                    NewsService.enqueueWork(this@Splash, this)
                }
            } else {
                showToast(ERROR_INTERNET, Toast.LENGTH_LONG)
                finish()
            }
        }
    }
}

