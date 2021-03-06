
package com.demskv.webassignmenteditor.organization;


public class OrganizationDTO {
    private Integer id;
    private String name;
    private String physAddress;
    private String yurAddress;
    private Integer director;

    public OrganizationDTO() {
    }
    
    public OrganizationDTO(Integer id, String name, String physAddress, String yurAddress, Integer directorId) {
        this.id = id;
        this.name = name;
        this.physAddress = physAddress;
        this.yurAddress = yurAddress;
        this.director = directorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhysAddress() {
        return physAddress;
    }

    public void setPhysAddress(String physAddress) {
        this.physAddress = physAddress;
    }

    public String getYurAddress() {
        return yurAddress;
    }

    public void setYurAddress(String yurAddress) {
        this.yurAddress = yurAddress;
    }

    public Integer getDirector() {
        return director;
    }

    public void setDirector(Integer director) {
        this.director = director;
    }
    
    
}
