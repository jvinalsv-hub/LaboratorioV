package com.lab.apis.controller;
import com.lab.apis.model.Tarea;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/tareas")
public class TareaController {
    private final List<Tarea> tareas=new ArrayList<>();
    public TareaController(){
        tareas.add(new Tarea(1, "Estudiar Java", "Repasar POO", "ALTA", false));
        tareas.add(new Tarea(2, "Hacer laboratorio", "Completar APIs REST", "ALTA", false));
        tareas.add(new Tarea(3, "Leer documentacion", "Revisar Spring Boot", "MEDIA", true));
        tareas.add(new Tarea(4, "Subir codigo", "Crear GitHub", "ALTA", false));
        tareas.add(new Tarea(5, "Preparar exposicion", "Organizar diapositivas", "BAJA", false));
    }
    @GetMapping public List<Tarea> obtenerTodos(){return tareas;}
    @GetMapping("/{id}") public ResponseEntity<Tarea> obtenerPorId(@PathVariable Long id){return tareas.stream().filter(x->x.getId().equals(id)).findFirst().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @PostMapping public ResponseEntity<Tarea> crear(@RequestBody Tarea objeto){long id=tareas.stream().mapToLong(x->x.getId()).max().orElse(0)+1; objeto.setId(id); tareas.add(objeto); return ResponseEntity.status(HttpStatus.CREATED).body(objeto);}
    @PutMapping("/{id}") public ResponseEntity<Tarea> put(@PathVariable Long id,@RequestBody Tarea objeto){for(int i=0;i<tareas.size();i++)if(tareas.get(i).getId().equals(id)){objeto.setId(id);tareas.set(i,objeto);return ResponseEntity.ok(objeto);}return ResponseEntity.notFound().build();}
    @PatchMapping("/{id}") public ResponseEntity<Tarea> patch(@PathVariable Long id,@RequestBody Map<String,Object> datos){Tarea objeto=tareas.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);if(objeto==null)return ResponseEntity.notFound().build();
        if(datos.containsKey("titulo")){ Object valor=datos.get("titulo"); if(valor!=null) objeto.setTitulo(String.valueOf(valor)); }
        if(datos.containsKey("descripcion")){ Object valor=datos.get("descripcion"); if(valor!=null) objeto.setDescripcion(String.valueOf(valor)); }
        if(datos.containsKey("prioridad")){ Object valor=datos.get("prioridad"); if(valor!=null) objeto.setPrioridad(String.valueOf(valor)); }
        if(datos.containsKey("completada")){ Object valor=datos.get("completada"); if(valor!=null) objeto.setCompletada((Boolean) valor); }
return ResponseEntity.ok(objeto);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){return tareas.removeIf(x->x.getId().equals(id))?ResponseEntity.noContent().build():ResponseEntity.notFound().build();}
}
