package net.meilcli.darch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import net.meilcli.darch.common.parameters.UnitParameter
import net.meilcli.darch.loggers.Logger
import kotlin.math.min

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainViewModel = MainViewModel(this, Logger())
        launch {
            mainViewModel.textChangeCount.openSubscription().consumeEach {
                textChangeCount.text = it
            }
        }
        launch {
            mainViewModel.text.openSubscription().consumeEach {
                val start = text.selectionStart
                val end = text.selectionEnd
                text.setText(it)
                text.setSelection(min(start, text.length()), min(end, text.length()))
            }
        }
        button.setOnClickListener {
            if (mainViewModel.wasTextChangedQuery(UnitParameter)) {
                Toast.makeText(applicationContext, "WasChanged", Toast.LENGTH_SHORT).show()
            }
            mainViewModel.helloWorldCommand.execute(UnitParameter)
        }
        text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s ?: return
                mainViewModel.text.offer(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}
