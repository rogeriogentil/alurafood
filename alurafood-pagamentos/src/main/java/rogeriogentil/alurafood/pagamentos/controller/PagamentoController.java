package rogeriogentil.alurafood.pagamentos.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import rogeriogentil.alurafood.pagamentos.dto.PagamentoDTO;
import rogeriogentil.alurafood.pagamentos.service.PagamentoService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.logging.Logger;

@RestController
@RequestMapping("/pagamentos/v1")
public class PagamentoController {

    private static final Logger LOGGER = Logger.getLogger(PagamentoController.class.getName());

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public Page<PagamentoDTO> listar(@PageableDefault Pageable paginacao) {
        return pagamentoService.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "detalharPagamento", fallbackMethod = "detalharSemItensDoPedido")
    public ResponseEntity<PagamentoDTO> detalhar(@PathVariable @NotNull Long id) {
        PagamentoDTO pagamentoDTO = pagamentoService.obterPorId(id);
        return ResponseEntity.ok(pagamentoDTO);
    }

    public ResponseEntity<PagamentoDTO> detalharSemItensDoPedido(Long id, Exception e) {
        PagamentoDTO pagamentoDTO = pagamentoService.obterPorIdSemDetalharPedido(id);
        return ResponseEntity.ok(pagamentoDTO);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> cadastrar(@RequestBody @Valid PagamentoDTO pagamentoDTO, UriComponentsBuilder uriBuilder) {
        PagamentoDTO pagamentoDtoRetorno = pagamentoService.criarPagamento(pagamentoDTO);
        URI uri = uriBuilder.path("/pagamentos/v1/{id}")
                .buildAndExpand(pagamentoDtoRetorno.getId())
                .toUri();
        return ResponseEntity.created(uri).body(pagamentoDtoRetorno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDTO dto) {
        PagamentoDTO atualizado = pagamentoService.atualizarPagamento(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDTO> remover(@PathVariable @NotNull Long id) {
        pagamentoService.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "confirmarPagamentoComAtualizacaoPedidoPendente")
    public void confirmarPagamento(@PathVariable @NotNull Long id) {
        LOGGER.info("Confirmando pagamento e atualizando pedido");
        pagamentoService.confirmarPagamento(id);
    }

    public void confirmarPagamentoComAtualizacaoPedidoPendente(Long id, Exception e) {
        LOGGER.info("[FALLBACK] Confirmando pagamento sem atualizar pedido");
        pagamentoService.confirmarPagamentoSemAtualizarPedido(id);
    }
}
