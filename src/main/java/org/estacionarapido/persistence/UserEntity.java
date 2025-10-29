package org.estacionarapido.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "estaciona_user")
@NamedQuery(name = "UserEntities.findByName",
    query = "SELECT anuser FROM UserEntity anuser WHERE anuser.name = :name")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    public int id;

    @Column(name = "name", nullable = false, length = 50)
    public String name;

    // TODO: think if String and Text and the best ways to store and enforce hashes.
    @Column(name = "password_hash", nullable = false)
    public String passwordHash;
}
