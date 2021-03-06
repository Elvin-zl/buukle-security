package top.buukle.security.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created By MBG-GUI-EXTENSION https:// github.com/spawpaw/mybatis-generator-gui-extension
 * Description:
 * 区域
 *
 * @author 
 */
public class Area implements Serializable {
    /**
     * 主键id
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private Integer id;

    /**
     * 上级区域id
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private Integer pid;

    /**
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private String name;

    /**
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private String description;

    /**
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private String remark;

    /**
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private Date gmtCreated;

    /**
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private String creator;

    /**
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private String creatorCode;

    /**
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private Date gmtModified;

    /**
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private String modifier;

    /**
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private String modifierCode;

    /**
     * 状态
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private Integer status;

    /**
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private String bak01;

    /**
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private String bak02;

    /**
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private String bak03;

    /**
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private String bak04;

    /**
     *
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private String bak05;

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method returns the value of the database column buukle-security-new..area.id
     *
     * @return the value of buukle-security-new..area.id
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withId(Integer id) {
        this.setId(id);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.id
     *
     * @param id the value for buukle-security-new..area.id
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.pid
     *
     * @return the value of buukle-security-new..area.pid
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Integer getPid() {
        return pid;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withPid(Integer pid) {
        this.setPid(pid);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.pid
     *
     * @param pid the value for buukle-security-new..area.pid
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.name
     *
     * @return the value of buukle-security-new..area.name
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withName(String name) {
        this.setName(name);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.name
     *
     * @param name the value for buukle-security-new..area.name
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.description
     *
     * @return the value of buukle-security-new..area.description
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withDescription(String description) {
        this.setDescription(description);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.description
     *
     * @param description the value for buukle-security-new..area.description
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.remark
     *
     * @return the value of buukle-security-new..area.remark
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withRemark(String remark) {
        this.setRemark(remark);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.remark
     *
     * @param remark the value for buukle-security-new..area.remark
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.gmt_created
     *
     * @return the value of buukle-security-new..area.gmt_created
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withGmtCreated(Date gmtCreated) {
        this.setGmtCreated(gmtCreated);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.gmt_created
     *
     * @param gmtCreated the value for buukle-security-new..area.gmt_created
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.creator
     *
     * @return the value of buukle-security-new..area.creator
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public String getCreator() {
        return creator;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withCreator(String creator) {
        this.setCreator(creator);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.creator
     *
     * @param creator the value for buukle-security-new..area.creator
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.creator_code
     *
     * @return the value of buukle-security-new..area.creator_code
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public String getCreatorCode() {
        return creatorCode;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withCreatorCode(String creatorCode) {
        this.setCreatorCode(creatorCode);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.creator_code
     *
     * @param creatorCode the value for buukle-security-new..area.creator_code
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.gmt_modified
     *
     * @return the value of buukle-security-new..area.gmt_modified
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withGmtModified(Date gmtModified) {
        this.setGmtModified(gmtModified);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.gmt_modified
     *
     * @param gmtModified the value for buukle-security-new..area.gmt_modified
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.modifier
     *
     * @return the value of buukle-security-new..area.modifier
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public String getModifier() {
        return modifier;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withModifier(String modifier) {
        this.setModifier(modifier);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.modifier
     *
     * @param modifier the value for buukle-security-new..area.modifier
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.modifier_code
     *
     * @return the value of buukle-security-new..area.modifier_code
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public String getModifierCode() {
        return modifierCode;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withModifierCode(String modifierCode) {
        this.setModifierCode(modifierCode);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.modifier_code
     *
     * @param modifierCode the value for buukle-security-new..area.modifier_code
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setModifierCode(String modifierCode) {
        this.modifierCode = modifierCode;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.status
     *
     * @return the value of buukle-security-new..area.status
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withStatus(Integer status) {
        this.setStatus(status);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.status
     *
     * @param status the value for buukle-security-new..area.status
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.bak01
     *
     * @return the value of buukle-security-new..area.bak01
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public String getBak01() {
        return bak01;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withBak01(String bak01) {
        this.setBak01(bak01);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.bak01
     *
     * @param bak01 the value for buukle-security-new..area.bak01
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setBak01(String bak01) {
        this.bak01 = bak01;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.bak02
     *
     * @return the value of buukle-security-new..area.bak02
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public String getBak02() {
        return bak02;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withBak02(String bak02) {
        this.setBak02(bak02);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.bak02
     *
     * @param bak02 the value for buukle-security-new..area.bak02
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setBak02(String bak02) {
        this.bak02 = bak02;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.bak03
     *
     * @return the value of buukle-security-new..area.bak03
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public String getBak03() {
        return bak03;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withBak03(String bak03) {
        this.setBak03(bak03);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.bak03
     *
     * @param bak03 the value for buukle-security-new..area.bak03
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setBak03(String bak03) {
        this.bak03 = bak03;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.bak04
     *
     * @return the value of buukle-security-new..area.bak04
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public String getBak04() {
        return bak04;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withBak04(String bak04) {
        this.setBak04(bak04);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.bak04
     *
     * @param bak04 the value for buukle-security-new..area.bak04
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setBak04(String bak04) {
        this.bak04 = bak04;
    }

    /**
     * This method returns the value of the database column buukle-security-new..area.bak05
     *
     * @return the value of buukle-security-new..area.bak05
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public String getBak05() {
        return bak05;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public Area withBak05(String bak05) {
        this.setBak05(bak05);
        return this;
    }

    /**
     * This method sets the value of the database column buukle-security-new..area.bak05
     *
     * @param bak05 the value for buukle-security-new..area.bak05
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    public void setBak05(String bak05) {
        this.bak05 = bak05;
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pid=").append(pid);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", remark=").append(remark);
        sb.append(", gmtCreated=").append(gmtCreated);
        sb.append(", creator=").append(creator);
        sb.append(", creatorCode=").append(creatorCode);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", modifier=").append(modifier);
        sb.append(", modifierCode=").append(modifierCode);
        sb.append(", status=").append(status);
        sb.append(", bak01=").append(bak01);
        sb.append(", bak02=").append(bak02);
        sb.append(", bak03=").append(bak03);
        sb.append(", bak04=").append(bak04);
        sb.append(", bak05=").append(bak05);
        sb.append("]");
        return sb.toString();
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Area other = (Area) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCreatorCode() == null ? other.getCreatorCode() == null : this.getCreatorCode().equals(other.getCreatorCode()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getModifierCode() == null ? other.getModifierCode() == null : this.getModifierCode().equals(other.getModifierCode()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getBak01() == null ? other.getBak01() == null : this.getBak01().equals(other.getBak01()))
            && (this.getBak02() == null ? other.getBak02() == null : this.getBak02().equals(other.getBak02()))
            && (this.getBak03() == null ? other.getBak03() == null : this.getBak03().equals(other.getBak03()))
            && (this.getBak04() == null ? other.getBak04() == null : this.getBak04().equals(other.getBak04()))
            && (this.getBak05() == null ? other.getBak05() == null : this.getBak05().equals(other.getBak05()));
    }

    /**
     *
     * @mbg.generated Wed Dec 25 20:29:15 CST 2019
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getGmtCreated() == null) ? 0 : getGmtCreated().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreatorCode() == null) ? 0 : getCreatorCode().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getModifierCode() == null) ? 0 : getModifierCode().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getBak01() == null) ? 0 : getBak01().hashCode());
        result = prime * result + ((getBak02() == null) ? 0 : getBak02().hashCode());
        result = prime * result + ((getBak03() == null) ? 0 : getBak03().hashCode());
        result = prime * result + ((getBak04() == null) ? 0 : getBak04().hashCode());
        result = prime * result + ((getBak05() == null) ? 0 : getBak05().hashCode());
        return result;
    }
}