package org.def.snapqr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.time.LocalDateTime;

@Entity
@Table(name = "snap_qr")
public class SnapQR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(
            name = "short_code",
            nullable = false,
            unique = true,
            length = 20
    )
    private String shortcode;

    @NotNull
    @Column(
            nullable = false,
            length = 100
    )
    private String title;

    @NotNull
    @Column(
            name = "destination_url",
            nullable = false,
            length = 2048
    )
    private String destinationUrl;

    @NotNull
    @Column(
            name = "management_token_hash",
            nullable = false,
            unique = true,
            length = 255
    )
    private String managementTokenHash;

    @NotNull
    @Column(
            name = "is_active",
            nullable = false
    )
    private boolean isActive = true;

    @Generated(event = EventType.INSERT)
    @Column(
            name = "created_at",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @Generated(
            event = {
                    EventType.INSERT,
                    EventType.UPDATE
            }
    )
    @Column(
            name = "updated_at",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private LocalDateTime updatedAt;
}
