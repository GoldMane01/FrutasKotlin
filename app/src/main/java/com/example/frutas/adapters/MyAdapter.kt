package com.example.frutas.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.frutas.models.Fruta
import com.example.frutas.R

class MyAdapter(val contexto:Context, val layout:Int, val frutas:MutableList<Fruta>): BaseAdapter() {
    override fun getCount(): Int {
        return frutas.size
    }

    override fun getItem(p0: Int): Any {
        return frutas[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var vista = convertView
        val holder: ViewHolder
        if(vista == null) {
            val inflater = LayoutInflater.from(contexto)
            vista = inflater.inflate(R.layout.list_item, null)
            holder = ViewHolder()
            holder.texto = vista.findViewById(R.id.tvNombre)
            holder.foto = vista.findViewById(R.id.ivFoto)
            holder.descripcion = vista.findViewById(R.id.tvDescripcion)
            vista.tag = holder
        } else {
            holder = vista.tag as ViewHolder
        }
        val fruta = frutas[position]
        if(fruta!=null) {
            holder.texto?.text = fruta.nombre
            holder.foto?.setImageDrawable(contexto.getDrawable(fruta.imagen))
            holder.descripcion?.text = fruta.descripcion
        }
        return vista
    }

    internal class ViewHolder {
        var foto: ImageView? = null
        var texto: TextView? = null
        var descripcion: TextView? = null
    }
}