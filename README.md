# laboratorio-is-2016-2

Repositorio de documentos y ejemplos de la clase de laboratorio de Ingenieria de Software.

* Profesora: Hanna Oktaba
* Ayudante: Isael Durán
* Ayudante de laboratorio: Miguel Angel Piña Avelino

## Ejemplo de la clase 3.

Para poder ejecutar el ejemplo de la clase número 3, hay que cargar el archivo clase-3/usuarios.sql
```
sh
psql nombre-base-datos -f clase-3/usuarios.sql
```

Una vez cargada el código de su base de datos, basta con correr el proyecto desde Netbeans,
seleccionado con el botón derecho la opción de run/ejecutar.

### Gotchas

Hay que cambiar el nombre del usuario de la base de datos por el de ustedes. Esto se cambia en
el archivo `hibernate.cfg.xml` en el atributo `hibernate.connection.username`.

También hay que tener cuidado con el puerto y el nombre de la base de datos.

## Ejemplo de la clase 6

El ejemplo de los managed beans no requiere configuración adicional,
basta con abrirla con netbeans y ejecutar el proyecto.
