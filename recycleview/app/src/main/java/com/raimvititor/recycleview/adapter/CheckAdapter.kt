package com.raimvititor.recycleview.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raimvititor.recycleview.R
import com.raimvititor.recycleview.models.CheckModel
import kotlinx.android.synthetic.main.list_check.view.*

class CheckAdapter(val context: Context, val items: List<CheckModel>) : RecyclerView.Adapter<CheckAdapter.ViewHolder>(), View.OnClickListener {
    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_check, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(checkModel: CheckModel) {
            view.checkbox.text = checkModel.text
            view.checkbox.isChecked = checkModel.check
            view.checkbox.setOnCheckedChangeListener(
                    { compoundButton, b ->
                        view.checkbox.isChecked = b
                        checkModel.check = b
                    })
        }
    }
}
