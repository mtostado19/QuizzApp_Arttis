package com.example.quizapp_arttis

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.room_demo_application.db.AppDatabase
import com.example.quizapp_arttis.db.Puntuacion
import java.util.*

class PuntosAdapter(val puntos: List<Puntuacion>) :
    RecyclerView.Adapter<PuntosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var txtPuntuacion: TextView
        private var txtFecha: TextView
        private var txtHints : TextView
        private var txtUsuario : TextView

        private lateinit var points: Puntuacion

        init {
            txtPuntuacion = view.findViewById(R.id.id_points)
            txtFecha = view.findViewById(R.id.id_date)
            txtHints = view.findViewById(R.id.id_hints)
            txtUsuario = view.findViewById(R.id.id_user)

        }
        fun bind(points: Puntuacion) {
            txtPuntuacion.text = points.score.toString()
            txtFecha.text = points.date
            txtHints.text = points.hints.toString()
            txtUsuario.text = points.user

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
    private lateinit var db : AppDatabase
    private lateinit var btnMenu : Button
    private lateinit var btnGlobal : Button
    private lateinit var switchFilter: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puntacion)

        rv = findViewById(R.id.rvPuntuacion)
        rv.layoutManager = LinearLayoutManager(this)
        btnMenu = findViewById(R.id.btn_points_menu)
        btnGlobal = findViewById(R.id.btn_points_global)
        switchFilter = findViewById(R.id.switchyea)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "game_v4.db"
        ).allowMainThreadQueries().build()

        val instanceScoreDb = db.scoreDAO()
        var arrayPoints = instanceScoreDb.getAscFirst()


        rv.adapter = PuntosAdapter(arrayPoints)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        switchFilter?.setOnCheckedChangeListener({ _ , isChecked ->
            arrayPoints = if (isChecked) instanceScoreDb.getAscDate() else instanceScoreDb.getAscFirst()
            rv.adapter = PuntosAdapter(arrayPoints)
            rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        })

        btnMenu.setOnClickListener { _ ->
            var intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
        }

        btnGlobal.setOnClickListener { _ ->
            var intent = Intent(this, PointsActivityGlobal::class.java)
            startActivity(intent)
        }
    }
}