package br.edu.helpdesk;
import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
public class ChamadoController {
 private final ChamadoService service;
 public ChamadoController(ChamadoService service){this.service=service;}
 private boolean vazio(String s){return s==null||s.isBlank();}
 private boolean permitido(String s,String... valores){return s!=null && Arrays.asList(valores).contains(s);}
 private ResponseEntity<?> erro(int status,String msg){return ResponseEntity.status(status).body(Map.of("mensagem",msg));}
 @GetMapping("/health") public Map<String,String> health(){return Map.of("status","UP");}
 @PostMapping("/chamados") public ResponseEntity<?> criar(@RequestBody Chamado c){
  // TODO 1: Valide solicitante, descrição, categoria e prioridade no controller. Use service.criar(c) e retorne 201.
  return erro(501,"Funcionalidade pendente.");
 }
 @GetMapping("/chamados") public ResponseEntity<?> listar(@RequestParam(required=false) String status){
  if(!vazio(status)&&!permitido(status,"ABERTO","RESOLVIDO")) return erro(400,"Status inválido.");
  return ResponseEntity.ok(service.listar(status));
 }
 @PatchMapping("/chamados/{id}/status") public ResponseEntity<?> atualizar(@PathVariable long id,@RequestBody Map<String,String> body){
  String status=body.get("status"); if(!permitido(status,"ABERTO","RESOLVIDO")) return erro(400,"Status inválido.");
  Chamado c=service.atualizar(id,status); return c==null?erro(404,"Chamado inexistente."):ResponseEntity.ok(c);
 }
 @DeleteMapping("/chamados/{id}") public ResponseEntity<?> excluir(@PathVariable long id){
  return service.excluir(id)?ResponseEntity.noContent().build():erro(404,"Chamado inexistente.");
 }
 @GetMapping("/chamados/estatisticas") public ResponseEntity<?> estatisticas(){
  return ResponseEntity.ok(service.estatisticas());
 }
}
