extends Node

var client := StreamPeerTCP.new()
var host := "127.0.0.1"  # Dirección IP del servidor
var port := 5000         # Puerto del servidor
var mapa_path := ""

@onready var menu := $Menu
@onready var fileDialog := $FileDialog
@onready var vista := $vista
<<<<<<< Updated upstream
@onready var energia := $vista/Energia
=======
@onready var energia_label := $vista/Energia
@onready var opciones := $opciones
>>>>>>> Stashed changes

const ruta_absoluta = "C:/Users/Usuario/Documents/DBA/Practica2/DBA_UGR/P2/json/entorno.json"

func _ready():
	iniciar_conexion()
	Signals.connect("file_updated",Callable(self,"_file_updated"), CONNECT_DEFERRED)
	Signals.connect("finished",Callable(self,"_finished"), CONNECT_DEFERRED)
	Signals.connect("variables_cambiadas", Callable(self,"_actualizar_entorno"), CONNECT_DEFERRED)

func iniciar_conexion():
	var err = client.connect_to_host(host, port)
	if err == OK:
		print("Intentando conectar al servidor Godot en ", host, ":", port)

	else:
		print("Error al intentar conectar: ", err)

@warning_ignore("unused_parameter")
func _process(delta):
	client.poll()

func send_message(message):
	var cleaned_message = message.strip_edges()
	client.put_utf8_string(cleaned_message + "\n")
	receive_response()

func receive_response():
	if client.get_available_bytes() > 0:
		var respuesta = client.get_utf8_string(client.get_available_bytes())
		print("Respuesta del servidor: ", respuesta)
		

func _on_iniciar_pressed() -> void:
	send_message("START")
	await receive_response()
	menu.hide()
	read_mapa()
	vista.show()
<<<<<<< Updated upstream
	
	
	

func _on_elegir_mapa_pressed() -> void:
	send_message("CHOOSE_MAP")
	fileDialog.popup()
	

func _on_file_dialog_file_selected(path: String) -> void:
	var file_name = path.get_file()
	print("Archivo seleccionado: ", file_name)
	send_message(file_name)

=======
	$Music.play()
>>>>>>> Stashed changes

func read_mapa():

	var ruta_entrada = ruta_absoluta
	var ruta_salida = "res://entorno.json"
	
	$Timer.start(5)
	await $Timer.timeout # Espera 1 segundo antes de comprobar de nuevo
		
	var file = FileAccess.open(ruta_entrada, FileAccess.READ)
	var data
	
	if file:
		data = file.get_as_text()  # Guarda los datos en el archivo
		file.close()  # Cierra el archivo después de escribir
		print("Archivo guardado o sobrescrito correctamente.")
	else:
		print("Error al abrir el archivo.")


	file = FileAccess.open(ruta_salida, FileAccess.WRITE)
	
	if file:
		file.store_string(data)  # Guarda los datos en el archivo
		file.close()  # Cierra el archivo después de escribir
		print("Archivo guardado o sobrescrito correctamente.")
	else:
		print("Error al abrir el archivo.")
	
	Signals.emit_signal("mapa_read")


func file_read():

	var ruta_entrada = ruta_absoluta
	var ruta_salida = "res://entorno.json"
	
	$Timer.start(1)
	await $Timer.timeout # Espera 1 segundo antes de comprobar de nuevo
		
	var file = FileAccess.open(ruta_entrada, FileAccess.READ)
	var data
	
	if file:
		data = file.get_as_text()  # Guarda los datos en el archivo
		file.close()  # Cierra el archivo después de escribir
		print("Archivo guardado o sobrescrito correctamente.")
	else:
		print("Error al abrir el archivo.")


	file = FileAccess.open(ruta_salida, FileAccess.WRITE)
	
	if file:
		file.store_string(data)  # Guarda los datos en el archivo
		file.close()  # Cierra el archivo después de escribir
		print("Archivo guardado o sobrescrito correctamente.")
	else:
		print("Error al abrir el archivo.")
	
	Signals.emit_signal("file_read")

func file_opciones():
	var ruta_entrada = ruta_absoluta
	var ruta_salida = "res://entorno.json"
	
	$Timer.start(1)
	await $Timer.timeout # Espera 1 segundo antes de comprobar de nuevo
		
	var file = FileAccess.open(ruta_entrada, FileAccess.READ)
	var data
	
	if file:
		data = file.get_as_text()  # Guarda los datos en el archivo
		file.close()  # Cierra el archivo después de escribir
		print("Archivo guardado o sobrescrito correctamente.")
	else:
		print("Error al abrir el archivo.")


	file = FileAccess.open(ruta_salida, FileAccess.WRITE)
	
	if file:
		file.store_string(data)  # Guarda los datos en el archivo
		file.close()  # Cierra el archivo después de escribir
		print("Archivo guardado o sobrescrito correctamente.")
	else:
		print("Error al abrir el archivo.")
	
	Signals.emit_signal("mapa_inicial")

func _file_updated() -> void:
	send_message("STEP")
	file_read()

func _finished() -> void:
	send_message("EXIT")
	print("El agente ha alcanzado la meta. Desconectando...")
	client.disconnect_from_host()
	print("Cliente desconectado.")
	
<<<<<<< Updated upstream
=======
func actualizar_energia(energia) -> void:
	energia_label.text = "Energía: %d" % energia


func _on_options_pressed() -> void:
	send_message("OPTION")
	await receive_response()
	menu.hide()
	file_opciones()
	opciones.show()
	
func _actualizar_entorno() -> void:
	var source = "res://new_entorno.json"
	var source_file = FileAccess.open(source, FileAccess.READ)
	
	if not source_file:
		print("No se pudo abrir el archivo de origen")
		return
	
	var destination_file = FileAccess.open(ruta_absoluta, FileAccess.WRITE)
	
	if not destination_file:
		print("No se pudo abrir el archivo de destino")
		source_file.close()
		return
	
	# Copiar el contenido del archivo de origen al de destino
	destination_file.store_string(source_file.get_as_text())
	
	# Cerrar los archivos
	source_file.close()
	destination_file.close()

	print("Archivo copiado con éxito")
	
	send_message("CHANGE")


func _on_quit_pressed() -> void:
	get_tree().quit()
>>>>>>> Stashed changes
