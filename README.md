# DeepSpace

Práctica final de la asignatura de **Programación y Diseño Orientado a Objetos**.

Grupo formado por:

- Jose Luis Ruiz Benito.
- Juan Manuel Mateos Pérez.

## Descripción

Este juego es multijugador ya que deben ser 2 personas como mínimo las que participen en él, y 4 como máximo. 

Al comienzo del juego, se reparte aleatoriamente a cada jugador un set de armas y escudos en sus hangares. Cada uno podrá decidir qué armas equipar en su ataque/defensa o qué armas almacenar en el hangar. Por cada ronda, se le asocia a cada jugador un enemigo (hay distintos niveles de dificultad) y se procede a un combate que se decide probabilísticamente dependiendo del ataque y defensa del jugador, y del enemigo a enfrentar. 

Tras el combate, en caso de ganar será premiado el jugador con más armas y/o escudos. Mientras que en caso de perder el combate, deberá descartarse de algunas armas y/o escudos. 

El usuario ganará el juego cuando consiga ganar un número total de combates (aprox 6), o perderá si es vencido ese mismo número de veces.

## Ejecución 

Este juego está programado en dos lenguages: *Java* y *Ruby*.

* Para ejecutar en **Linux** el programa de *Java*, debemos acceder a la carpeta `Java` e introducir en la terminal el siguiente comando :

    `java -cp ./dist/deepspace.jar Main.PlayWithUI`

    si queremos jugar en la versión de terminal, es decir, sin interfaz gráfica. O

    `java -cp ./dist/deepspace.jar Main.PlayWithGUI`

    si quieremos jugar en la versión con interfaz gráfica.

* Para ejecutar en **Linux** el programa de *Ruby*, debemos acceder a la carpeta `Ruby` e introducir en la terminal el siguiente comando :

    `ruby Main.rb`

    y disfrutar del juego.

### Notas :

1. Para ejecutar en **Windows** solo hay que cambiar `/` por `\`.
2. Obviamente debemos tener instalado tanto *Java* como *Ruby*.