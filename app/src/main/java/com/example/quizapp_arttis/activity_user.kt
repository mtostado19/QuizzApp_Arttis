package com.example.quizapp_arttis

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.quizapp_arttis.db.Puntuacion
import com.example.quizapp_arttis.db.Usuario
import com.example.room_demo_application.db.AppDatabase
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class activity_user : AppCompatActivity() {

    private  lateinit var db : AppDatabase
    private lateinit var btnAdd: ImageButton
    private lateinit var txtInput: EditText
    private lateinit var lvUsers: ListView
    private lateinit var selectedUser: Usuario
    private lateinit var activeUser: Usuario
    private lateinit var btnDelete: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnSetActive: Button
    private lateinit var tvActiveUser: TextView
    private lateinit var btnPerfilPoints : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "game_v4.db"
        ).allowMainThreadQueries().build()
        var userManager = db.usuarioDAO()

        btnAdd = findViewById<ImageButton>(R.id.add_user_button)
        txtInput = findViewById<EditText>(R.id.user_name)
        lvUsers = findViewById<ListView>(R.id.users_list)
        btnDelete = findViewById<Button>(R.id.delete_button)
        btnUpdate = findViewById<Button>(R.id.update_button)
        btnSetActive = findViewById<Button>(R.id.select_button)
        tvActiveUser = findViewById<TextView>(R.id.active_user)
        btnPerfilPoints = findViewById(R.id.points_button)

        var selectedFlag = false


        var defaultUser = Usuario(0, "none", "yes")

        var activeUsers = userManager.getActiveUser("yes")
        if (activeUsers.isEmpty()){
            activeUser = defaultUser
            selectedUser = defaultUser
            tvActiveUser.text = "${resources.getString(R.string.activeUser)}: ${defaultUser.name}"
        } else {
            activeUser = activeUsers[0]
            selectedUser = activeUsers[0]
            tvActiveUser.text = "${resources.getString(R.string.activeUser)}: ${activeUsers[0].name}"
        }

        var allUsers = userManager.getAll()

        println("-------All Users --------")
        println(allUsers)

        println("-------Active User --------")
        println(activeUser)

        var arrayAdapter : ArrayAdapter<String>
        var arrayListUsers = allUsers.map { it.name }
        arrayAdapter = customAdapter(this, arrayListUsers.toCollection(ArrayList()))
        lvUsers.adapter = arrayAdapter

        btnAdd.setOnClickListener {

            if (selectedFlag) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(resources.getString(R.string.addNewUser))
                builder.setMessage(resources.getString(R.string.addNewUserQuestion))
                builder.setNegativeButton(resources.getString(R.string.confirmResponse)) { dialogInterface: DialogInterface, i: Int ->
                    txtInput.setText("")
                    selectedFlag = false
                    btnAdd.setImageResource(R.drawable.add)
                    var activeUsers = userManager.getActiveUser("yes")
                    if (!activeUsers.isEmpty()) {
                        selectedUser = userManager.getActiveUser("yes")[0]
                    } else {
                        selectedUser = defaultUser
                    }

                }
                builder.setPositiveButton(resources.getString(R.string.RejectRespone)) { dialogInterface: DialogInterface, i: Int ->

                }

                builder.create().show()

            } else {
                var newName = txtInput.text.toString()
                var allNames =  userManager.getSpecific(newName)
                println("--------All Names -------")
                println(allNames)
                println(newName)

                if (newName.length <= 0) {
                    Toast.makeText(this, R.string.noEmptyNames, Toast.LENGTH_LONG).show()
                } else if (!allNames.isEmpty()) {
                    Toast.makeText(this, R.string.noRepeatedNames, Toast.LENGTH_LONG).show()
                } else {
                    var maxIdUser = userManager.getMaxId();
                    var newId = 1;
                    if (!maxIdUser.isEmpty()) {
                        newId = maxIdUser[0].id + 1
                    }

                    var newUser = Usuario(newId, newName,"no")
                    userManager.insert(newUser)

                    txtInput.setText("")

                    allUsers = userManager.getAll();
                    var arrayListUsersUpdated = allUsers.map { it.name }

                    arrayAdapter.clear()
                    arrayAdapter.addAll(arrayListUsersUpdated.toCollection(ArrayList()))
                }
            }

        }

        lvUsers.setOnItemClickListener { parent, view, position, id ->
            selectedUser =  allUsers[position]

            println("------- Selected --------")
            println(selectedUser)

            selectedFlag = true;
            btnAdd.setImageResource(R.drawable.add_cancelled)

            txtInput.setText(selectedUser.name)
        }

        btnDelete.setOnClickListener {
            if(selectedFlag) {
                if (selectedUser.active == "yes") {
                    activeUser = defaultUser
                    tvActiveUser.text = "${resources.getString(R.string.activeUser)}: ${defaultUser.name}"
                }
                userManager.deleteSpecific(selectedUser.id.toString())
                db.scoreDAO().deleteAll(selectedUser.name)
                db.optionsDAO().deleteAll(selectedUser.name)
                db.currenGameDAO().deleteAll(selectedUser.name)
                allUsers = userManager.getAll();
                var arrayListUsersUpdated = allUsers.map { it.name }

                arrayAdapter.clear()
                arrayAdapter.addAll(arrayListUsersUpdated.toCollection(ArrayList()))

                txtInput.setText("")
                selectedFlag = false
                btnAdd.setImageResource(R.drawable.add)

            } else {
                Toast.makeText(this, R.string.noUserSelected, Toast.LENGTH_LONG).show()
            }
        }

        btnPerfilPoints.setOnClickListener {
            if(selectedUser.active == "yes") {
                var intent = Intent(this , PointsActivityUser::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(this, R.string.noUserSelected, Toast.LENGTH_LONG).show()
            }
        }

        btnUpdate.setOnClickListener {
            if(selectedFlag) {
                var newName = txtInput.text.toString();
                if (newName.length > 0) {
                    var userUpdated = Usuario(selectedUser.id, newName, selectedUser.active)
                    userManager.update(userUpdated)

                    allUsers = userManager.getAll();
                    var arrayListUsersUpdated = allUsers.map { it.name }
                    arrayAdapter.clear()
                    arrayAdapter.addAll(arrayListUsersUpdated.toCollection(ArrayList()))

                    txtInput.setText("")
                    selectedFlag = false
                    btnAdd.setImageResource(R.drawable.add)
                    selectedUser = userUpdated
                    if (userUpdated.active == "yes") {
                        activeUser = userUpdated
                        tvActiveUser.text = "${resources.getString(R.string.activeUser)}: ${activeUser.name}"
                    }
                } else {
                    Toast.makeText(this, R.string.noEmptyNames, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, R.string.noUserSelected, Toast.LENGTH_LONG).show()
            }
        }

        btnSetActive.setOnClickListener {
            if(selectedFlag) {
                if ( selectedUser.id > 0 ) {
                    var disabledUser = Usuario(activeUser.id, activeUser.name, "no")
                    userManager.update(disabledUser)

                    var enabledUser = Usuario(selectedUser.id, selectedUser.name, "yes")
                    userManager.update(enabledUser)

                    activeUser = enabledUser
                    selectedUser = enabledUser
                    tvActiveUser.text = "${resources.getString(R.string.activeUser)}: ${activeUser.name}"

                } else {
                    Toast.makeText(this, R.string.noUserSelected, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, R.string.noUserSelected, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}