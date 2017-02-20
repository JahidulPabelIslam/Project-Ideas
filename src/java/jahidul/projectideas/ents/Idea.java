/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jahidul.projectideas.ents;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author up733474
 */
@Entity
public class Idea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String title;

    /**
     * Get the value of title
     *
     * @return the value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the value of title
     *
     * @param title new value of title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    private String aimsObjectives;

    /**
     * Get the value of aimsObjectives
     *
     * @return the value of aimsObjectives
     */
    public String getAimsObjectives() {
        return aimsObjectives;
    }

    /**
     * Set the value of aimsObjectives
     *
     * @param aimsObjectives new value of aimsObjectives
     */
    public void setAimsObjectives(String aimsObjectives) {
        this.aimsObjectives = aimsObjectives;
    }

    private String question;

    /**
     * Get the value of question
     *
     * @return the value of question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Set the value of question
     *
     * @param question new value of question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    private String deliverables;

    /**
     * Get the value of deliverables
     *
     * @return the value of deliverables
     */
    public String getDeliverables() {
        return deliverables;
    }

    /**
     * Set the value of deliverables
     *
     * @param deliverables new value of deliverables
     */
    public void setDeliverables(String deliverables) {
        this.deliverables = deliverables;
    }

    private String status;

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateUpdated;

    /**
     * Get the value of date
     *
     * @return the value of date
     */
    public Date getDateUpdated() {
        return dateUpdated;
    }

    /**
     * Set the value of date
     *
     * @param dateUpdated new value of date
     */
    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @ManyToOne
    private Person appliedStudent;

    /**
     * Get the value of appliedStudent
     *
     * @return the value of appliedStudent
     */
    public Person getAppliedStudent() {
        return appliedStudent;
    }

    /**
     * Set the value of appliedStudent
     *
     * @param appliedStudent new value of appliedStudent
     */
    public void setAppliedStudent(Person appliedStudent) {
        this.appliedStudent = appliedStudent;
    }

    @ManyToOne
    private Person submitter;

    /**
     * Get the value of submitterStudent
     *
     * @return the value of submitterStudent
     */
    public Person getSubmitter() {
        return submitter;
    }

    /**
     * Set the value of submitterStudent
     *
     * @param submitter new value of submitterStudent
     */
    public void setSubmitter(Person submitter) {
        this.submitter = submitter;
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
        if (!(object instanceof Idea)) {
            return false;
        }
        Idea other = (Idea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jahidul.projectideas.ents.Idea[ id=" + id + " ]";
    }

}
