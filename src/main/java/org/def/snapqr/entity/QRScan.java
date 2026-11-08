package org.def.snapqr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.time.LocalDateTime;

@Entity
@Table(name = "qr_scans",
        indexes = {
            @Index(
                    name = "idx_qr_code_scanned_at",
                    columnList = "qr_code_id, scanned_at"
            )
        }
)
public class QRScans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "qr_code_id",
            nullable = false
    )
    private SnapQR snapQr;

    @Generated(event = EventType.INSERT)
    @Column(
            name = "scanned_at",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private LocalDateTime scannedAt;

    @Column(
            name = "user_agent",
            length = 500
    )
    private String userAgent;

    @Column(
            name = "referer",
            length = 2048
    )
    private String referer;
}
