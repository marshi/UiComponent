package dev.marshi.android.shard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dev.marshi.android.shard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding: ActivityMainBinding =
      DataBindingUtil.setContentView(this, R.layout.activity_main)
    binding.recyclerView.adapter = Adapter(
      this,
      mutableListOf(
        NormalItem(),
        NormalItem(),
        NormalItem(),
        NormalItem()
      )
    )
  }
}
