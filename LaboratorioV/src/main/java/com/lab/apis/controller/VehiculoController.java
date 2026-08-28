package com.lab.apis.controller;
import com.lab.apis.model.Vehiculo;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    private final List<Vehiculo> vehiculos=new ArrayList<>();
    public VehiculoController(){
        vehiculos.add(new Vehiculo(1, "Toyota", "Corolla", 2022, 14500));
        vehiculos.add(new Vehiculo(2, "Honda", "Civic", 2021, 18000));
        vehiculos.add(new Vehiculo(3, "Mazda", "CX-5", 2023, 28500));
        vehiculos.add(new Vehiculo(4, "Kia", "Sportage", 2022, 24000));
        vehiculos.add(new Vehiculo(5, "Hyundai", "Elantra", 2024, 19500));
    }
    @GetMapping public List<Vehiculo> obtenerTodos(){return vehiculos;}
    @GetMapping("/{id}") public ResponseEntity<Vehiculo> obtenerPorId(@PathVariable Long id){return vehiculos.stream().filter(x->x.getId().equals(id)).findFirst().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @PostMapping public ResponseEntity<Vehiculo> crear(@RequestBody Vehiculo objeto){long id=vehiculos.stream().mapToLong(x->x.getId()).max().orElse(0)+1; objeto.setId(id); vehiculos.add(objeto); return ResponseEntity.status(HttpStatus.CREATED).body(objeto);}
    @PutMapping("/{id}") public ResponseEntity<Vehiculo> put(@PathVariable Long id,@RequestBody Vehiculo objeto){for(int i=0;i<vehiculos.size();i++)if(vehiculos.get(i).getId().equals(id)){objeto.setId(id);vehiculos.set(i,objeto);return ResponseEntity.ok(objeto);}return ResponseEntity.notFound().build();}
    @PatchMapping("/{id}") public ResponseEntity<Vehiculo> patch(@PathVariable Long id,@RequestBody Map<String,Object> datos){Vehiculo objeto=vehiculos.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);if(objeto==null)return ResponseEntity.notFound().build();
        if(datos.containsKey("marca")){ Object valor=datos.get("marca"); if(valor!=null) objeto.setMarca(String.valueOf(valor)); }
        if(datos.containsKey("modelo")){ Object valor=datos.get("modelo"); if(valor!=null) objeto.setModelo(String.valueOf(valor)); }
        if(datos.containsKey("anio")){ Object valor=datos.get("anio"); if(valor!=null) objeto.setAnio(((Number) valor).intValue()); }
        if(datos.containsKey("precio")){ Object valor=datos.get("precio"); if(valor!=null) objeto.setPrecio(((Number) valor).doubleValue()); }
return ResponseEntity.ok(objeto);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){return vehiculos.removeIf(x->x.getId().equals(id))?ResponseEntity.noContent().build():ResponseEntity.notFound().build();}
}
