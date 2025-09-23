package com.github.kolesovv.aisle.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.core.widget.doBeforeTextChanged
import androidx.lifecycle.ViewModelProvider
import com.github.kolesovv.aisle.R
import com.github.kolesovv.aisle.domain.Item
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var saveButton: Button

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = Item.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            v.updatePadding(
                left = systemBars.left,
                top = maxOf(systemBars.top, imeInsets.top),
                right = systemBars.right,
                bottom = maxOf(systemBars.bottom, imeInsets.bottom)
            )
            insets
        }

        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews()
        addTextChangeListeners()
        launchMode()
        observeViewModel()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_ITEM_ID)) {
                throw RuntimeException("Param screen mode is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_ITEM_ID, Item.UNDEFINED_ID)
        }
    }

    fun initViews() {
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        etName = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        saveButton = findViewById(R.id.save_button)
    }

    private fun addTextChangeListeners() {
        etName.doBeforeTextChanged { _, _, _, _ -> viewModel.resetErrorInputName() }
        etCount.doBeforeTextChanged { _, _, _, _ -> viewModel.resetErrorInputCount() }
    }

    private fun launchMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun launchAddMode() {

        saveButton.setOnClickListener {
            viewModel.addItem(
                etName.text?.toString(),
                etCount.text?.toString()
            )
        }
    }

    private fun launchEditMode() {

        viewModel.getItem(shopItemId)
        viewModel.item.observe(this) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }

        saveButton.setOnClickListener {
            viewModel.updateItem(
                etName.text?.toString(),
                etCount.text?.toString()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }

        viewModel.errorInputCount.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }

        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_ITEM_ID = "extra_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, itemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_ITEM_ID, itemId)
            return intent
        }
    }
}
