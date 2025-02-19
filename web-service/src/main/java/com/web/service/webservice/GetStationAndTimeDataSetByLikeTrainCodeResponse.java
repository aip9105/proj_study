
package com.web.service.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="getStationAndTimeDataSetByLikeTrainCodeResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;any maxOccurs="2" minOccurs="2"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getStationAndTimeDataSetByLikeTrainCodeResult"
})
@XmlRootElement(name = "getStationAndTimeDataSetByLikeTrainCodeResponse")
public class GetStationAndTimeDataSetByLikeTrainCodeResponse {

    protected GetStationAndTimeDataSetByLikeTrainCodeResponse.GetStationAndTimeDataSetByLikeTrainCodeResult getStationAndTimeDataSetByLikeTrainCodeResult;

    /**
     * 获取getStationAndTimeDataSetByLikeTrainCodeResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link GetStationAndTimeDataSetByLikeTrainCodeResponse.GetStationAndTimeDataSetByLikeTrainCodeResult }
     *     
     */
    public GetStationAndTimeDataSetByLikeTrainCodeResponse.GetStationAndTimeDataSetByLikeTrainCodeResult getGetStationAndTimeDataSetByLikeTrainCodeResult() {
        return getStationAndTimeDataSetByLikeTrainCodeResult;
    }

    /**
     * 设置getStationAndTimeDataSetByLikeTrainCodeResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link GetStationAndTimeDataSetByLikeTrainCodeResponse.GetStationAndTimeDataSetByLikeTrainCodeResult }
     *     
     */
    public void setGetStationAndTimeDataSetByLikeTrainCodeResult(GetStationAndTimeDataSetByLikeTrainCodeResponse.GetStationAndTimeDataSetByLikeTrainCodeResult value) {
        this.getStationAndTimeDataSetByLikeTrainCodeResult = value;
    }


    /**
     * <p>anonymous complex type的 Java 类。
     * 
     * <p>以下模式片段指定包含在此类中的预期内容。
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;any maxOccurs="2" minOccurs="2"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "any"
    })
    public static class GetStationAndTimeDataSetByLikeTrainCodeResult {

        @XmlAnyElement(lax = true)
        protected List<Object> any;

        /**
         * Gets the value of the any property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the any property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAny().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Object }
         * 
         * 
         */
        public List<Object> getAny() {
            if (any == null) {
                any = new ArrayList<Object>();
            }
            return this.any;
        }

    }

}
