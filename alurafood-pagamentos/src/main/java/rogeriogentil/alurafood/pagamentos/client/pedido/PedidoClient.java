package rogeriogentil.alurafood.pagamentos.client.pedido;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rogeriogentil.alurafood.pagamentos.model.Pedido;

@FeignClient("alurafood-pedidos-ms")
public interface PedidoClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/pedidos/v1/{id}/pago")
    void atualizaPagamento(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/pedidos/v1/{id}")
    Pedido obterPedido(@PathVariable Long id);
}
