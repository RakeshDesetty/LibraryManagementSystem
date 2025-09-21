package Services;
import PatronModels.Patron;

import java.util.Collection;
import java.util.Map;

public interface IMemberService {

    public void addMember(Patron p);
    public void removeMember(Long patronId);
    public void updateMember(Patron p);
    public Patron getById(Long id);
    public Collection<Patron> listAllPatrons();

    public boolean isEligibleForBookCheckout(String Isbn, Long Id);

}
