package com.lab.apis.controller;
import com.lab.apis.model.Estudiante;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {
    private final List<Estudiante> estudiantes=new ArrayList<>();
    public EstudianteController(){
        estudiantes.add(new Estudiante(1, "Carlos", "Lopez", "Ingenieria en Sistemas", 20));
        estudiantes.add(new Estudiante(2, "Maria", "Gomez", "Administracion", 21));
        estudiantes.add(new Estudiante(3, "Jose", "Perez", "Ingenieria Civil", 22));
        estudiantes.add(new Estudiante(4, "Ana", "Ramirez", "Psicologia", 19));
        estudiantes.add(new Estudiante(5, "Luis", "Hernandez", "Contaduria", 23));
    }
    @GetMapping public List<Estudiante> obtenerTodos(){return estudiantes;}
    @GetMapping("/{id}") public ResponseEntity<Estudiante> obtenerPorId(@PathVariable Long id){return estudiantes.stream().filter(x->x.getId().equals(id)).findFirst().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @PostMapping public ResponseEntity<Estudiante> crear(@RequestBody Estudiante objeto){long id=estudiantes.stream().mapToLong(x->x.getId()).max().orElse(0)+1; objeto.setId(id); estudiantes.add(objeto); return ResponseEntity.status(HttpStatus.CREATED).body(objeto);}
    @PutMapping("/{id}") public ResponseEntity<Estudiante> put(@PathVariable Long id,@RequestBody Estudiante objeto){for(int i=0;i<estudiantes.size();i++)if(estudiantes.get(i).getId().equals(id)){objeto.setId(id);estudiantes.set(i,objeto);return ResponseEntity.ok(objeto);}return ResponseEntity.notFound().build();}
    @PatchMapping("/{id}") public ResponseEntity<Estudiante> patch(@PathVariable Long id,@RequestBody Map<String,Object> datos){Estudiante objeto=estudiantes.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);if(objeto==null)return ResponseEntity.notFound().build();
        if(datos.containsKey("nombre")){ Object valor=datos.get("nombre"); if(valor!=null) objeto.setNombre(String.valueOf(valor)); }
        if(datos.containsKey("apellido")){ Object valor=datos.get("apellido"); if(valor!=null) objeto.setApellido(String.valueOf(valor)); }
        if(datos.containsKey("carrera")){ Object valor=datos.get("carrera"); if(valor!=null) objeto.setCarrera(String.valueOf(valor)); }
        if(datos.containsKey("edad")){ Object valor=datos.get("edad"); if(valor!=null) objeto.setEdad(((Number) valor).intValue()); }
return ResponseEntity.ok(objeto);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){return estudiantes.removeIf(x->x.getId().equals(id))?ResponseEntity.noContent().build():ResponseEntity.notFound().build();}
}
