package com.example.model.hibernateInheritance.joined;


import com.example.model.hibernateInheritance.tableperclass.Person1;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="EMPLOYEE_3")
public class Employee3 extends Person3 {

    @Column(name="joining_date")
    private Date joiningDate;

    @Column(name="department_name")
    private String departmentName;


    public Employee3() {
    }

    public Employee3(String firstname, String lastname, String departmentName, Date joiningDate) {

        super(firstname, lastname);

        this.departmentName = departmentName;
        this.joiningDate = joiningDate;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Employee1{" +
                "joiningDate=" + joiningDate +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Employee3 employee1 = (Employee3) o;

        if (joiningDate != null ? !joiningDate.equals(employee1.joiningDate) : employee1.joiningDate != null)
            return false;
        return departmentName != null ? departmentName.equals(employee1.departmentName) : employee1.departmentName == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (joiningDate != null ? joiningDate.hashCode() : 0);
        result = 31 * result + (departmentName != null ? departmentName.hashCode() : 0);
        return result;
    }
}
