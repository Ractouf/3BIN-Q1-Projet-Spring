package be.vinci.ipl.wallet;

import be.vinci.ipl.wallet.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class WalletService {
    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    public double netWorth(String owner) throws NotFoundException {
        if (!repository.existsByUsername(owner))
            throw new NotFoundException();
        double result = 0;
        Iterable<PositionUser> positions = repository.findAllByUsername(owner);

        for (PositionUser p : positions) {
            result += (p.getQuantity() * p.getUnitvalue());
        }
        return result;
    }

    public Iterable<PositionUser> positions(String owner) throws NotFoundException {
        if (!repository.existsByUsername(owner))
            throw new NotFoundException();
        ArrayList<PositionUser> result = new ArrayList<>();
        Iterable<PositionUser> positions = repository.findAllByUsername(owner);
        for (PositionUser p : positions) {
            if(p.getQuantity() > 0)
                result.add(p);
        }
        return result;
    }

    public Iterable<PositionUser> addPosition(String owner, Iterable<Position> ajouts) {
        Iterable<PositionUser> positions = repository.findAllByUsername(owner);
        boolean enter;

        for (Position pAjout : ajouts) {
            enter = false;
            for (PositionUser pPossede : positions) {
                if (Objects.equals(pAjout.getTicker(), pPossede.getTicker())) {
                    pPossede.setQuantity(pPossede.getQuantity() + pAjout.getQuantity());
                    repository.save(pPossede);
                    enter = true;
                    break;
                }
            }
            if (!enter) {
                PositionUser pNouveau = new PositionUser();
                pNouveau.setTicker(pAjout.getTicker());
                pNouveau.setUsername(owner);
                pNouveau.setUnitvalue(pAjout.getUnitvalue());
                pNouveau.setQuantity(pAjout.getQuantity());
                repository.save(pNouveau);
            }
        }
        return repository.findAllByUsername(owner);
    }
}
