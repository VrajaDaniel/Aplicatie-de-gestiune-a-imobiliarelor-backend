package serviciu;

import model.Utilizator;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UtilizatorService {
    Utilizator salveazaUtilizator(Utilizator user);

    void stergeUtilizator(Long id);

    Utilizator actualizeazaUtilizator(Long id, Utilizator user);

    List<Utilizator> getAllUsers();

    Utilizator getUserById(Long id);

}
