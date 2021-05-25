package com.baldede.postman.domain;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlSeeAlso({ Stop.class })
@XmlJavaTypeAdapter(XmlAdapter.class)
public interface Standstill {

    /**
     * @return never null
     */
    Location getLocation();

    /**
     * @param standstill never null
     * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    long getDistanceTo(Standstill standstill);
}
