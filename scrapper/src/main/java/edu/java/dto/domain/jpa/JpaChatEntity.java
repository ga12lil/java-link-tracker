package edu.java.dto.domain.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"links"})
public class JpaChatEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(name = "subscription",
            joinColumns = @JoinColumn(name = "chat_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "link_id",
                    referencedColumnName = "id"))
    private List<JpaLinkEntity> links = new ArrayList<>();
}
