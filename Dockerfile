# ===== RUNTIME =====
# Imagen solo JRE Java 11
FROM eclipse-temurin:11-jre

# Directorio de trabajo dentro del contenedor en producción
WORKDIR /app

# Copia el JAR generado por Jenkins
# Para local y Jenkins
#COPY target/*.jar app.jar
# Para github actions
COPY target/*.jar app.jar

# Indica el puerto por el que la aplicación escuchará dentro del contenedor
EXPOSE 8888

# Comando que se ejecuta al arrancar el contenedor: ejecuta la aplicación Java empaquetada
ENTRYPOINT ["java","-jar","app.jar"]
