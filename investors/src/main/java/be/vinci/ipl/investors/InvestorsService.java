package be.vinci.ipl.investors;

import be.vinci.ipl.investors.repositories.AuthenticationProxy;
import be.vinci.ipl.investors.repositories.InvestorsRepository;
import be.vinci.ipl.investors.repositories.WalletProxy;
import be.vinci.ipl.investors.exceptions.BadRequestException;
import be.vinci.ipl.investors.exceptions.NotFoundException;
import be.vinci.ipl.investors.models.Investor;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class InvestorsService {
    private final InvestorsRepository repository;
    private final WalletProxy walletProxy;
    private final AuthenticationProxy authenticationProxy;

    public InvestorsService(InvestorsRepository repository, WalletProxy walletProxy,
        AuthenticationProxy authenticationProxy) {
        this.repository = repository;
        this.walletProxy = walletProxy;
        this.authenticationProxy = authenticationProxy;
    }

    /**
     * Creates a new investor in the database.
     * @param investor The investor to create.
     * @return True if the investor was created, false otherwise.
     */
    public boolean createOne(Investor investor) {
        if (repository.existsByUsername(investor.getUsername())) return false;

        repository.save(investor);
        return true;
    }

    /**
     * Reads an investor from the database.
     * @param username The username of the investor to read.
     * @return The investor if it exists, null otherwise.
     */
    public Investor readOne(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    /**
     * Updates an investor in the database.
     * @param newInvestor The new investor to update.
     * @return True if the investor was updated, false otherwise.
     */
    public boolean updateOne(Investor newInvestor) {
        Investor oldInvestor = repository.findByUsername(newInvestor.getUsername()).orElse(null);
        if (oldInvestor == null) return false;

        newInvestor.setId(oldInvestor.getId());

        repository.save(newInvestor);
        return true;
    }

    /**
     * Deletes an investor from the database.
     * @param username The username of the investor to delete.
     * @throws NotFoundException If the investor does not exist.
     * @throws BadRequestException If the investor has a non-zero net worth.
     */
    public void deleteOne(String username) throws NotFoundException, BadRequestException {
        if (!repository.existsByUsername(username)) throw new NotFoundException();

        try {
            if (walletProxy.netWorth(username) != 0) {
                throw new BadRequestException();
            }

            authenticationProxy.deleteCredentials(username);
            repository.deleteByUsername(username);
        } catch (FeignException e) {
            throw new NotFoundException();
        }
    }
}
