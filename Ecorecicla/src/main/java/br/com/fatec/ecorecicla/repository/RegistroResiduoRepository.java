package br.com.fatec.ecorecicla.repository;

import br.com.fatec.ecorecicla.model.RegistroResiduo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistroResiduoRepository
        extends JpaRepository<RegistroResiduo, Long> {

    List<RegistroResiduo> findByEstado(String estado);

    List<RegistroResiduo> findByTaxaReciclagemLessThan(Double taxa);
}

