package eu.brokoko.thymely.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "project")
    private Set<Workperiod> workperiods = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("projects")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Project title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Workperiod> getWorkperiods() {
        return workperiods;
    }

    public Project workperiods(Set<Workperiod> workperiods) {
        this.workperiods = workperiods;
        return this;
    }

    public Project addWorkperiod(Workperiod workperiod) {
        this.workperiods.add(workperiod);
        workperiod.setProject(this);
        return this;
    }

    public Project removeWorkperiod(Workperiod workperiod) {
        this.workperiods.remove(workperiod);
        workperiod.setProject(null);
        return this;
    }

    public void setWorkperiods(Set<Workperiod> workperiods) {
        this.workperiods = workperiods;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Project customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
