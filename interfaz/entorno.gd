extends Node

class Mapa:
	var ruta: String
	var N : int
	var M : int
	var matriz : Array

	# Constructor para inicializar el mapa
	func _init(ruta: String, N: int, M: int, matriz: Array):
		self.N = N
		self.M = M
		self.matriz = matriz

	# Método para mostrar la matriz (opcional)
	func mostrar_matriz() -> void:
		for fila in matriz:
			print(fila)
			

class Entorno:
	var mapa : Mapa
	var posAgente : Array
	var posObjetivo : Array
	var distancia_actual : float
	var recorrido : Array

	func _init(mapa, posAgente, posObjetivo, distancia_actual, recorrido):
		self.mapa = mapa
		self.posAgente = posAgente
		self.posObjetivo = posObjetivo
		self.distancia_actual = distancia_actual
		self.recorrido = recorrido

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
			var mapa = Mapa.new(data["mapa"]["ruta"], data["mapa"]["N"], data["mapa"]["M"], data["mapa"]["matriz"])
			var entorno = Entorno.new(mapa, data["posAgente"], data["posObjetivo"], data["distancia_actual"], data["recorrido"])
			print("Mapa: ", entorno.mapa)
			print("Posición Agente: ", entorno.posAgente)
			print("Posición Objetivo: ", entorno.posObjetivo)
			print("Distancia Actual: ", entorno.distancia_actual)
			print("Recorrido: ", entorno.recorrido)
			Signals.emit_signal("file_updated")
		else:
			print("Error al parsear el JSON")
	else:
		print("No se pudo abrir el archivo JSON")

func _delete_file() -> void:
	DirAccess.remove_absolute("res://entorno.json")
