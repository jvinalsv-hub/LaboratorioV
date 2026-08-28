package com.lab.apis.controller;
import com.lab.apis.model.Libro;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/libros")
public class LibroController {
    private final List<Libro> libros=new ArrayList<>();
    public LibroController(){
        libros.add(new Libro(1, "Cien anos de soledad", "Gabriel Garcia Marquez", "Novela", 18.5));
        libros.add(new Libro(2, "1984", "George Orwell", "Distopia", 22));
        libros.add(new Libro(3, "El Principito", "Antoine de Saint-Exupery", "Fantasia", 15.75));
        libros.add(new Libro(4, "Don Quijote", "Miguel de Cervantes", "Novela", 30));
        libros.add(new Libro(5, "La Odisea", "Homero", "Epico", 24.9));
    }
    @GetMapping public List<Libro> obtenerTodos(){return libros;}
    @GetMapping("/{id}") public ResponseEntity<Libro> obtenerPorId(@PathVariable Long id){return libros.stream().filter(x->x.getId().equals(id)).findFirst().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @PostMapping public ResponseEntity<Libro> crear(@RequestBody Libro objeto){long id=libros.stream().mapToLong(x->x.getId()).max().orElse(0)+1; objeto.setId(id); libros.add(objeto); return ResponseEntity.status(HttpStatus.CREATED).body(objeto);}
    @PutMapping("/{id}") public ResponseEntity<Libro> put(@PathVariable Long id,@RequestBody Libro objeto){for(int i=0;i<libros.size();i++)if(libros.get(i).getId().equals(id)){objeto.setId(id);libros.set(i,objeto);return ResponseEntity.ok(objeto);}return ResponseEntity.notFound().build();}
    @PatchMapping("/{id}") public ResponseEntity<Libro> patch(@PathVariable Long id,@RequestBody Map<String,Object> datos){Libro objeto=libros.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);if(objeto==null)return ResponseEntity.notFound().build();
        if(datos.containsKey("titulo")){ Object valor=datos.get("titulo"); if(valor!=null) objeto.setTitulo(String.valueOf(valor)); }
        if(datos.containsKey("autor")){ Object valor=datos.get("autor"); if(valor!=null) objeto.setAutor(String.valueOf(valor)); }
        if(datos.containsKey("genero")){ Object valor=datos.get("genero"); if(valor!=null) objeto.setGenero(String.valueOf(valor)); }
        if(datos.containsKey("precio")){ Object valor=datos.get("precio"); if(valor!=null) objeto.setPrecio(((Number) valor).doubleValue()); }
return ResponseEntity.ok(objeto);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){return libros.removeIf(x->x.getId().equals(id))?ResponseEntity.noContent().build():ResponseEntity.notFound().build();}
}
