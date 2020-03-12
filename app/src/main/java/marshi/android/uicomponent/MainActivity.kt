package marshi.android.uicomponent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import marshi.android.uicomponent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding: ActivityMainBinding =
      DataBindingUtil.setContentView(this, R.layout.activity_main)
    binding.recyclerView.adapter = Adapter(
      this,
      mutableListOf(NormalItem(), NormalItem(), NormalItem(), NormalItem())
    )
  }
}
