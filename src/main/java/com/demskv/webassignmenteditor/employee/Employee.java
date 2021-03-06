package com.demskv.webassignmenteditor.employee;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * Employee (Сотрудник) entity class.
 * @author demskv
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    /**
     * Фамилия
     */
    @Basic(optional = false)
    @Column(name = "lastname")
    @NotBlank
    private String lastname;
    
    /**
     * Имя
     */
    @Basic(optional = false)
    @Column(name = "firstname")
    @NotBlank
    private String firstname;
    
    /**
     * Отчество
     */
    @Column(name = "middlename")
    private String middlename;
    
    /**
     * Должность
     */
    @Basic(optional = false)
    @Column(name = "position")
    @NotBlank
    private String position;

    public Employee() {
    }

    public Employee(Integer id) {
        this.id = id;
    }

    public Employee(Integer id, String lastname, String firstname, String middlename, String position) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.middlename = middlename;
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.demskv.webassignmenteditor.assignment.Employee[ id=" + id + " ]";
    }

}

