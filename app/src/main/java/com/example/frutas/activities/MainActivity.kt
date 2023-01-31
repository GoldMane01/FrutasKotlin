package com.example.frutas.activities

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.GridView
import android.widget.ListView
import android.widget.Toast
import com.example.frutas.models.Fruta
import com.example.frutas.adapters.MyAdapter
import com.example.frutas.R
import com.example.frutas.adapters.GridAdapter

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    var items:MutableList<Fruta> = mutableListOf<Fruta>()
    var contFrutaNueva:Int = 1;
    private var listView:ListView? = null
    private var gridView:GridView? = null
    private var adapterListView:MyAdapter? = null
    private var adapterGridView:MyAdapter? = null
    private var itemListView:MenuItem? = null
    private var itemGridView:MenuItem? = null
    private val SWITCHTOLISTVIEW = 0
    private val SWITCHTOGRIDVIEW = 1

    init {
        items = cargar_lista()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        forzarIconBar()
        listView = findViewById(R.id.lvLista)
        gridView = findViewById(R.id.gvGrid)
        listView!!.onItemClickListener = this
        gridView!!.onItemClickListener = this
        adapterListView = MyAdapter(this,R.layout.list_item, items)
        adapterGridView = MyAdapter(this,R.layout.grid_item, items)
        listView!!.adapter = adapterListView
        gridView!!.adapter = adapterGridView
        registerForContextMenu(listView)
        registerForContextMenu(gridView)

    }

    private fun forzarIconBar() {
        supportActionBar!!.setIcon(R.drawable.sandia_logo)
        supportActionBar!!.setDisplayUseLogoEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        itemListView = menu?.findItem(R.id.lista)
        itemGridView = menu?.findItem(R.id.grid)
        return true
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {

        val info:AdapterContextMenuInfo = menuInfo as AdapterContextMenuInfo

        if (menu != null) {
            menu.setHeaderTitle(items[info.position].nombre)
        }

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_contextual, menu)

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.borrar -> {

                val menuInfo: AdapterContextMenuInfo = item.menuInfo as AdapterContextMenuInfo

                items.removeAt(menuInfo.position)
                adapterListView?.notifyDataSetChanged()
                adapterGridView?.notifyDataSetChanged()

            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.añadir -> {

                val fruta = Fruta("Nueva nª"+contFrutaNueva, R.drawable.nueva,"Desconocido")
                items.add(fruta)
                contFrutaNueva++
                adapterListView?.notifyDataSetChanged()
                adapterGridView?.notifyDataSetChanged()

            }
            R.id.lista -> {

                switchListGridView(SWITCHTOLISTVIEW)
                true

            }
            R.id.grid -> {

                switchListGridView(SWITCHTOGRIDVIEW)
                true

            }
        }
        return true
    }

    private fun switchListGridView(opcion: Int) {
        if(opcion == SWITCHTOLISTVIEW) {
            if(listView!!.visibility == View.INVISIBLE) {
                gridView!!.visibility = View.INVISIBLE
                itemGridView!!.isVisible = true

                listView!!.visibility = View.VISIBLE
                itemListView!!.isVisible = false
            }
        } else {
            if(gridView!!.visibility == View.INVISIBLE) {
                listView!!.visibility = View.INVISIBLE
                itemListView!!.isVisible = true

                gridView!!.visibility = View.VISIBLE
                itemGridView!!.isVisible = false
            }
        }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(items[p2].descripcion == "Desconocido") {
            val toast: Toast = Toast.makeText(this,"Lo sentimos, no tenemos información sobre "+items[p2].nombre, Toast.LENGTH_SHORT)
            toast.show()
        } else {
            val toast: Toast = Toast.makeText(this,"La mejor fruta de "+items[p2].descripcion+" es la "+items[p2].nombre, Toast.LENGTH_SHORT)
            toast.show()
        }

    }

    fun cargar_lista():MutableList<Fruta> {

        for (i in items.indices) {
            items.removeAt(0)
        }

        val imagenes = intArrayOf(
            R.drawable.banana, R.drawable.cereza, R.drawable.frambuesa, R.drawable.fresa,
            R.drawable.manzana, R.drawable.naranja, R.drawable.pera
        )
        val nombres = arrayOf(
            "Banana","Cereza","Frambuesa","Fresa","Manzana","Naranja","Pera"
        )
        val descripciones = arrayOf(
            "Gran Canaria","Galicia","Barcelona","Huelva","Madrid","Valencia","Zaragoza"
        )
        for(i in nombres.indices) {
            val fruta = Fruta(nombres[i],imagenes[i],descripciones[i])
            items.add(fruta)
        }
        return items
    }

}

