package rogeriogentil.alurafood.pagamentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rogeriogentil.alurafood.pagamentos.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
