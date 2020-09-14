package gwf.workflows.data

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "data")
class JaxbXmlData {

    @XmlElement(name = "name")
    String fileName;

    @XmlElement(name = "value")
    String val;

    @Override
    String toString() {
        return "JaxbXmlData{" +
                "name='" + fileName + '\'' +
                ", value='" + val + '\'' +
                '}';
    }
}
