package de.adorsys.opba.db.domain.entity.fintech;

import de.adorsys.opba.db.domain.entity.Bank;
import de.adorsys.opba.db.domain.entity.psu.Psu;
import de.adorsys.opba.db.domain.generators.AssignedUuidGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FintechPsuAspspPrvKeyInbox {

    @Id
    @GenericGenerator(
            name = AssignedUuidGenerator.ASSIGNED_ID_GENERATOR,
            strategy = AssignedUuidGenerator.ASSIGNED_ID_STRATEGY
    )
    @GeneratedValue(
            generator = AssignedUuidGenerator.ASSIGNED_ID_GENERATOR,
            strategy = GenerationType.AUTO
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Fintech fintech;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Psu psu;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Bank aspsp;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private byte[] encData;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant modifiedAt;
}
