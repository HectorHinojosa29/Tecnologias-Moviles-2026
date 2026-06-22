package com.hector.smartreply

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
// Importación de la clase autogenerada por View Binding para tu layout activity_main.xml
import com.hector.smartreply.databinding.ActivityMainBinding
import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult

import com.google.mlkit.nl.smartreply.TextMessage
class MainActivity : AppCompatActivity() {
    // Instancia global para acceder a los componentes visuales de manera segura y eficiente
    private lateinit var binding: ActivityMainBinding
    // Historial que almacena la conversación en la memoria local del dispositivo
    private var conversation = ArrayList<TextMessage>()
    //Mensajes en pantalla

    private val chatMessages = mutableListOf<Message>()

    private lateinit var adapter: MessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Mensajes en pantalla

        adapter = MessageAdapter(chatMessages)

        binding.rvChat.adapter = adapter

        binding.rvChat.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(this)



        binding.sendButton.setOnClickListener {
            addMesage(binding.messageText.text.toString())
        }
        binding.hintsButton.setOnClickListener {
            getHints()
        }
        binding.clearButton.setOnClickListener {
            conversation = ArrayList()
            binding.hint0Button.visibility = View.GONE
            binding.hint1Button.visibility = View.GONE
            binding.hint2Button.visibility = View.GONE
            binding.messageText.setText("")
            binding.nameText.setText("")
            binding.errorText.text = ""

            //Mensaje en pantalla
            chatMessages.clear()
            adapter.notifyDataSetChanged()
        }
        binding.hint0Button.setOnClickListener {
            addMesage(binding.hint0Button.text.toString())
        }
        binding.hint1Button.setOnClickListener {
            addMesage(binding.hint1Button.text.toString())
        }
        binding.hint2Button.setOnClickListener {
            addMesage(binding.hint2Button.text.toString())
        }
    }
    /**
     * Agrega el mensaje ingresado al historial de la conversación.
     * se registra como un usuario remoto para que el
     * modelo entienda el contexto y pueda generar respuestas para ti.
     */
    private fun addMesage(text: String) {
        if (text.isNotEmpty()) {
            conversation.add(
                TextMessage.createForRemoteUser(
                    text,
                    System.currentTimeMillis(),
                    binding.nameText.text.toString()
                )
            )
            //Mensaje en pantalla
            chatMessages.add(
                Message(
                    binding.nameText.text.toString(),
                    text
                )
            )

            adapter.notifyItemInserted(chatMessages.size - 1)

            binding.rvChat.scrollToPosition(
                chatMessages.size - 1
            )
            binding.messageText.setText("") // Limpia el campo de entrada
        }
    }
/*** Procesa la lista de mensajes acumulados y solicita sugerencias
automáticas
 * al SDK local de Smart Reply.
 */
private fun getHints() {
    val smartReply = SmartReply.getClient()
    smartReply.suggestReplies(conversation)
        .addOnSuccessListener { result ->
            // Validación del idioma (ML Kit actualmente solo procesa conversaciones en inglés)
            if (result.status ==
                SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                Toast.makeText(
                    applicationContext,
                    "Lenguaje no soportado",
                    Toast.LENGTH_SHORT
                ).show()
            }
            // Si la generación fue exitosa, asigna los textos y hace  visibles los botones
            else if (result.status ==
            SmartReplySuggestionResult.STATUS_SUCCESS) {
            binding.hint0Button.text = result.suggestions[0].text
            binding.hint1Button.text = result.suggestions[1].text
            binding.hint2Button.text = result.suggestions[2].text
            binding.hint0Button.visibility = View.VISIBLE
            binding.hint1Button.visibility = View.VISIBLE
            binding.hint2Button.visibility = View.VISIBLE
        }
        }
        .addOnFailureListener { exception ->
            // Despliega el error en el TextView en caso de fallo  crítico de inicialización
            binding.errorText.text = exception.toString()
        }
}
}