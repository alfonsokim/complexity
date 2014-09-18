@echo off
goto finComentario
  == Complejidad y Computabilidad ==
Alfonso Kim
Script de compilacion para Windows

:finComentario
goto checar

:checar
set JAVAC=
set JAVA=
set JAR=
javac -version >nul 2>&1 && ( set JAVAC=t ) || ( goto faltaJavaC )
java -version >nul 2>&1 && ( set JAVA=t ) || ( goto faltaJava )
jar -t <compilar.bat >nul 2>&1 && ( set JAR=t ) || ( goto faltaJar )

goto limpiar

:limpiar
IF exist bin/ ( rmdir /S /Q bin )
mkdir bin
IF exist simulacion.jar (del simulacion.jar)
goto compilar

:compilar
javac -d bin src/cyc/simulacion/*.java src/cyc/simulacion/funcion/*.java src/cyc/simulacion/util/*.java
cd bin
jar cvfe simulacion.jar cyc.simulacion.Simulacion cyc/simulacion/ cyc/simulacion/funcion/ cyc/simulacion/util/
move simulacion.jar ..
cd ..
goto fin

:faltaJavaC
echo ejecutable javac no encontrado en el path
goto fin

:faltaJava
echo ejecutable java no encontrado en el path
goto fin

:faltaJar
echo ejecutable jar no encontrado en el path
goto fin

:fin