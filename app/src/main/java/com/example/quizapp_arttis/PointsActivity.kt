package com.example.quizapp_arttis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

data class Puntos(val puntuacion: Int, val fecha: String)

class PuntosAdapter(val puntos: MutableList<Puntos>) :
    RecyclerView.Adapter<PuntosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var txtPuntuacion: TextView
        private var txtFecha: TextView

        private lateinit var points: Puntos

        init {
            txtPuntuacion = view.findViewById(R.id.id_points)
            txtFecha = view.findViewById(R.id.id_date)

        }
        fun bind(points: Puntos) {
            txtPuntuacion.text = points.puntuacion.toString()
            txtFecha.text = points.fecha

            this.points = points
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_recyclerview, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(puntos[position])
    }

    override fun getItemCount() = puntos.size
}


class PointsActivity : AppCompatActivity(){

    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puntacion)

        rv = findViewById(R.id.rvPuntuacion)
        rv.layoutManager = LinearLayoutManager(this)

        val puntos = mutableListOf<Puntos>(
            Puntos(9999, "2020-12-21")
        )

        rv.adapter = PuntosAdapter(puntos)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}