package com.example.moneycounter4.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moneycounter4.R
import com.example.moneycounter4.base.BaseFragment
import com.example.moneycounter4.beannew.CounterDataItem
import com.example.moneycounter4.model.DataReader
import com.example.moneycounter4.model.TypePicFinder
import com.example.moneycounter4.utils.TimeUtil
import com.example.moneycounter4.viewmodel.GlobalViewModel
import kotlinx.android.synthetic.main.fragment_item_detail.*

class FragmentItemDetail : BaseFragment() {

    lateinit var viewModel: GlobalViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(GlobalViewModel::class.java)

        return inflater.inflate(R.layout.fragment_item_detail, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dataItem = arguments?.get("dataItem") as CounterDataItem

        textViewTime.text = dataItem.time?.let { TimeUtil.dayStr(it) }
        textViewMoney.text = String.format("%.2f", dataItem.money?.let { kotlin.math.abs(it) })
        textViewExpend.text = if (dataItem.money!! < 0) "支出" else "收入"
        textViewType.text = dataItem.type
        textViewTips.text = if (dataItem.tips!!.isEmpty()) "无" else dataItem.tips
        imageViewTypeImage.setImageResource(
            TypePicFinder.findTypePicIdByName(
                viewModel.typeListIn, viewModel.typeListOut,
                dataItem.type
            )
        )
        constraintDel.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("真的要删除嘛~")
                .setNegativeButton("手滑了", null)
            builder.setPositiveButton(
                "是的"
            ) { _, _ ->
                dataItem.time?.let { it1 ->
                    DataReader.delItem(it1)
                    close()
                }
            }.show()

        }
        textViewFinish.setOnClickListener {
            close()
        }
    }

    fun close() {
        val navController = findNavController()
        navController.popBackStack()
    }

}