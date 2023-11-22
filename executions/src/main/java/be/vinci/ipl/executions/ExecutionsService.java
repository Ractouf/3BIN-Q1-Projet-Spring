package be.vinci.ipl.executions;

import org.springframework.stereotype.Service;

@Service
public class ExecutionsService {
    private final ExecutionsRepository repository;

    public ExecutionsService(ExecutionsRepository repository) {
        this.repository = repository;
    }
}
