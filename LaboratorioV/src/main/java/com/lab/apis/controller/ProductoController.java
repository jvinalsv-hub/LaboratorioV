package com.lab.apis.controller;
import com.lab.apis.model.Producto;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final List<Producto> productos=new ArrayList<>();
    public ProductoController(){
        productos.add(new Producto(1, "Laptop Lenovo", 799.99, "Tecnologia"));
        productos.add(new Producto(2, "Mouse Logitech", 25.5, "Accesorios"));
        productos.add(new Producto(3, "Teclado Mecanico", 65, "Accesorios"));
        productos.add(new Producto(4, "Monitor Samsung", 249.99, "Tecnologia"));
        productos.add(new Producto(5, "Audifonos Sony", 89.9, "Audio"));
    }
    @GetMapping public List<Producto> obtenerTodos(){return productos;}
    @GetMapping("/{id}") public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id){return productos.stream().filter(x->x.getId().equals(id)).findFirst().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @PostMapping public ResponseEntity<Producto> crear(@RequestBody Producto objeto){long id=productos.stream().mapToLong(x->x.getId()).max().orElse(0)+1; objeto.setId(id); productos.add(objeto); return ResponseEntity.status(HttpStatus.CREATED).body(objeto);}
    @PutMapping("/{id}") public ResponseEntity<Producto> put(@PathVariable Long id,@RequestBody Producto objeto){for(int i=0;i<productos.size();i++)if(productos.get(i).getId().equals(id)){objeto.setId(id);productos.set(i,objeto);return ResponseEntity.ok(objeto);}return ResponseEntity.notFound().build();}
    @PatchMapping("/{id}") public ResponseEntity<Producto> patch(@PathVariable Long id,@RequestBody Map<String,Object> datos){Producto objeto=productos.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);if(objeto==null)return ResponseEntity.notFound().build();
        if(datos.containsKey("nombre")){ Object valor=datos.get("nombre"); if(valor!=null) objeto.setNombre(String.valueOf(valor)); }
        if(datos.containsKey("precio")){ Object valor=datos.get("precio"); if(valor!=null) objeto.setPrecio(((Number) valor).doubleValue()); }
        if(datos.containsKey("categoria")){ Object valor=datos.get("categoria"); if(valor!=null) objeto.setCategoria(String.valueOf(valor)); }
return ResponseEntity.ok(objeto);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){return productos.removeIf(x->x.getId().equals(id))?ResponseEntity.noContent().build():ResponseEntity.notFound().build();}
}
