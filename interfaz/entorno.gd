extends Node

class_name Entorno 

var mapa : Mapa
var posAgente : Array
var posObjetivo : Array
var recorrido : Array

func _init(mapa, posAgente, posObjetivo, recorrido):
	self.mapa = mapa
	self.posAgente = posAgente
	self.posObjetivo = posObjetivo
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

# Getter para 'recorrido'
func get_recorrido() -> Array:
	return recorrido

# Setter para 'recorrido'
func set_recorrido(value: Array) -> void:
	recorrido = value
	
# Método para convertir el objeto a un diccionario
func _to_dict() -> Dictionary:
	var dict = {
		"mapa": mapa._to_dict(),  # Suponiendo que Mapa también tiene un método _to_dict()
		"posAgente": posAgente,
		"posObjetivo": posObjetivo,
		"recorrido": recorrido
	}
	return dict
	
# Método para convertir el objeto a JSON
func to_json() -> String:
	var dict = _to_dict()  # Convertir el objeto a diccionario
	return JSON.stringify(dict)  # Convertir el diccionario a una cadena JSON
