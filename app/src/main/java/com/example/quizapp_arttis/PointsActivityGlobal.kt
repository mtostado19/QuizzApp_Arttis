package com.example.quizapp_arttis

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import org.w3c.dom.Text
import java.util.*

class PuntosAdapter2(val puntos: List<Puntuacion>) :
    RecyclerView.Adapter<PuntosAdapter2.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var txtPuntuacion: TextView
        private var txtFecha: TextView
        private var txtHints : TextView
        private var txtUsuario : TextView
        private var txtPartidas : TextView

        private lateinit var points: Puntuacion

        init {
            txtPuntuacion = view.findViewById(R.id.id_points_global)
            txtFecha = view.findViewById(R.id.id_date_global)
            txtHints = view.findViewById(R.id.id_hints_global)
            txtUsuario = view.findViewById(R.id.id_user_global)
            txtPartidas = view.findViewById(R.id.id_partidas_global)


        }
        fun bind(points: Puntuacion) {
            txtPuntuacion.text = points.score.toString()
            txtFecha.text = points.date
            txtHints.text = points.hints.toString()
            txtPartidas.text = 3.toString()

            this.points = points
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_recyclerviewglobal, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(puntos[position])
    }

    override fun getItemCount() = puntos.size
}


class PointsActivityGlobal : AppCompatActivity(){

    private lateinit var rv: RecyclerView
    private lateinit var db : AppDatabase
    private lateinit var btnMenu : Button
    private lateinit var btnLocal : Button
    private lateinit var switchFilter: Switch


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puntuacionglobal)

        rv = findViewById(R.id.rvPuntuacion)
        rv.layoutManager = LinearLayoutManager(this)
        btnMenu = findViewById(R.id.btn_points_menu2)
        btnLocal = findViewById(R.id.btn_points_local)
        switchFilter = findViewById(R.id.switch1)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "game_v4.db"
        ).allowMainThreadQueries().build()

        val instanceScoreDb = db.scoreDAO()
        var arrayPoints = instanceScoreDb.getAscNumPlaying()


        rv.adapter = PuntosAdapter2(arrayPoints)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        switchFilter.setOnCheckedChangeListener { _, isChecked ->
            arrayPoints =
                if (isChecked) instanceScoreDb.getAscMatchesPlayed() else instanceScoreDb.getAscNumPlaying()
            println(arrayPoints)
            rv.adapter = PuntosAdapter(arrayPoints)
            rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        }


        btnMenu.setOnClickListener { _ ->
            var intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
        }

        btnLocal.setOnClickListener { _ ->
            var intent = Intent(this, PointsActivity::class.java)
            startActivity(intent)
        }
    }
}