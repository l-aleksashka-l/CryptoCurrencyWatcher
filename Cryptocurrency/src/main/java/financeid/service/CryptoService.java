package financeid.service;

import financeid.model.Crypto;
import financeid.repo.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoService {

    private CryptoRepository cryptoRepository;

    @Autowired
    public CryptoService(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    public List<Crypto> findAll(){
        return cryptoRepository.findAll();
    }

    public Crypto saveCrypto(Crypto crypto){
        return cryptoRepository.save(crypto);
    }
}
