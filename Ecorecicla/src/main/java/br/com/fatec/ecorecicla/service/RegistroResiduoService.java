package br.com.fatec.ecorecicla.service;

import br.com.fatec.ecorecicla.model.RegistroResiduo;
import br.com.fatec.ecorecicla.repository.RegistroResiduoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.opencsv.CSVReader;
import java.io.InputStreamReader;
import java.io.Reader;



@Service
public class RegistroResiduoService {

    @Autowired
    private RegistroResiduoRepository repository;

    public List<RegistroResiduo> buscarPorEstado(String estado) {
        return repository.findByEstado(estado);
    }

    public List<RegistroResiduo> buscarPorTaxaMenorQue(Double taxa) {
        return repository.findByTaxaReciclagemLessThan(taxa);
    }


    public RegistroResiduo atualizarRegistro(Long id, RegistroResiduo dadosNovos) {
        return repository.findById(id)
                .map(registro ->{
                    registro.setMunicipio(dadosNovos.getMunicipio());
                    registro.setEstado(dadosNovos.getEstado());
                    registro.setQuantidadeGerada(dadosNovos.getQuantidadeGerada());
                    registro.setTaxaReciclagem(dadosNovos.getTaxaReciclagem());
                    registro.setAno(dadosNovos.getAno());
                    return repository.save(registro);
                })
                .orElseThrow(() -> new RuntimeException("Registro não encontrado para o ID: " + id));
    }


    public List<RegistroResiduo> listarTodos() {
        return repository.findAll();
    }

    public Optional<RegistroResiduo> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public RegistroResiduo salvar(RegistroResiduo registro) {
        return repository.save(registro);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @PostConstruct
    public void carregarDadosCSV() {

        try {
            Reader reader = new InputStreamReader(
                    getClass().getResourceAsStream("/data/residuos.csv"));
            CSVReader csvReader = new CSVReader(reader);
            String[] linha;
            csvReader.readNext();

            while ((linha = csvReader.readNext()) != null) {
                RegistroResiduo registro = new RegistroResiduo(
                        null,
                        linha[0],
                        linha[1],
                        Double.parseDouble(linha[2]),
                        Double.parseDouble(linha[3]),
                        Integer.parseInt(linha[4])
                );

                repository.save(registro);
            }

            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}