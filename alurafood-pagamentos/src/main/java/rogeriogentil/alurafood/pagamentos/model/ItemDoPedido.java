package rogeriogentil.alurafood.pagamentos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDoPedido {

    private Long id;
    private Integer quantidade;
    private String descricao;

}
