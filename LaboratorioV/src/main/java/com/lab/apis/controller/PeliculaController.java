package com.lab.apis.controller;
import com.lab.apis.model.Pelicula;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {
    private final List<Pelicula> peliculas=new ArrayList<>();
    public PeliculaController(){
        peliculas.add(new Pelicula(1, "Inception", "Christopher Nolan", "Ciencia ficcion", 2010));
        peliculas.add(new Pelicula(2, "Interstellar", "Christopher Nolan", "Ciencia ficcion", 2014));
        peliculas.add(new Pelicula(3, "The Matrix", "Wachowski", "Accion", 1999));
        peliculas.add(new Pelicula(4, "Coco", "Lee Unkrich", "Animacion", 2017));
        peliculas.add(new Pelicula(5, "Avengers Endgame", "Russo", "Superheroes", 2019));
    }
    @GetMapping public List<Pelicula> obtenerTodos(){return peliculas;}
    @GetMapping("/{id}") public ResponseEntity<Pelicula> obtenerPorId(@PathVariable Long id){return peliculas.stream().filter(x->x.getId().equals(id)).findFirst().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @PostMapping public ResponseEntity<Pelicula> crear(@RequestBody Pelicula objeto){long id=peliculas.stream().mapToLong(x->x.getId()).max().orElse(0)+1; objeto.setId(id); peliculas.add(objeto); return ResponseEntity.status(HttpStatus.CREATED).body(objeto);}
    @PutMapping("/{id}") public ResponseEntity<Pelicula> put(@PathVariable Long id,@RequestBody Pelicula objeto){for(int i=0;i<peliculas.size();i++)if(peliculas.get(i).getId().equals(id)){objeto.setId(id);peliculas.set(i,objeto);return ResponseEntity.ok(objeto);}return ResponseEntity.notFound().build();}
    @PatchMapping("/{id}") public ResponseEntity<Pelicula> patch(@PathVariable Long id,@RequestBody Map<String,Object> datos){Pelicula objeto=peliculas.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);if(objeto==null)return ResponseEntity.notFound().build();
        if(datos.containsKey("titulo")){ Object valor=datos.get("titulo"); if(valor!=null) objeto.setTitulo(String.valueOf(valor)); }
        if(datos.containsKey("director")){ Object valor=datos.get("director"); if(valor!=null) objeto.setDirector(String.valueOf(valor)); }
        if(datos.containsKey("genero")){ Object valor=datos.get("genero"); if(valor!=null) objeto.setGenero(String.valueOf(valor)); }
        if(datos.containsKey("anio")){ Object valor=datos.get("anio"); if(valor!=null) objeto.setAnio(((Number) valor).intValue()); }
return ResponseEntity.ok(objeto);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){return peliculas.removeIf(x->x.getId().equals(id))?ResponseEntity.noContent().build():ResponseEntity.notFound().build();}
}
