extends Node

var client := StreamPeerTCP.new()
var host := "127.0.0.1"  # Dirección IP del servidor
var port := 5000         # Puerto del servidor
var is_connected := false

var mapa_path := ""

@onready var menu := $Menu
@onready var file := $FileDialog
@onready var next := $Next
@onready var entorno := $Entorno

func _ready():
	iniciar_conexion()
	Signals.connect("file_updated",Callable(self,"_file_updated"), CONNECT_DEFERRED)

func iniciar_conexion():
	var err = client.connect_to_host(host, port)
	if err == OK:
		print("Intentando conectar al servidor Godot en ", host, ":", port)

	else:
		print("Error al intentar conectar: ", err)

func _process(delta):
	var status = client.get_status()
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
	next.show()
	file_read()
	entorno.show()
	

func _on_elegir_mapa_pressed() -> void:
	send_message("CHOOSE_MAP")
	file.popup()
	


func _on_file_dialog_file_selected(path: String) -> void:
	var file_name = path.get_file()
	print("Archivo seleccionado: ", file_name)
	send_message(file_name)


func _on_next_pressed() -> void:
	send_message("STEP")
	await "receive_response"
	file_read()
	next.disabled = true

func file_read():
	var ruta_entrada = "C:/Users/carme/OneDrive/Documentos/Universidad/4GII/PRIMER_CUATRI/Desarrollo Basado en Agentes/Practicas/DBA_UGR/P2/json/entorno.json"
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
	
	Signals.emit_signal("file_read")

func _file_updated() -> void:
	next.disabled = false
