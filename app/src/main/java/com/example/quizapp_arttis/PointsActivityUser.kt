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

class PuntosAdapter3(val puntos: List<Puntuacion>) :
    RecyclerView.Adapter<PuntosAdapter3.ViewHolder>() {

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


class PointsActivityUser : AppCompatActivity(){

    private lateinit var rv: RecyclerView
    private lateinit var db : AppDatabase
    private lateinit var btnFecha : Button
    private lateinit var btnPoints : Button
    private lateinit var switchHints: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_points)

        rv = findViewById(R.id.rvPuntuacionUser)
        rv.layoutManager = LinearLayoutManager(this)
        btnFecha = findViewById(R.id.btn_fechaUser)
        btnPoints = findViewById(R.id.btn_PuntosUser)
        switchHints = findViewById(R.id.switch_hintsUser)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "game_v4.db"
        ).allowMainThreadQueries().build()

        val instanceUserDb = db.usuarioDAO()
        var arrayUser = instanceUserDb.getActiveUser("yes")
        var hintsOn = false
        var btn_bandera = false

        val instanceScoreDb = db.scoreDAO()
        var arrayPoints = instanceScoreDb.getPointsUserByDate(arrayUser[0].name)


        rv.adapter = PuntosAdapter3(arrayPoints)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        btnFecha.setOnClickListener { _ ->

            var arrayPoints =
                if (!hintsOn) instanceScoreDb.getPointsUserByDate(arrayUser[0].name) else instanceScoreDb.getPointsUserByDateNoHints(arrayUser[0].name)

            rv.adapter = PuntosAdapter3(arrayPoints)
            btn_bandera = false
        }

        btnPoints.setOnClickListener { _ ->
            var arrayPoints =
                if (!hintsOn) instanceScoreDb.getPointsUserByPoints(arrayUser[0].name) else instanceScoreDb.getPointsUserByPointsNoHints(arrayUser[0].name)

            rv.adapter = PuntosAdapter3(arrayPoints)
            btn_bandera = true
        }

        switchHints.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) hintsOn = true else hintsOn = false
            if (!btn_bandera && isChecked) {
                arrayPoints = instanceScoreDb.getPointsUserByPointsNoHints(arrayUser[0].name)
            } else if (!btn_bandera && !isChecked) {
                arrayPoints = instanceScoreDb.getPointsUserByDate(arrayUser[0].name)
            } else if (btn_bandera && isChecked) {
                arrayPoints = instanceScoreDb.getPointsUserByPointsNoHints(arrayUser[0].name)
            }else if (btn_bandera && !isChecked) {
                arrayPoints = instanceScoreDb.getPointsUserByPoints(arrayUser[0].name)
            }
            rv.adapter = PuntosAdapter3(arrayPoints)
        }
    }
}