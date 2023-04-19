package rogeriogentil.alurafood.pagamentos.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Pedido {

    private List<ItemDoPedido> itens = new ArrayList<>();
}
