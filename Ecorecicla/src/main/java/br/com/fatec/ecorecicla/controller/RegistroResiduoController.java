package br.com.fatec.ecorecicla.controller;

import br.com.fatec.ecorecicla.model.RegistroResiduo;
import br.com.fatec.ecorecicla.service.RegistroResiduoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/residuos")
@CrossOrigin("*")
public class RegistroResiduoController {

    @Autowired
    private RegistroResiduoService service;

    @GetMapping
    public List<RegistroResiduo> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroResiduo> buscarPorId(@PathVariable Long id) {

        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public List<RegistroResiduo> buscarPorEstado(
            @PathVariable String estado) {

        return service.buscarPorEstado(estado);
    }

    @GetMapping("/taxa/{taxa}")
    public List<RegistroResiduo> buscarPorTaxa(
            @PathVariable Double taxa) {

        return service.buscarPorTaxaMenorQue(taxa);
    }

    @PostMapping
    public ResponseEntity<RegistroResiduo> salvar(@RequestBody RegistroResiduo registro) {

        RegistroResiduo novoRegistro = service.salvar(registro);

        return ResponseEntity.status(201).body(novoRegistro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
    @PostMapping("/carregar")

    public ResponseEntity<String> carregarCSV() {
        service.carregarDadosCSV();
        return ResponseEntity.ok("Dados carregados com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroResiduo> atualizar(@PathVariable long id,@RequestBody RegistroResiduo registro) {
        try {
            RegistroResiduo atualizado = service.atualizarRegistro(id, registro);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

