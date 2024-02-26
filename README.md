# SPRING-BOOT-APP

Para ejecutar la aplicación:
# Windows y Linux

- Ingresar al directorio root desde terminal.
- Ejecutar 'mvn clean package'.
- Luego, 'mvn spring-boot:run'.

# Los datos iniciales ya se cargan solos

# Autenticación JWT

usuario: user@user.com
password: password

También existe la opción de crear tu propio usuario. /api/signup

Utilizando este payload
{
  "firstName":"string",
  "lastName": "string",
  "email": "string",
  "password":"string"
}

Luego para logearse solamente se ingresa a /api/signin y se utiliza el Token devuelto para poder pegarle a los endpoints

# Swagger
También entrando a http://localhost:8080/swagger-ui.html se puede ver el template generado por swagger como documentación.

URL de Servicio hosteado en Azure
https://market-webapp.azurewebsites.net/
