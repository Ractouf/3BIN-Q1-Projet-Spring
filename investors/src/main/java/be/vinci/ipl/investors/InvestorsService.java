package be.vinci.ipl.investors;

import org.springframework.stereotype.Service;

@Service
public class InvestorsService {
    private final InvestorsRepository repository;

    public InvestorsService(InvestorsRepository repository) {
        this.repository = repository;
    }

    public boolean createOne(Investor investor) {
        if (repository.existsByUsername(investor.getUsername())) return false;

        repository.save(investor);
        return true;
    }

    public Investor readOne(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    public boolean updateOne(Investor newInvestor) {
        Investor oldInvestor = repository.findByUsername(newInvestor.getUsername()).orElse(null);
        if (oldInvestor == null) return false;

        newInvestor.setId(oldInvestor.getId());

        repository.save(newInvestor);
        return true;
    }

    public boolean deleteOne(String username) {
        if (!repository.existsByUsername(username)) return false;

        //TODO /!\ Attention /!\
        // Lorsqu'on supprime un utilisateur du système, on supprime ses credentials et son portefeuille
        // (il faut que celui-ci soit vide pour qu'on puisse supprimer le compte de l'investisseur).

        repository.deleteByUsername(username);
        return true;
    }
}
