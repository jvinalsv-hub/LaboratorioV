package com.lab.apis.controller;
import com.lab.apis.model.Pedido;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final List<Pedido> pedidos=new ArrayList<>();
    public PedidoController(){
        pedidos.add(new Pedido(1, "Juan Perez", "Laptop Lenovo", 1, 799.99, "PENDIENTE"));
        pedidos.add(new Pedido(2, "Maria Lopez", "Mouse Logitech", 2, 51, "ENVIADO"));
        pedidos.add(new Pedido(3, "Carlos Gomez", "Monitor Samsung", 1, 249.99, "ENTREGADO"));
        pedidos.add(new Pedido(4, "Ana Ramirez", "Teclado Mecanico", 1, 65, "PENDIENTE"));
        pedidos.add(new Pedido(5, "Luis Hernandez", "Audifonos Sony", 2, 179.8, "ENVIADO"));
    }
    @GetMapping public List<Pedido> obtenerTodos(){return pedidos;}
    @GetMapping("/{id}") public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id){return pedidos.stream().filter(x->x.getId().equals(id)).findFirst().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @PostMapping public ResponseEntity<Pedido> crear(@RequestBody Pedido objeto){long id=pedidos.stream().mapToLong(x->x.getId()).max().orElse(0)+1; objeto.setId(id); pedidos.add(objeto); return ResponseEntity.status(HttpStatus.CREATED).body(objeto);}
    @PutMapping("/{id}") public ResponseEntity<Pedido> put(@PathVariable Long id,@RequestBody Pedido objeto){for(int i=0;i<pedidos.size();i++)if(pedidos.get(i).getId().equals(id)){objeto.setId(id);pedidos.set(i,objeto);return ResponseEntity.ok(objeto);}return ResponseEntity.notFound().build();}
    @PatchMapping("/{id}") public ResponseEntity<Pedido> patch(@PathVariable Long id,@RequestBody Map<String,Object> datos){Pedido objeto=pedidos.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);if(objeto==null)return ResponseEntity.notFound().build();
        if(datos.containsKey("cliente")){ Object valor=datos.get("cliente"); if(valor!=null) objeto.setCliente(String.valueOf(valor)); }
        if(datos.containsKey("producto")){ Object valor=datos.get("producto"); if(valor!=null) objeto.setProducto(String.valueOf(valor)); }
        if(datos.containsKey("cantidad")){ Object valor=datos.get("cantidad"); if(valor!=null) objeto.setCantidad(((Number) valor).intValue()); }
        if(datos.containsKey("total")){ Object valor=datos.get("total"); if(valor!=null) objeto.setTotal(((Number) valor).doubleValue()); }
        if(datos.containsKey("estado")){ Object valor=datos.get("estado"); if(valor!=null) objeto.setEstado(String.valueOf(valor)); }
return ResponseEntity.ok(objeto);}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){return pedidos.removeIf(x->x.getId().equals(id))?ResponseEntity.noContent().build():ResponseEntity.notFound().build();}
}
