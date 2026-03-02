# 📚 Literalura
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

Aplicación de consola desarrollada con **Java + Spring Boot + JPA + Hibernate** que permite consultar, almacenar y gestionar libros y autores utilizando una base de datos relacional.

Proyecto realizado como práctica de persistencia de datos y consultas personalizadas con Spring Data JPA.

---

## 🚀 Funcionalidades

✔️ Buscar libros por título  
✔️ Guardar libros evitando duplicados  
✔️ Reutilizar autores existentes en la base  
✔️ Listar todos los libros almacenados  
✔️ Listar autores con sus respectivos libros  
✔️ Consultar autores vivos en un año específico  
✔️ Filtrar libros por idioma  

---

## 🧠 Lógica destacada

### 🔎 Consulta de autores vivos en un año

Se consideran vivos aquellos autores que cumplan:

anioNacimiento <= añoConsultado
AND
(anioFallecimiento IS NULL OR anioFallecimiento >= añoConsultado)


Implementado mediante JPQL personalizada.

---

### 🌍 Filtro por idioma

Mini menú interactivo que permite seleccionar:

- 🇺🇸 Inglés (`en`)
- 🇪🇸 Español (`es`)
- 🇫🇷 Francés (`fr`)
- 🇧🇷 Portugués (`pt`)

Utiliza **Derived Query Methods** de Spring Data:

```java
List<Libro> findByIdiomaIgnoreCase(String idioma);
```

🏗️ Tecnologías utilizadas

☕ Java 17+
🌱 Spring Boot
📦 Spring Data JPA
🐘 Hibernate
🗄️ Base de datos relacional
🖥️ Aplicación de consola

🗃️ Modelo de datos

📘 Libro
Título
Idioma
Número de descargas
Autor (ManyToOne)

👤 Autor
Nombre
Año de nacimiento
Año de fallecimiento
Lista de libros (OneToMany)

🧩 Conceptos aplicados
Relaciones bidireccionales JPA
JOIN FETCH para evitar LazyInitializationException
DISTINCT en consultas con colecciones
Queries personalizadas con @Query
Manejo de N+1 problem
Reutilización de métodos para impresión
Validación de duplicados antes de persistir

▶️ Cómo ejecutar

Clonar el repositorio:

git clone <url-del-repo>

Entrar al proyecto:

cd Literalura

Ejecutar:

mvn spring-boot:run
📈 Estado del proyecto

✅ Funcional
✅ Persistencia estable
✅ Consultas optimizadas
✅ Listo para presentación

👨‍💻 Autor

Desarrollado por Marcos Santino Mazzanti
