package npl.ist.department.tsp.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Long employeeId;
    private String indentor;
    private int totalQty;
    private String tenderNo;
    private Date purchaseOrderDate;
    private String purchaseOrderNo;
    private Date dateOfDeliveryISTRAC;
    private int quantityISTRAC;
    private Date installationDateISTRAC;
    private Date dateOfDeliveryNPL;
    private int quantityNPL;
    private Date installationDateNPL;
    private Date whiteSlipDate;
    private String whiteSlipNo;
    private String whiteSlipGivenBy;
    private String remarks;
    private String category;

    // Constructors, getters, and setters
}
