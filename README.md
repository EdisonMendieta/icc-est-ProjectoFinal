## preve explicacion de lo que hago:
primero hacemos dos paneles el primero es el panel donde esta dibujado el mapa (panelMapa)
el segundo panel sera el de los botones llamado panelControles 

modificamos el layout del panelMapa para poder poner un panel encima de otro y volvemos transparente el 
panelControles para que solo aparescan los botones y no tape el mapa 


## Mejoras - Nicolas Cornejo
mejoras visuales y funcionales:
implementamos JLayeredPane en la ventana principal para manejar capas reales; esto separa el mapa (fondo) de los controles (frente) y soluciona el error donde los botones desaparecían al dibujar.

mejoramos el renderizado usando Graphics2D y activando el anti-aliasing, lo que hace que los círculos y las líneas se vean suaves y definidos en lugar de pixelados.

agregamos la lógica completa de conexión: ahora se puede seleccionar un nodo y luego otro para trazar una línea visual entre ellos (aristas).

rediseñamos el panelControles para que tenga un fondo oscuro semitransparente y organización vertical, mejorando la estética flotante sobre el mapa.