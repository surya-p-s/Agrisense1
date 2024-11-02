package com.example.agrisense




import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class DataActivity : AppCompatActivity() {


    private lateinit var btnLogOut : Button
    private lateinit var tvhumidity: TextView
    private lateinit var tvsoilMoisture: TextView
    private lateinit var tvtemperature: TextView
    private lateinit var tvlight: TextView
    private lateinit var database: DatabaseReference
    private lateinit var pump: SwitchCompat
    private lateinit var light: SwitchCompat
    private lateinit var air: SwitchCompat
    private lateinit var auth: FirebaseAuth
    private lateinit var farmid:TextView
    private lateinit var submitbtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        database = FirebaseDatabase.getInstance().reference
        btnLogOut = findViewById(R.id.button2)
        tvhumidity = findViewById(R.id.textView4)
        tvsoilMoisture = findViewById(R.id.textView6)
        tvtemperature = findViewById(R.id.textView8)
        tvlight = findViewById(R.id.textView10)
        farmid = findViewById(R.id.textView12)
        submitbtn = findViewById(R.id.button)
        auth = FirebaseAuth.getInstance()
//  fetch value from firebase
        // Initialize Firebase Realtime Database reference
        database = FirebaseDatabase.getInstance().reference.child("user/farm1")
// Add a ValueEventListener to continuously listen for data changes


        submitbtn.setOnClickListener{
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Get the data from the snapshot and update the UI
                    val humidity = snapshot.child("humidity").value.toString()
                    val soilMoisture = snapshot.child("soilMoisture").value.toString()
                    val temperature = snapshot.child("temperature").value.toString()
                    val light = snapshot.child("ldr").value.toString()
                    val id = snapshot.child("id").value.toString()


                    initializeSwitch()
                    // Update the TextViews with the retrieved values
                    tvhumidity.text = humidity
                    if(humidity.toInt() < 20){
                        tvhumidity.setTextColor(Color.rgb(200,0,0))
                    }else if (humidity.toInt() < 50){
                        tvhumidity.setTextColor(Color.rgb(200,200,0))
                    }else{
                        tvhumidity.setTextColor(Color.rgb(0,200,0))
                    }




                    tvsoilMoisture.text = soilMoisture
                    if(soilMoisture.toInt() < 20){
                        tvsoilMoisture.setTextColor(Color.rgb(200,0,0))
                    }else if (humidity.toInt() < 50){
                        tvsoilMoisture.setTextColor(Color.rgb(200,200,0))
                    }else{
                        tvsoilMoisture.setTextColor(Color.rgb(0,200,0))
                    }


                    tvtemperature.text = temperature
                    if(temperature.toFloat() < 20.00){
                        tvtemperature.setTextColor(Color.rgb(200,0,0))
                    }else if (humidity.toInt() < 50.00){
                        tvtemperature.setTextColor(Color.rgb(200,200,0))
                    }else{
                        tvtemperature.setTextColor(Color.rgb(0,200,0))
                    }


                    tvlight.text = light
                    if(light.toInt() < 20){
                        tvlight.setTextColor(Color.rgb(200,0,0))
                    }else if (humidity.toInt() < 50){
                        tvlight.setTextColor(Color.rgb(200,200,0))
                    }else{
                        tvlight.setTextColor(Color.rgb(0,200,0))
                    }


                    farmid.text = id
                }


                override fun onCancelled(error: DatabaseError) {
                    // Handle any errors that occur while trying to read data
                    Toast.makeText(this@DataActivity, "Failed to read data: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })




// Switch toggle setup
            air= findViewById(R.id.switch1)
            pump = findViewById(R.id.switch2)
            light = findViewById(R.id.switch4)



// Initialize the switch based on the current data in the Firebase database
//            initializeSwitch()


// Set listener to update Firebase when the switch is toggled
            pump.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Switch is ON, send value "1" to Firebase
                    sendSwitchDataToFirebase(1)
                } else {
                    // Switch is OFF, send value "0" to Firebase
                    sendSwitchDataToFirebase(0)
                }
            }
        }


        btnLogOut.setOnClickListener{
            auth.signOut()
            updateUI()
        }


    }
    private fun initializeSwitch() {
        // Optional: Get the current switch value from Firebase and set the switch state accordingly
        database.child("pump").get().addOnSuccessListener { dataSnapshot ->
            val switchValue = dataSnapshot.value as? Int ?: 0
            pump.isChecked = switchValue == 0
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to get switch state", Toast.LENGTH_SHORT).show()
        }
    }
    private fun sendSwitchDataToFirebase(value: Int) {
        // Send switch state (1 or 0) to Firebase
        database.child("pump").setValue(value).addOnSuccessListener {
            Toast.makeText(this, "Switch state updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to update switch state", Toast.LENGTH_SHORT).show()
        }
    }


//    function to update switch status manually


//    private fun updateSwitchStatus(isOn: Boolean) {
//        pump.isChecked = isOn
//    }




    private fun updateUI(){
        val intent= Intent(this,SignInPage::class.java)
        startActivity(intent)
        finish()
    }




}
