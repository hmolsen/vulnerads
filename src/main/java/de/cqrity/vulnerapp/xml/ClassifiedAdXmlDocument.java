package de.cqrity.vulnerapp.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

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
