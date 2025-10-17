package com.github.kolesovv.aisle.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.kolesovv.aisle.AisleApp
import com.github.kolesovv.aisle.R
import com.github.kolesovv.aisle.domain.Item
import com.github.kolesovv.aisle.presentation.screens.shopItem.ShopItemViewModel
import com.google.android.material.textfield.TextInputLayout
import javax.inject.Inject
import kotlin.getValue

class ShopItemFragment : Fragment() {

    private val TAG = "LifeCycle"

    lateinit var viewModel: ShopItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var saveButton: Button

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = Item.UNDEFINED_ID

    @Inject
    lateinit var viewmodelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as AisleApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(shopItemFragment = this)

        Log.d(TAG, "onAttach")
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewmodelFactory)[ShopItemViewModel::class.java]
        initViews(view)
        addTextChangeListeners()
        launchMode()
        observeViewModel()
    }

    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(ITEM_ID)) {
                throw RuntimeException("Param screen mode is absent")
            }
            shopItemId = args.getInt(ITEM_ID, Item.UNDEFINED_ID)
        }
    }

    fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        saveButton = view.findViewById(R.id.save_button)
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
        viewModel.item.observe(viewLifecycleOwner) {
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
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }

    companion object {

        private const val SCREEN_MODE = "extra_mode"
        private const val ITEM_ID = "extra_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(itemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(ITEM_ID, itemId)
                }
            }
        }
    }
}
