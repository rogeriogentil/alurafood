package rogeriogentil.alurafood.pagamentos.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rogeriogentil.alurafood.pagamentos.client.pedido.PedidoClient;
import rogeriogentil.alurafood.pagamentos.dto.PagamentoDTO;
import rogeriogentil.alurafood.pagamentos.model.Pedido;
import rogeriogentil.alurafood.pagamentos.model.Pagamento;
import rogeriogentil.alurafood.pagamentos.model.Status;
import rogeriogentil.alurafood.pagamentos.repository.PagamentoRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PedidoClient pedidoClient;

    public Page<PagamentoDTO> obterTodos(Pageable pageable) {
        return pagamentoRepository.findAll(pageable)
                .map(page -> modelMapper.map(page, PagamentoDTO.class));
    }

    public PagamentoDTO obterPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        PagamentoDTO pagamentoDTO = modelMapper.map(pagamento, PagamentoDTO.class);

        Pedido pedido = pedidoClient.obterPedido(pagamento.getPedidoId());
        pagamentoDTO.setItens(pedido.getItens());

        return pagamentoDTO;
    }

    public PagamentoDTO obterPorIdSemDetalharPedido(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
       return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO obterPorIdSemDetalhesDoPedido(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO criarPagamento(PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = modelMapper.map(pagamentoDTO, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO atualizarPagamento(Long id, PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = modelMapper.map(pagamentoDTO, Pagamento.class);
        pagamento.setId(id);
        pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public void excluirPagamento(Long id) {
        pagamentoRepository.deleteById(id);
    }

    public void confirmarPagamento(Long id) {
        this.confirmarPagamento(id, Status.CONFIRMADO);
        pedidoClient.atualizaPagamento(id);
    }

    public void confirmarPagamentoSemAtualizarPedido(Long id) {
        this.confirmarPagamento(id, Status.CONFIRMADO_SEM_ATUALIZACAO_PEDIDO);
    }

    private void confirmarPagamento(Long id, Status status) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);

        if (!pagamento.isPresent()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(status);
        pagamentoRepository.save(pagamento.get());
    }
}
