extends Node

@onready var tileMap := $Mapa
@onready var agente := $Agente
@onready var mapaLabel := $VBoxContainer/mapaLabel

@onready var fila_agente:= $VBoxContainer/fila_agente
@onready var columna_agente := $VBoxContainer/columna_agente
@onready var fila_meta:= $VBoxContainer/fila_meta
@onready var columna_meta := $VBoxContainer/columna_meta

var mapa_path : String
var new_mapa : Mapa
var new_entorno : Entorno

var tile_size : float

func _ready():
	Signals.connect("mapa_inicial",Callable(self, "_mapa_paint"),CONNECT_DEFERRED)
	agente.play()

func _proccess(delta):
	pass

func _mapa_paint() -> void:
	var file = FileAccess.open("res://entorno.json", FileAccess.READ)
	if file:
		var json_data = file.get_as_text()
		file.close()
		var json = JSON.new()
		var parse_err = json.parse(json_data)
		if parse_err == OK:
			var data = json.get_data()
			var mapa_data = data["mapa"]
			save_mapa(mapa_data)
			save_entorno(data,new_mapa)
			pintar_mapa(new_mapa)
			pintar_entorno(new_entorno)
			
			mapaLabel.text = mapa_path.get_file()
		else:
			print("Error al parsear el JSON")
	else:
		print("No se pudo abrir el archivo JSON")
		

func save_mapa(data) -> void:
	mapa_path = data["ruta"].get_file()
	var mapa = Mapa.new(data["ruta"], data["N"], data["M"], data["matriz"])
	print("Ruta: ", mapa.ruta)
	print("N: ", mapa.N)
	print("M: ", mapa.M)
	print("Matriz: ", mapa.matriz)
	new_mapa = mapa
	
func save_entorno(data, mapa: Mapa) -> void:
	var entorno = Entorno.new(mapa, data["posAgente"], data["posObjetivo"], data["recorrido"])
	print("Mapa: ", entorno.mapa)
	print("Posición Agente: ", entorno.posAgente)
	print("Posición Objetivo: ", entorno.posObjetivo)
	print("Recorrido: ", entorno.recorrido)
	new_entorno = entorno

func leer_mapa_desde_txt(ruta:String) -> Mapa:
	var file = FileAccess.open("res://mapas/" + ruta, FileAccess.READ)
	if not file:
		print("No se pudo abrir el archivo")
		return null
	var N = file.get_line().to_int()
	var M = file.get_line().to_int()
	var matriz = []
	
	# Leer cada fila y agregarla como una lista dentro de la matriz
	for i in range(N):
		var linea = file.get_line().strip_edges()  # Leer la línea y quitar los espacios
		var valores = linea.split("\t")  # Dividir la línea en valores usando tabuladores
		
		var fila = []  # Crear una lista vacía para la fila
		for j in range(M):
			fila.append(valores[j].to_int())  # Convertir cada valor a entero y agregar a la fila
		matriz.append(fila)  # Agregar la fila completa a la matriz
	file.close()
	
	return Mapa.new(ruta, N, M, matriz)

func pintar_entorno(entorno:Entorno)->void:

	agente.position.y = entorno.get_pos_agente()[0] * tile_size + tile_size/2
	agente.position.x = entorno.get_pos_agente()[1] * tile_size + tile_size/2
	fila_agente.text = str(entorno.get_pos_agente()[0])
	columna_agente.text = str(entorno.get_pos_agente()[1])
	fila_meta.text = str(entorno.get_pos_objetivo()[0])
	columna_meta.text = str(entorno.get_pos_objetivo()[1])
	
	if not agente.visible:
		agente.show()
	tileMap.clear_layer(2)
	tileMap.set_cell(2,Vector2i(entorno.get_pos_objetivo()[1],entorno.get_pos_objetivo()[0]),7,Vector2i(0,0),0)

func pintar_mapa(mapa : Mapa) -> void:
	tileMap.clear_layer(0)
	tileMap.set_scale(Vector2(float(45)/mapa.get_N(),float(45)/mapa.get_N()))
	tile_size = 16 * 45/mapa.get_N()
	agente.set_scale(Vector2(float(10)/mapa.get_N(),float(10)/mapa.get_N()))
	for y in range(mapa.get_N()):
		for x in range(mapa.get_M()):
			if mapa.matriz[y][x] == 0:
				tileMap.set_cell(0,Vector2i(x,y),0,Vector2i(0,0),0)
			elif mapa.matriz[y][x] == -1:
				tileMap.set_cell(0,Vector2i(x,y),1,Vector2i(0,0),0)
			else:
				pass
	Signals.emit_signal("mapa_updated")

func _update() -> void:
	Signals.emit_signal("file_updated")


func _on_actualizar_pressed() -> void:
	new_mapa = leer_mapa_desde_txt(mapa_path)
	mapa_entorno(new_mapa)
	cambiar_entorno()
	pintar_mapa(new_mapa)
	pintar_entorno(new_entorno)
	mapaLabel.text = mapa_path.get_file()
	var file = FileAccess.open("res://new_entorno.json", FileAccess.WRITE)
	if file:
		file.store_string(new_entorno.to_json())
		file.close()
	
	Signals.emit_signal("variables_cambiadas")

func _on_mapa_button_pressed() -> void:
	$FileDialog.show()

func _on_file_dialog_file_selected(path: String) -> void:
	mapa_path = $FileDialog.current_file
	new_mapa = leer_mapa_desde_txt(mapa_path)
	mapa_entorno(new_mapa)
	pintar_mapa(new_mapa)

func mapa_entorno(mapa : Mapa) -> void:
	new_entorno.mapa = mapa

func _on_atras_pressed() -> void:
	$"../Menu".show()
	$".".hide()

func cambiar_entorno() -> void:
	new_entorno.posAgente = [int(fila_agente.text),int(columna_agente.text)]
	new_entorno.posObjetivo = [int(fila_meta.text),int(columna_meta.text)]
	new_entorno.recorrido = []
	for i in range(new_entorno.mapa.get_N()):
		var fila = []
		for j in range(new_entorno.mapa.get_M()):
			fila.append(0)
		new_entorno.recorrido.append(fila)
	
	new_entorno.recorrido[new_entorno.posAgente[0]][new_entorno.posAgente[1]] = 1
	
