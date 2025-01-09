package tlu.edu.vn.ht63.htnongnghiep.Model;

import androidx.camera.core.processing.SurfaceProcessorNode;

import java.io.Serializable;

public class InforProductShop implements Serializable {
    private String id;
    private String nameOfTree;
    private String ownerName;
    private String ownerId;
    private String treeImageIntro;
    private String ownerImage;

    public InforProductShop() {

    }

    public InforProductShop(String id, String nameOfTree, String ownerName, String ownerId, String treeImageIntro, String ownerImage) {
        this.id = id;
        this.ownerName = ownerName;
        this.nameOfTree = nameOfTree;
        this.ownerImage = ownerImage;
        this.treeImageIntro = treeImageIntro;
        this.ownerId = ownerId;

    }

    public String getId() {
        return id;
    }

    public String getNameOfTree() {
        return nameOfTree;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getOwnerImage() {
        return ownerImage;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getTreeImageIntro() {
        return treeImageIntro;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNameOfTree(String nameOfTree) {
        this.nameOfTree = nameOfTree;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setOwnerImage(String ownerImage) {
        this.ownerImage = ownerImage;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setTreeImageIntro(String treeImageIntro) {
        this.treeImageIntro = treeImageIntro;
    }
}
