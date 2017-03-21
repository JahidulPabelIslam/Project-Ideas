/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.ents;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author up733474
 */
@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private String type;

    @NotNull
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = true)
    private String middleName;

    @NotNull
    @Column(nullable = false)
    private String surname;

    @Column(nullable = true)
    private int studentID;

    @Column(nullable = true)
    private String organisationName;

    @Column(nullable = true)
    private String organisationOutline;

    @Column(nullable = true)
    private String organisationAddress;

    @Column(nullable = true)
    private String staffRole;

    @OneToOne(mappedBy = "implementer")
    private Idea implementingIdea;

    @OneToMany(mappedBy = "submitter")
    private List<Idea> ideas;

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the value of email
     *
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the value of email
     *
     * @param email new value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the value of phoneNumber
     *
     * @return the value of phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set the value of phoneNumber
     *
     * @param phoneNumber new value of phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get the value of firstName
     *
     * @return the value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the value of firstName
     *
     * @param firstName new value of firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the value of middleName
     *
     * @return the value of middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Set the value of middleName
     *
     * @param middleName new value of middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Get the value of surname
     *
     * @return the value of surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Set the value of surname
     *
     * @param surname new value of surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Get the value of studentID
     *
     * @return the value of studentID
     */
    public int getStudentID() {
        return studentID;
    }

    /**
     * Set the value of studentID
     *
     * @param studentID new value of studentID
     */
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    /**
     * Get the value of organisationName
     *
     * @return the value of organisationName
     */
    public String getOrganisationName() {
        return organisationName;
    }

    /**
     * Set the value of organisationName
     *
     * @param organisationName new value of organisationName
     */
    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    /**
     * Get the value of organisationOutline
     *
     * @return the value of organisationOutline
     */
    public String getOrganisationOutline() {
        return organisationOutline;
    }

    /**
     * Set the value of organisationOutline
     *
     * @param organisationOutline new value of organisationOutline
     */
    public void setOrganisationOutline(String organisationOutline) {
        this.organisationOutline = organisationOutline;
    }

    /**
     * Get the value of organisationAddress
     *
     * @return the value of organisationAddress
     */
    public String getOrganisationAddress() {
        return organisationAddress;
    }

    /**
     * Set the value of organisationAddress
     *
     * @param organisationAddress new value of organisationAddress
     */
    public void setOrganisationAddress(String organisationAddress) {
        this.organisationAddress = organisationAddress;
    }

    /**
     * Get the value of staffRole
     *
     * @return the value of staffRole
     */
    public String getStaffRole() {
        return staffRole;
    }

    /**
     * Set the value of staffRole
     *
     * @param staffRole new value of staffRole
     */
    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }

    /**
     *
     * @return
     */
    public Idea getImplementingIdea() {
        return implementingIdea;
    }

    /**
     *
     * @param implementingIdea
     */
    public void setImplementingIdea(Idea implementingIdea) {
        this.implementingIdea = implementingIdea;
    }

    /**
     *
     * @return
     */
    public List<Idea> getIdeas() {
        return ideas;
    }

    /**
     *
     * @param ideas
     */
    public void setIdeas(List<Idea> ideas) {
        this.ideas = ideas;
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
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if ("Organisation".equals(this.type) || "Unapproved Organisation".equals(this.type)) {
            return this.organisationName;
        } else {
            return this.firstName + " " + this.surname;
        }
    }

}
