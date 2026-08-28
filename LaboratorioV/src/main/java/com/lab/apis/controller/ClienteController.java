package com.lab.apis.controller;
import com.lab.apis.model.Cliente;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final List<Cliente> clientes=new ArrayList<>();
    public ClienteController(){
        clientes.add(new Cliente(1, "Juan", "Perez", "juan@example.com", "5555-1001"));
        clientes.add(new Cliente(2, "Maria", "Lopez", "maria@example.com", "5555-1002"));
        clientes.add(new Cliente(3, "Carlos", "Gomez", "carlos@example.com", "5555-1003"));
        clientes.add(new Cliente(4, "Ana", "Ramirez", "ana@example.com", "5555-1004"));
        clientes.add(new Cliente(5, "Luis", "Hernandez", "luis@example.com", "5555-1005"));
    }
    @GetMapping public List<Cliente> obtenerTodos(){return clientes;}
    @GetMapping("/{id}") public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id){return clientes.stream().filter(x->x.getId().equals(id)).findFirst().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @PostMapping public ResponseEntity<Cliente> crear(@RequestBody Cliente objeto){long id=clientes.stream().mapToLong(x->x.getId()).max().orElse(0)+1; objeto.setId(id); clientes.add(objeto); return ResponseEntity.status(HttpStatus.CREATED).body(objeto);}
    @PutMapping("/{id}") public ResponseEntity<Cliente> put(@PathVariable Long id,@RequestBody Cliente objeto){for(int i=0;i<clientes.size();i++)if(clientes.get(i).getId().equals(id)){objeto.setId(id);clientes.set(i,objeto);return ResponseEntity.ok(objeto);}return ResponseEntity.notFound().build();}
    @PatchMapping("/{id}") public ResponseEntity<Cliente> patch(@PathVariable Long id,@RequestBody Map<String,Object> datos){Cliente objeto=clientes.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);if(objeto==null)return ResponseEntity.notFound().build();
        if(datos.containsKey("nombre")){ Object valor=datos.get("nombre"); if(valor!=null) objeto.setNombre(String.valueOf(valor)); }
        if(datos.containsKey("apellido")){ Object valor=datos.get("apellido"); if(valor!=null) objeto.setApellido(String.valueOf(valor)); }
        if(datos.containsKey("correo")){ Object valor=datos.get("correo"); if(valor!=null) objeto.setCorreo(String.valueOf(valor)); }
        if(datos.containsKey("telefono")){ Object valor=datos.get("telefono"); if(valor!=null) objeto.setTelefono(String.valueOf(valor)); }
return ResponseEntity.ok(objeto);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){return clientes.removeIf(x->x.getId().equals(id))?ResponseEntity.noContent().build():ResponseEntity.notFound().build();}
}
