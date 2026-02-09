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

## Mejoras - Nicolas y Edison

separamos la lógica de negocio de la interfaz gráfica; creamos la clase GrafoBackend que se encarga de los cálculos y datos, dejando al PanelMapa únicamente la responsabilidad de dibujar e interactuar con el usuario.

implementamos los algoritmos de búsqueda BFS (anchura) y DFS (profundidad) en botones separados, cumpliendo con la rúbrica.

agregamos el "Modo Exploración": una casilla que al activarse muestra una animación paso a paso (nodos azules) de cómo el algoritmo recorre el grafo antes de encontrar la ruta final (línea amarilla).

añadimos soporte para aristas dirigidas; ahora se pueden crear conexiones de un solo sentido (flechas) o de doble sentido, afectando el resultado de la ruta.

incorporamos la definición visual de puntos clave: botones específicos para marcar el nodo de Inicio (color verde) y el nodo Destino (color magenta).

logramos la persistencia de datos mediante serialización; el proyecto ahora permite Guardar el mapa actual en un archivo .dat y Cargar proyectos anteriores recuperando toda la estructura.

ajustamos la ventana principal a un tamaño fijo y bloqueamos el redimensionamiento para evitar que la imagen de fondo se deforme o se pixele excesivamente.