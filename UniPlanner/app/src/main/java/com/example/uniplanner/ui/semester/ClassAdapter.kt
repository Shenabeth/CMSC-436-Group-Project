package com.example.uniplanner.ui.semester

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.example.uniplanner.R


/*THIS CLASS ADAPTER IS LARGELY BASED OFF THE TODOADAPTER IN LAB5_UILAB
* https://gitlab.cs.umd.edu/cmsc436spring2022.git*/

class ClassAdapter(private val mContext: Context) :
    RecyclerView.Adapter<ClassAdapter.ViewHolder>() {

    private val mItems = ArrayList<ClassItem>()

    fun add(item: ClassItem) {
        mItems.add(item)
        notifyItemChanged(mItems.size)
    }

    fun clear() {
        mItems.clear()
        notifyDataSetChanged()
    }

    fun getItem(pos: Int): Any {
        return mItems[pos - 1]
    }

    override fun getItemCount(): Int {
        return mItems.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position==0) HEADER_VIEW_TYPE else CLASS_VIEW_TYPE
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == HEADER_VIEW_TYPE) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.no_classes, parent, false)
            return ViewHolder(v)
        }else {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.class_item, parent, false)
            val viewHolder = ViewHolder(v)

            viewHolder.mItemLayout = v.findViewById(R.id.constraintLayout1)
            viewHolder.mClassNameView = viewHolder.mItemLayout.findViewById(R.id.classNameView)
            viewHolder.mCreditsView = viewHolder.mItemLayout.findViewById(R.id.creditsView)
            viewHolder.mLocationView = viewHolder.mItemLayout.findViewById(R.id.locationView)
            return viewHolder
        }
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        if (position == 0) {
            viewHolder.itemView.setOnClickListener {
                Log.i(SemesterConfigurationActivity.TAG, "Entered footerView.OnClickListener.onClick()")
                val options: Bundle? = null
                startActivityForResult(
                    mContext as Activity,
                    Intent(
                        mContext,
                        AddClassActivity::class.java
                    ),
                    SemesterConfigurationActivity.ADD_CLASS_ITEM_REQUEST,
                    options
                )
            }
        } else {
            val ClassItem = mItems[position - 1]

            viewHolder.mClassNameView!!.text = ClassItem.className

            viewHolder.mCreditsView!!.text = ClassItem.credits.toString()

            viewHolder.mLocationView!!.text = ClassItem.location

        }
    }


    override fun getItemId(pos: Int): Long {
        return pos.toLong() - 1
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mLocationView: TextView? = null
        var mCreditsView: TextView? = null
        var mItemLayout: View = itemView
        var mClassNameView: TextView? = null
        
    }


    companion object {
        private const val TAG = "UniPlanner"
        private const val HEADER_VIEW_TYPE = R.layout.no_classes
        private const val CLASS_VIEW_TYPE = R.layout.class_item
    }


}