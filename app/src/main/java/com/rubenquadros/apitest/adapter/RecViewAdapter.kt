package com.rubenquadros.apitest.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.rubenquadros.apitest.R
import com.rubenquadros.apitest.callbacks.IActivityCallBacks
import com.rubenquadros.apitest.callbacks.IDetailCallBack
import com.rubenquadros.apitest.data.model.fetch.Datum
import com.rubenquadros.apitest.data.model.fetch.FetchResponse
import com.rubenquadros.apitest.utils.ApplicationConstants
import com.squareup.picasso.Picasso

class RecViewAdapter(private val fetchResponse: FetchResponse, private val activityListener: IActivityCallBacks, private val detailListener: IDetailCallBack): RecyclerView.Adapter<RecViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return fetchResponse.data!!.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(fetchResponse.data!![position].imageUrl)
            .placeholder(R.color.colorBlack).into(holder.profilePic)

        holder.nameTv?.text = ApplicationConstants.NAME + fetchResponse.data!![position].name

        holder.emailTv?.text = ApplicationConstants.EMAIL + fetchResponse.data!![position].email

        holder.contactTv?.text = ApplicationConstants.CONTACT + fetchResponse.data!![position].contact.toString()

        holder.userNameTv?.text = ApplicationConstants.USERNAME + fetchResponse.data!![position].username

        holder.card?.setOnClickListener {
            val datum = Datum()
            datum.name = fetchResponse.data!![position].name
            datum.email = fetchResponse.data!![position].email
            datum.contact = fetchResponse.data!![position].contact
            datum.username = fetchResponse.data!![position].username
            datum.id = fetchResponse.data!![position].id
            detailListener.getDetail(datum)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val card: CardView? = itemView.findViewById(R.id.cardView)
        val profilePic: ImageView? = itemView.findViewById(R.id.profilePic)
        val nameTv: TextView? = itemView.findViewById(R.id.name)
        val emailTv: TextView? = itemView.findViewById(R.id.email)
        val contactTv: TextView? = itemView.findViewById(R.id.contact)
        val userNameTv: TextView? = itemView.findViewById(R.id.username)
    }
}