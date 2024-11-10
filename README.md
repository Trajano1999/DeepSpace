# DeepSpace

Práctica final de la asignatura de **Programación y Diseño Orientado a Objetos** del Doble Grado en Ingeniería Informática y Matemáticas de la Universidad de Granada.

Grupo formado por:

- Jose Luis Ruiz Benito ([@jruib](https://github.com/jruib))
- Juan Manuel Mateos Pérez ([@Trajano1999](https://github.com/Trajano1999))

## Descripción

Este juego es de tipo multijugador, ya que requiere la participación de un mínimo de 2 y un máximo de 4 personas.

Al inicio de la partida, a cada jugador se le asigna aleatoriamente un conjunto de armas y escudos en sus hangares. Los jugadores pueden decidir cuáles de estas armas equipar para sus acciones de ataque o defensa y cuáles almacenar en el hangar. En cada ronda, se asigna a cada jugador un oponente (con niveles de dificultad variados) y se lleva a cabo un combate. El resultado de este combate se determina de manera probabilística, teniendo en cuenta las capacidades de ataque y defensa tanto del jugador como del oponente.

Tras el combate, si el jugador resulta vencedor, recibe una recompensa en forma de armas y/o escudos adicionales. Por el contrario, si pierde, deberá descartar algunas de sus armas y/o escudos.

La victoria se obtiene tras vencer en un número determinado de combates (aproximadamente 6), mientras que la misma cantidad de derrotas resultará en la pérdida del juego.

## Ejecución 

Este juego está programado en dos lenguages: **Java** y **Ruby**.

* Para ejecutar en **Linux** el programa en **Java**, es necesario acceder a la carpeta ``Java`` e introducir en la terminal alguno de los siguientes comandos:

    - Si queremos jugar en la versión de terminal, es decir, sin interfaz gráfica:

        ``java -cp ./dist/deepspace.jar Main.PlayWithUI``

    - Si quieremos jugar en la versión con interfaz gráfica:

        ``java -cp ./dist/deepspace.jar Main.PlayWithGUI``

* Para ejecutar en **Linux** el programa de **Ruby**, debemos acceder a la carpeta ``Ruby`` e introducir en la terminal el siguiente comando:

    ``ruby Main.rb``

### Notas

1. Para ejecutar el programa en **Windows**, solo hay que cambiar ``/`` por ``\``.
2. Obviamente debemos tener instalado tanto **Java** como **Ruby**.
