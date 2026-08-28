package com.lab.apis.controller;
import com.lab.apis.model.Empleado;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    private final List<Empleado> empleados=new ArrayList<>();
    public EmpleadoController(){
        empleados.add(new Empleado(1, "Ana Lopez", "Analista", 4500, "Tecnologia"));
        empleados.add(new Empleado(2, "Carlos Perez", "Programador", 6000, "Desarrollo"));
        empleados.add(new Empleado(3, "Maria Gomez", "Contadora", 5000, "Finanzas"));
        empleados.add(new Empleado(4, "Luis Ramirez", "Vendedor", 3800, "Ventas"));
        empleados.add(new Empleado(5, "Sofia Hernandez", "Gerente", 8500, "Administracion"));
    }
    @GetMapping public List<Empleado> obtenerTodos(){return empleados;}
    @GetMapping("/{id}") public ResponseEntity<Empleado> obtenerPorId(@PathVariable Long id){return empleados.stream().filter(x->x.getId().equals(id)).findFirst().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @PostMapping public ResponseEntity<Empleado> crear(@RequestBody Empleado objeto){long id=empleados.stream().mapToLong(x->x.getId()).max().orElse(0)+1; objeto.setId(id); empleados.add(objeto); return ResponseEntity.status(HttpStatus.CREATED).body(objeto);}
    @PutMapping("/{id}") public ResponseEntity<Empleado> put(@PathVariable Long id,@RequestBody Empleado objeto){for(int i=0;i<empleados.size();i++)if(empleados.get(i).getId().equals(id)){objeto.setId(id);empleados.set(i,objeto);return ResponseEntity.ok(objeto);}return ResponseEntity.notFound().build();}
    @PatchMapping("/{id}") public ResponseEntity<Empleado> patch(@PathVariable Long id,@RequestBody Map<String,Object> datos){Empleado objeto=empleados.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);if(objeto==null)return ResponseEntity.notFound().build();
        if(datos.containsKey("nombre")){ Object valor=datos.get("nombre"); if(valor!=null) objeto.setNombre(String.valueOf(valor)); }
        if(datos.containsKey("puesto")){ Object valor=datos.get("puesto"); if(valor!=null) objeto.setPuesto(String.valueOf(valor)); }
        if(datos.containsKey("salario")){ Object valor=datos.get("salario"); if(valor!=null) objeto.setSalario(((Number) valor).doubleValue()); }
        if(datos.containsKey("departamento")){ Object valor=datos.get("departamento"); if(valor!=null) objeto.setDepartamento(String.valueOf(valor)); }
return ResponseEntity.ok(objeto);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){return empleados.removeIf(x->x.getId().equals(id))?ResponseEntity.noContent().build():ResponseEntity.notFound().build();}
}
