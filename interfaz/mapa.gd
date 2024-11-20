extends Node

class_name Mapa

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

func _to_dict() -> Dictionary:
	var dict = {
		"ruta": "./maps/" + ruta.get_file(),
		"N": N,
		"M": M,
		"matriz": matriz
	}
	return dict
