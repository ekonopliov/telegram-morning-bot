package lt.kono.tmb.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.kono.tmb.utils.DateProvider;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String message;

    @Builder.Default
    Date creationTime = DateProvider.getCurrentDate();

    @Builder.Default
    Date deliveryTime =
            DateProvider.addSeconds(DateProvider.getCurrentDate(), 10);

    @Builder.Default
    Boolean delivered = false;

    @NotNull
    Long chatId;
}
