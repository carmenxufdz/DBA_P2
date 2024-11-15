extends Node

var client := StreamPeerTCP.new()
var host := "127.0.0.1"  # Dirección IP del servidor
var port := 5000         # Puerto del servidor
var mapa_path := ""

@onready var menu := $Menu
@onready var fileDialog := $FileDialog
@onready var vista := $vista
@onready var energia := $vista/Energia

const ruta_absoluta = "C:/Users/Usuario/Documents/DBA/Practica2/DBA_UGR/P2/json/entorno.json"

func _ready():
	iniciar_conexion()
	Signals.connect("file_updated",Callable(self,"_file_updated"), CONNECT_DEFERRED)
	Signals.connect("finished",Callable(self,"_finished"), CONNECT_DEFERRED)

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
	
	
	

func _on_elegir_mapa_pressed() -> void:
	send_message("CHOOSE_MAP")
	fileDialog.popup()
	

func _on_file_dialog_file_selected(path: String) -> void:
	var file_name = path.get_file()
	print("Archivo seleccionado: ", file_name)
	send_message(file_name)


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

func _file_updated() -> void:
	send_message("STEP")
	file_read()

func _finished() -> void:
	send_message("EXIT")
	print("El agente ha alcanzado la meta. Desconectando...")
	client.disconnect_from_host()
	print("Cliente desconectado.")
	
