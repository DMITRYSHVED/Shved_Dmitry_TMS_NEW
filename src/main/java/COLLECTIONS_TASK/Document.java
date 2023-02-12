package COLLECTIONS_TASK;

import java.util.Objects;

public class Document {

    private String contractNumber;
    private String creationDate;
    private String information;

    public String getContractNumber() {
        return contractNumber;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getInformation() {
        return information;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return "Document{" +
                "contractNumber='" + contractNumber + '\'' +
                ", creationDate=" + creationDate +
                ", information='" + information + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(contractNumber, document.contractNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contractNumber);
    }
}
