extends Node2D

func _ready():
	# Obtener el tamaño de la ventana
	var screen_size = get_viewport().get_visible_rect().size
	
	# Posicionar el nodo en el centro de la pantalla
	position = screen_size / 2
