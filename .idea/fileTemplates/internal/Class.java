#if (${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import java.util.Hashtable;
import java.util.Hashtable;
import java.util.Hashtable;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
#parse("File Header.java")

#if (${VISIBILITY} == "PUBLIC")public #end #if (${ABSTRACT} == "TRUE")abstract #end #if (${FINAL} == "TRUE")final #end class ${NAME} #if (${SUPERCLASS} != "")extends ${SUPERCLASS} #end #if (${INTERFACES} != "")implements ${INTERFACES} #end {
	private static Logger logger = LoggerFactory.getLogger(${NAME}.class);

	// - F I E L D - S E C T I O N

	// - C O N S T R U C T O R - S E C T I O N
	public ${NAME} () {
		super();
	}

	// - G E T T E R S   &   S E T T E R S
	// - M E T H O D - S E C T I O N
	@Override
	public String toString() {
		return new StringBuilder("${NAME} [")
		.append("name: ").append(0)
		.append("]")
		.append("->").append(super.toString())
		.toString();
	}
}
