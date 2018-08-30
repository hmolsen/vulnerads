package de.cqrity.vulnerapp.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.cqrity.vulnerapp.domain.ClassifiedAd;

@XmlRootElement(name = "ClassifiedAdImport")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassifiedAdXmlDocument {

    @XmlElement(required = true)
    private ClassifiedAd ad;

    public ClassifiedAd getAd() {
        return ad;
    }

    public void setAd(ClassifiedAd ad) {
        this.ad = ad;
    }

}
