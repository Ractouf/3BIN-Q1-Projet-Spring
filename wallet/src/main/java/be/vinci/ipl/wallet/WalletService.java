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

    /**
     * Get the value of an investor's entire wallet
     * @param owner the investor
     * @throws NotFoundException when the investor isn't found
     * @return the net-worth of an investor
     */
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

    /**
     * Get a list of an investor's open positions
     * @param owner the investor
     * @throws NotFoundException when the investor isn't found
     * @return a list of an investor's open positions
     */
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

    /**
     * Add a list of positions to an investor's wallet
     * @param owner the investor
     * @param ajouts the list of positions to add to the wallet
     * @return the wallet after the changes
     * we decided not to throw a 404 error since there wasn't any other way to create a wallet
     */
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
