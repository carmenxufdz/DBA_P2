extends Node

class Mapa:
	var ruta: String
	var N : int
	var M : int
	var matriz : Array

	# Constructor para inicializar el mapa
	@warning_ignore("shadowed_variable")
	func _init(ruta: String, N: int, M: int, matriz: Array):
		self.ruta = "res://mapas/" + ruta.get_file()
		self.N = N
		self.M = M
		self.matriz = matriz

	# Método para mostrar la matriz (opcional)
	func mostrar_matriz() -> void:
		for fila in matriz:
			print(fila)
	
	# Getter para 'ruta'
	func get_ruta() -> String:
		return ruta

	# Setter para 'ruta'
	func set_ruta(value: String) -> void:
		ruta = value

	# Getter para 'N'
	func get_N() -> int:
		return N

	# Setter para 'N'
	func set_N(value: int) -> void:
		N = value

	# Getter para 'M'
	func get_M() -> int:
		return M

	# Setter para 'M'
	func set_M(value: int) -> void:
		M = value

	# Getter para 'matriz'
	func get_matriz() -> Array:
		return matriz

	# Setter para 'matriz'
	func set_matriz(value: Array) -> void:
		matriz = value

class Entorno:
	var mapa : Mapa
	var posAgente : Array
	var posObjetivo : Array
	var distancia_actual : float
	var recorrido : Array

	@warning_ignore("shadowed_variable")
	func _init(mapa, posAgente, posObjetivo, distancia_actual, recorrido):
		self.mapa = mapa
		self.posAgente = posAgente
		self.posObjetivo = posObjetivo
		self.distancia_actual = distancia_actual
		self.recorrido = recorrido
	
	# Getter para 'mapa'
	func get_mapa() -> Mapa:
		return mapa

	# Setter para 'mapa'
	func set_mapa(value: Mapa) -> void:
		mapa = value

	# Getter para 'posAgente'
	func get_pos_agente() -> Array:
		return posAgente

	# Setter para 'posAgente'
	func set_pos_agente(value: Array) -> void:
		posAgente = value

	# Getter para 'posObjetivo'
	func get_pos_objetivo() -> Array:
		return posObjetivo

	# Setter para 'posObjetivo'
	func set_pos_objetivo(value: Array) -> void:
		posObjetivo = value

	# Getter para 'distancia_actual'
	func get_distancia_actual() -> float:
		return distancia_actual

	# Setter para 'distancia_actual'
	func set_distancia_actual(value: float) -> void:
		distancia_actual = value

	# Getter para 'recorrido'
	func get_recorrido() -> Array:
		return recorrido

	# Setter para 'recorrido'
	func set_recorrido(value: Array) -> void:
		recorrido = value


@onready var suelo = $Suelo
@onready var tileMap = $TileMap

func _ready():
	Signals.connect("file_read",Callable(self, "_actualizar"),CONNECT_DEFERRED)



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
			pintar_mapa(mapa)
			Signals.emit_signal("file_updated")
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
	var entorno = Entorno.new(mapa, data["posAgente"], data["posObjetivo"], data["distancia_actual"], data["recorrido"])
	print("Mapa: ", entorno.mapa)
	print("Posición Agente: ", entorno.posAgente)
	print("Posición Objetivo: ", entorno.posObjetivo)
	print("Distancia Actual: ", entorno.distancia_actual)
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


	
func pintar_mapa(mapa : Mapa) -> void:
	for y in range(mapa.get_N()):
		for x in range(mapa.get_M()):
			if mapa.matriz[y][x] == 0:
				tileMap.set_cell(0,Vector2i(x,y),0,Vector2i(0,0),0)
			elif mapa.matriz[y][x] == -1:
				tileMap.set_cell(0,Vector2i(x,y),1,Vector2i(0,0),0)
	
