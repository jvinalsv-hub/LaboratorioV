package com.lab.apis.controller;
import com.lab.apis.model.Curso;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/cursos")
public class CursoController {
    private final List<Curso> cursos=new ArrayList<>();
    public CursoController(){
        cursos.add(new Curso(1, "Programacion I", "Fundamentos de programacion", 5, "Presencial"));
        cursos.add(new Curso(2, "Bases de Datos", "Diseno y SQL", 4, "Virtual"));
        cursos.add(new Curso(3, "Matematica I", "Calculo y algebra", 5, "Presencial"));
        cursos.add(new Curso(4, "Redes", "Fundamentos de redes", 4, "Hibrida"));
        cursos.add(new Curso(5, "Ingenieria de Software", "Analisis y desarrollo", 4, "Virtual"));
    }
    @GetMapping public List<Curso> obtenerTodos(){return cursos;}
    @GetMapping("/{id}") public ResponseEntity<Curso> obtenerPorId(@PathVariable Long id){return cursos.stream().filter(x->x.getId().equals(id)).findFirst().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @PostMapping public ResponseEntity<Curso> crear(@RequestBody Curso objeto){long id=cursos.stream().mapToLong(x->x.getId()).max().orElse(0)+1; objeto.setId(id); cursos.add(objeto); return ResponseEntity.status(HttpStatus.CREATED).body(objeto);}
    @PutMapping("/{id}") public ResponseEntity<Curso> put(@PathVariable Long id,@RequestBody Curso objeto){for(int i=0;i<cursos.size();i++)if(cursos.get(i).getId().equals(id)){objeto.setId(id);cursos.set(i,objeto);return ResponseEntity.ok(objeto);}return ResponseEntity.notFound().build();}
    @PatchMapping("/{id}") public ResponseEntity<Curso> patch(@PathVariable Long id,@RequestBody Map<String,Object> datos){Curso objeto=cursos.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);if(objeto==null)return ResponseEntity.notFound().build();
        if(datos.containsKey("nombre")){ Object valor=datos.get("nombre"); if(valor!=null) objeto.setNombre(String.valueOf(valor)); }
        if(datos.containsKey("descripcion")){ Object valor=datos.get("descripcion"); if(valor!=null) objeto.setDescripcion(String.valueOf(valor)); }
        if(datos.containsKey("creditos")){ Object valor=datos.get("creditos"); if(valor!=null) objeto.setCreditos(((Number) valor).intValue()); }
        if(datos.containsKey("modalidad")){ Object valor=datos.get("modalidad"); if(valor!=null) objeto.setModalidad(String.valueOf(valor)); }
return ResponseEntity.ok(objeto);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){return cursos.removeIf(x->x.getId().equals(id))?ResponseEntity.noContent().build():ResponseEntity.notFound().build();}
}
