extends Node

@onready var gif := $Ole

@onready var tileMap = $Mapa
@onready var agente := $Agente


var tile_size : float
var terminado : bool = false


func _ready():
	Signals.connect("file_read",Callable(self, "_actualizar"),CONNECT_DEFERRED)
	Signals.connect("mapa_read",Callable(self, "_mapa_paint"),CONNECT_DEFERRED)
	Signals.connect("entorno_updated",Callable(self, "_update"),CONNECT_DEFERRED)
	Signals.connect("finished",Callable(self, "_finished"),CONNECT_DEFERRED)
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
			var mapa = save_mapa(mapa_data)
			var entorno = save_entorno(data,mapa)
			pintar_mapa(mapa)
			pintar_entorno(entorno)
		else:
			print("Error al parsear el JSON")
	else:
		print("No se pudo abrir el archivo JSON")
		
func _actualizar() -> void:
	var file = FileAccess.open("res://entorno.json", FileAccess.READ)
	if file:
		var json_data = file.get_as_text()
		file.close()
		var json = JSON.new()
		var parse_err = json.parse(json_data)
		if parse_err == OK:
			var data = json.get_data()
			var mapa_data = data["mapa"]
			var mapa = save_mapa(mapa_data)
			var entorno = save_entorno(data,mapa)
			pintar_entorno(entorno)
			is_finished(entorno)
		else:
			print("Error al parsear el JSON")
	else:
		print("No se pudo abrir el archivo JSON")
	

func save_mapa(data) -> Mapa:
	var mapa = Mapa.new(data["ruta"], data["N"], data["M"], data["matriz"])
	print("Ruta: ", mapa.ruta)
	print("N: ", mapa.N)
	print("M: ", mapa.M)
	print("Matriz: ", mapa.matriz)
	return mapa
	
func save_entorno(data, mapa: Mapa) -> Entorno:
	var entorno = Entorno.new(mapa, data["posAgente"], data["posObjetivo"], data["recorrido"])
	print("Mapa: ", entorno.mapa)
	print("Posición Agente: ", entorno.posAgente)
	print("Posición Objetivo: ", entorno.posObjetivo)
	print("Recorrido: ", entorno.recorrido)
	return entorno

func leer_mapa_desde_txt(ruta:String) -> Mapa:
	var file = FileAccess.open(ruta, FileAccess.READ)
	if not file:
		print("No se pudo abrir el archivo")
		return null
	var N = file.get_line().to_int()
	var M = file.get_line().to_int()
	var matriz = []
	
	for i in range(N):
		var linea = file.get_line().strip_edges()
		var valores = linea.split("\t")
		for j in range(M):
			matriz.append([valores[j].to_int()])
	file.close()
	
	return Mapa.new(ruta, N, M, matriz)

func pintar_entorno(entorno:Entorno)->void:

	agente.position.y = entorno.get_pos_agente()[0] * tile_size + tile_size/2
	agente.position.x = entorno.get_pos_agente()[1] * tile_size + tile_size/2
	
	if not agente.visible:
		agente.show()
	tileMap.set_cell(2,Vector2i(entorno.get_pos_objetivo()[1],entorno.get_pos_objetivo()[0]),7,Vector2i(0,0),0)
	for y in range(entorno.mapa.get_N()):
		for x in range(entorno.mapa.get_M()):
			match int(entorno.get_recorrido()[y][x]):
				0:
					pass
				1:
					tileMap.set_cell(1,Vector2i(x,y),2,Vector2i(0,0),0)
				2:
					tileMap.set_cell(1,Vector2i(x,y),3,Vector2i(0,0),0)
				3:
					tileMap.set_cell(1,Vector2i(x,y),4,Vector2i(0,0),0)
				4:
					tileMap.set_cell(1,Vector2i(x,y),5,Vector2i(0,0),0)
				5:
					tileMap.set_cell(1,Vector2i(x,y),6,Vector2i(0,0),0)	
	if not terminado:
		Signals.emit_signal("entorno_updated")
	elif terminado:
		Signals.emit_signal("finished")

func pintar_mapa(mapa : Mapa) -> void:
	tileMap.set_scale(Vector2(float(45)/mapa.get_N(),float(45)/mapa.get_N()))
	tile_size = 16 * 45/mapa.get_N()
	agente.set_scale(Vector2(float(10)/mapa.get_N(),float(10)/mapa.get_N()))
	for y in range(mapa.get_N()):
		for x in range(mapa.get_M()):
			if mapa.matriz[y][x] == 0:
				tileMap.set_cell(0,Vector2i(x,y),0,Vector2i(0,0),0)
			elif mapa.matriz[y][x] == -1:
				tileMap.set_cell(0,Vector2i(x,y),1,Vector2i(0,0),0)
	Signals.emit_signal("mapa_updated")

func _update() -> void:
	Signals.emit_signal("file_updated")
	
func is_finished(entorno : Entorno) -> void:
	if entorno.get_pos_agente() == entorno.get_pos_objetivo():
		terminado = true
		
func _finished() -> void:
	$"../Music".stop()
	gif.show()
	gif.play("default")
	$"../Yay".play()
	$Quit.show()
	
