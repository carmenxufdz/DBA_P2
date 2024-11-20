extends LineEdit

func _ready():
	set_process_input(true)

func _gui_input(event):
	if event is InputEventKey and event.is_pressed():
		if event.is_action_pressed("ui_accept"):
			focus_mode = Control.FOCUS_NONE
		if event.unicode != 0: 
			# Verifica se o unicode digitado é um número
			if !(event.as_text() >= "0" && event.as_text() <= "9"):
				accept_event()
