# JuegoGDX

## Platformas

- `core`: Módulo que contendría toda la lógica del programa (packages, .java, etc.)
- `lwjgl3`: Plataforma de escritorio LWJGL3 que se utiliza para **ejecutar el programa**.

## Gradle
El proyecto utiliza Gradle para manejar las dependencias que utiliza el programa, en este caso
debido a que utilizamos la librería `freetype` de LibGDX que sería externa a las librerías por defecto
de LibGDX.
Para hacer build al projecto se tendría que ejecutar `./gradlew clean build` para posterior ejecución
del juego mismo.

Se recomienda tener una configuración `Run` en el caso de IntelliJ, el cual permitirá ejecutar el comando
build y ejecutar el archivo .java Lwjgl3Launcher de manera sucesiva para mayor comodidad.

## Instalación
Para instalar el programa simplemente debemos arrastrar/clonar el proyecto a un directorio en el que
deseamos tener el juego y sus binarios, y asegurarse que no existan conflictos de directorios en el caso de
importar los packages manualmente.

## Ejecutar
Para ejecutar el programa debemos de ir al package lwjgl3 que contendría el .java para ejecutar el programa,
en donde, al correr el .java Lwjgl3Launcher dentro del directorio "io/github/juego/lwjgl3", se podrá ejecutar
el juego sin problemas.
